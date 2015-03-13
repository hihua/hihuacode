#include "stdafx.h"
#include "Spectrum.h"
#include "Util.h"
#include "DxSound.h"

SPECTRUM spectrum;

ATOM SpectrumReg(HINSTANCE hInstance, TCHAR* szWindowClass)
{
	WNDCLASSEX wcex;
	wcex.cbSize			= sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= SpectrumProc;
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

SPECTRUM* SpectrumInit(const RECT* rect, const MAINWND* main_wnd)
{
	spectrum.name = L"Spectrum HWND";
	SpectrumReg(main_wnd->inst, spectrum.name);

	spectrum.delay = 25;
	spectrum.rect.left = 0;
	spectrum.rect.top = rect->top;
	spectrum.rect.right = rect->right;
	spectrum.rect.bottom = rect->bottom;

	LONG x = spectrum.rect.left;
	LONG y = spectrum.rect.top;
	LONG w = spectrum.rect.right - spectrum.rect.left;
	LONG h = spectrum.rect.bottom - spectrum.rect.top;

	spectrum.hWnd = CreateWindowEx(WS_EX_ACCEPTFILES, spectrum.name, NULL, WS_CHILD | WS_VISIBLE, x, y, w, h, main_wnd->hWnd, NULL, main_wnd->inst, NULL);
	if (spectrum.hWnd == NULL)
		return NULL;
	
	spectrum.rect.left = 0;
	spectrum.rect.top = 0;
	spectrum.rect.right = w;
	spectrum.rect.bottom = h;

	spectrum.mdc = GetDC(spectrum.hWnd);
	spectrum.bdc = CreateCompatibleDC(spectrum.mdc);
	spectrum.gdc = CreateCompatibleDC(spectrum.mdc);
	spectrum.pdc = CreateCompatibleDC(spectrum.mdc);
	spectrum.hdc = CreateCompatibleDC(spectrum.mdc);
	spectrum.mbitmap = CreateCompatibleBitmap(spectrum.mdc, spectrum.rect.right - spectrum.rect.left, spectrum.rect.bottom - spectrum.rect.top);
	spectrum.gbitmap = CreateCompatibleBitmap(spectrum.mdc, spectrum.rect.right - spectrum.rect.left, spectrum.rect.bottom - spectrum.rect.top);
	spectrum.pbitmap = CreateCompatibleBitmap(spectrum.mdc, spectrum.rect.right - spectrum.rect.left, spectrum.rect.bottom - spectrum.rect.top);
	spectrum.hbitmap = CreateCompatibleBitmap(spectrum.mdc, spectrum.rect.right - spectrum.rect.left, spectrum.rect.bottom - spectrum.rect.top);
	SelectObject(spectrum.bdc, spectrum.mbitmap);
	SelectObject(spectrum.gdc, spectrum.gbitmap);
	SelectObject(spectrum.pdc, spectrum.pbitmap);
	SelectObject(spectrum.hdc, spectrum.hbitmap);
	spectrum.black = CreateSolidBrush(RGB(0, 0, 0));

	CFastFourierTransform* fft = new CFastFourierTransform(SAMPLESIZE);
	spectrum.fft = fft;
	
	SpectrumArrayReset();
	DWORD colors[] = { RGB(255, 0, 0), RGB(255, 255, 0), RGB(0, 255, 0) };
	if (!SpectrumGradient(&spectrum.gdc, &spectrum.rect, colors, sizeof(colors) / sizeof(DWORD), GRADIENT_FILL_RECT_V))
		return NULL;

	if (!SpectrumGradient(&spectrum.pdc, &spectrum.rect, colors, sizeof(colors) / sizeof(DWORD), GRADIENT_FILL_RECT_V))
		return NULL;

	if (!SpectrumGradient(&spectrum.hdc, &spectrum.rect, colors, sizeof(colors) / sizeof(DWORD), GRADIENT_FILL_RECT_V))
		return NULL;

	for (int i = 0;i < h;i++)
	{
		if (i % 2 == 0)
			continue;

		MoveToEx(spectrum.gdc, 0, i, NULL);
		LineTo(spectrum.gdc, w, i);
	}

	spectrum.handle = CreateEvent(NULL, FALSE, FALSE, NULL);
	spectrum.stop = CreateEvent(NULL, TRUE, TRUE, NULL);
	spectrum.exit = CreateEvent(NULL, FALSE, FALSE, NULL);
	spectrum.thread = CreateThread(NULL, 0, SpectrumThread, &spectrum, 0, &spectrum.thread_id);
	return &spectrum;
}

BOOL SpectrumGradient(HDC* hdc, const RECT* rect, const DWORD* color, int num, DWORD mode)
{
	if (hdc == NULL || color == NULL || num < 1 || rect == NULL || mode == GRADIENT_FILL_TRIANGLE || mode == GRADIENT_FILL_RECT_H)
	{
		SetLastError(ERROR_INVALID_PARAMETER);
		return FALSE;
	}

	if (num == 1)
	{		
		FillRect(*hdc, rect, RGB(0, 0, 0));		
		return TRUE;
	}

	int width = rect->right - rect->left;
	int height = rect->bottom - rect->top;

	GRADIENT_RECT* gradient = new GRADIENT_RECT[num];
	TRIVERTEX* vertex = new TRIVERTEX[num * 2 - 2];

	for (int i = 0;i < num - 1;i++)
	{
		vertex[i * 2].x = rect->left;
		vertex[i * 2].y = rect->top + i * height / (num - 1);

		vertex[i * 2 + 1].x = rect->right;
		vertex[i * 2 + 1].y = rect->top + (i + 1) * height / (num - 1);

		vertex[i * 2].Red    = (WORD)GetRValue((color[i])) << 8;
		vertex[i * 2].Green  = (WORD)GetGValue((color[i])) << 8;
		vertex[i * 2].Blue   = (WORD)GetBValue((color[i])) << 8;
		vertex[i * 2].Alpha  = 0x0000;

		vertex[i * 2 + 1].Red    = (WORD)GetRValue((color[i + 1])) << 8;
		vertex[i * 2 + 1].Green  = (WORD)GetGValue((color[i + 1])) << 8;
		vertex[i * 2 + 1].Blue   = (WORD)GetBValue((color[i + 1])) << 8;
		vertex[i * 2 + 1].Alpha  = 0x0000;

		gradient[i].UpperLeft  = i * 2;
		gradient[i].LowerRight = i * 2 + 1;
	}

	BOOL ret = GradientFill(*hdc, vertex, num * 2, gradient, num - 1, mode);
	
	SAFE_DELETE_ARRAY(gradient);
	SAFE_DELETE_ARRAY(vertex);

	return ret;
}

void SpectrumStart()
{
	SetEvent(spectrum.handle);
}

void SpectrumStop()
{
	SetEvent(spectrum.handle);
	WaitForSingleObject(spectrum.stop, INFINITE);
}

void SpectrumExit()
{	
	SetEvent(spectrum.handle);
	WaitForSingleObject(spectrum.exit, INFINITE);
	CloseHandle(spectrum.exit);
}

void SpectrumSetFPS(DWORD fps)
{
	spectrum.delay = 1000 / fps;
}

void SpectrumClear()
{		
	HDC dc = GetDC(spectrum.hWnd);
	FillRect(dc, &spectrum.rect, spectrum.black);
	ReleaseDC(spectrum.hWnd, dc);
	SpectrumArrayReset();
}

void SpectrumArrayReset()
{
	for (DWORD i = 0;i < BANDCOUNT;i++)
	{
		spectrum.max[i] = spectrum.rect.bottom;
		spectrum.top[i] = spectrum.rect.bottom;
		spectrum.peek[i] = 5;
		spectrum.step[i] = 1;
	}
}

void SpectrumSetPcm(char* buffer, int channels, int bits)
{
	ZeroMemory(spectrum.pcm, sizeof(spectrum.pcm));	
	DWORD a = 0, b = 0;
	float sample = 0.0F, c = 0.0F;

	switch (bits)
	{
	case 1:
		b = 1;
		sample = 128.0F;
		break;

	case 2:
		b = 2;
		sample = 32767.0F;
		break;

	default:
		return;
	}

	switch (channels)
	{
	case 1:
		c = 1.0F;
		break;

	case 2:
		c = 2.0F;		
		break;

	default:
		c = 2.0F;
		break;
	}

	DWORD i = 0, j = 0;
	while (i < SAMPLESIZE)
	{		
		float total = 0.0f;
		j = 0;		
		while (j++ < channels)
		{			
			float size = 0.0f;
			if (bits == 1)
				size = (float)buffer[i];
			else
			{
				if (i + 1 < SAMPLESIZE)
					size = (float)((buffer[i + 1] << 8) + buffer[i]);
				else
					size = (float)buffer[i];
			}

			size /= sample;
			total += size;

			i += b;
			if (i >= SAMPLESIZE)
				break;
		}

		spectrum.pcm[a++] = total / c;
	}
}

void SpectrumBuffer()
{
	PLAYERINFO* playerinfo = spectrum.player->current;
	if (playerinfo != NULL)
	{
		if (DxGetBuffer(SAMPLESIZE, spectrum.buffer, playerinfo))
		{
			int channels = playerinfo->decode.channels;
			int bits = playerinfo->decode.bits;
			SpectrumSetPcm(spectrum.buffer, channels, bits);
		}		
	}
}

void SpectrumDraw()
{
	CFastFourierTransform* fft = spectrum.fft;
	HDC* mdc = &spectrum.mdc;
	HDC* bdc = &spectrum.bdc;
	
	FillRect(*bdc, &spectrum.rect, spectrum.black);
	float* arrays = fft->Calculate(spectrum.pcm, SAMPLESIZE);			
	DWORD multiplier = SAMPLESIZE / BANDCOUNT / 2;			
	DWORD bd = 0;

	for (DWORD a = 0, bd = 0; bd < BANDCOUNT; a += multiplier, bd++) 
	{
		float fs = 0;
		for (int b = 0; b < multiplier; b++) 
		{
			fs += arrays[a + b];			
		}

		fs *= 1.2F;
		fs = (fs * (float)log(bd + 2.0F));
		
		wchar_t buf[128] = {0};
		swprintf(buf, L"%d %d %10.8f", spectrum.rect.bottom, bd, fs);
		DPRINT(buf);

		RECT rect;
		rect.left = bd * BANDWIDTH + bd * BANDSPACE;
		rect.right = rect.left + BANDWIDTH;
		rect.bottom = spectrum.rect.bottom + 2;
		rect.top = rect.bottom - rect.bottom * fs;								

		if (rect.top < spectrum.top[bd])
		{
			spectrum.top[bd] = rect.top;
			spectrum.step[bd] = 2;
		}
		else
		{				
			spectrum.step[bd] += 1;
			spectrum.top[bd] += spectrum.step[bd];
			if (spectrum.top[bd] > rect.bottom)
				spectrum.top[bd] = rect.bottom;
		}

		if (spectrum.top[bd] < spectrum.max[bd])
		{
			spectrum.max[bd] = spectrum.top[bd] - 1;
			if (spectrum.max[bd] < 0)
				spectrum.max[bd] = 0;

			spectrum.peek[bd] = 5;
		}
		else
		{
			spectrum.peek[bd]--;
			if (spectrum.peek[bd] < 0)
				spectrum.max[bd] += 3;

			if (spectrum.max[bd] > rect.bottom)
				spectrum.max[bd] = rect.bottom;
		}

		rect.top = spectrum.top[bd];

		if (rect.top < 0)
		{
			rect.top = 0;
			spectrum.top[bd] = rect.top;
		}

		if (rect.top > rect.bottom)
		{
			rect.top = rect.bottom;
			spectrum.top[bd] = rect.top;
		}

		if (GetSpectrumLine())			
			BitBlt(spectrum.bdc, rect.left, rect.top, BANDWIDTH, rect.bottom - rect.top, spectrum.gdc, rect.left, rect.top, SRCCOPY);
		else
			BitBlt(spectrum.bdc, rect.left, rect.top, BANDWIDTH, rect.bottom - rect.top, spectrum.pdc, rect.left, rect.top, SRCCOPY);

		rect.top = spectrum.max[bd];				
		rect.bottom = rect.top + 1;
		BitBlt(spectrum.bdc, rect.left, rect.top, BANDWIDTH, rect.bottom - rect.top, spectrum.hdc, rect.left, rect.top, SRCCOPY);
	}

	//ReleaseArray(fft);
	BitBlt(spectrum.mdc, spectrum.rect.left, spectrum.rect.top, spectrum.rect.right - spectrum.rect.left, spectrum.rect.bottom - spectrum.rect.top, spectrum.bdc, 0, 0, SRCCOPY);
}

LRESULT CALLBACK SpectrumProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	HDC hdc;

	switch (message)
	{
	case WM_PAINT:
		{		
			hdc = BeginPaint(hWnd, &ps);			
			EndPaint(hWnd, &ps);			
		}	
		break;

	case WM_DROPFILES:
		{
			HDROP hDrop = (HDROP)wParam;
			UINT n = DragQueryFile(hDrop, -1, NULL, 0);
			if (n > 0)
			{		
				wchar_t filename[512] = {0};
				DragQueryFile(hDrop, 0, filename, sizeof(filename));
				PLAYERINFO* playerinfo = new PLAYERINFO();
				ZeroMemory(playerinfo, sizeof(PLAYERINFO));
				playerinfo->filename = WideCharToChar(filename, wcslen(filename));
				playerinfo->n = -1;
				InitializeCriticalSection(&playerinfo->lock_lyric);
				InitializeCriticalSection(&playerinfo->lock_lyricinfo);
				PlayInfoPrepare(playerinfo);
			}

			DragFinish(hDrop);
		}
		break;

	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

DWORD WINAPI SpectrumThread(LPVOID param)
{	
	while (TRUE)
	{		
		DWORD ret = WaitForSingleObject(spectrum.handle, INFINITE);
		if (ret == WAIT_OBJECT_0)
		{			
			if (*spectrum.status == ID_STATUS_STOP)
			{
				SetEvent(spectrum.stop);
				continue;
			}

			if (*spectrum.status == ID_STATUS_START)
			{
				ResetEvent(spectrum.stop);
				DWORD current = 0, last = 0, delay = 0;
				//LARGE_INTEGER start, end, frequent;

				PLAYERSTATUS status = *spectrum.status;

				while (TRUE)
				{
					if (*spectrum.status == ID_STATUS_PAUSE && status != *spectrum.status)
						SpectrumClear();

					status = *spectrum.status;
					if (status == ID_STATUS_STOP || status == ID_STATUS_EXIT)
						break;

					if (status == ID_STATUS_PAUSE)
					{
						Sleep(100);
						continue;
					}

					current = timeGetTime();
					//QueryPerformanceCounter(&start); 
					SpectrumBuffer();
					SpectrumDraw();
					//QueryPerformanceCounter(&end);
					//BOOL a = QueryPerformanceFrequency(&frequent);

					last = timeGetTime();
					delay = last - current;
					if (delay < spectrum.delay)
						Sleep(spectrum.delay - delay);	
				}

				SpectrumClear();
				SetEvent(spectrum.stop);
			}

			if (*spectrum.status == ID_STATUS_EXIT)
				break;
		}
	}
	
	SAFE_DELETE(spectrum.fft);
	DeleteObject(spectrum.black);
	DeleteObject(spectrum.mbitmap);
	DeleteObject(spectrum.gbitmap);
	DeleteObject(spectrum.pbitmap);
	DeleteObject(spectrum.hbitmap);
	DeleteDC(spectrum.bdc);
	DeleteDC(spectrum.gdc);
	DeleteDC(spectrum.pdc);
	DeleteDC(spectrum.hdc);
	ReleaseDC(spectrum.hWnd, spectrum.mdc);	
	CloseHandle(spectrum.stop);
	CloseHandle(spectrum.handle);
	CloseHandle(spectrum.thread);
	SetEvent(spectrum.exit);
	return 1;
}