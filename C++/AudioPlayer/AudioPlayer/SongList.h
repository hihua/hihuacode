#include "AudioPlayer.h"

enum SONGLIST_CMD { ID_CMD_DIR = WM_USER + 1, ID_CMD_FILE, ID_CMD_CD };

SONGLIST* SongListInit(const RECT* rect, const MAINWND* main_wnd);
LRESULT CALLBACK SongListProc(HWND, UINT, WPARAM, LPARAM);
void SongListColumn();
void RefreshItem(int n);
void DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct);
PLAYERINFO* GetPlayerInfo(int n);
BOOL SongListScan();
void SongListExit();
void SongListRemove(int n, PLAYERENTRY* player);
void SongListClear(PLAYERENTRY* player);
void SongListClear();
void SongListFile(const wchar_t* filename);
void SongListCD(const wchar_t* letter, UCHAR index, wchar_t* driver, DWORD sector_start, DWORD sector_end);
UINT SongListGetSelectCount();
int SongListGetSelectItem();
void BrowseDir(const wchar_t* path);
DWORD WINAPI SongListThread(LPVOID param);