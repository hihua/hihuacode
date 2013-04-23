#include "AudioPlayer.h"

ATOM SpectrumReg(HINSTANCE hInstance, TCHAR* szWindowClass);
SPECTRUM* SpectrumInit(const RECT* rect, const MAINWND* main_wnd);
BOOL SpectrumGradient(HDC* hdc, const RECT* rect, const DWORD* color, int num, DWORD dwMode);
void SpectrumStart();
void SpectrumStop();
void SpectrumExit();
void SpectrumSetFPS(DWORD fps);
void SpectrumClear();
void SpectrumArrayReset();
void SpectrumSetPcm(char* buffer, int channels, int bits);
void SpectrumBuffer();
void SpectrumDraw();
LRESULT CALLBACK SpectrumProc(HWND, UINT, WPARAM, LPARAM);
DWORD WINAPI SpectrumThread(LPVOID param);