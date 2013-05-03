#include "stdafx.h"
#include "ClockInfo.h"
#include "Spectrum.h"
#include "SongList.h"
#include "DxSound.h"
#include "Decode.h"
#include "Dsp.h"
#include "Lyric.h"
#include "LyricSearch.h"
#include "Util.h"
#include "Cdrom.h"

ULONG_PTR gditoken;
MAINWND main_wnd;
CDROM* main_cdrom = NULL;

PLAYERSTATUS playerstatus = ID_STATUS_STOP;
PLAYERENTRY player;

int APIENTRY _tWinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPTSTR lpCmdLine, int nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

 	MSG msg;
	HACCEL hAccelTable;
	
	LoadString(hInstance, IDS_APP_TITLE, main_wnd.szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_AUDIOPLAYER, main_wnd.szWindowClass, MAX_LOADSTRING);
	MainWndReg(hInstance, main_wnd.szWindowClass);

	if (!InitInstance(hInstance, nCmdShow, &main_wnd))	
		return FALSE;
	
	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_AUDIOPLAYER));

	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return (int) msg.wParam;
}

ATOM MainWndReg(HINSTANCE hInstance, TCHAR* szWindowClass)
{
	WNDCLASSEX wcex;
	wcex.cbSize			= sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= WndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_AUDIOPLAYER));
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_BACKGROUND);
	wcex.lpszMenuName	= MAKEINTRESOURCE(IDR_MENU_AUDIOPLAYER);
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_AUDIOPLAYER_SMALL));

	return RegisterClassEx(&wcex);
}

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow, MAINWND* main_wnd)
{  		
	DWORD style = WS_OVERLAPPEDWINDOW;
	style ^= WS_MAXIMIZEBOX;
	style ^= WS_SIZEBOX;

	main_wnd->inst = hInstance;
	main_wnd->hWnd = CreateWindowEx(WS_EX_ACCEPTFILES, main_wnd->szWindowClass, main_wnd->szTitle, style, CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, NULL, NULL, hInstance, NULL);
		
	if (!main_wnd->hWnd)
		return FALSE;
	
	main_cdrom = GetCDROM();
	Gdiplus::GdiplusStartupInput startupInput;	
	Gdiplus::GdiplusStartup(&gditoken, &startupInput, NULL);
	
	RECT rect = {0};		
	GetClientRect(main_wnd->hWnd, &rect);	
	LONG height = 0;
	LONG width = BANDCOUNT * (BANDWIDTH + BANDSPACE) + 10;
			
	rect.right = width;
	rect.bottom = 38;
	CLOCKINFO* clockinfo = ClockInfoInit(&rect, main_wnd);
	if (clockinfo == NULL)
		return FALSE;

	clockinfo->status = &playerstatus;
	clockinfo->player = &player;

	rect.top = rect.bottom;
	rect.bottom = rect.top + 80;	
	SPECTRUM* spectrum = SpectrumInit(&rect, main_wnd);
	if (spectrum == NULL)
		return FALSE;
	
	spectrum->status = &playerstatus;
	spectrum->player = &player;
	
	rect.top = rect.bottom + 1;
	rect.bottom = rect.top + 20;
	main_wnd->hTrack = CreateWindow(TRACKBAR_CLASS, _T(""), WS_CHILD | WS_VISIBLE | TBS_BOTH | TBS_NOTICKS, rect.left, rect.top, rect.right - rect.left - 6, rect.bottom - rect.top, main_wnd->hWnd, NULL, hInstance, NULL);
	if (main_wnd->hTrack == NULL)
		return FALSE;

	SendMessage(main_wnd->hTrack, TBM_SETRANGE, (WPARAM)FALSE, (LPARAM)MAKELONG(0, TRACK_PROGRESS));
	SendMessage(main_wnd->hTrack, TBM_SETLINESIZE, (WPARAM)FALSE, (LPARAM)5);
	SendMessage(main_wnd->hTrack, TBM_SETPAGESIZE, (WPARAM)FALSE, (LPARAM)5);
	TrackEnable(FALSE);
		
	height += rect.bottom;
	rect.top = rect.bottom + 1;
	rect.bottom = rect.top + 300;	
	if (!SongListInit(&rect, main_wnd))
		return FALSE;

	LYRICWMD* lyric_wnd = LyricInit(main_wnd);
	if (lyric_wnd == NULL)
		return FALSE;

	lyric_wnd->status = &playerstatus;
	lyric_wnd->player = &player;

	LYRICSEARCH* lyric_search = LyricSearchInit(main_wnd);
	if (lyric_search == NULL)
		return FALSE;

	lyric_search->status = &playerstatus;
	lyric_search->player = &player;

	height += rect.bottom - rect.top;
	GetClientRect(main_wnd->hWnd, &rect);
	LONG bottom = rect.bottom - rect.top;
	GetWindowRect(main_wnd->hWnd, &rect);
	rect.left = 0;
	rect.right = width;
	bottom = rect.bottom - rect.top - bottom;
	SetWindowPos(main_wnd->hWnd, NULL, 0, 0, rect.right - rect.left, height + bottom, SWP_NOMOVE);
	
	if (!DspInit(main_wnd))
		return FALSE;

	if (!DxSoundInit(&main_wnd->hWnd))
	{
		MessageBox(main_wnd->hWnd, L"初始化DirectSound错误", L"错误", MB_ICONERROR);
		return FALSE;
	}
	
	avcodec_register_all();   
	av_register_all();
	
	main_wnd->handle = CreateEvent(NULL, TRUE, FALSE, NULL);
	main_wnd->thread = CreateThread(NULL, 0, MainThread, main_wnd, 0, &main_wnd->thread_id);
	
	ShowWindow(main_wnd->hWnd, nCmdShow);
	UpdateWindow(main_wnd->hWnd);
	
	return TRUE;
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{			
	int wmId, wmEvent;
	switch (message)
	{
	case WM_DRAWITEM:
		{				
			LPDRAWITEMSTRUCT lpDrawItemStruct = (LPDRAWITEMSTRUCT)lParam;
			if (lpDrawItemStruct->CtlType == ODT_LISTVIEW)
			{
				if (lpDrawItemStruct->CtlID == IDC_SONGLIST)
					return SongListProc(hWnd, message, wParam, lParam);
			}
		}
		break;

	case WM_MEASUREITEM:
		{			
			LPMEASUREITEMSTRUCT lpMeasureItemStruct = (LPMEASUREITEMSTRUCT)lParam;
			if (lpMeasureItemStruct->CtlType == ODT_LISTVIEW)
			{
				if (lpMeasureItemStruct->CtlID == IDC_SONGLIST)
					return SongListProc(hWnd, message, wParam, lParam); 
			}
		}
		break;

		case WM_HSCROLL:
		{
			if (LOWORD(wParam) == TB_ENDTRACK)
			{				
				DWORD pos = (DWORD)SendMessage(main_wnd.hTrack, TBM_GETPOS, 0, 0);
				TrackSetProgress(pos);
			}
		}
		break;

	case WM_NOTIFY:
		{
			LPNMHDR lpnmh = (LPNMHDR)lParam;
			if (lpnmh->idFrom == IDC_SONGLIST)
			{				
				LPNMITEMACTIVATE lpnmia = (LPNMITEMACTIVATE)lParam;				
				switch (lpnmh->code)
				{
				case NM_DBLCLK:
					{						
						if (lpnmia->iItem > -1)
						{
							PLAYERINFO* playerinfo = GetPlayerInfo(lpnmia->iItem);
							if (playerinfo != NULL)
								PlayInfoPrepare(playerinfo);
						}						
					}
					break;

				case NM_RCLICK:
					{
						POINT p = {0};
						GetCursorPos(&p);
						HMENU hMenu = LoadMenu(main_wnd.inst, MAKEINTRESOURCE(IDR_MENU_SONGLIST));
						TrackPopupMenu(GetSubMenu(hMenu, 0), TPM_LEFTALIGN, p.x, p.y, 0, hWnd, NULL);						
					}
					break;
				}
			}
			else
			{
				if (lpnmh->hwndFrom == main_wnd.hTrack)
				{
					DWORD pos = (DWORD)SendMessage(main_wnd.hTrack, TBM_GETPOS, 0, 0);
					if (pos != main_wnd.track && !main_wnd.lock_track)
						TrackLock(TRUE);
				}
			}
		}
		break;

	case WM_COMMAND:		
		wmId    = LOWORD(wParam);
		wmEvent = HIWORD(wParam);		
				
		switch (wmId)
		{		
		case IDM_PLAY_START:
			{
				DWORD size = 1024;
				TCHAR* filename = new TCHAR[size];
				ZeroMemory(filename, size); 

				OPENFILENAME ofn = {0};
				ofn.lStructSize = sizeof(ofn);
				ofn.hwndOwner = hWnd;
				ofn.lpstrFile = filename;
				ofn.nMaxFile = size;
				ofn.lpstrFileTitle = L"选择播放文件";
				ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;

				if (GetOpenFileName(&ofn) && wcslen(filename) > 0)
				{
					PLAYERINFO* playerinfo = new PLAYERINFO();
					ZeroMemory(playerinfo, sizeof(PLAYERINFO));
					playerinfo->filename = WideCharToChar(filename, wcslen(filename));
					playerinfo->n = -1;
					InitializeCriticalSection(&playerinfo->lock_lyric);
					InitializeCriticalSection(&playerinfo->lock_lyricinfo);
					PlayInfoPrepare(playerinfo);
				}
				
				SAFE_DELETE_ARRAY(filename);
			}
			break;

		case IDM_PLAY_STOP:
			{
				player.stop = TRUE;
				playerstatus = ID_STATUS_STOP;
				SetEvent(main_wnd.handle);
			}
			break;

		case IDM_PLAY_PAUSE:
			{
				if (player.current != NULL)
				{
					PLAYERINFO* playerinfo = player.current;
					LPDIRECTSOUNDBUFFER dbuffer = playerinfo->dx.dbuffer;
					if (playerinfo->playing && dbuffer != NULL)
					{
						if (playerinfo->pause)
						{
							playerinfo->pause = FALSE;
							playerinfo->playing = TRUE;
							playerstatus = ID_STATUS_START;							
							dbuffer->Play(0, 0, DSBPLAY_LOOPING);
						}
						else
						{
							playerinfo->pause = TRUE;
							playerinfo->playing = FALSE;
							playerstatus = ID_STATUS_PAUSE;
							dbuffer->Stop();
						}
					}
					else
					{
						if (playerinfo->pause)
						{
							playerinfo->pause = FALSE;
							playerinfo->playing = TRUE;
							playerstatus = ID_STATUS_START;
							dbuffer->Play(0, 0, DSBPLAY_LOOPING);
						}
						else
						{
							playerinfo->pause = TRUE;
							playerinfo->playing = FALSE;
							playerstatus = ID_STATUS_PAUSE;
							dbuffer->Stop();
						}
					}

					SetWindowText(playerinfo);
					RefreshItem(playerinfo->n);
				}					
			}
			break;

		case IDM_SONGLIST_ADD_DIR:
			return SongListProc(hWnd, message, wParam, lParam);

		case IDM_SONGLIST_ADD_FILE:
			return SongListProc(hWnd, message, wParam, lParam);

		case IDM_SONGLIST_REMOVE_FILE:
			{
				if (SongListScan())				
					MessageBox(hWnd, L"正在扫描文件", L"错误", MB_ICONERROR);				
				else
				{
					while (SongListGetSelectCount() > 0)
					{
						int n = SongListGetSelectItem();
						if (n == -1)
							break;
						else
							SongListRemove(n, &player);
					}
				}				
			}
			break;

		case IDM_SONGLIST_REMOVE_ALL:
			{
				if (SongListScan())				
					MessageBox(hWnd, L"正在扫描文件", L"错误", MB_ICONERROR);
				else
					SongListClear(&player);
			}
			break;

		case IDM_MENU_25FPS:
			SetFPS(IDM_MENU_25FPS);
			break;

		case IDM_MENU_30FPS:
			SetFPS(IDM_MENU_30FPS);
			break;

		case IDM_MENU_40FPS:
			SetFPS(IDM_MENU_40FPS);
			break;

		case IDM_MENU_50FPS:
			SetFPS(IDM_MENU_50FPS);
			break;

		case IDM_MENU_LYRIC_STATUS:
			{
				if (GetLyricStatus())
				{
					SetLyricStatus(FALSE);
				}
				else
				{
					SetLyricStatus(TRUE);
					LyricInfoReStart(player.current);
				}			
			}
			break;

		case IDM_MENU_LYRIC_FONT:
			{
				LOGFONT* font = LyricGetFont();
				CHOOSEFONT cf = {0};
				cf.lStructSize = sizeof(CHOOSEFONT);				
				cf.Flags = CF_INITTOLOGFONTSTRUCT | CF_SCREENFONTS | CF_EFFECTS;
				cf.lpLogFont = font;
				if (ChooseFont(&cf))
					LyricSetFont();
			}
			break;

		case IDM_MENU_LYRIC_COLOR:
			{
				DialogBox(main_wnd.inst, MAKEINTRESOURCE(IDD_DIALOG_FONTCOLOR), main_wnd.hWnd, FontColorProc);
			}
			break;

		case IDM_MENU_LYRIC_SUB_500:
			SetLyricDelay(-500);
			break;

		case IDM_MENU_LYRIC_SUB_1000:
			SetLyricDelay(-1000);
			break;

		case IDM_MENU_LYRIC_SUB_3000:
			SetLyricDelay(-3000);
			break;

		case IDM_MENU_LYRIC_0:
			SetLyricDelay(0);
			break;

		case IDM_MENU_LYRIC_ADD_500:
			SetLyricDelay(500);
			break;

		case IDM_MENU_LYRIC_ADD_1000:
			SetLyricDelay(1000);
			break;

		case IDM_MENU_LYRIC_ADD_3000:
			SetLyricDelay(3000);
			break;

		case IDM_MENU_LYRIC_SEARCH:
			{
				LyricSearchShow(TRUE);
			}
			break;

		case IDM_MENU_DSP_STATUS:
			{
				HMENU menu = GetMenu(main_wnd.hWnd);
				if (menu != NULL)
				{
					if (DspGetStatus())					
					{
						DspSetStatus(FALSE);
						CheckMenuItem(menu, IDM_MENU_DSP_STATUS, MF_UNCHECKED);
					}
					else
					{
						DspSetStatus(TRUE);
						CheckMenuItem(menu, IDM_MENU_DSP_STATUS, MF_CHECKED);
					}
				}					
			}
			break;

		case IDM_MENU_DSP_SOUNDTOUCH:
			{
				SoundTouchShowDialog(TRUE);
			}
			break;

		default:
			{
				if (wmId >= WM_USER + WM_CDROM)
				{
					CDROM* current = main_cdrom;
					while (current != NULL)
					{
						if (current->msgId == wmId)
							break;
						else
							current = current->next;
					}

					if (current != NULL)			
						return SongListProc(hWnd, WM_COMMAND, IDM_SONGLIST_ADD_CD, (LPARAM)current);
				}
				else
					return DefWindowProc(hWnd, message, wParam, lParam);
			}
			break;
		}
		break;

	case WM_CLOSE:
		{
			player.stop = TRUE;
			playerstatus = ID_STATUS_EXIT;
			SetEvent(main_wnd.handle);
		}
		break;

	case WM_DROPFILES:
		return SongListProc(hWnd, message, wParam, lParam); 
		
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
				
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

BOOL CALLBACK FontColorProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_INITDIALOG:
		{			
			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(0, 255));
			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(0, 255));
			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(0, 255));
			COLORREF* color = LyricGetColor(TRUE);

			BYTE red = GetRValue(*color);
			BYTE green = GetGValue(*color);
			BYTE blue = GetBValue(*color);

			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_SETPOS, TRUE, (LPARAM)red);
			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_SETPOS, TRUE, (LPARAM)green);
			SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_SETPOS, TRUE, (LPARAM)blue);

			SetDlgItemInt(hWnd, IDC_EDIT_COLOR_RED, red, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_COLOR_GREEN, green, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_COLOR_BLUE, blue, FALSE);			
		}
		break;

	case WM_NOTIFY:
		{
			NMHDR* pNMHDR = (NMHDR*)lParam;
			if (pNMHDR->idFrom == IDC_SLIDER_COLOR_RED || pNMHDR->idFrom == IDC_SLIDER_COLOR_GREEN || pNMHDR->idFrom == IDC_SLIDER_COLOR_BLUE)
			{
				UINT red = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_GETPOS, 0, 0);
				UINT green = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_GETPOS, 0, 0);
				UINT blue = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_GETPOS, 0, 0);

				SetDlgItemInt(hWnd, IDC_EDIT_COLOR_RED, red, FALSE);
				SetDlgItemInt(hWnd, IDC_EDIT_COLOR_GREEN, green, FALSE);
				SetDlgItemInt(hWnd, IDC_EDIT_COLOR_BLUE, blue, FALSE);

				int n = SendMessage(GetDlgItem(hWnd, IDC_CHECK_COLOR_BACKGROUD), BM_GETCHECK, 0, 0);
				COLORREF* color = LyricGetColor(TRUE);

				if (n == BST_CHECKED)
					color = LyricGetColor(FALSE);

				*color = RGB(red, green, blue);
			}
		}
		break;

	case WM_COMMAND:
		{
			WORD id = LOWORD(wParam);
			WORD event = HIWORD(wParam);
						
			switch (event)
			{
			case EN_CHANGE:
				{
					switch (id)
					{
					case IDC_EDIT_COLOR_RED:
						{
							wchar_t buffer[8] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_COLOR_RED, buffer, sizeof(buffer) / sizeof(wchar_t));
							if (wcslen(buffer) > 0)
							{
								DWORD red = GetDlgItemInt(hWnd, IDC_EDIT_COLOR_RED, NULL, FALSE);
								if (red > 255)
									SetDlgItemInt(hWnd, IDC_EDIT_COLOR_RED, 255, FALSE);

								SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_SETPOS, TRUE, (LPARAM)red);

								UINT green = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_GETPOS, 0, 0);
								UINT blue = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_GETPOS, 0, 0);

								int n = SendMessage(GetDlgItem(hWnd, IDC_CHECK_COLOR_BACKGROUD), BM_GETCHECK, 0, 0);
								COLORREF* color = LyricGetColor(TRUE);

								if (n == BST_CHECKED)
									color = LyricGetColor(FALSE);

								*color = RGB(red, green, blue);
							}				
						}
						break;

					case IDC_EDIT_COLOR_GREEN:
						{
							wchar_t buffer[8] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_COLOR_GREEN, buffer, sizeof(buffer) / sizeof(wchar_t));
							if (wcslen(buffer) > 0)
							{
								DWORD green = GetDlgItemInt(hWnd, IDC_EDIT_COLOR_GREEN, NULL, FALSE);
								if (green > 255)
									SetDlgItemInt(hWnd, IDC_EDIT_COLOR_GREEN, 255, FALSE);

								SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_SETPOS, TRUE, (LPARAM)green);

								UINT red = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_GETPOS, 0, 0);
								UINT blue = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_GETPOS, 0, 0);

								int n = SendMessage(GetDlgItem(hWnd, IDC_CHECK_COLOR_BACKGROUD), BM_GETCHECK, 0, 0);
								COLORREF* color = LyricGetColor(TRUE);

								if (n == BST_CHECKED)
									color = LyricGetColor(FALSE);

								*color = RGB(red, green, blue);
							}
						}
						break;

					case IDC_EDIT_COLOR_BLUE:
						{
							wchar_t buffer[8] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_COLOR_BLUE, buffer, sizeof(buffer) / sizeof(wchar_t));
							if (wcslen(buffer) > 0)
							{
								DWORD blue = GetDlgItemInt(hWnd, IDC_EDIT_COLOR_BLUE, NULL, FALSE);
								if (blue > 255)
									SetDlgItemInt(hWnd, IDC_EDIT_COLOR_BLUE, 255, FALSE);

								SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_SETPOS, TRUE, (LPARAM)blue);

								UINT red = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_GETPOS, 0, 0);
								UINT green = (UINT)SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_GETPOS, 0, 0);
								
								int n = SendMessage(GetDlgItem(hWnd, IDC_CHECK_COLOR_BACKGROUD), BM_GETCHECK, 0, 0);
								COLORREF* color = LyricGetColor(TRUE);

								if (n == BST_CHECKED)
									color = LyricGetColor(FALSE);

								*color = RGB(red, green, blue);
							}
						}
						break;
					}
				}
				break;

			case BN_CLICKED:
				{
					switch (id)
					{
					case IDC_CHECK_COLOR_BACKGROUD:
						{
							int n = SendMessage(GetDlgItem(hWnd, id), BM_GETCHECK, 0, 0);
							COLORREF* color = LyricGetColor(TRUE);

							if (n == BST_CHECKED)							
								color = LyricGetColor(FALSE);

							BYTE red = GetRValue(*color);
							BYTE green = GetGValue(*color);
							BYTE blue = GetBValue(*color);

							SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_RED), TBM_SETPOS, TRUE, (LPARAM)red);
							SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_GREEN), TBM_SETPOS, TRUE, (LPARAM)green);
							SendMessage(GetDlgItem(hWnd, IDC_SLIDER_COLOR_BLUE), TBM_SETPOS, TRUE, (LPARAM)blue);

							SetDlgItemInt(hWnd, IDC_EDIT_COLOR_RED, red, FALSE);
							SetDlgItemInt(hWnd, IDC_EDIT_COLOR_GREEN, green, FALSE);
							SetDlgItemInt(hWnd, IDC_EDIT_COLOR_BLUE, blue, FALSE);						
						}
						break;
					}
				}
				break;
			}
		}
		break;
		
	case WM_CLOSE:
		EndDialog(hWnd, 0);
		break;
	}
		
	return 0;
}

void SetLyricDelay(int delay)
{
	PLAYERINFO* playerinfo = player.current;
	if (playerinfo != NULL)	
		LyricSetDelay(playerinfo->lyricinfo, delay);	
}

void SetFPS(UINT id)
{
	HMENU menu = GetMenu(main_wnd.hWnd);
	if (menu != NULL)
	{
		HMENU sub = GetSubMenu(menu, 1);
		int count = GetMenuItemCount(sub);

		if (sub != NULL && count > 0)
		{
			for (int i = 0;i < count;i++)
			{
				UINT idx = GetMenuItemID(sub, i);
				if (idx == id)		
					CheckMenuItem(sub, idx, MF_BYCOMMAND | MF_CHECKED);			
				else
					CheckMenuItem(sub, idx, MF_BYCOMMAND | MF_UNCHECKED);
			}

			if (id == IDM_MENU_25FPS)
			{
				SpectrumSetFPS(25);
				return;
			}

			if (id == IDM_MENU_30FPS)
			{
				SpectrumSetFPS(30);
				return;
			}

			if (id == IDM_MENU_40FPS)
			{
				SpectrumSetFPS(40);
				return;
			}

			if (id == IDM_MENU_50FPS)
			{
				SpectrumSetFPS(50);
				return;
			}
		}		
	}
}

void SetWindowText(PLAYERINFO* playerinfo)
{
	PLAYERTAG* playertag = &playerinfo->tag;
	wchar_t* s = L"[播放]";
	if (playerstatus == ID_STATUS_PAUSE)
		s = L"[暂停]";

	if (playerstatus == ID_STATUS_STOP)
		s = L"[停止]";

	DWORD size = wcslen(s) + 6;
	if (playerstatus != ID_STATUS_STOP)
	{
		if (playertag->artist != NULL)
			size += wcslen(playertag->artist);

		if (playertag->title != NULL)
			size += wcslen(playertag->title);
	}	

	wchar_t* text = new wchar_t[size];
	wmemset(text, 0, size);
	StrCat(text, s);

	if (playerstatus != ID_STATUS_STOP)
	{
		if (playertag->artist != NULL && playertag->title != NULL)
		{
			StrCat(text, L"[");
			StrCat(text, playertag->artist);
			StrCat(text, L" - ");
			StrCat(text, playertag->title);
			StrCat(text, L"]");
		}
		else
		{
			if (playertag->artist != NULL)
			{
				StrCat(text, L"[");
				StrCat(text, playertag->artist);
				StrCat(text, L"]");
			}

			if (playertag->title != NULL)
			{
				StrCat(text, L"[");
				StrCat(text, playertag->title);
				StrCat(text, L"]");
			}
		}
	}	

	SetWindowText(main_wnd.hWnd, text);
	SAFE_DELETE_ARRAY(text);
}

void TrackSetProgress(int pos)
{	
	if (pos > TRACK_PROGRESS)
		pos = TRACK_PROGRESS;

	if (playerstatus == ID_STATUS_START || playerstatus == ID_STATUS_PAUSE)
	{
		PLAYERINFO* playerinfo = player.current;
		if (playerinfo != NULL)
		{			
			int timestamp = (int)((float)playerinfo->duration * (float)(pos) / (float)TRACK_PROGRESS);			
			playerinfo->seek = timestamp;
		}
	}	
}

void TrackProgress(int ms, DWORD total)
{	
	if (main_wnd.lock_track)
		return;

	float percent = (float)ms / (float)total;
	int position = floor(percent * TRACK_PROGRESS + 0.5f);
	if (position > TRACK_PROGRESS)
		position = TRACK_PROGRESS;
	
	main_wnd.track = position;
	SendMessage(main_wnd.hTrack, TBM_SETPOS, (WPARAM)TRUE, (LPARAM)position);	
}

void TrackEnable(BOOL enable)
{		
	main_wnd.track = 0;	
	TrackLock(FALSE);
	EnableWindow(main_wnd.hTrack, enable);
	SendMessage(main_wnd.hTrack, TBM_SETPOS, (WPARAM)TRUE, (LPARAM)0);
}

void TrackLock(BOOL lock)
{
	main_wnd.lock_track = lock;
}

DWORD WINAPI MainThread(LPVOID param)
{	
	while (TRUE)
	{		
		DWORD ret = WaitForSingleObject(main_wnd.handle, INFINITE);
		if (ret == WAIT_OBJECT_0)
		{
			if (playerstatus == ID_STATUS_STOP)
			{
				ResetEvent(main_wnd.handle);
				continue;
			}

			if (playerstatus == ID_STATUS_START)
			{
				ResetEvent(main_wnd.handle);
				PLAYERINFO* playerinfo = player.current;
				BOOL success = FALSE;

				if (playerinfo->cd)
					success = DecodeCDInit(playerinfo);
				else
					success = DecodeInit(playerinfo->filename, &playerinfo->decode);				

				if (success)
				{
					PWAVEFORMATEXTENSIBLE waveformatEx = &playerinfo->dx.waveformatEx;
					PLAYERDECODE* decode = &playerinfo->decode;
					WaveFormatInit(waveformatEx, decode);

					if (playerinfo->cd)
						DecodeCDTag(playerinfo);
					else
						DecodeTag(playerinfo);

					PWAVEFORMATEX waveformat = &waveformatEx->Format;
					DspSet(waveformat->nSamplesPerSec, waveformat->nChannels, waveformat->nBlockAlign);
					if (DxBufferInit(playerinfo))
					{
						SetWindowText(playerinfo);
						if (!playerinfo->cd)
							LyricStart();

						TrackEnable(TRUE);
						DxPlay(playerinfo, &playerstatus);
					}
				}
								
				if (playerstatus == ID_STATUS_START || playerstatus == ID_STATUS_PAUSE)
					playerstatus = ID_STATUS_STOP;
				
				TrackEnable(FALSE);
				LyricStop();
				SpectrumStop();
				ClockInfoStop();

				playerinfo->playing = FALSE;
				playerinfo->pause = FALSE;
				SetWindowText(playerinfo);
				RefreshItem(playerinfo->n);
				PLAYERINFO* next = playerinfo->next;

				if (player.stop)				
					player.current = NULL;				
				else
				{
					if (player.entry)
					{
						playerstatus = ID_STATUS_START;
						player.current = player.next;
						player.next = NULL;
						player.entry = FALSE;
						if (player.current->n == -1 && playerinfo->n != -1)
							player.current->next = playerinfo;

						SetEvent(main_wnd.handle);
					}
					else
					{
						if (next != NULL)
						{
							playerstatus = ID_STATUS_START;
							player.current = next;
							player.next = NULL;
							player.entry = FALSE;
							SetEvent(main_wnd.handle);
						}
						else						
							player.current = NULL;							
					}
				}

				PlayInfoRelease(playerinfo, FALSE);	
			}

			if (playerstatus == ID_STATUS_EXIT)
			{
				if (player.next != NULL)
					PlayInfoRelease(player.next, FALSE);

				CDROMRelease();
				break;
			}
		}
	}

	SpectrumExit();
	ClockInfoExit();
	SongListExit();	
	LyricSearchExit();
	LyricExit();

	PostMessage(main_wnd.hWnd, WM_QUIT, 0, 0);	
	return 1;
}

void PlayInfoPrepare(PLAYERINFO* playerinfo)
{
	if (playerstatus == ID_STATUS_STOP)
	{			
		player.current = playerinfo;
		player.next = NULL;
		player.entry = FALSE;
		player.stop = FALSE;
		playerstatus = ID_STATUS_START;
		SetEvent(main_wnd.handle);
		return;
	}

	if (playerstatus == ID_STATUS_START || playerstatus == ID_STATUS_PAUSE)
	{			
		player.next = playerinfo;
		player.entry = TRUE;		
		player.stop = FALSE;
		playerstatus = ID_STATUS_STOP;
		SetEvent(main_wnd.handle);
		return;
	}
}

void PlayInfoRelease(PLAYERINFO* playerinfo, BOOL all)
{
	if (playerinfo->n == -1 || all)
	{
		SAFE_DELETE_ARRAY(playerinfo->filename);
		SAFE_DELETE_ARRAY(playerinfo->tag.artist);
		SAFE_DELETE_ARRAY(playerinfo->tag.title);
	}
	
	if (playerinfo->cd)
		CDTrackRelease(&playerinfo->cdtrack);

	DecodeRelease(&playerinfo->decode);
	DxBufferRelease(playerinfo);
	
	playerinfo->seek = -1;	
	playerinfo->ms = 0;
	playerinfo->playing = FALSE;
	playerinfo->pause = FALSE;
	
	LYRIC* lyric = playerinfo->lyric;
	LyricClear(lyric);
	playerinfo->lyric = NULL;
	playerinfo->lyricid = 0;

	LYRICINFO* lyricinfo = playerinfo->lyricinfo;
	LyricInfoClear(lyricinfo);
	playerinfo->lyricinfo = NULL;

	if (playerinfo->n == -1 || all)
	{
		DeleteCriticalSection(&playerinfo->lock_lyric);
		DeleteCriticalSection(&playerinfo->lock_lyricinfo);
		playerinfo->n = -1;
		SAFE_DELETE(playerinfo);		
	}
}

CDROM* GetCDROM()
{
	CDROM* first = NULL;
	CDROM* last = NULL;
	wchar_t* name = L"播放光盘(&G)";
	HMENU menu = GetMenu(main_wnd.hWnd);
	HMENU sub = GetSubMenu(menu, 0);
	MENUITEMINFO item = {0};
	item.fMask = MIIM_STRING | MIIM_ID | MIIM_SUBMENU;
	item.fType = MFT_STRING;
	item.fState = MFS_ENABLED;
	item.cbSize = sizeof(MENUITEMINFO);
	item.cch = wcslen(name);
	item.dwTypeData = name;	
	item.hSubMenu = CreateMenu();
	InsertMenuItem(sub, 1, TRUE, &item);
	
	DWORD n = GetLogicalDriveStrings(0, NULL);
	if (n > 0)
	{		
		wchar_t* buffer = new wchar_t[n];
		DWORD len = GetLogicalDriveStrings(n, buffer);
		wchar_t* ptr = buffer;
		DWORD i = 0;

		while (len > 0)
		{			
			UINT type = GetDriveType(ptr);
			if (type == DRIVE_CDROM)
			{				
				wchar_t driver[16] = {0};				
				StrCat(driver, L"\\\\.\\");				
				StrNCat(driver, ptr, 3);				
				name = CDROMName(driver);				
				if (name != NULL)
				{
					StrTrim(name, L" ");
					CDROM* cdrom = new CDROM();
					ZeroMemory(cdrom, sizeof(CDROM));
					StrNCat(cdrom->letter, ptr, 3);
					StrCat(cdrom->driver, driver);

					DWORD length = wcslen(name) + 16;
					cdrom->name = new wchar_t[length];
					wmemset(cdrom->name, 0, length);
					StrCat(cdrom->name, L"[");
					StrCat(cdrom->name, cdrom->letter);
					StrCat(cdrom->name, L"] ");
					StrCat(cdrom->name, name);
					SAFE_DELETE_ARRAY(name);
					
					cdrom->msgId = WM_USER + WM_CDROM + i;
					cdrom->next = NULL;
					i++;

					if (first == NULL)
					{
						first = cdrom;
						last = cdrom;
					}
					else
					{
						last->next = cdrom;
						last = cdrom;
					}

					MENUITEMINFO child = {0};
					child.fMask = MIIM_STRING | MIIM_ID;
					child.fType = MFT_STRING;
					child.fState = MFS_ENABLED;
					child.cbSize = sizeof(MENUITEMINFO);
					child.cch = wcslen(cdrom->name);
					child.dwTypeData = cdrom->name;
					child.wID = cdrom->msgId;
					
					InsertMenuItem(item.hSubMenu, 0, FALSE, &child);
				}			
			}

			ptr += 4;
			len -= 4;
		}

		SAFE_DELETE_ARRAY(buffer);
	}

	return first;
}

void CDROMRelease()
{
	CDROM* next = main_cdrom;
	while (next != NULL)
	{		
		CDROM* current = next;
		next = next->next;
		SAFE_DELETE_ARRAY(current->name);
		SAFE_DELETE(current);
	}

	main_cdrom = NULL;
}

BOOL GetLyricStatus()
{
	HMENU menu = GetMenu(main_wnd.hWnd);
	if (menu != NULL)
	{
		if (GetMenuState(menu, IDM_MENU_LYRIC_STATUS, MF_BYCOMMAND | MF_CHECKED) > 0)
			return TRUE;
	}

	return FALSE;
}

void SetLyricStatus(BOOL status)
{
	HMENU menu = GetMenu(main_wnd.hWnd);
	if (menu != NULL)
	{		
		HMENU sub = GetSubMenu(menu, 2);
		if (sub != NULL)
		{
			if (status)
			{
				ModifyMenu(sub, 0, MF_BYPOSITION | MF_CHECKED, IDM_MENU_LYRIC_STATUS, _T("显示(&S)"));
				LyricShow(TRUE);
			}
			else
			{
				ModifyMenu(sub, 0, MF_BYPOSITION | MF_UNCHECKED, IDM_MENU_LYRIC_STATUS, _T("隐藏(&H)"));
				LyricShow(FALSE);
			}		
		}
	}
}