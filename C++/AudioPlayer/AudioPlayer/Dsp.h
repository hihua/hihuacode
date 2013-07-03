#include "AudioPlayer.h"

BOOL DspInit(const MAINWND* main_wnd);
void DspSetStatus(BOOL status);
BOOL DspGetStatus();
void DspSet(DWORD sampleRate, DWORD channels, WORD align);
BOOL DspProcess(LPVOID in, UINT in_count, LPVOID out, UINT out_count, DWORD& size, UINT& left, BOOL status);
void DspFlush(char* out, DWORD size);
void SoundTouchSet(DWORD sampleRate, DWORD channels, WORD align);
UINT SoundTouchProcess(SOUNDTOUCH* soundtouch, LPVOID in, UINT in_count, LPVOID out, UINT out_count, DWORD& size);
UINT SoundTouchProcess(SOUNDTOUCH* soundtouch, UINT num, LPVOID out, UINT out_count, DWORD& size);
void SoundTouchFlush(char* out, DWORD size);
BOOL SoundTouchDialogInit(const MAINWND* main_wnd);
void SoundTouchShowDialog(BOOL show);
void SoundTouchSet(int n, const wchar_t* im, const wchar_t* re);
int CALLBACK SoundTouchProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);

DWORD DspInput(LPVOID in, UINT in_count);
void DspOutput(LPVOID in, UINT in_count, LPVOID out1, UINT out_count1, LPVOID out2, UINT out_count2);
UINT SoundTouchSamples(SOUNDTOUCH* soundtouch);
UINT SoundTouchInput(SOUNDTOUCH* soundtouch, LPVOID in, UINT in_count);
void SoundTouchOutput(SOUNDTOUCH* soundtouch, LPVOID out, UINT out_count);