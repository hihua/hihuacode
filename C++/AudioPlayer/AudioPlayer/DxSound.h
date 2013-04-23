#include "AudioPlayer.h"

BOOL DxSoundInit(const HWND* hWnd);
BOOL DxBufferInit(PLAYERINFO* playerinfo);
DWORD GetDistance(DWORD p1, DWORD p2, DWORD size);
void DxPlay(PLAYERINFO* playerinfo, PLAYERSTATUS* status);
BOOL DxSeek(DWORD& offset, DWORD& index, DWORD& last, DWORD& sum, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode);
DWORD DxSetBuffer(DWORD& offset, DWORD& index, PLAYERBUFFER* temp, PLAYERBUFFER* buffer, LPDIRECTSOUNDBUFFER dbuffer, PLAYERDECODE* decode);
BOOL DxLockBuffer(DWORD& offset, DWORD count, LPVOID* ptr1, DWORD& count1, LPVOID* ptr2, DWORD& count2, LPDIRECTSOUNDBUFFER dbuffer);
BOOL DxUnlockBuffer(LPVOID ptr1, DWORD count1, LPVOID ptr2, DWORD count2, LPDIRECTSOUNDBUFFER dbuffer);
BOOL DxGetBuffer(DWORD length, char* desc, PLAYERINFO* playerinfo);
void DxSoundRelease();
void DxBufferRelease(PLAYERINFO* playerinfo);