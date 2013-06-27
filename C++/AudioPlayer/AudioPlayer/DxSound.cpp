#include "stdafx.h"
#include "DxSound.h"
#include "Decode.h"
#include "Spectrum.h"
#include "ClockInfo.h"
#include "SongList.h"
#include "Lyric.h"
#include "Dsp.h"

LPDIRECTSOUND dsound;
//HANDLE hFile;

BOOL DxSoundInit(const HWND* hWnd)
{	
	HRESULT hr = DirectSoundCreate(NULL, &dsound, NULL);
	if (SUCCEEDED(hr))
	{		
		if (hWnd == NULL)
		{
			hr = dsound->SetCooperativeLevel(GetDesktopWindow(), DSSCL_NORMAL);
			if (SUCCEEDED(hr))	
				return TRUE;
			else
				return FALSE;
		}
		else
		{
			hr = dsound->SetCooperativeLevel(*hWnd, DSSCL_NORMAL);
			if (SUCCEEDED(hr))	
				return TRUE;
			else
				return FALSE;
		}
	}
	else
		return FALSE;
}

DWORD GetDistance(DWORD p1, DWORD p2, DWORD size)
{		
	if (p1 < p2)
		return p2 - p1;
	else			
		return size - p1 + p2;	
}

BOOL DxBufferInit(PLAYERINFO* playerinfo)
{	
	PLAYERDX* dx = &playerinfo->dx;
	LPDIRECTSOUNDBUFFER dbuffer = NULL;
	PWAVEFORMATEXTENSIBLE waveformatEx = &dx->waveformatEx;
	PWAVEFORMATEX waveformat = &waveformatEx->Format;
	DWORD total = waveformat->nAvgBytesPerSec * 4;
	DWORD size = waveformat->nAvgBytesPerSec;
	
	ZeroMemory(&dx->desc, sizeof(DSBUFFERDESC));
	dx->desc.dwSize = sizeof(DSBUFFERDESC);
	dx->desc.dwFlags = DSBCAPS_GLOBALFOCUS | DSBCAPS_CTRLPOSITIONNOTIFY | DSBCAPS_GETCURRENTPOSITION2 | DSBCAPS_CTRLVOLUME;
	dx->desc.lpwfxFormat = waveformat;
	dx->desc.dwBufferBytes = total;
	
	waveformat->wFormatTag = WAVE_FORMAT_EXTENSIBLE;
	waveformat->cbSize = sizeof(WAVEFORMATEXTENSIBLE) - sizeof(WAVEFORMATEX);
	waveformatEx->Samples.wValidBitsPerSample = waveformat->wBitsPerSample;
	waveformatEx->dwChannelMask = SPEAKER_FRONT_LEFT | SPEAKER_FRONT_RIGHT;
	waveformatEx->SubFormat = KSDATAFORMAT_SUBTYPE_PCM;

	switch (waveformat->nChannels)
	{
	case 8:
		waveformatEx->dwChannelMask |= SPEAKER_FRONT_LEFT_OF_CENTER | SPEAKER_FRONT_RIGHT_OF_CENTER;
		waveformatEx->dwChannelMask |= SPEAKER_FRONT_CENTER			| SPEAKER_LOW_FREQUENCY;
		waveformatEx->dwChannelMask |= SPEAKER_BACK_LEFT			| SPEAKER_BACK_RIGHT;
		break;

	case 7:
		waveformatEx->dwChannelMask |= SPEAKER_FRONT_LEFT_OF_CENTER | SPEAKER_FRONT_RIGHT_OF_CENTER;
		waveformatEx->dwChannelMask |= SPEAKER_LOW_FREQUENCY;
		waveformatEx->dwChannelMask |= SPEAKER_BACK_LEFT			| SPEAKER_BACK_RIGHT;
		break;

	case 6:
		waveformatEx->dwChannelMask |= SPEAKER_FRONT_CENTER | SPEAKER_LOW_FREQUENCY;
		waveformatEx->dwChannelMask |= SPEAKER_BACK_LEFT	| SPEAKER_BACK_RIGHT;	
		break;

	case 4:
		waveformatEx->dwChannelMask |= SPEAKER_BACK_LEFT | SPEAKER_BACK_RIGHT;
		break;

	default:
		break;
	}

	HRESULT hr = dsound->CreateSoundBuffer(&dx->desc, &dbuffer, NULL);
	if (FAILED(hr))			
		return FALSE;
	
	hr = dbuffer->SetCurrentPosition(0);
	if (FAILED(hr))
		return FALSE;

	hr = dbuffer->SetVolume(DSBVOLUME_MAX);
	if (FAILED(hr))			
		return FALSE;

	char* buffer = new char[total];
	playerinfo->buffer.buffer = buffer;
	playerinfo->buffer.total = total;
	//playerinfo->buffer.half = size;
	playerinfo->buffer.half = total / 2;

	buffer = new char[size];
	playerinfo->temp.buffer = buffer;
	playerinfo->temp.total = size;
	playerinfo->temp.half = size / waveformat->nBlockAlign;

	dx->dbuffer = dbuffer;
	return TRUE;
}

void DxPlay(PLAYERINFO* playerinfo, PLAYERSTATUS* status)
{
	//hFile = CreateFile(L"D:\\tmp.txt", GENERIC_WRITE | GENERIC_READ, 0, NULL, CREATE_NEW, FILE_ATTRIBUTE_NORMAL, NULL);
	BOOL inited = FALSE;
	DWORD offset = 0, index = 0, play = 0, write = 0;
	PLAYERDX* dx = &playerinfo->dx;
	LPDIRECTSOUNDBUFFER dbuffer = dx->dbuffer;
	PWAVEFORMATEXTENSIBLE waveformatEx = &dx->waveformatEx;
	PWAVEFORMATEX waveformat = &waveformatEx->Format;
	PLAYERBUFFER* buffer = &playerinfo->buffer;
	PLAYERBUFFER* temp = &playerinfo->temp;
	PLAYERDECODE* decode = &playerinfo->decode;
	HRESULT hr = S_FALSE;
	DWORD total = 0, last = 0, sum = 0;
	int channels = playerinfo->decode.channels;
	int bits = playerinfo->decode.bits;
		
	while (*status == ID_STATUS_START || *status == ID_STATUS_PAUSE)
	{			
		if (playerinfo->seek >= 0)
		{				
			DWORD start = timeGetTime();			
			total = (DWORD)((double)playerinfo->seek / 1000.0f * (double)waveformat->nAvgBytesPerSec);
			sum = total;
			DspFlush(temp->buffer, temp->total);

			if (!DecodeSeek(decode, playerinfo->seek) || !DxSeek(offset, index, last, sum, temp, buffer, dbuffer, decode)) 
			{
				TrackLock(FALSE);
				break;
			}
			
			playerinfo->ms = playerinfo->seek;
			LyricInfoReset(playerinfo->seek, start, playerinfo);
			ClockInfoRefresh();			
			playerinfo->seek = -1;
			TrackLock(FALSE);
			continue;
		}
		
		if (*status == ID_STATUS_PAUSE)
		{
			Sleep(100);
			continue;
		}
		else
		{
			if (!inited)
			{
				sum += DxSetBuffer(offset, index, temp, buffer, dbuffer, decode);
				if (sum == 0)
					break;

				SpectrumSetPcm(buffer->buffer, channels, bits);
				SpectrumDraw();
				
				hr = dbuffer->Play(0, 0, DSBPLAY_LOOPING);
				if (FAILED(hr))
					break;

				playerinfo->playing = TRUE;
				RefreshItem(playerinfo->n);
				ClockInfoStart();
				SpectrumStart();				
				inited = TRUE;
			}
			else
			{
				//DWORD cc = 0;
				//char b[128] = {0};
				
				hr = dbuffer->GetCurrentPosition(&play, &write);
				if (FAILED(hr))
					break;
				
				total += GetDistance(last, play, buffer->total);
				//sprintf(b, "last:%d\r\n", last);
				//WriteFile(hFile, b, strlen(b), &cc, NULL);
				last = play;
				//sprintf(b, "total:%d\r\n", total);
				//WriteFile(hFile, b, strlen(b), &cc, NULL);
				//sprintf(b, "play:%d\r\n", play);
				//WriteFile(hFile, b, strlen(b), &cc, NULL);
				playerinfo->ms = (int)((double)total / (double)waveformat->nAvgBytesPerSec * 1000);
				TrackProgress(playerinfo->ms, playerinfo->duration);
				if (total < sum)
				{
					//DWORD a = GetDistance(play, index, buffer->total);
					//sprintf(b, "distance:%d/%d\r\n", a, buffer->total);
					//WriteFile(hFile, b, strlen(b), &cc, NULL);
					if (GetDistance(play, index, buffer->total) < buffer->half)
						sum += DxSetBuffer(offset, index, temp, buffer, dbuffer, decode);

					//sprintf(b, "sum:%d\r\n", sum);
					//WriteFile(hFile, b, strlen(b), &cc, NULL);
					//const char* ccc = "--------------------\r\n";
					//WriteFile(hFile, ccc, strlen(ccc), &cc, NULL);		
				}
				else
					break;
			}	
		}		

		Sleep(30);
	}
		
	DspFlush(temp->buffer, temp->total);
	//CloseHandle(hFile);
}

BOOL DxSeek(DWORD& offset, DWORD& index, DWORD& last, DWORD& sum, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode)
{	
	DWORD play = 0, write = 0;
	HRESULT hr = dbuffer->GetCurrentPosition(&play, &write);
	if (FAILED(hr))
		return FALSE;

	offset = play;
	index = play;
	last = play;
	sum += DxSetBuffer(offset, index, temp, buffer, dbuffer, decode);
	return TRUE;	
}

DWORD DxSetBuffer(DWORD& offset, DWORD& index, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode)
{
	DWORD count = 0;
	if (DecodeFrame(temp->buffer, temp->total, count, decode) && count > 0)
	{		
		DWORD cc = 0;
		UINT left = 0;
		LPVOID in = temp->buffer;
		DWORD size = 0;
		BOOL status = FALSE;
		DWORD sum = 0;

		if (count < temp->total)
		{
			ZeroMemory(temp + count, temp->total - count);
			count = temp->total;
		}
				
		do 
		{			
			status = DspProcess(in, count, buffer->buffer + offset, buffer->total - offset, size, left, status);			
			/*char b[128] = {0};
			sprintf(b, "offset:%d\r\n", offset);
			WriteFile(hFile, b, strlen(b), &cc, NULL);
			sprintf(b, "incount:%d\r\n", count);
			WriteFile(hFile, b, strlen(b), &cc, NULL);
			sprintf(b, "buffer->left:%d/%d\r\n", buffer->total - offset, buffer->total);
			WriteFile(hFile, b, strlen(b), &cc, NULL);
			sprintf(b, "size:%d\r\n", size);
			WriteFile(hFile, b, strlen(b), &cc, NULL);
			sprintf(b, "left:%d\r\n", left);
			WriteFile(hFile, b, strlen(b), &cc, NULL);*/

			if (status)
			{
				in = NULL;
				count = 0;
			}
			else
			{
				if (left > 0)
				{
					in = temp->buffer + size;
					count -= size;					
				}				
			}

			if (size > 0)
			{
				DWORD total = size, p = 0;
				while (size > 0)
				{
					LPVOID ptr1 = NULL, ptr2 = NULL;
					DWORD count1 = 0, count2 = 0;
					
					if (!DxLockBuffer(index, size, &ptr1, count1, &ptr2, count2, dbuffer))
						return 0;

					//sprintf(b, "count1:%d\r\n", count1);
					//WriteFile(hFile, b, strlen(b), &cc, NULL);
					
					CopyMemory(ptr1, buffer->buffer + offset + p, count1);
					sum += count1;
					p += count1;
					size -= count1;
					index += count1;
					index %= buffer->total;

					//sprintf(b, "index1:%d/%d\r\n", index, buffer->total);
					//WriteFile(hFile, b, strlen(b), &cc, NULL);

					if (size > 0 && count2 > 0)
					{	
						//sprintf(b, "count2:%d\r\n", count2);
						//WriteFile(hFile, b, strlen(b), &cc, NULL);

						CopyMemory(ptr2, buffer->buffer + offset + p, count2);
						sum += count2;
						p += count2;
						size -= count2;
						index = count2;		

						//sprintf(b, "index2:%d/%d\r\n", index, count2);
						//WriteFile(hFile, b, strlen(b), &cc, NULL);
					}

					if (!DxUnlockBuffer(ptr1, count1, ptr2, count2, dbuffer))
						return 0;
				}				
								
				offset += total;
				offset %= buffer->total;
			}
			else
				return 0;							
		} while (left > 0);

		return sum;
	}
	else
		return 0;
}

BOOL DxLockBuffer(DWORD& offset, DWORD count, LPVOID* ptr1, DWORD& count1, LPVOID* ptr2, DWORD& count2, LPDIRECTSOUNDBUFFER dbuffer)
{
	HRESULT hr = dbuffer->Lock(offset, count, ptr1, &count1, ptr2, &count2, 0);
	if (hr == DSERR_BUFFERLOST)
	{
		hr = dbuffer->Restore();
		if (FAILED(hr))
		{
			hr = dbuffer->Unlock(ptr1, count1, ptr2, count2);
			return FALSE;
		}

		hr = dbuffer->Lock(offset, count, ptr1, &count1, ptr2, &count2, 0);		
	}

	if (SUCCEEDED(hr) && count1 > 0)
		return TRUE;
	else
		return FALSE;
}

BOOL DxUnlockBuffer(LPVOID ptr1, DWORD count1, LPVOID ptr2, DWORD count2, LPDIRECTSOUNDBUFFER dbuffer)
{
	HRESULT hr = dbuffer->Unlock(ptr1, count1, ptr2, count2);
	if (SUCCEEDED(hr))
		return TRUE;
	else
		return FALSE;
}

BOOL DxGetBuffer(DWORD length, char* dest, PLAYERINFO* playerinfo)
{
	PLAYERDX* dx = &playerinfo->dx;
	PLAYERBUFFER* buffer = &playerinfo->buffer;
	DWORD total = playerinfo->buffer.total;
	LPDIRECTSOUNDBUFFER dbuffer = dx->dbuffer;
	if (buffer->buffer != NULL && dbuffer != NULL)
	{
		DWORD play = 0, write = 0;
		HRESULT hr = dbuffer->GetCurrentPosition(&play, &write);
		if (FAILED(hr))
			return FALSE;

		ZeroMemory(dest, length);

		if (play + length > total)
		{
			DWORD len = total - play;
			CopyMemory(dest, buffer->buffer + play, len);		
			CopyMemory(dest + len, buffer->buffer, length - len);			
		}
		else		
			CopyMemory(dest, buffer->buffer + play, length);	

		return TRUE;
	}
	else
		return FALSE;
}

void DxSoundRelease()
{
	SAFE_RELEASE(dsound);
}

void DxBufferRelease(PLAYERINFO* playerinfo)
{	
	if (playerinfo->dx.dbuffer != NULL)
	{
		playerinfo->dx.dbuffer->Stop();
		SAFE_RELEASE(playerinfo->dx.dbuffer);
	}

	SAFE_DELETE_ARRAY(playerinfo->buffer.buffer);
	playerinfo->buffer.half = 0;
	playerinfo->buffer.total = 0;

	SAFE_DELETE_ARRAY(playerinfo->temp.buffer);
	playerinfo->temp.half = 0;
	playerinfo->temp.total = 0;	
}