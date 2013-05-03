#include "stdafx.h"
#include "Lyric.h"
#include "LyricSearch.h"
#include "Util.h"
#include "xml/tinyxml.h"

LYRICWMD lyric_wnd;

ATOM LyricReg(HINSTANCE hInstance, TCHAR* szWindowClass)
{	
	WNDCLASSEX wcex;
	wcex.cbSize			= sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= LyricProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= NULL;
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName	= NULL;
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= NULL;

	return RegisterClassEx(&wcex);
}

LYRICWMD* LyricInit(const MAINWND* main_wnd)
{
	lyric_wnd.name = L"Lyric HWND";
	LyricReg(main_wnd->inst, lyric_wnd.name);

	lyric_wnd.mask = RGB(255, 255, 255);
	lyric_wnd.foreground = RGB(255, 132, 0);
	lyric_wnd.background= RGB(161, 161, 179);

	lyric_wnd.logfont.lfHeight = 64;
	lyric_wnd.logfont.lfWidth = 0;
	lyric_wnd.logfont.lfEscapement = 0;
	lyric_wnd.logfont.lfOrientation = 0;
	lyric_wnd.logfont.lfWeight = FW_HEAVY;
	lyric_wnd.logfont.lfItalic = 0;
	lyric_wnd.logfont.lfUnderline = 0;
	lyric_wnd.logfont.lfStrikeOut = 0;
	lyric_wnd.logfont.lfCharSet = DEFAULT_CHARSET;
	lyric_wnd.logfont.lfOutPrecision = OUT_DEFAULT_PRECIS;
	lyric_wnd.logfont.lfClipPrecision = CLIP_DEFAULT_PRECIS;
	lyric_wnd.logfont.lfQuality = NONANTIALIASED_QUALITY;
	lyric_wnd.logfont.lfPitchAndFamily = DEFAULT_PITCH | FF_SWISS;
	StrCat(lyric_wnd.logfont.lfFaceName, L"свт╡");	
		
	HWND destop = GetDesktopWindow();
	GetClientRect(destop, &lyric_wnd.rect);	
	lyric_wnd.rect.bottom -= 50;
	lyric_wnd.rect.top = lyric_wnd.rect.bottom - 64;
	
	LONG x = lyric_wnd.rect.left;
	LONG y = lyric_wnd.rect.top;
	LONG w = lyric_wnd.rect.right - lyric_wnd.rect.left;
	LONG h = lyric_wnd.rect.bottom - lyric_wnd.rect.top;
	
	lyric_wnd.hWnd = CreateWindow(lyric_wnd.name, NULL, DS_SETFONT | DS_FIXEDSYS | WS_POPUP | WS_SYSMENU, x, y, w, h, main_wnd->hWnd, NULL, main_wnd->inst, NULL);
	if (lyric_wnd.hWnd == NULL)
		return NULL;
		
	SetWindowLong(lyric_wnd.hWnd, GWL_EXSTYLE, GetWindowLong(lyric_wnd.hWnd, GWL_EXSTYLE) | WS_EX_LAYERED);
	SetLayeredWindowAttributes(lyric_wnd.hWnd, lyric_wnd.mask, 255, LWA_COLORKEY);

	lyric_wnd.rect.left = 0;
	lyric_wnd.rect.top = 0;
	lyric_wnd.rect.right = w;
	lyric_wnd.rect.bottom = h;

	lyric_wnd.logfont.lfHeight = w / 30;
	lyric_wnd.hfont = CreateFontIndirect(&lyric_wnd.logfont);
	lyric_wnd.mdc = GetDC(lyric_wnd.hWnd);
	lyric_wnd.bdc = CreateCompatibleDC(lyric_wnd.mdc);	
	lyric_wnd.bitmap = CreateCompatibleBitmap(lyric_wnd.mdc, w, h);
	lyric_wnd.brush = CreateSolidBrush(lyric_wnd.mask);
	SelectObject(lyric_wnd.bdc, lyric_wnd.bitmap);
	SelectObject(lyric_wnd.bdc, lyric_wnd.hfont);
	DeleteObject(destop);

	SIZE size = {0};
	LONG height = 0;
	if (GetTextExtentPoint(lyric_wnd.bdc, L"1", 1, &size))
		height = size.cy;
	
	RECT rect = {0};
	GetWindowRect(lyric_wnd.hWnd, &rect);
	w = rect.right - rect.left;	
	h = rect.bottom - rect.top;
	GetClientRect(lyric_wnd.hWnd, &rect);	

	if (height == 0)
		height = rect.bottom - rect.top;

	h -= rect.bottom - rect.top;
	h += height;
	lyric_wnd.height = height;
	SetWindowPos(lyric_wnd.hWnd, NULL, 0, 0, w, h, SWP_NOZORDER | SWP_NOMOVE);

	ShowWindow(lyric_wnd.hWnd, SW_SHOW);
	UpdateWindow(lyric_wnd.hWnd);

	lyric_wnd.httpreq.resolveTimeout = 10000;
	lyric_wnd.httpreq.connectTimeout = 10000;
	lyric_wnd.httpreq.sendTimeout = 10000;
	lyric_wnd.httpreq.receiveTimeout = 20000;
	lyric_wnd.handle = CreateEvent(NULL, FALSE, FALSE, NULL);
	lyric_wnd.stop = CreateEvent(NULL, TRUE, TRUE, NULL);
	lyric_wnd.exit = CreateEvent(NULL, FALSE, FALSE, NULL);
	lyric_wnd.thread = CreateThread(NULL, 0, LyricThread, &lyric_wnd, 0, &lyric_wnd.thread_id);

	return &lyric_wnd;
}

void LyricShow(BOOL show)
{
	if (show)
		ShowWindow(lyric_wnd.hWnd, SW_SHOW);
	else
		ShowWindow(lyric_wnd.hWnd, SW_HIDE);
}

void LyricSetDelay(LYRICINFO* lyricinfo, int delay)
{
	while (lyricinfo != NULL)
	{
		if (delay == 0)
			lyricinfo->delay = 0;
		else
			lyricinfo->delay += delay;

		lyricinfo = lyricinfo->next;
	}
}

void LyricStart()
{
	lyric_wnd.automatic = TRUE;
	SetEvent(lyric_wnd.handle);
}

void LyricStop()
{
	SetEvent(lyric_wnd.handle);
	WaitForSingleObject(lyric_wnd.stop, INFINITE);
}

void LyricExit()
{
	SetEvent(lyric_wnd.handle);		
	WaitForSingleObject(lyric_wnd.exit, INFINITE);	
	CloseHandle(lyric_wnd.exit);
}

LOGFONT* LyricGetFont()
{
	return &lyric_wnd.logfont;
}

void LyricSetFont()
{
	DeleteObject(lyric_wnd.hfont);
	lyric_wnd.hfont = CreateFontIndirect(&lyric_wnd.logfont);
	SelectObject(lyric_wnd.bdc, lyric_wnd.hfont);

	SIZE size = {0};
	LONG height = 0;
	if (GetTextExtentPoint(lyric_wnd.bdc, L"1", 1, &size))
		height = size.cy;
		
	RECT rect = {0};
	GetWindowRect(lyric_wnd.hWnd, &rect);
	LONG w = rect.right - rect.left;	
	LONG h = rect.bottom - rect.top;
	GetClientRect(lyric_wnd.hWnd, &rect);	

	if (height == 0)
		height = rect.bottom - rect.top;

	h -= rect.bottom - rect.top;
	h += height;
	lyric_wnd.height = height;
	SetWindowPos(lyric_wnd.hWnd, NULL, 0, 0, w, h, SWP_NOZORDER | SWP_NOMOVE);
}

COLORREF* LyricGetColor(BOOL foreground)
{
	if (foreground)
		return &lyric_wnd.foreground;
	else
		return &lyric_wnd.background;
}

LYRIC* GetLyric(const wchar_t* tag_artist, const wchar_t* tag_title, HTTPREQ* req)
{
	if (tag_artist == NULL && tag_title == NULL)
		return NULL;

	if (tag_title == NULL)
		return NULL;
	
	wchar_t* host = L"http://ttlrcct2.qianqian.com/dll/lyricsvr.dll?sh?";
	unsigned char r = RandomNum(255);
	if (r % 2 == 0)
		host = L"http://ttlrcct.qianqian.com/dll/lyricsvr.dll?sh?";

	const wchar_t* ar = L"Artist=";
	const wchar_t* ti = L"Title=";
	const wchar_t* flag = L"&Flags=0";
	wchar_t* artist = NULL;
	wchar_t* title = NULL;
	
	if (tag_artist != NULL)
	{
		DWORD len = wcslen(tag_artist);
		wchar_t* a = new wchar_t[len + 1];
		wmemset(a, 0, len + 1);		
		DWORD i = 0, j = 0;
		while (tag_artist[j] != '\0')
		{
			if (tag_artist[j] != ' ')			
				a[i++] = tag_artist[j];				
			
			j++;
		}
		
		_wcslwr_s(a, i + 1);
		artist = WideCharToHex(a, wcslen(a));
		SAFE_DELETE_ARRAY(a);
	}

	if (tag_title != NULL)
	{
		DWORD len = wcslen(tag_title);
		wchar_t* b = new wchar_t[len + 1];
		wmemset(b, 0, len + 1);			
		DWORD i = 0, j = 0;
		while (tag_title[j] != '\0')
		{
			if (tag_title[j] >= 0x80 || (tag_title[j] >= 'a' && tag_title[j] <= 'z') || (tag_title[j] >= 'A' && tag_title[j] <= 'Z') || (tag_title[j] >= '0' && tag_title[j] <= '9'))
				b[i++] = tag_title[j];

			j++;
		}

		_wcslwr_s(b, i + 1);
		title = WideCharToHex(b, wcslen(b));
		SAFE_DELETE_ARRAY(b);
	}

	DWORD size = wcslen(host) + wcslen(ar) + wcslen(ti) + wcslen(flag) + 2;
	if (artist != NULL)	
		size += wcslen(artist);
	
	if (title != NULL)
		size += wcslen(title);

	wchar_t* url = new wchar_t[size + 1];
	wmemset(url, 0, size + 1);
	StrCat(url, host);
	StrCat(url, ar);
	if (artist != NULL)			
		StrCat(url, artist);
	
	StrCat(url, L"&");
	StrCat(url, ti);
	if (title != NULL)		
		StrCat(url, title);

	StrCat(url, flag);
	LYRIC* lyric = NULL;

	if (HttpRequest(req, url, HTTPHEADER, wcslen(HTTPHEADER), NULL, 0) && req->buffer != NULL && req->dataLength > 0)
	{
#ifndef NDEBUG
		HANDLE hfile = CreateFile(L"C:\\l.txt", GENERIC_WRITE,	FILE_SHARE_WRITE, NULL,	CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
		if (hfile != NULL)
		{			
			WriteFile(hfile, "\xEF\xBB\xBF", 3, &size, NULL);
			WriteFile(hfile, req->buffer, req->dataLength, &size, NULL);			
			CloseHandle(hfile);
		}
#endif
		lyric = ExcuteLyric(req->buffer);		
	}

	HttpClose(req, FALSE);
	SAFE_DELETE_ARRAY(url);	
	SAFE_DELETE_ARRAY(artist);
	SAFE_DELETE_ARRAY(title);
	return lyric;
}

LYRIC* ExcuteLyric(const char* body)
{
	LYRIC* first = NULL;
	LYRIC* last = NULL;
	int n = 0;

	try
	{		
		TiXmlDocument doc;
		doc.Parse(body, 0, TIXML_ENCODING_UTF8);
		TiXmlElement* root = doc.RootElement();
		if (root == NULL)
			return NULL;
				
		for (TiXmlElement* element = root->FirstChildElement("lrc"); element != NULL; element = element->NextSiblingElement())
		{
			const char* id = element->Attribute("id");
			const char* artist = element->Attribute("artist");
			const char* title = element->Attribute("title");

			if (id == NULL || artist == NULL || title == NULL)
				continue;

			char* p = UTF8toANSI(id, strlen(id));
			if (!IsNumber(p))
			{
				SAFE_DELETE_ARRAY(p);
				continue;
			}
			
			LYRIC* lyric = new LYRIC();
			lyric->id = atoi(p);			
			lyric->artist = UTF8toWideChar(artist, strlen(artist));			
			lyric->title = UTF8toWideChar(title, strlen(title));
			lyric->asame = 0.0F;
			lyric->tsame = 0.0F;
			lyric->aword = 0;
			lyric->tword = 0;
			lyric->n = n;
			lyric->next = NULL;
			n++;
						
			SAFE_DELETE_ARRAY(p);

			if (first == NULL)
			{
				first = lyric;
				last = lyric;
			}
			else
			{
				last->next = lyric;
				last = lyric;
			}
		}

		doc.Clear();
		return first;
	}
	catch (...)
	{
		LyricClear(first);
		return NULL;
	}
}

LYRIC* SelectLyric(PLAYERINFO* playerinfo)
{
	if (playerinfo->lyric == NULL)
		return NULL;

	PLAYERTAG* playertag = &playerinfo->tag;
	LYRIC* lyric = playerinfo->lyric;
	LYRIC* ly = NULL;
	wchar_t* filename = CharToWideChar(playerinfo->filename, strlen(playerinfo->filename));

	wchar_t* a = NULL;
	if (playertag->artist != NULL)
	{
		DWORD len = wcslen(playertag->artist);
		a = new wchar_t[len + 1];
		wmemset(a, 0, len + 1);			
		DWORD i = 0, j = 0;
		while (playertag->artist[j] != '\0')
		{
			if (playertag->artist[j] >= 0x80 || (playertag->artist[j] >= 'a' && playertag->artist[j] <= 'z') || (playertag->artist[j] >= 'A' && playertag->artist[j] <= 'Z') || (playertag->artist[j] >= '0' && playertag->artist[j] <= '9'))
				a[i++] = playertag->artist[j];

			j++;
		}

		_wcslwr_s(a, i + 1);
	}

	wchar_t* b = NULL;
	if (playertag->title != NULL)
	{
		DWORD len = wcslen(playertag->title);
		b = new wchar_t[len + 1];
		wmemset(b, 0, len + 1);			
		DWORD i = 0, j = 0;
		while (playertag->title[j] != '\0')
		{
			if (playertag->title[j] >= 0x80 || (playertag->title[j] >= 'a' && playertag->title[j] <= 'z') || (playertag->title[j] >= 'A' && playertag->title[j] <= 'Z') || (playertag->title[j] >= '0' && playertag->title[j] <= '9'))			
				b[i++] = playertag->title[j];				

			j++;
		}

		_wcslwr_s(b, i + 1);
	}
	
	while (lyric != NULL)
	{
		wchar_t* la = NULL;
		if (lyric->artist != NULL)
		{
			DWORD len = wcslen(lyric->artist);
			la = new wchar_t[len + 1];
			wmemset(la, 0, len + 1);				
			DWORD i = 0, j = 0;
			while (lyric->artist[j] != '\0')
			{
				if (lyric->artist[j] >= 0x80 || (lyric->artist[j] >= 'a' && lyric->artist[j] <= 'z') || (lyric->artist[j] >= 'A' && lyric->artist[j] <= 'Z') || (lyric->artist[j] >= '0' && lyric->artist[j] <= '9'))			
					la[i++] = lyric->artist[j];

				j++;
			}

			_wcslwr_s(la, i + 1);
		}

		wchar_t* lb = NULL;
		if (lyric->title != NULL)
		{
			DWORD len = wcslen(lyric->title);
			lb = new wchar_t[len + 1];
			wmemset(lb, 0, len + 1);
			DWORD i = 0, j = 0;
			while (lyric->title[j] != '\0')
			{
				if (lyric->title[j] >= 0x80 || (lyric->title[j] >= 'a' && lyric->title[j] <= 'z') || (lyric->title[j] >= 'A' && lyric->title[j] <= 'Z') || (lyric->title[j] >= '0' && lyric->title[j] <= '9'))				
					lb[i++] = lyric->title[j];

				j++;
			}

			_wcslwr_s(lb, i + 1);
		}

		if (la != NULL && a != NULL)
		{
			if (wcslen(la) > wcslen(a))
				lyric->aword = wcslen(la) - wcslen(a);
			else
				lyric->aword = wcslen(a) - wcslen(la);
		}			

		if (lb != NULL && b != NULL)
		{
			if (wcslen(lb) > wcslen(b))
				lyric->tword = wcslen(lb) - wcslen(b);
			else
				lyric->tword = wcslen(b) - wcslen(lb);
		}			

		if (la != NULL && a != NULL && lb != NULL && b != NULL)				
		{				
			lyric->asame = Compare(a, la);
			lyric->tsame = Compare(b, lb);				
		}

		if (la != NULL && a == NULL && lb != NULL && b != NULL)
		{
			lyric->asame = Compares(filename, la);
			lyric->tsame = Compare(b, lb);
		}

		SAFE_DELETE_ARRAY(la);
		SAFE_DELETE_ARRAY(lb);
		
		if (ly != NULL)
		{
			if (lyric->tsame > ly->tsame)
			{							
				ly = lyric;
				lyric = lyric->next;
				continue;
			}

			if (lyric->asame > ly->asame)
			{								
				ly = lyric;
				lyric = lyric->next;
				continue;
			}

			if (lyric->tsame == ly->tsame && lyric->asame == ly->asame && lyric->tword < ly->tword)
			{							
				ly = lyric;
				lyric = lyric->next;
				continue;
			}

			if (lyric->aword < ly->aword)
			{				
				ly = lyric;
				lyric = lyric->next;
				continue;
			}			
		}
		else
			ly = lyric;

		lyric = lyric->next;
	}

	SAFE_DELETE_ARRAY(a);
	SAFE_DELETE_ARRAY(b);
	SAFE_DELETE_ARRAY(filename);
	return ly;
}

FLOAT Compare(const wchar_t* src, const wchar_t* dest)
{
	DWORD total = 0;
	DWORD slen = wcslen(src);	
	DWORD dlen = wcslen(dest);
	for (DWORD i = 0;i < slen;i++)
	{		
		for (DWORD j = 0;j < dlen;j++)
		{
			if (src[i] == dest[j])
			{
				total++;
				break;
			}
		}
	}

	return (FLOAT)total / (FLOAT)slen;
}

FLOAT Compares(const wchar_t* src, const wchar_t* dest)
{
	DWORD i = 0, j = 0;
	FLOAT total = 0.0F;

	while (TRUE)
	{		
		if ((src[i] == '\0' || src[i] == '\\') && i > 2)
		{			
			DWORD len = i - j;
			wchar_t* c = new wchar_t[len + 1];
			wmemset(c, 0, len + 1);
			CopyMemory(c, src + j, len * 2);

			if (StrStr(dest, c) != NULL)
				total += 1.0F;

			SAFE_DELETE_ARRAY(c);
			if (src[i] == '\0')
				break;

			if (src[i] == '\\')
				j = i + 1;
		}

		i++;
	}

	return total;
}

int SetCode(const LYRIC* lyric)
{
	char* artist = WideChartoUTF8(lyric->artist, wcslen(lyric->artist));
	char* title = WideChartoUTF8(lyric->title, wcslen(lyric->title));
	DWORD id = lyric->id;

	DWORD len = strlen(artist) + strlen(title);
	char* song = new char[len];
	ZeroMemory(song, len);

	DWORD j = 0;
	for (DWORD i = 0;i < strlen(artist);i++)
	{
		song[j] = artist[i] & 0xFF;
		j++;
	}

	for (DWORD i = 0;i < strlen(title);i++)
	{
		song[j] = title[i] & 0xFF;
		j++;
	}

	int val1 = 0, val2 = 0, val3 = 0;

	val1 = (id & 0xFF00) >> 8;
	if ((id & 0xFF0000) == 0)
		val3 = 0xFF & ~val1;
	else
		val3 = 0xFF & ((id & 0x00FF0000) >> 16);

	val3 = val3 | ((0xFF & id) << 8);
	val3 = val3 << 8;
	val3 = val3 | (0xFF & val1);
	val3 = val3 << 8;

	if ((id & 0xFF000000) == 0)
		val3 = val3 | (0xFF & (~id));
	else
		val3 = val3 | (0xFF & (id >> 24));

	int bound = len - 1;
	while (bound >= 0) 
	{
		int c = song[bound];
		if (c >= 0x80)
			c = c - 0x100;

		val1 = c + val2;
		val2 = val2 << (bound % 2 + 4);
		val2 = val1 + val2;
		bound -= 1;
	}

	bound = 0;
	val1 = 0;
	while (bound <= len - 1)
	{
		int c = song[bound];
		if (c >= 0x80)
			c = c - 0x100;

		int val4 = c + val1;
		val1 = val1 << (bound % 2 + 3);
		val1 = val1 + val4;
		bound += 1;
	}

	int val5 = val2 ^ val3;
	val5 = val5 + (val1 | id);
	val5 = val5 * (val1 | val3);
	val5 = val5 * (val2 ^ id);

	SAFE_DELETE_ARRAY(song);
	SAFE_DELETE_ARRAY(artist);
	SAFE_DELETE_ARRAY(title);
	return val5;
}

LYRICINFO* SetLyricInfo(const LYRIC* lyric, HTTPREQ* req)
{
	wchar_t* host = L"http://ttlrcct2.qianqian.com/dll/lyricsvr.dll?dl?Id=";
	unsigned char r = RandomNum(255);
	if (r % 2 == 0)
		host = L"http://ttlrcct.qianqian.com/dll/lyricsvr.dll?dl?Id=";

	DWORD size = wcslen(host) + 64;
	wchar_t* url = new wchar_t[size];
	wmemset(url, 0, size);
	StrCat(url, host);
	size = wcslen(url);
	wsprintf(url + size, L"%d", lyric->id);
	StrCat(url, L"&Code=");
	size = wcslen(url);
	wsprintf(url + size, L"%d", SetCode(lyric));
	LYRICINFO* lyricinfo = NULL;

	if (HttpRequest(req, url, HTTPHEADER, wcslen(HTTPHEADER), NULL, 0) && req->buffer != NULL && req->dataLength > 0)
	{
#ifndef NDEBUG
		HANDLE hfile = CreateFile(L"C:\\lyric.txt", GENERIC_WRITE,	FILE_SHARE_WRITE, NULL,	CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
		if (hfile != NULL)
		{
			char buffer[32] = {0};
			DWORD size = 0;

			sprintf_s(buffer, "%d\r\n", lyric->id);
			WriteFile(hfile, "\xEF\xBB\xBF", 3, &size, NULL);
			WriteFile(hfile, buffer, strlen(buffer), &size, NULL);
			WriteFile(hfile, req->buffer, req->dataLength, &size, NULL);
			CloseHandle(hfile);
		}
#endif
		lyricinfo = ExcuteLyricInfo(req->buffer);		
	}
	
	HttpClose(req, FALSE);
	SAFE_DELETE_ARRAY(url);
	return lyricinfo;
}

LYRICINFO* ExcuteLyricInfo(const char* body)
{
	if (body == NULL)
		return NULL;

	LYRICINFO* first = NULL;	
	DWORD start = 0, current = 0, last = 0;	
	while (TRUE)
	{
		if (current > 0 && (body[current] == '\r' || body[current] == '\n' || body[current] == '\0'))
		{
			char* content = NULL;
			last = current - 1;
			while (body[last] != ']' && start < last)			
				last--;

			if (body[last] == ']')
			{				
				if (current > last + 1)
				{
					DWORD len = current - last;
					content = new char[len];
					ZeroMemory(content, len);
					CopyMemory(content, body + last + 1, len - 1);	
				}

				while (start < last)
				{				
					int ms = 0;
					if (body[start] == '[' && GetMillisecond(body + start, ms))
					{
						LYRICINFO* lyricinfo = InsertLyricInfo(ms, content, first);
						if (lyricinfo != NULL)
							first = lyricinfo;						
					}

					start++;
				}
			}

			SAFE_DELETE_ARRAY(content);
			if (body[current] == '\0')
				break;

			current++;			
			while (body[current] == '\r' || body[current] == '\n')			
				current++;

			if (body[current] == '\0')
				break;
			else
				start = current;
		}

		current++;		
	}

	if (first != NULL)
	{
		LYRICINFO* current = first;
		LYRICINFO* prev = NULL;

		while (current != NULL)
		{
			if (prev != NULL)
				prev->length = current->ms - prev->ms;

			prev = current;
			current = current->next;
		}
	}

	return first;
}

LYRICINFO* InsertLyricInfo(int ms, const char* content, LYRICINFO* first)
{		
	LYRICINFO* lyricinfo = new LYRICINFO();
	lyricinfo->ms = ms;
	if (content != NULL)
		lyricinfo->content = UTF8toWideChar(content, strlen(content));
	else
		lyricinfo->content = NULL;

	lyricinfo->length = 0;
	lyricinfo->delay = 0;
	lyricinfo->next = NULL;

	if (first == NULL)
		return lyricinfo;
	
	LYRICINFO* current = first;
	LYRICINFO* prev = NULL;

	while (current != NULL)
	{
		if (ms < current->ms)
			break;
		else
		{
			prev = current;
			current = current->next;
		}
	}

	if (current == NULL)
		prev->next = lyricinfo;
	else
	{
		if (prev != NULL)
			prev->next = lyricinfo;

		lyricinfo->next = current;
	}

	return NULL;
}

void LyricReset(LYRIC* lyric, PLAYERINFO* playerinfo)
{
	EnterCriticalSection(&playerinfo->lock_lyric);
	LyricClear(playerinfo->lyric);
	playerinfo->lyric = lyric;
	LeaveCriticalSection(&playerinfo->lock_lyric);
}

void LyricClear(LYRIC* lyric)
{
	while (lyric != NULL)
	{
		SAFE_DELETE_ARRAY(lyric->artist);
		SAFE_DELETE_ARRAY(lyric->title);
		LYRIC* current = lyric;
		lyric = lyric->next;
		SAFE_DELETE(current);
	}
}

void LyricInfoReset(LYRICINFO* lyricinfo, LYRIC* lyric, PLAYERINFO* playerinfo, BOOL redraw)
{
	EnterCriticalSection(&playerinfo->lock_lyricinfo);
	LyricInfoClear(playerinfo->lyricinfo);
	playerinfo->lyricinfo = lyricinfo;
	playerinfo->lyricid = lyric->id;
	
	if (redraw)
	{
		if (lyric_wnd.run)
		{
			int ms = playerinfo->ms;
			LyricInfoTime(ms, playerinfo);			
		}
		else
		{			
			lyric_wnd.automatic = FALSE;
			SetEvent(lyric_wnd.handle);
		}
	}

	LeaveCriticalSection(&playerinfo->lock_lyricinfo);
}

void LyricInfoReset(int seek, DWORD start, PLAYERINFO* playerinfo)
{
	if (lyric_wnd.run)
	{
		EnterCriticalSection(&playerinfo->lock_lyricinfo);
		int* ms = &playerinfo->ms;
		*ms = seek + (timeGetTime() - start);
		LyricInfoTime(*ms, playerinfo);
		LeaveCriticalSection(&playerinfo->lock_lyricinfo);
	}
}

void LyricInfoTime(int ms, const PLAYERINFO* playerinfo)
{
	LYRICINFO* prev = NULL;
	lyric_wnd.current = playerinfo->lyricinfo;
	lyric_wnd.next = NULL;

	while (lyric_wnd.current != NULL)
	{
		if (ms < lyric_wnd.current->ms + lyric_wnd.current->delay)
			break;
		else
		{
			prev = lyric_wnd.current;
			lyric_wnd.current = lyric_wnd.current->next;
		}
	}

	if (lyric_wnd.current != NULL)
	{				
		if (prev != NULL)
		{						
			lyric_wnd.next = lyric_wnd.current;
			lyric_wnd.current = prev;					
		}
		else									
			lyric_wnd.next = lyric_wnd.current->next;				
	}
}

void LyricInfoReStart(const PLAYERINFO* playerinfo)
{
	if (playerinfo == NULL)
		return;

	if (!lyric_wnd.run)
	{		
		lyric_wnd.automatic = TRUE;
		SetEvent(lyric_wnd.handle);
	}
}

void LyricInfoClear(LYRICINFO* lyricinfo)
{
	while (lyricinfo != NULL)
	{
		SAFE_DELETE_ARRAY(lyricinfo->content);		
		LYRICINFO* current = lyricinfo;
		lyricinfo = lyricinfo->next;
		SAFE_DELETE(current);
	}
}

void LyricHwndClear()
{
	RECT* rect = &lyric_wnd.rect;
	HDC* bdc = &lyric_wnd.bdc;
	HDC* mdc = &lyric_wnd.mdc;
	FillRect(*bdc, rect, lyric_wnd.brush);
	BitBlt(*mdc, 0, 0, rect->right - rect->left, rect->bottom - rect->top, *bdc, 0, 0, SRCCOPY);
}

void LyricDraw(PLAYERINFO* playerinfo)
{	
	CRITICAL_SECTION* lock_lyricinfo = &playerinfo->lock_lyricinfo;
#ifndef NDEBUG
	EnterCriticalSection(lock_lyricinfo);
	HANDLE hfile = CreateFile(L"C:\\ly.txt", GENERIC_WRITE,	FILE_SHARE_WRITE, NULL,	CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
	if (hfile != NULL)
	{
		DWORD size = 0;		
		WriteFile(hfile, "\xEF\xBB\xBF", 3, &size, NULL);
		char buffer[16] = {0};
		LYRICINFO* p = playerinfo->lyricinfo;

		while (p != NULL)
		{			
			sprintf_s(buffer, "%d", p->ms);
			WriteFile(hfile, "[", 1, &size, NULL);
			WriteFile(hfile, buffer, strlen(buffer), &size, NULL);
			WriteFile(hfile, "]", 1, &size, NULL);
			if (p->content != NULL)
			{
				char* content = WideChartoUTF8(p->content, wcslen(p->content));
				WriteFile(hfile, content, strlen(content), &size, NULL);
				SAFE_DELETE_ARRAY(content);
			}
			
			WriteFile(hfile, " ", 1, &size, NULL);
			sprintf_s(buffer, "%d", p->length);
			WriteFile(hfile, buffer, strlen(buffer), &size, NULL);
			WriteFile(hfile, " ", 1, &size, NULL);
			WriteFile(hfile, "\r\n", 2, &size, NULL);
			p = p->next;
		}

		CloseHandle(hfile);
	}
	LeaveCriticalSection(lock_lyricinfo);
#endif
	int* ms = &playerinfo->ms;
	LYRICINFO* prev = NULL;
	PLAYERSTATUS* status = lyric_wnd.status;	
	RECT* rect = &lyric_wnd.rect;
	HDC* mdc = &lyric_wnd.mdc;
	HDC* bdc = &lyric_wnd.bdc;	
	BOOL scroll = FALSE;
	BOOL entry = FALSE;
	DWORD sleep = 30;	
	LONG currentY = 0, nextY = 0;
	LONG step = 4;
	LONG* h = &lyric_wnd.height;
	SIZE size = {0};
		
	while (*status != ID_STATUS_STOP && *status != ID_STATUS_EXIT)
	{
		EnterCriticalSection(lock_lyricinfo);
		if (!entry)
		{
			currentY = 0;
			nextY = 0;
			scroll = FALSE;
			step = 4;			
			prev = NULL;
			lyric_wnd.current = playerinfo->lyricinfo;
			lyric_wnd.next = NULL;

			while (lyric_wnd.current != NULL)
			{
				if (*ms < lyric_wnd.current->ms + lyric_wnd.current->delay)
					break;
				else
				{
					prev = lyric_wnd.current;
					lyric_wnd.current = lyric_wnd.current->next;
				}
			}

			if (lyric_wnd.current != NULL)
			{				
				if (prev != NULL)
				{						
					lyric_wnd.next = lyric_wnd.current;
					lyric_wnd.current = prev;					
				}
				else									
					lyric_wnd.next = lyric_wnd.current->next;				
			}
			
			entry = TRUE;
		}
		else
		{
			if (scroll)
			{
				if (nextY - step <= 0)
				{						
					lyric_wnd.current = lyric_wnd.next;
					lyric_wnd.next = lyric_wnd.next->next;
					scroll = FALSE;	
				}
				else
				{
					FillRect(*bdc, rect, lyric_wnd.brush);
					currentY -= step;
					nextY -= step;					

					if (lyric_wnd.current != NULL && lyric_wnd.current->content != NULL && GetTextExtentPoint(*bdc, lyric_wnd.current->content, wcslen(lyric_wnd.current->content), &size))
					{						
						LONG x = (rect->right - rect->left - size.cx) / 2;
						SetTextColor(*bdc, lyric_wnd.background);
						TextOut(*bdc, x, currentY, lyric_wnd.current->content, wcslen(lyric_wnd.current->content));					
					}

					if (lyric_wnd.next != NULL && lyric_wnd.next->content != NULL && GetTextExtentPoint(*bdc, lyric_wnd.next->content, wcslen(lyric_wnd.next->content), &size))
					{						
						LONG x = (rect->right - rect->left - size.cx) / 2;
						SetTextColor(*bdc, lyric_wnd.foreground);
						TextOut(*bdc, x, nextY, lyric_wnd.next->content, wcslen(lyric_wnd.next->content));						
					}

					BitBlt(*mdc, rect->left, rect->top, rect->right - rect->left, rect->bottom - rect->top, *bdc, 0, 0, SRCCOPY);
				}
			}
			else
			{					
				if (lyric_wnd.next != NULL && (lyric_wnd.next->ms + lyric_wnd.next->delay - *ms <= sleep + 15 || *ms > lyric_wnd.next->ms + lyric_wnd.next->delay))
				{										
					currentY = 0;
					nextY = *h;
					scroll = TRUE;					

					/*if (next->length < 3000)
						step = nextY < 10 ? 1 : nextY / 10;
					else
					{
						if (next->length > 5000)
							step = nextY < 32 ? 1 : nextY / 32;
						else
							step = nextY < 16 ? 1 : nextY / 16;
					}*/

					if (lyric_wnd.next->length < 3000)
						step = 6;
					else
					{
						if (lyric_wnd.next->length > 5000)
							step = 2;
						else
							step = 4;
					}
				}
				else
				{					
					FillRect(*bdc, rect, lyric_wnd.brush);
					SetTextColor(*bdc, lyric_wnd.foreground);
					if (lyric_wnd.current != NULL && lyric_wnd.current->content != NULL)
						DrawText(*bdc, lyric_wnd.current->content, -1, rect, DT_CENTER);

					BitBlt(*mdc, rect->left, rect->top, rect->right - rect->left, rect->bottom - rect->top, *bdc, 0, 0, SRCCOPY);					
				}
			}
		}

		LeaveCriticalSection(lock_lyricinfo);
		Sleep(sleep);
	}

	lyric_wnd.current = NULL;
	lyric_wnd.next = NULL;
}

LRESULT CALLBACK LyricProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_NCHITTEST:
		{
			UINT hit = DefWindowProc(hWnd, WM_NCHITTEST, wParam, lParam);
			if (hit == HTCLIENT)
				return HTCAPTION;
			else
				return hit;
		}
		break;
	
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

DWORD WINAPI LyricThread(LPVOID param)
{	
	while (TRUE)
	{		
		DWORD ret = WaitForSingleObject(lyric_wnd.handle, INFINITE);
		if (ret == WAIT_OBJECT_0)
		{
			PLAYERSTATUS* status = lyric_wnd.status;
			if (*status == ID_STATUS_STOP)
			{
				SetEvent(lyric_wnd.stop);
				continue;
			}

			if (*status == ID_STATUS_START)
			{
				ResetEvent(lyric_wnd.stop);
				PLAYERENTRY* player = lyric_wnd.player;
				if (player != NULL)
				{
					PLAYERINFO* playerinfo = player->current;
					if (playerinfo != NULL)
					{
						PLAYERTAG* playertag = &playerinfo->tag;
						if (lyric_wnd.automatic)
						{	
							HTTPREQ* httpreq = &lyric_wnd.httpreq;
							if (playerinfo->lyric == NULL)
							{								
								LYRIC* lyric = GetLyric(playertag->artist, playertag->title, httpreq);
								if (lyric != NULL)								
									LyricReset(lyric, playerinfo);								
							}
							
							if (GetLyricStatus())
							{
								lyric_wnd.run = TRUE;
								LYRIC* ly = SelectLyric(playerinfo);
								if (ly != NULL)
								{								
									LYRICINFO* lyricinfo = SetLyricInfo(ly, httpreq);
									if (lyricinfo != NULL)
									{
										LyricInfoReset(lyricinfo, ly, playerinfo, FALSE);
										LyricDraw(playerinfo);
										LyricHwndClear();
									}
								}

								lyric_wnd.run = FALSE;
							}							
						}
						else
						{
							lyric_wnd.run = TRUE;
							LyricDraw(playerinfo);
							LyricHwndClear();
							lyric_wnd.run = FALSE;							
						}
					}
				}
				
				LyricSearchClear(TRUE);
				SetEvent(lyric_wnd.stop);
			}

			if (*status == ID_STATUS_EXIT)
				break;
		}
	}

	DeleteObject(lyric_wnd.bitmap);
	DeleteObject(lyric_wnd.hfont);
	DeleteObject(lyric_wnd.brush);
	DeleteDC(lyric_wnd.bdc);	
	ReleaseDC(lyric_wnd.hWnd, lyric_wnd.mdc);	
	CloseHandle(lyric_wnd.stop);
	CloseHandle(lyric_wnd.handle);
	CloseHandle(lyric_wnd.thread);
	HttpClose(&lyric_wnd.httpreq, TRUE);
	SetEvent(lyric_wnd.exit);
	return 1;
}
