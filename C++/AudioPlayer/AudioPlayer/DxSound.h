#include "AudioPlayer.h"

BOOL DxSoundInit(const HWND* hWnd);
BOOL DxBufferInit(PLAYERINFO* playerinfo);
DWORD GetDistance(DWORD p1, DWORD p2, DWORD size);
void DxPlay(PLAYERINFO* playerinfo, PLAYERSTATUS* status);
BOOL DxSeek(DWORD& offset, DWORD& index, DWORD& last, DWORD& left, DWORD& sum, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode);
DWORD DxSetBuffer(DWORD& offset, DWORD& index, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode);
BOOL DxLockBuffer(DWORD& offset, DWORD count, LPVOID* ptr1, DWORD& count1, LPVOID* ptr2, DWORD& count2, LPDIRECTSOUNDBUFFER dbuffer);
BOOL DxUnlockBuffer(LPVOID ptr1, DWORD count1, LPVOID ptr2, DWORD count2, LPDIRECTSOUNDBUFFER dbuffer);
BOOL DxGetBuffer(DWORD length, char* desc, PLAYERINFO* playerinfo);
void DxSoundRelease();
void DxBufferRelease(PLAYERINFO* playerinfo);

DWORD DxSetBuffer(LPVOID in, DWORD count, DWORD& offset, DWORD& index, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer);
DWORD DxSetFrame(DWORD& offset, DWORD& index, DWORD& left, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode);
void SetBuffer(LPVOID buffer, LPVOID* out1, DWORD& out_count1, LPVOID* out2, DWORD& out_count2, DWORD offset, DWORD size, DWORD total);