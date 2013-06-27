#include "stdafx.h"
#include "Cdrom.h"
#include "Util.h"
#include "SongList.h"
#include "DxSound.h"

wchar_t* CDROMName(const wchar_t* driver)
{
	wchar_t* name = NULL;
	HANDLE hfile = CreateFile(driver, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, NULL, OPEN_EXISTING, 0, NULL);
	if (hfile != INVALID_HANDLE_VALUE)
	{					
		CDB_INQUIRY inquiry = {0};
		SCSI_INQUIRY_STD_DATA data = {0};
		inquiry.AllocationLength = sizeof(SCSI_INQUIRY_STD_DATA);
		inquiry.OperationCode = 0x12;

		UCHAR cmd[sizeof(SCSI_PASS_THROUGH_DIRECT) + 96] = {0};
		SCSI_PASS_THROUGH_DIRECT* pcmd = (SCSI_PASS_THROUGH_DIRECT*)cmd;
		CopyMemory(pcmd->Cdb, &inquiry, sizeof(CDB_INQUIRY));
		
		pcmd->DataBuffer = &data;
		pcmd->DataTransferLength = sizeof(SCSI_INQUIRY_STD_DATA);
		pcmd->DataIn = SCSI_IOCTL_DATA_IN;
		pcmd->CdbLength = sizeof(CDB_INQUIRY);
		pcmd->Length = sizeof(SCSI_PASS_THROUGH_DIRECT);
		pcmd->SenseInfoLength = sizeof(cmd) - sizeof(SCSI_PASS_THROUGH_DIRECT);
		pcmd->SenseInfoOffset = sizeof(SCSI_PASS_THROUGH_DIRECT);
		pcmd->TimeOutValue = 6000;

		DWORD returned = 0;
		BOOL ret = DeviceIoControl(hfile, IOCTL_SCSI_PASS_THROUGH_DIRECT, (LPVOID)&cmd, sizeof(cmd), (LPVOID)&cmd, sizeof(cmd), &returned, NULL);
		if (ret)
		{
			DWORD length = sizeof(data.VendorId) + sizeof(data.ProductId);
			name = new wchar_t[length + 1];
			wmemset(name, 0, length + 1);
			if (!CharToWideChar(data.VendorId, length, name, length))
			{
				SAFE_DELETE_ARRAY(name);
			}
		}
	}

	CloseHandle(hfile);
	return name;
}

void GetCDTrack(CDROM* cdrom)
{	
	HANDLE hfile = CreateFile(cdrom->driver, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, NULL, OPEN_EXISTING, 0, NULL);
	if (hfile != INVALID_HANDLE_VALUE)
	{
		DWORD returned = 0;
		CDROM_TOC toc = {0};
		BOOL ret = DeviceIoControl(hfile, IOCTL_CDROM_READ_TOC, NULL, 0, &toc, sizeof(CDROM_TOC), &returned, NULL);
		if (ret)
		{
			for (UCHAR i = toc.FirstTrack;i <= toc.LastTrack;i++)
			{
				DWORD sector_start = GetCDStart(toc, i);
				DWORD sector_end = GetCDEnd(toc, i);
				SongListCD(cdrom->letter, i, cdrom->driver, sector_start, sector_end);				
			}
		}		
	}	

	CloseHandle(hfile);
}

DWORD GetCDStart(const CDROM_TOC& toc, UCHAR track)
{
	return (toc.TrackData[track - 1].Address[1] * 60 * 75 + toc.TrackData[track - 1].Address[2] * 75 + toc.TrackData[track - 1].Address[3]) - 150;
}

DWORD GetCDEnd(const CDROM_TOC& toc, UCHAR track)
{
	return (toc.TrackData[track].Address[1] * 60 * 75 + toc.TrackData[track].Address[2] * 75 + toc.TrackData[track].Address[3]) - 151;
}

BOOL ReadCDTrack(const HANDLE& hfile, DWORD start, DWORD count, void* buffer, DWORD size, DWORD& out)
{
	RAW_READ_INFO raw = {0};	
	raw.TrackMode = CDDA;
	raw.SectorCount = count;
	raw.DiskOffset.QuadPart = start;
	BOOL ret = DeviceIoControl(hfile, IOCTL_CDROM_RAW_READ, &raw, sizeof(RAW_READ_INFO), buffer, size, &out, NULL);
	if (ret)	
		return TRUE;
	else
		return FALSE;
}

int ReadCDPacket(void* opaque, uint8_t* buf, int buf_size)
{	
	int total = 0;
	int left = buf_size;
	char* ptr = (char*)buf;
	CDTRACK* cdtrack = (CDTRACK*)opaque;
	PLAYERBUFFER* cdbuffer = &cdtrack->cdbuffer;
	
	if (cdtrack->header)
	{
		if (left < sizeof(CD_WAVEFORMAT))
			return 0;

		CD_WAVEFORMAT* waveformat = (CD_WAVEFORMAT*)ptr;
		MMCKINFO* info = &waveformat->info;
		info->ckid = mmioFOURCC('R', 'I', 'F', 'F');
		info->cksize = cdtrack->total_length + sizeof(CD_WAVEFORMAT) - 8;
		info->fccType = mmioFOURCC('W', 'A', 'V', 'E');
		info->dwDataOffset = mmioFOURCC('f', 'm', 't', ' ');
		info->dwFlags = 0x10;

		PCMWAVEFORMAT* format = &waveformat->format;
		format->wf.wFormatTag = WAVE_FORMAT_PCM;
		format->wf.nChannels = 2;
		format->wf.nSamplesPerSec = CD_SAMPLE;
		format->wf.nBlockAlign = CD_ALIGN;
		format->wf.nAvgBytesPerSec = CD_BYTES_SECOND;
		format->wBitsPerSample = 0x10;

		waveformat->data = mmioFOURCC('d', 'a', 't', 'a');
		waveformat->size = cdtrack->total_length;

		left -= sizeof(CD_WAVEFORMAT);
		ptr += sizeof(CD_WAVEFORMAT);
		total += sizeof(CD_WAVEFORMAT);
		cdtrack->header = FALSE;
	}
	
	if (cdtrack->left == 0 && cdtrack->start < cdtrack->sector_end)
	{	
		DWORD count = cdbuffer->total / RAW_SECTOR_SIZE;
		count = ((cdtrack->start + count) < cdtrack->sector_end) ? count : (cdtrack->sector_end - cdtrack->start + 1);
		
		if (ReadCDTrack(cdtrack->hfile, cdtrack->start * CD_READ_SIZE, count, cdbuffer->buffer, cdbuffer->total, cdbuffer->half))
		{
			cdtrack->left = cdbuffer->half;
			cdtrack->start += count;
		}
	}
		
	if (left > 0 && cdtrack->left > 0)
	{
		DWORD start = cdbuffer->half - cdtrack->left;
		void* src = cdbuffer->buffer + start;		
		if (cdtrack->left > left)
		{
			CopyMemory(ptr, src, left);
			total += left;
			cdtrack->left -= left;			
		}
		else
		{
			CopyMemory(ptr, src, cdtrack->left);
			total += cdtrack->left;
			cdtrack->left = 0;			
		}		
	}
	
	return total;
}

int64_t SeekCDPacket(void *opaque, int64_t offset, int whence)
{
	if (whence != 0)
		return offset;
			
	CDTRACK* cdtrack = (CDTRACK*)opaque;	
	cdtrack->start = cdtrack->sector_start + offset / RAW_SECTOR_SIZE;
	cdtrack->left = 0;	
	return offset;
}

BOOL CDTrackInit(CDTRACK* cdtrack)
{	
	PLAYERBUFFER* cdbuffer = &cdtrack->cdbuffer;
	cdbuffer->total = RAW_SECTOR_SIZE * 25;
	cdbuffer->half = 0;
	cdbuffer->buffer = new char[cdbuffer->total];
		
	cdtrack->header = TRUE;
	cdtrack->start = cdtrack->sector_start;
	cdtrack->left = 0;
	cdtrack->hfile = CreateFile(cdtrack->driver, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, NULL, OPEN_EXISTING, 0, NULL);
	if (cdtrack->hfile != INVALID_HANDLE_VALUE)
		return TRUE;
	else
		return FALSE;
}

void CDTrackRelease(CDTRACK* cdtrack)
{
	if (cdtrack != NULL)
	{
		cdtrack->header = TRUE;
		cdtrack->start = 0;
		cdtrack->left = 0;
				
		SAFE_DELETE_ARRAY(cdtrack->cdbuffer.buffer);
		cdtrack->cdbuffer.half = 0;
		cdtrack->cdbuffer.total = 0;
		
		if (cdtrack->hfile != NULL)
		{
			CloseHandle(cdtrack->hfile);
			cdtrack->hfile = NULL;
		}
	}	
}

