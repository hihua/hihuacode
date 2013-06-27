#include "AudioPlayer.h"

typedef struct _SCSI_INQUIRY_STD_DATA
{
	UCHAR DeviceType : 5;	
	UCHAR Qualifier : 3;
	UCHAR Reserved : 7;
	UCHAR Rmb : 1;
	UCHAR Version;
	UCHAR Format;
	UCHAR AdditionalLength;
	UCHAR SCCSReserved;
	UCHAR Flags1;
	UCHAR Flags2;
	char VendorId[8];
	char ProductId[16];
	char ProductLevel[4];
} SCSI_INQUIRY_STD_DATA;

typedef struct _CDB_INQUIRY 
{
	UCHAR OperationCode;
	UCHAR Reserved1 : 5;
	UCHAR LogicalUnitNumber : 3;
	UCHAR PageCode;
	UCHAR Reserved2;
	UCHAR AllocationLength;
	UCHAR Control;
} CDB_INQUIRY;

typedef struct _CD_WAVEFORMAT
{
	MMCKINFO info;
	PCMWAVEFORMAT format;
	DWORD data;
	DWORD size;
} CD_WAVEFORMAT;

wchar_t* CDROMName(const wchar_t* driver);
void GetCDTrack(CDROM* cdrom);
DWORD GetCDStart(const CDROM_TOC& toc, UCHAR track);
DWORD GetCDEnd(const CDROM_TOC& toc, UCHAR track);
BOOL ReadCDTrack(const HANDLE& hfile, DWORD start, DWORD count, void* buffer, DWORD size, DWORD& out);
int ReadCDPacket(void* opaque, uint8_t* buf, int buf_size);
int64_t SeekCDPacket(void *opaque, int64_t offset, int whence);
BOOL CDTrackInit(CDTRACK* cdtrack);
void CDTrackRelease(CDTRACK* cdtrack);