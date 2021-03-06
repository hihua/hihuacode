#include "stdafx.h"
#include "LyricSearch.h"
#include "Lyric.h"
#include "Util.h"

LYRICSEARCH lyric_search;

LYRICSEARCH* LyricSearchInit(const MAINWND* main_wnd)
{
	HRSRC hsrc = FindResource(main_wnd->inst, MAKEINTRESOURCE(IDD_DIALOG_LYRICSEARCH), RT_DIALOG);
	if (hsrc == NULL)
		return NULL;

	HGLOBAL htemp = LoadResource(main_wnd->inst, hsrc);
	if (htemp == NULL)
		return NULL;

	LPCDLGTEMPLATE dialog = (LPCDLGTEMPLATE)LockResource(htemp);	
	lyric_search.hWnd = CreateDialogIndirect(main_wnd->inst, dialog, main_wnd->hWnd, LyricSearchProc);

	UnlockResource(htemp);
	FreeResource(htemp);
		
	lyric_search.httpreq.resolveTimeout = 10000;
	lyric_search.httpreq.connectTimeout = 10000;
	lyric_search.httpreq.sendTimeout = 10000;
	lyric_search.httpreq.receiveTimeout = 20000;
	lyric_search.exit = CreateEvent(NULL, FALSE, FALSE, NULL);
	lyric_search.thread = CreateThread(NULL, 0, LyricSearchThread, &lyric_search, 0, &lyric_search.thread_id);
	
	return &lyric_search;
}

void LyricSearchShow(BOOL show)
{
	if (show)
		ShowWindow(lyric_search.hWnd, SW_SHOW);
	else
		ShowWindow(lyric_search.hWnd, SW_HIDE);
}

void LyricSearchArtistTile(PLAYERINFO* playerinfo)
{
	PLAYERTAG* playertag = &playerinfo->tag;
	if (playertag->artist != NULL)
		SetDlgItemText(lyric_search.hWnd, IDC_EDIT_ARTIST, playertag->artist);
	else
		SetDlgItemText(lyric_search.hWnd, IDC_EDIT_ARTIST, L"");

	if (playertag->title != NULL)
		SetDlgItemText(lyric_search.hWnd, IDC_EDIT_TITLE, playertag->title);
	else
		SetDlgItemText(lyric_search.hWnd, IDC_EDIT_TITLE, L"");
}

void LyricSearchList(LYRIC* lyric, DWORD lyricid)
{	
	if (lyric == NULL)
		return;

	HWND listview = GetDlgItem(lyric_search.hWnd, IDC_LIST_LYRIC);
	ListView_DeleteAllItems(listview);
	
	while (lyric != NULL)
	{
		wchar_t id[16] = {0};
		wsprintf(id, L"%d", lyric->id);

		LVITEM lv = {0};
		lv.mask = LVIF_TEXT | LVIF_COLUMNS;
		lv.pszText = id;
		lv.iItem = ListView_GetItemCount(listview);
		int p = ListView_InsertItem(listview, &lv);

		if (lyric->artist != NULL)
		{
			ListView_SetItemText(listview, p, 1, lyric->artist);
		}
		else
		{
			ListView_SetItemText(listview, p, 1, L"");
		}

		if (lyric->title != NULL)
		{
			ListView_SetItemText(listview, p, 2, lyric->title);
		}
		else
		{
			ListView_SetItemText(listview, p, 2, L"");
		}

		if (lyricid > 0 && lyric->id == lyricid)
			ListView_SetItemText(listview, p, 3, L"正在使用");

		lyric = lyric->next;
	}	
}

void LyricSearchClear()
{
	SetDlgItemText(lyric_search.hWnd, IDC_EDIT_ARTIST, L"");
	SetDlgItemText(lyric_search.hWnd, IDC_EDIT_TITLE, L"");
	HWND listview = GetDlgItem(lyric_search.hWnd, IDC_LIST_LYRIC);
	ListView_DeleteAllItems(listview);
}

void LyricSearchExit()
{
	PostThreadMessage(lyric_search.thread_id, WM_QUIT, 0, 0);
	WaitForSingleObject(lyric_search.exit, INFINITE);
	CloseHandle(lyric_search.exit);
}

int CALLBACK LyricSearchProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_INITDIALOG:
		{
			lyric_search.hWnd = hWnd;
			HWND listview = GetDlgItem(hWnd, IDC_LIST_LYRIC);
			DWORD style = ListView_GetExtendedListViewStyle(listview);
			style |= LVS_EX_FULLROWSELECT;
			style |= LVS_EX_GRIDLINES;
			ListView_SetExtendedListViewStyle(listview, style);

			LV_COLUMN lvc = {0};
			lvc.mask = LVCF_FMT | LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
			lvc.fmt = LVCFMT_LEFT;
			lvc.pszText = L"序号";
			lvc.cx = 70;
			ListView_InsertColumn(listview, 0, &lvc);

			lvc.pszText = L"歌手";
			lvc.cx = 140;
			ListView_InsertColumn(listview, 1, &lvc);

			lvc.pszText = L"歌曲";
			lvc.cx = 170;
			ListView_InsertColumn(listview, 2, &lvc);

			lvc.pszText = L"状态";
			lvc.cx = 68;
			ListView_InsertColumn(listview, 3, &lvc);				
		}		
		break;

	case WM_COMMAND:
		{			
			WORD id = LOWORD(wParam);
			WORD event = HIWORD(wParam);

			switch (event)
			{
			case BN_CLICKED:
				{
					switch (id)
					{
					case IDC_BUTTON_LYRIC_SEARCH:
						{
							PostThreadMessage(lyric_search.thread_id, ID_CMD_LYRIC, 0, 0);
							EnableWindow(GetDlgItem(hWnd, id), FALSE);
						}
						break;
					}
				}
				break;
			}
		}
		break;

	case WM_NOTIFY:
		{
			LPNMHDR lpnmh = (LPNMHDR)lParam;
			if (lpnmh->idFrom == IDC_LIST_LYRIC)
			{
				LPNMITEMACTIVATE lpnmia = (LPNMITEMACTIVATE)lParam;

				switch (lpnmh->code)
				{
				case NM_DBLCLK:
					{	
						PLAYERENTRY* player = lyric_search.player;
						if (player != NULL)
						{
							PLAYERINFO* playerinfo = player->current;
							if (playerinfo != NULL)
							{
								LYRIC* lyric = playerinfo->lyric;
								if (lyric != NULL && lpnmia->iItem > -1)
								{
									int n = lpnmia->iItem;
									while (lyric != NULL)
									{
										if (lyric->n == n)
											break;
										else
											lyric = lyric->next;
									}

									if (lyric != NULL)
									{
										LYRIC* ly = new LYRIC();										
										ly->id = lyric->id;
										if (lyric->artist != NULL)
										{
											DWORD length = wcslen(lyric->artist);
											ly->artist = new wchar_t[length + 1];
											wmemset(ly->artist, 0, length + 1);
											StrCat(ly->artist, lyric->artist);
										}

										if (lyric->title != NULL)
										{
											DWORD length = wcslen(lyric->title);
											ly->title = new wchar_t[length + 1];
											wmemset(ly->title, 0, length + 1);
											StrCat(ly->title, lyric->title);
										}

										ly->asame = lyric->asame;
										ly->tsame = lyric->tsame;
										ly->aword = lyric->aword;
										ly->tword = lyric->tword;
										ly->n = lyric->n;
										ly->next = NULL;

										PostThreadMessage(lyric_search.thread_id, ID_CMD_LYRICINFO, (WPARAM)ly, 0);
									}
								}
							}
						}																	
					}
					break;
				}
			}
		}
		break;

	case WM_CLOSE:		
		LyricSearchShow(FALSE);	
		break;
	}

	return 0;
}

DWORD WINAPI LyricSearchThread(LPVOID param)
{
	MSG msg;

	while (GetMessage(&msg, 0, 0, 0))
	{
		switch (msg.message)
		{
		case ID_CMD_LYRIC:
			{				
				HWND hWnd = lyric_search.hWnd;
				HTTPREQ* httpreq = &lyric_search.httpreq;
				wchar_t* artist = NULL;
				UINT length = SendMessage(GetDlgItem(hWnd, IDC_EDIT_ARTIST), WM_GETTEXTLENGTH, 0, 0);
				if (length > 0)
				{
					artist = new wchar_t[length + 1];
					wmemset(artist, 0, length + 1);
					GetDlgItemText(hWnd, IDC_EDIT_ARTIST, artist, length + 1);
				}

				wchar_t* title = NULL;
				length = SendMessage(GetDlgItem(hWnd, IDC_EDIT_TITLE), WM_GETTEXTLENGTH, 0, 0);
				if (length > 0)
				{
					title = new wchar_t[length + 1];
					wmemset(title, 0, length + 1);
					GetDlgItemText(hWnd, IDC_EDIT_TITLE, title, length + 1);
				}

				LYRIC* lyric = GetLyric(artist, title, httpreq);
				SAFE_DELETE_ARRAY(artist);
				SAFE_DELETE_ARRAY(title);

				if (lyric != NULL)
				{
					DWORD lyricid = 0;
					PLAYERENTRY* player = lyric_search.player;
					if (player != NULL)
					{
						PLAYERINFO* playerinfo = player->current;
						if (playerinfo != NULL)
						{
							lyricid = playerinfo->lyricid;							
							LyricReset(lyric, playerinfo);
							LyricSearchList(lyric, lyricid);
						}
					}										
				}

				EnableWindow(GetDlgItem(hWnd, IDC_BUTTON_LYRIC_SEARCH), TRUE);
			}
			break;

		case ID_CMD_LYRICINFO:
			{
				HTTPREQ* httpreq = &lyric_search.httpreq;
				LYRIC* lyric = (LYRIC*)msg.wParam;
				LYRICINFO* lyricinfo = SetLyricInfo(lyric, httpreq);
				if (lyricinfo != NULL)
				{
					PLAYERENTRY* player = lyric_search.player;
					if (player != NULL)
					{
						PLAYERINFO* playerinfo = player->current;
						if (playerinfo != NULL)
						{
							LyricInfoReset(lyricinfo, lyric, playerinfo, TRUE);
							HWND listview = GetDlgItem(lyric_search.hWnd, IDC_LIST_LYRIC);
							int n = lyric->n;							
							int total = ListView_GetItemCount(listview);
							for (int i = 0;i < total;i++)
							{
								if (i == n)
								{
									ListView_SetItemText(listview, i, 3, L"正在使用");
								}
								else
								{
									ListView_SetItemText(listview, i, 3, L"");
								}
							}

							SetLyricStatus(TRUE);
						}
						else
							LyricInfoClear(lyricinfo);
					}
					else					
						LyricInfoClear(lyricinfo);					
				}

				LyricClear(lyric);	
			}
			break;
		}

		DispatchMessage(&msg);
	}

	CloseHandle(lyric_search.thread);	
	HttpClose(&lyric_search.httpreq, TRUE);
	PostMessage(lyric_search.hWnd, WM_QUIT, 0, 0);		
	SetEvent(lyric_search.exit);
	return 1;
}