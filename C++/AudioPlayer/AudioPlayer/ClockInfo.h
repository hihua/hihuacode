#include "AudioPlayer.h"

ATOM ClockInfoReg(HINSTANCE hInstance, TCHAR* szWindowClass);
CLOCKINFO* ClockInfoInit(const RECT* rect, const MAINWND* main_wnd);
void ClockInfoStart();
void ClockInfoStop();
void ClockInfoExit();
void ClockInfoDraw();
void ClockInfoRefresh();
LRESULT CALLBACK ClockInfoProc(HWND, UINT, WPARAM, LPARAM);
DWORD WINAPI ClockInfoThread(LPVOID param);