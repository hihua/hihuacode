#include "AudioPlayer.h"

#define HTTPHEADER L"Referer: http://ttlrcct.qianqian.com/\r\nAccept: image/gif, image/x-xbitmap, image/jpg, image/pjpeg, text/html, text/xml, */*\r\nUser-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\r\nConnection: close\r\nCache-Control: no-cache\r\n"

ATOM LyricReg(HINSTANCE hInstance, TCHAR* szWindowClass);
LYRICWMD* LyricInit(const MAINWND* main_wnd);
void LyricShow(BOOL show);
HBITMAP CreateBitMap(const HDC* dc, const LONG width, const LONG height, PVOID* bits);
void LyricSetDelay(LYRICINFO* lyricinfo, int delay);
void LyricStart();
void LyricStop();
void LyricExit();
LOGFONT* LyricGetFont();
void LyricSetFont();
COLORREF* LyricGetColor(BOOL foreground);
LYRIC* GetLyric(const wchar_t* tag_artist, const wchar_t* tag_title, HTTPREQ* req);
LYRIC* ExcuteLyric(const char* body);
LYRIC* SelectLyric(PLAYERINFO* playerinfo);
FLOAT Compare(const wchar_t* src, const wchar_t* dest);
FLOAT Compares(const wchar_t* src, const wchar_t* dest);
int SetCode(const LYRIC* lyric);
LYRICINFO* SetLyricInfo(const LYRIC* lyric, HTTPREQ* req);
LYRICINFO* ExcuteLyricInfo(const char* body);
LYRICINFO* InsertLyricInfo(int ms, const char* content, LYRICINFO* first);
void LyricReset(LYRIC* lyric, PLAYERINFO* playerinfo);
void LyricClear(LYRIC* lyric);
void LyricClear(PLAYERINFO* playerinfo);
void LyricInfoReset(LYRICINFO* lyricinfo, LYRIC* lyric, PLAYERINFO* playerinfo, BOOL redraw);
void LyricInfoReset(int seek, DWORD start, PLAYERINFO* playerinfo);
void LyricInfoTime(int ms, const PLAYERINFO* playerinfo);
void LyricInfoReStart(const PLAYERINFO* playerinfo);
void LyricInfoClear(LYRICINFO* lyricinfo);
void LyricHwndClear();
void LyricDraw(PLAYERINFO* playerinfo);
LRESULT CALLBACK LyricProc(HWND, UINT, WPARAM, LPARAM);
DWORD WINAPI LyricThread(LPVOID param);
