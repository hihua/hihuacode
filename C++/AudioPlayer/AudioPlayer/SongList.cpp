#include "stdafx.h"
#include "SongList.h"
#include "Util.h"
#include "Decode.h"
#include "Cdrom.h"

SONGLIST songlist;
PLAYERINFO* first;
PLAYERINFO* last;

SONGLIST* SongListInit(const RECT* rect, const MAINWND* main_wnd)
{
	songlist.scan = FALSE;
	songlist.rect.left = 0;
	songlist.rect.top = rect->top;
	songlist.rect.right = rect->right;
	songlist.rect.bottom = rect->bottom;

	LONG x = songlist.rect.left;
	LONG y = songlist.rect.top;
	LONG w = songlist.rect.right - songlist.rect.left - 6;
	LONG h = songlist.rect.bottom - songlist.rect.top;
		
	songlist.hWnd = CreateWindow(WC_LISTVIEW, NULL, WS_CHILD | WS_VISIBLE | LVS_REPORT | LVS_OWNERDRAWFIXED, x, y, w, h, main_wnd->hWnd, (HMENU)IDC_SONGLIST, main_wnd->inst, NULL);
	if (songlist.hWnd == NULL)
		return FALSE;

	DWORD style = ListView_GetExtendedListViewStyle(songlist.hWnd);
	style |= LVS_EX_FULLROWSELECT;	
	ListView_SetExtendedListViewStyle(songlist.hWnd, style);

	songlist.rect.left = 0;
	songlist.rect.top = 0;
	songlist.rect.right = w;
	songlist.rect.bottom = h;

	songlist.mdc = GetDC(songlist.hWnd);
	songlist.bdc = CreateCompatibleDC(songlist.mdc);
	songlist.bitmap = CreateCompatibleBitmap(songlist.mdc, w, h);		
	SelectObject(songlist.bdc, songlist.bitmap);
	if (!LoadImage(main_wnd->inst, IDB_PNG_PLAY, L"PNG", &songlist.imgplay, &songlist.mdc))
		return FALSE;

	if (!LoadImage(main_wnd->inst, IDB_PNG_PAUSE, L"PNG", &songlist.imgpause, &songlist.mdc))
		return FALSE;
	
	SongListColumn();	
	ListView_SetBkColor(songlist.hWnd, RGB(0, 0, 0));

	songlist.quit = FALSE;
	songlist.exit = CreateEvent(NULL, FALSE, FALSE, NULL);
	songlist.thread = CreateThread(NULL, 0, SongListThread, &songlist, 0, &songlist.thread_id);
	return &songlist;
}

LRESULT CALLBACK SongListProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId, wmEvent;

	switch (message)
	{
	case WM_COMMAND:
		wmId    = LOWORD(wParam);
		wmEvent = HIWORD(wParam);		
		switch (wmId)
		{		
		case IDM_SONGLIST_ADD_DIR:
			{
				DWORD size = 1024;
				TCHAR* path = new TCHAR[size];
				ZeroMemory(path, size); 

				BROWSEINFO bi;   
				bi.hwndOwner = songlist.hWnd;
				bi.pidlRoot = NULL;   
				bi.pszDisplayName = path;   
				bi.lpszTitle = L"请选择目录";
				bi.ulFlags = 0;   
				bi.lpfn = NULL;   
				bi.lParam = 0;   
				bi.iImage = 0;

				LPITEMIDLIST lp = SHBrowseForFolder(&bi);
				if (lp != NULL && SHGetPathFromIDList(lp, path))				
					PostThreadMessage(songlist.thread_id, ID_CMD_DIR, (WPARAM)path, 0);
				else
					SAFE_DELETE_ARRAY(path);
			}
			break;

		case IDM_SONGLIST_ADD_FILE:
			{
				DWORD size = 1024;
				TCHAR* filename = new TCHAR[size];
				ZeroMemory(filename, size); 

				OPENFILENAME ofn = {0};
				ofn.lStructSize = sizeof(ofn);
				ofn.hwndOwner = songlist.hWnd;
				ofn.lpstrFile = filename;
				ofn.nMaxFile = size;
				ofn.lpstrFileTitle = L"选择播放文件";
				ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;

				if (GetOpenFileName(&ofn) && wcslen(filename) > 0)
					PostThreadMessage(songlist.thread_id, ID_CMD_FILE, (WPARAM)filename, 0);
				else
					SAFE_DELETE_ARRAY(filename);
			}
			break;

		case IDM_SONGLIST_ADD_CD:
			{				
				PostThreadMessage(songlist.thread_id, ID_CMD_CD, (WPARAM)lParam, 0);				
			}
			break;
		}
		break;
		
	case WM_DRAWITEM:
		{			
			DrawItem((LPDRAWITEMSTRUCT)lParam);
		}
		break;

	case WM_MEASUREITEM:
		{
			LPMEASUREITEMSTRUCT lpMeasureItemStruct = (LPMEASUREITEMSTRUCT)lParam;
			lpMeasureItemStruct->itemHeight = 16;
		}
		break;
	
	case WM_DROPFILES:
		{
			HDROP hDrop = (HDROP)wParam;
			UINT n = DragQueryFile(hDrop, -1, NULL, 0);
			if (n > 0)
			{		
				DWORD size = 512;
				wchar_t* filename = new wchar_t[size];
				wmemset(filename, 0, size);
				DragQueryFile(hDrop, 0, filename, size);
				PostThreadMessage(songlist.thread_id, ID_CMD_FILE, (WPARAM)filename, 0);
			}

			DragFinish(hDrop);
		}
		break;

	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

void SongListColumn()
{	
	LV_COLUMN lvc = {0};
	lvc.mask = LVCF_FMT | LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
	lvc.fmt = LVCFMT_LEFT;
	lvc.pszText = L"状态";
	lvc.cx = 40;
	ListView_InsertColumn(songlist.hWnd, 0, &lvc);

	lvc.pszText = L"歌曲";
	lvc.cx = 115;
	ListView_InsertColumn(songlist.hWnd, 1, &lvc);
	
	lvc.pszText = L"歌手";
	lvc.cx = 70;
	ListView_InsertColumn(songlist.hWnd, 2, &lvc);

	lvc.pszText = L"时间";
	lvc.cx = 59;
	ListView_InsertColumn(songlist.hWnd, 3, &lvc);	
}

void RefreshItem(int n)
{
	if (n > -1)			
		ListView_RedrawItems(songlist.hWnd, n, n);
}

void DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct)
{	
	if (songlist.quit)
		return;

	UINT n = lpDrawItemStruct->itemID;
	HDC* mdc = &lpDrawItemStruct->hDC;
	HWND* hWnd = &songlist.hWnd;	
	HDC* bdc = &songlist.bdc;
	IMGINFO* imgplay = &songlist.imgplay;
	IMGINFO* imgpause = &songlist.imgpause;
	PLAYERINFO* playerinfo = GetPlayerInfo(n);
	if (playerinfo == NULL)	
		return;
	
	HFONT font = (HFONT)GetCurrentObject(*mdc, OBJ_FONT);
	SelectObject(*bdc, font);		

	RECT r = {0};
	LONG x = lpDrawItemStruct->rcItem.left;
	LONG y = lpDrawItemStruct->rcItem.top;
	LONG w = lpDrawItemStruct->rcItem.right - lpDrawItemStruct->rcItem.left;
	LONG h = lpDrawItemStruct->rcItem.bottom - lpDrawItemStruct->rcItem.top;
	r.left = lpDrawItemStruct->rcItem.left;
	r.right = lpDrawItemStruct->rcItem.right - lpDrawItemStruct->rcItem.left;
	r.top = 0;
	r.bottom = lpDrawItemStruct->rcItem.bottom - lpDrawItemStruct->rcItem.top;

	COLORREF color = RGB(0, 0, 0);

	if ((lpDrawItemStruct->itemAction | ODA_SELECT) && (lpDrawItemStruct->itemState & ODS_SELECTED))  
	{
		SetTextColor(*bdc, RGB(0, 0, 0));
		color = RGB(192, 192, 192);		
	}
	else
		SetTextColor(*bdc, RGB(255, 255, 255));	

	SetBkColor(*bdc, color);
	HBRUSH brush = CreateSolidBrush(color);	
	ExtTextOut(*bdc, 0, 0, ETO_OPAQUE, &r, NULL, 0, NULL);
	DeleteObject(brush);

	RECT rect = {0};
	if (playerinfo != NULL)
	{
		Gdiplus::Graphics graphics(*bdc);
		ListView_GetSubItemRect(*hWnd, n, 0, LVIR_LABEL, &rect);	
		Gdiplus::PointF point;

		if (playerinfo->playing)
		{
			point.X = (Gdiplus::REAL)(rect.right - rect.left - imgplay->image->GetWidth()) / 2.0F;
			point.Y = (Gdiplus::REAL)(rect.bottom - rect.top - imgplay->image->GetHeight()) / 2.0F;
			Gdiplus::Status s = graphics.DrawImage(imgplay->image, point);
		}

		if (playerinfo->pause)
		{
			point.X = (Gdiplus::REAL)(rect.right - rect.left - imgpause->image->GetWidth()) / 2.0F;
			point.Y = (Gdiplus::REAL)(rect.bottom - rect.top - imgpause->image->GetHeight()) / 2.0F;
			Gdiplus::Status s = graphics.DrawImage(imgpause->image, point);
		}
	}
	
	for (int i = 1;i < 4;i++)
	{
		wchar_t text[256] = {0};		
		ListView_GetItemText(*hWnd, n, i, text, sizeof(text) / sizeof(wchar_t));
		ListView_GetSubItemRect(*hWnd, n, i, LVIR_LABEL, &rect);
		r.left = rect.left;
		r.right = rect.right;
		r.top = 0;
		r.bottom = rect.bottom - rect.top;
		DrawText(*bdc, text, wcslen(text), &r, DT_LEFT | DT_SINGLELINE | DT_VCENTER);		
	}

	BitBlt(*mdc, x, y, w, h, *bdc, 0, 0, SRCCOPY);	
}

PLAYERINFO* GetPlayerInfo(int n)
{	
	PLAYERINFO* current = first;
	while (current != NULL)
	{
		if (current->n == n)
			return current;

		current = current->next;
	}

	return NULL;
}

BOOL SongListScan()
{
	return songlist.scan;
}

void SongListExit()
{
	PostThreadMessage(songlist.thread_id, WM_QUIT, 0, 0);
	WaitForSingleObject(songlist.exit, INFINITE);
	CloseHandle(songlist.exit);
}

void SongListRemove(int n, PLAYERENTRY* player)
{
	if (n > -1)
	{
		PLAYERINFO* current = first;
		PLAYERINFO* prev = NULL;
		while (current != NULL)
		{		
			if (current->n == n)		
				break;
			else
			{
				prev = current;
				current = current->next;
			}
		}

		if (current != NULL)
		{
			PLAYERINFO* next = current->next;
			while (next != NULL)		
			{
				next->n--;
				next = next->next;
			}

			if (player->next != NULL && player->next == current)
				player->next = NULL;

			if (first == current)
				first = current->next;
						
			if (prev != NULL)
			{
				prev->next = current->next;
				if (last == current)
					last = prev;
			}
			else
			{
				if (last == current)
					last = NULL;
			}
			
			if (current->playing || current->pause)			
				current->n = -1;			
			else
				PlayInfoRelease(current, TRUE);

			ListView_DeleteItem(songlist.hWnd, n);
		}
	}
}

void SongListClear(PLAYERENTRY* player)
{
	PLAYERINFO* next = first;
	while (next != NULL)
	{
		PLAYERINFO* current = next;
		next = next->next;
		
		if (player->next != NULL && player->next == current)
			player->next = NULL;

		if (current->playing || current->pause)
		{
			current->n = -1;
			current->next = NULL;
		}
		else
			PlayInfoRelease(current, TRUE);
	}
		
	ListView_DeleteAllItems(songlist.hWnd);
	first = NULL;
	last = NULL;
}

void SongListClear()
{
	PLAYERINFO* next = first;
	while (next != NULL)
	{
		PLAYERINFO* current = next;
		next = next->next;
		PlayInfoRelease(current, TRUE);
	}
		
	ListView_DeleteAllItems(songlist.hWnd);
	first = NULL;
	last = NULL;
}

void SongListFile(const wchar_t* filename)
{
	PLAYERINFO* playerinfo = new PLAYERINFO();
	ZeroMemory(playerinfo, sizeof(PLAYERINFO));
	playerinfo->cd = FALSE;	
	playerinfo->filename = WideCharToChar(filename, wcslen(filename));
	InitializeCriticalSection(&playerinfo->lock_lyric);
	InitializeCriticalSection(&playerinfo->lock_lyricinfo);
		
	if (DecodeInit(playerinfo->filename, &playerinfo->decode))
	{
		PWAVEFORMATEXTENSIBLE waveformatEx = &playerinfo->dx.waveformatEx;		
		PLAYERDECODE* decode = &playerinfo->decode;
		WaveFormatInit(waveformatEx, decode);
		DecodeTag(playerinfo);
		PlayInfoRelease(playerinfo, FALSE);		
	}
	else
	{		
		PlayInfoRelease(playerinfo, TRUE);		
		return;
	}

	if (first == NULL)
	{
		playerinfo->n = 0;
		first = playerinfo;
		last = playerinfo;
	}
	else
	{
		playerinfo->n = last->n + 1;
		last->next = playerinfo;
		last = playerinfo;
	}

	PLAYERTAG* tag = &playerinfo->tag;

	LVITEM lv = {0};
	lv.mask = LVIF_TEXT | LVIF_COLUMNS;	
	lv.pszText = L"";	
	lv.iItem = ListView_GetItemCount(songlist.hWnd);
	int p = ListView_InsertItem(songlist.hWnd, &lv);

	if (tag->title != NULL)
	{
		ListView_SetItemText(songlist.hWnd, p, 1, tag->title);
	}
	else
	{
		ListView_SetItemText(songlist.hWnd, p, 1, L"");
	}

	if (tag->artist != NULL)
	{
		ListView_SetItemText(songlist.hWnd, p, 2, tag->artist);
	}
	else
	{
		ListView_SetItemText(songlist.hWnd, p, 2, L"");
	}

	ListView_SetItemText(songlist.hWnd, p, 3, tag->timer);	
}

void SongListCD(const wchar_t* letter, UCHAR index, wchar_t* driver, DWORD sector_start, DWORD sector_end)
{
	PLAYERINFO* playerinfo = new PLAYERINFO();
	ZeroMemory(playerinfo, sizeof(PLAYERINFO));
	playerinfo->cd = TRUE;
	playerinfo->filename = NULL;
	InitializeCriticalSection(&playerinfo->lock_lyric);
	InitializeCriticalSection(&playerinfo->lock_lyricinfo);

	playerinfo->seek = -1;	
	playerinfo->ms = 0;
	playerinfo->playing = FALSE;
	playerinfo->pause = FALSE;
	playerinfo->lyric = NULL;
	playerinfo->lyricid = 0;
	playerinfo->lyricinfo = NULL;	
	playerinfo->next = NULL;

	if (first == NULL)
	{
		playerinfo->n = 0;
		first = playerinfo;
		last = playerinfo;
	}
	else
	{
		playerinfo->n = last->n + 1;
		last->next = playerinfo;
		last = playerinfo;
	}
		
	CDTrack* cdtrack = &playerinfo->cdtrack;
	cdtrack->driver = driver;
	cdtrack->header = TRUE;
	cdtrack->sector_start = sector_start;
	cdtrack->sector_end = sector_end;
	cdtrack->total_length = (sector_end - sector_start + 1) * RAW_SECTOR_SIZE;
	
	double bytes = CD_BYTES_SECOND;
	double value = (double)cdtrack->total_length / bytes;
	playerinfo->duration = DWORD(value + 0.5);	
	DecodeCDTag(playerinfo, letter, index);
	PLAYERTAG* tag = &playerinfo->tag;
		
	LVITEM lv = {0};
	lv.mask = LVIF_TEXT | LVIF_COLUMNS;	
	lv.pszText = L"";	
	lv.iItem = ListView_GetItemCount(songlist.hWnd);
	int p = ListView_InsertItem(songlist.hWnd, &lv);

	if (tag->title != NULL)
	{
		ListView_SetItemText(songlist.hWnd, p, 1, tag->title);
	}
	else
	{
		ListView_SetItemText(songlist.hWnd, p, 1, L"");
	}

	if (tag->artist != NULL)
	{
		ListView_SetItemText(songlist.hWnd, p, 2, tag->artist);
	}
	else
	{
		ListView_SetItemText(songlist.hWnd, p, 2, L"");
	}

	ListView_SetItemText(songlist.hWnd, p, 3, tag->timer);	
}

UINT SongListGetSelectCount()
{		
	return ListView_GetSelectedCount(songlist.hWnd);
}

int SongListGetSelectItem()
{
	int count = ListView_GetItemCount(songlist.hWnd);
	if (count > 0)
	{
		for (int i = 0;i < count;i++)
		{			
			int selected = ListView_GetNextItem(songlist.hWnd, i, LVNI_SELECTED);
			if (selected == -1)
				return i;
		}

		return -1;
	}
	else
		return -1;
}

void BrowseDir(const wchar_t* dir)
{
	int len = wcslen(dir);
	DWORD size = 1024;
	TCHAR* path = new TCHAR[size];
	ZeroMemory(path, size);
	StrCpy(path, dir);	
	if (dir[len - 1] != '\\') 
		StrCat(path, L"\\");

	StrCat(path, L"*");
	WIN32_FIND_DATA fd;  
	HANDLE hfile = FindFirstFile(path, &fd);
	if (hfile == INVALID_HANDLE_VALUE)  
	{
		FindClose(hfile);
		SAFE_DELETE_ARRAY(path);
		return;
	}
	
	TCHAR* temp = new TCHAR[size];
	ZeroMemory(temp, size);
	BOOL finish = FALSE;

	while (!finish)  
	{
		StrCpy(temp, dir);
		if (dir[len - 1] != '\\') 
			StrCat(temp, L"\\");

		StrCat(temp, fd.cFileName);
		BOOL directory = ((fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) != 0);
		if (directory)   
		{         
			if (StrCmp(fd.cFileName, L".") == 0 || StrCmp(fd.cFileName, L"..") == 0)
			{
				finish = (FindNextFile(hfile, &fd) == FALSE);				
				continue;
			}
			else
				BrowseDir(temp);
		}
		else
			SongListFile(temp);
		
		finish = (FindNextFile(hfile, &fd) == FALSE);		
	}

	FindClose(hfile);
	SAFE_DELETE_ARRAY(temp);
	SAFE_DELETE_ARRAY(path);
}

DWORD WINAPI SongListThread(LPVOID param)
{
	MSG msg;

	while (GetMessage(&msg, 0, 0, 0))
	{
		switch (msg.message)
		{
		case ID_CMD_DIR:
			{
				wchar_t* dir = (wchar_t*)msg.wParam;
				songlist.scan = TRUE;
				BrowseDir(dir);
				songlist.scan = FALSE;
				SAFE_DELETE_ARRAY(dir);
			}
			break;

		case ID_CMD_FILE:
			{
				wchar_t* filename = (wchar_t*)msg.wParam;
				songlist.scan = TRUE;
				SongListFile(filename);
				songlist.scan = FALSE;
				SAFE_DELETE_ARRAY(filename);
			}
			break;

		case ID_CMD_CD:
			{
				CDROM* cdrom = (CDROM*)msg.wParam;
				songlist.scan = TRUE;
				GetCDTrack(cdrom);
				songlist.scan = FALSE;
			}
			break;
		}

		DispatchMessage(&msg);
	}

	songlist.quit = TRUE;
	SongListClear();
	CloseHandle(songlist.thread);
	DeleteObject(songlist.bitmap);
	DeleteDC(songlist.imgplay.hdc);
	DeleteDC(songlist.imgpause.hdc);
	DeleteDC(songlist.bdc);
	ReleaseDC(songlist.hWnd, songlist.mdc);	
	SetEvent(songlist.exit);
	return 1;	
}