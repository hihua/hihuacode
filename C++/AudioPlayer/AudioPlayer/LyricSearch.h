#include "AudioPlayer.h"

enum LYRICSEARCH_CMD { ID_CMD_LYRIC = WM_USER + 1, ID_CMD_LYRICINFO };

LYRICSEARCH* LyricSearchInit(const MAINWND* main_wnd);
void LyricSearchShow(BOOL show);
void LyricSearchClear(BOOL clear);
void LyricSearchArtistTile(PLAYERINFO* playerinfo);
void LyricSearchRefresh();
void LyricSearchList(LYRIC* lyric, DWORD lyricid);
void LyricSearchExit();
int CALLBACK LyricSearchProc(HWND, UINT, WPARAM, LPARAM);
DWORD WINAPI LyricSearchThread(LPVOID param);