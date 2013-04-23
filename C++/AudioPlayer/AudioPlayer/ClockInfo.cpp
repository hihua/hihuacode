#include "stdafx.h"
#include "ClockInfo.h"
#include "Util.h"

CLOCKINFO clockinfo;

ATOM ClockInfoReg(HINSTANCE hInstance, TCHAR* szWindowClass)
{	
	WNDCLASSEX wcex;
	wcex.cbSize			= sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= ClockInfoProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= NULL;
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOWTEXT);
	wcex.lpszMenuName	= NULL;
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= NULL;

	return RegisterClassEx(&wcex);
}

CLOCKINFO* ClockInfoInit(const RECT* rect, const MAINWND* main_wnd)
{
	clockinfo.name = L"ClockInfo HWND";
	ClockInfoReg(main_wnd->inst, clockinfo.name);

	clockinfo.rect.left = 0;
	clockinfo.rect.top = rect->top;
	clockinfo.rect.right = rect->right;
	clockinfo.rect.bottom = rect->bottom;

	LONG x = clockinfo.rect.left;
	LONG y = clockinfo.rect.top;
	LONG w = clockinfo.rect.right - clockinfo.rect.left;
	LONG h = clockinfo.rect.bottom - clockinfo.rect.top;

	clockinfo.hWnd = CreateWindowEx(WS_EX_ACCEPTFILES, clockinfo.name, NULL, WS_CHILD | WS_VISIBLE, x, y, w, h, main_wnd->hWnd, NULL, main_wnd->inst, NULL);
	if (clockinfo.hWnd == NULL)
		return NULL;

	clockinfo.rect.left = 0;
	clockinfo.rect.top = 0;
	clockinfo.rect.right = w;
	clockinfo.rect.bottom = h;	
	
	clockinfo.mdc = GetDC(clockinfo.hWnd);
	clockinfo.bdc = CreateCompatibleDC(clockinfo.mdc);
	clockinfo.black = CreateSolidBrush(RGB(0, 0, 0));
	clockinfo.bitmap = CreateCompatibleBitmap(clockinfo.mdc, w, h);
	SelectObject(clockinfo.bdc, clockinfo.bitmap);
	SetTextColor(clockinfo.bdc, RGB(0, 255, 0));
	SetBkColor(clockinfo.bdc, TRANSPARENT);
			
	if (!LoadImage(main_wnd->inst, IDB_PNG_0, L"PNG", &clockinfo.imginfo[0], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_1, L"PNG", &clockinfo.imginfo[1], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_2, L"PNG", &clockinfo.imginfo[2], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_3, L"PNG", &clockinfo.imginfo[3], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_4, L"PNG", &clockinfo.imginfo[4], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_5, L"PNG", &clockinfo.imginfo[5], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_6, L"PNG", &clockinfo.imginfo[6], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_7, L"PNG", &clockinfo.imginfo[7], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_8, L"PNG", &clockinfo.imginfo[8], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_9, L"PNG", &clockinfo.imginfo[9], &clockinfo.mdc))
		return NULL;

	if (!LoadImage(main_wnd->inst, IDB_PNG_COLON, L"PNG", &clockinfo.imginfo[10], &clockinfo.mdc))
		return NULL;
	
	clockinfo.logfont.lfHeight = 12;
	clockinfo.logfont.lfWidth = 0;
	clockinfo.logfont.lfEscapement = 0;
	clockinfo.logfont.lfOrientation = 0;
	clockinfo.logfont.lfWeight = FW_REGULAR;
	clockinfo.logfont.lfItalic = 0;
	clockinfo.logfont.lfUnderline = 0;
	clockinfo.logfont.lfStrikeOut = 0;
	clockinfo.logfont.lfCharSet = DEFAULT_CHARSET;
	clockinfo.logfont.lfOutPrecision = OUT_DEFAULT_PRECIS;
	clockinfo.logfont.lfClipPrecision = CLIP_DEFAULT_PRECIS;
	clockinfo.logfont.lfQuality = NONANTIALIASED_QUALITY;
	clockinfo.logfont.lfPitchAndFamily = DEFAULT_PITCH | FF_SWISS;
	StrCat(clockinfo.logfont.lfFaceName, L"ËÎÌו");

	clockinfo.hfont = CreateFontIndirect(&clockinfo.logfont);
	SelectObject(clockinfo.bdc, clockinfo.hfont);

	clockinfo.handle = CreateEvent(NULL, FALSE, FALSE, NULL);
	clockinfo.stop = CreateEvent(NULL, TRUE, TRUE, NULL);
	clockinfo.exit = CreateEvent(NULL, FALSE, FALSE, NULL);
	clockinfo.thread = CreateThread(NULL, 0, ClockInfoThread, &clockinfo, 0, &clockinfo.thread_id);

	return &clockinfo;
}

void ClockInfoStart()
{
	SetEvent(clockinfo.handle);	
}

void ClockInfoStop()
{
	SetEvent(clockinfo.handle);
	WaitForSingleObject(clockinfo.stop, INFINITE);
}

void ClockInfoExit()
{
	SetEvent(clockinfo.handle);		
	WaitForSingleObject(clockinfo.exit, INFINITE);
	CloseHandle(clockinfo.exit);
}

void ClockInfoDraw()
{
	const RECT* rect = &clockinfo.rect;
	HDC* mdc = &clockinfo.mdc;
	HDC* bdc = &clockinfo.bdc;
	const PLAYERENTRY* play = clockinfo.player;
	const PLAYERINFO* playerinfo = play->current;
	const PLAYERTAG* playertag = &playerinfo->tag;
	
	FillRect(*bdc, rect, clockinfo.black);
	
	if (playerinfo != NULL)
	{	
		SIZE size = {0};		
		LONG y = 1;
		LONG h = 12;
		TEXTMETRIC tm = {0};

		if (GetTextMetrics(*bdc, &tm))
			h = tm.tmHeight;

		if (GetTextExtentPoint(*bdc, playertag->ext, wcslen(playertag->ext), &size))
		{
			TextOut(*bdc, 0, y, playertag->ext, wcslen(playertag->ext));
			y += size.cy;
		}
		else
			y += h;

		if (GetTextExtentPoint(*bdc, playertag->timer, wcslen(playertag->timer), &size))
		{
			TextOut(*bdc, 0, y, playertag->timer, wcslen(playertag->timer));
			y += size.cy;
		}
		else
			y += h;

		y = 1;		
		LONG x = rect->right - 20;
		if (GetTextExtentPoint(*bdc, playertag->sample, wcslen(playertag->sample), &size))
		{
			x = rect->right - size.cx - 6;
			TextOut(*bdc, x, y, playertag->sample, wcslen(playertag->sample));
			y += size.cy;
		}
		else
			y += h;

		if (GetTextExtentPoint(*bdc, playertag->bitrate, wcslen(playertag->bitrate), &size))
		{
			x = rect->right - size.cx - 6;
			TextOut(*bdc, x, y, playertag->bitrate, wcslen(playertag->bitrate));
			y += size.cy;
		}
		else
			y += h;

		if (GetTextExtentPoint(*bdc, playertag->channels, wcslen(playertag->channels), &size))
		{
			x = rect->right - size.cx - 6;
			TextOut(*bdc, x, y, playertag->channels, wcslen(playertag->channels));
			y += size.cy;
		}
		else
			y += h;

		int ms = playerinfo->ms / 1000;
		int minute = ms / 60;
		int second = ms % 60;

		int a = minute / 10;
		int b = minute % 10;
		int c = second / 10;
		int d = second % 10;

		IMGINFO* imga = &clockinfo.imginfo[a];
		IMGINFO* imgb = &clockinfo.imginfo[b];
		IMGINFO* img = &clockinfo.imginfo[10];
		IMGINFO* imgc = &clockinfo.imginfo[c];
		IMGINFO* imgd = &clockinfo.imginfo[d];
		
		LONG w = rect->right - rect->left;
		x = (w - (imga->image->GetWidth() + imgb->image->GetWidth() + img->image->GetWidth() + imgc->image->GetWidth() + imgd->image->GetWidth())) / 2;
		y = 1;

		BitBlt(*bdc, x, y, imga->image->GetWidth(), imga->image->GetHeight(), imga->hdc, 0, 0, SRCCOPY);
		x += imga->image->GetWidth();
		BitBlt(*bdc, x, y, imgb->image->GetWidth(), imgb->image->GetHeight(), imgb->hdc, 0, 0, SRCCOPY);
		x += imgb->image->GetWidth();
		BitBlt(*bdc, x, y, img->image->GetWidth(), img->image->GetHeight(), img->hdc, 0, 0, SRCCOPY);
		x += img->image->GetWidth();
		BitBlt(*bdc, x, y, imgc->image->GetWidth(), imgc->image->GetHeight(), imgc->hdc, 0, 0, SRCCOPY);
		x += imgc->image->GetWidth();
		BitBlt(*bdc, x, y, imgd->image->GetWidth(), imgd->image->GetHeight(), imgd->hdc, 0, 0, SRCCOPY);
		x += imgd->image->GetWidth();
	}

	BitBlt(*mdc, 0, 0, rect->right - rect->left, rect->bottom - rect->top, *bdc, 0, 0, SRCCOPY);
}

void ClockInfoClear()
{
	const RECT* rect = &clockinfo.rect;
	HDC* mdc = &clockinfo.mdc;
	HDC* bdc = &clockinfo.bdc;

	FillRect(*bdc, rect, clockinfo.black);
	BitBlt(*mdc, 0, 0, rect->right - rect->left, rect->bottom - rect->top, *bdc, 0, 0, SRCCOPY);
}

void ClockInfoRefresh()
{
	SendMessage(clockinfo.hWnd, WM_PAINT, 0, 0);
}

LRESULT CALLBACK ClockInfoProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	HDC hdc;

	switch (message)
	{	
	case WM_PAINT:
		{		
			hdc = BeginPaint(hWnd, &ps);
			ClockInfoDraw();		
			EndPaint(hWnd, &ps);
		}	
		break;

	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

DWORD WINAPI ClockInfoThread(LPVOID param)
{		
	while (TRUE)
	{		
		DWORD ret = WaitForSingleObject(clockinfo.handle, INFINITE);
		if (ret == WAIT_OBJECT_0)
		{
			PLAYERSTATUS* status = clockinfo.status;
			if (*status == ID_STATUS_STOP)
			{
				SetEvent(clockinfo.stop);
				continue;
			}

			if (*status == ID_STATUS_START)
			{
				ResetEvent(clockinfo.stop);
				while (*status == ID_STATUS_START || *status == ID_STATUS_PAUSE)
				{
					if (*status == ID_STATUS_PAUSE)
					{
						Sleep(100);
						continue;						
					}					
					
					ClockInfoDraw();
					Sleep(300);
				}

				ClockInfoClear();
				SetEvent(clockinfo.stop);
			}

			if (*status == ID_STATUS_EXIT)
				break;
		}
	}
	
	DeleteObject(clockinfo.black);
	DeleteObject(clockinfo.bitmap);
	DeleteObject(clockinfo.hfont);
	DeleteDC(clockinfo.bdc);

	int total = sizeof(clockinfo.imginfo) / sizeof(IMGINFO);
	for (int i = 0;i < total;i++)	
		DeleteDC(clockinfo.imginfo[i].hdc);

	ReleaseDC(clockinfo.hWnd, clockinfo.mdc);	
	CloseHandle(clockinfo.stop);
	CloseHandle(clockinfo.handle);
	CloseHandle(clockinfo.thread);
	SetEvent(clockinfo.exit);
	return 1;
}