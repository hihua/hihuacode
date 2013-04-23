#include "stdafx.h"
#include "Util.h"

void AppendBuffer(char** buffer, DWORD & dataLength, DWORD & bufferLength, const char* data, DWORD size)
{
	if (*buffer == NULL)
	{		
		DWORD total = size;
		*buffer = new char[total + 1];
		ZeroMemory(*buffer, total + 1);
		CopyMemory(*buffer, data, size);
		dataLength = size;
		bufferLength = total;
	}
	else
	{
		if (size >= bufferLength - dataLength)
		{
			DWORD total = bufferLength + size;
			char* pool = new char[total + 1];
			ZeroMemory(pool, total + 1);
			CopyMemory(pool, *buffer, dataLength);
			CopyMemory(pool + dataLength, data, size);
			dataLength += size;
			bufferLength = total;

			delete[] *buffer;
			*buffer = pool;
		}
		else
		{
			char* p = *buffer + dataLength;
			CopyMemory(p, data, size);
			dataLength += size;
			(*buffer)[dataLength] = '\0';
		}
	}
}

BOOL LoadImage(const HINSTANCE& inst, DWORD res, const TCHAR* name, IMGINFO* img_info, const HDC* mdc)
{
	HRSRC src = FindResource(inst, MAKEINTRESOURCE(res), name);
	if (src == NULL)
		return FALSE;

	DWORD len = SizeofResource(inst, src);
	BYTE* byte = (BYTE*)LoadResource(inst, src);
	if (byte == NULL)
		return FALSE;
	
	HGLOBAL global = GlobalAlloc(GMEM_FIXED, len);
	BYTE* mem = (BYTE*)GlobalLock(global);
	CopyMemory(mem, byte, len);	
	IStream* stream = NULL;
	Gdiplus::Image* image = NULL;
	HRESULT hr = CreateStreamOnHGlobal(mem, FALSE, &stream);
	if (SUCCEEDED(hr))
	{
		image = Gdiplus::Image::FromStream(stream);
		img_info->hdc = CreateCompatibleDC(*mdc);
		HBITMAP hbitmap = CreateCompatibleBitmap(*mdc, image->GetWidth(), image->GetHeight());
		SelectObject(img_info->hdc, hbitmap);		
		Gdiplus::Graphics graphics(img_info->hdc);		
		Gdiplus::Rect rect;
		rect.X = 0.0F;
		rect.Y = 0.0F;
		rect.Width = image->GetWidth();
		rect.Height = image->GetHeight();
		Gdiplus::Status s = graphics.DrawImage(image, rect);		
		DeleteObject(hbitmap);
		img_info->image = image;
	}

	GlobalUnlock(mem);
	SAFE_RELEASE(stream);
	FreeResource(byte);

	if (SUCCEEDED(hr))
		return TRUE;
	else
		return FALSE;
}

BOOL IsNumber(const char* buffer)
{
	if (buffer == NULL)
		return FALSE;

	for (DWORD i = 0;i < strlen(buffer);i++)
	{
		if (buffer[i] < '0' || buffer[i] > '9')
			return FALSE;
	}

	return TRUE;
}

BOOL IsNumber(const wchar_t* buffer)
{
	if (buffer == NULL)
		return FALSE;

	for (DWORD i = 0;i < wcslen(buffer);i++)
	{
		if (buffer[i] < '0' || buffer[i] > '9')
			return FALSE;
	}

	return TRUE;
}

void Split(const wchar_t* content, const wchar_t* string, wchar_t** first, wchar_t** second)
{
	if (wcslen(content) > wcslen(string))
	{
		wchar_t* p = StrStr(content, string);
		if (p == NULL)
			return;

		DWORD length = p - content;
		if (length > 0)
		{
			*first = new wchar_t[length + 1];
			wmemset(*first, 0, length + 1);
			StrNCat(*first, content, length + 1);			
		}

		p += wcslen(string);
		length = wcslen(content) - (p - content);
		if (length > 0)
		{
			*second = new wchar_t[length + 1];
			wmemset(*second, 0, length + 1);
			StrNCat(*second, p, length + 1);
		}
	}
}

BOOL CharToWideChar(const char* in, DWORD in_length, wchar_t* out, DWORD out_length)
{
	int len = MultiByteToWideChar(CP_ACP, 0, in, in_length, NULL, 0);
	if (len > 0)
	{
		if (len > out_length)		
			return FALSE;

		MultiByteToWideChar(CP_ACP, 0, in, in_length, out, len);
		return TRUE;
	}
	else
		return FALSE;
}

wchar_t* CharToWideChar(const char* buffer, DWORD length)
{
	int len = MultiByteToWideChar(CP_ACP, 0, buffer, length, NULL, 0);
	if (len > 0)
	{
		wchar_t* wchar = new wchar_t[len + 1];		
		wmemset(wchar, 0, len + 1);
		int error = MultiByteToWideChar(CP_ACP, 0, buffer, length, wchar, len);
		if (error == len)		
			return wchar;
		else
		{
			SAFE_DELETE_ARRAY(wchar);
			return NULL;
		}
	}
	else
		return NULL;
}

char* WideCharToChar(const wchar_t* buffer, DWORD length)
{
	int len = WideCharToMultiByte(CP_ACP, 0, buffer, length, NULL, 0, NULL, NULL);
	if (len > 0)
	{
		char* ch = new char[len + 1];
		ZeroMemory(ch, len + 1);		
		int error = WideCharToMultiByte(CP_ACP, 0, buffer, length, ch, len, NULL, NULL);
		if (error == len)		
			return ch;
		else
		{
			SAFE_DELETE_ARRAY(ch);
			return NULL;
		}
	}
	else
		return NULL;
}

char* ANSItoUTF8(const char* buffer, DWORD length)
{
	wchar_t* wchar = CharToWideChar(buffer, length);
	if (wchar == NULL)
		return NULL;

	int len = WideCharToMultiByte(CP_UTF8, 0, wchar, -1, NULL, 0, NULL, NULL);
	if (len > 0)
	{
		char* ch = new char[len];
		ZeroMemory(ch, len);
		int error = WideCharToMultiByte(CP_UTF8, 0, wchar, -1, ch, len, NULL, NULL);
		SAFE_DELETE_ARRAY(wchar);
		if (error == len)
			return ch;
		else
		{
			SAFE_DELETE_ARRAY(ch);
			return NULL;
		}
	}

	SAFE_DELETE_ARRAY(wchar);
	return NULL;
}

wchar_t* UTF8toWideChar(const char* buffer, DWORD length)
{
	char tmp[4] = {4};
	if (length < 3)
		return NULL;

	CopyMemory(tmp, buffer, 3);
	if (strcmp(tmp, "\xEF\xBB\xBF") == 0)
	{
		buffer = buffer + 3;
		length -= 3;
	}

	int len = MultiByteToWideChar(CP_UTF8, 0, buffer, -1, NULL, 0);
	if (len > 0)
	{
		wchar_t* wchar = new wchar_t[len + 1];
		wmemset(wchar, 0, len + 1);
		int error = MultiByteToWideChar(CP_UTF8, 0, buffer, -1, wchar, len);
		if (error == len)
			return wchar;
	}

	return NULL;
}

char* WideChartoUTF8(const wchar_t* buffer, DWORD length)
{	
	int len = WideCharToMultiByte(CP_UTF8, 0, buffer, length, NULL, 0, NULL, NULL);
	if (len > 0)
	{
		char* ch = new char[len + 1];
		ZeroMemory(ch, len + 1);		
		int error = WideCharToMultiByte(CP_UTF8, 0, buffer, length, ch, len, NULL, NULL);
		if (error == len)		
			return ch;
		else
		{
			SAFE_DELETE_ARRAY(ch);
			return NULL;
		}
	}
	else
		return NULL;
}

char* CharToHex(const char* buffer, DWORD length)
{
	if (length == 0)
		return NULL;

	DWORD len = length * 2 + 1;
	char* content = new char[len];
	ZeroMemory(content, len);

	DWORD i = 0;
	char tmp[3] = {0};
	while (i < length)
	{
		sprintf_s(tmp, "%02X", (unsigned char)buffer[i]);
		strcat_s(content, strlen(tmp), tmp);
		i++;
	}

	return content;
}

wchar_t* WideCharToHex(const wchar_t* buffer, DWORD length)
{
	if (length == 0)
		return NULL;

	DWORD len = length * 4;
	wchar_t* content = new wchar_t[len + 1];
	wmemset(content, 0, len + 1);

	DWORD i = 0;
	wchar_t tmp[5] = {0};
	wchar_t rev[2] = {0};
	while (i < length)
	{
		wsprintf(tmp, L"%02X", buffer[i]);
		if (buffer[i] > 0x7F)
		{
			rev[0] = tmp[0];
			rev[1] = tmp[1];
			tmp[0] = tmp[2];
			tmp[1] = tmp[3];
			tmp[2] = rev[0];
			tmp[3] = rev[1];
		}
		else
		{
			tmp[2] = '0';
			tmp[3] = '0';
		}

		StrCat(content, tmp);
		i++;
	}

	return content;
}

char* UTF8toANSI(const char* buffer, DWORD length)
{
	char tmp[4] = {0};
	if (length < 3)
		return NULL;

	CopyMemory(tmp, buffer, 3);
	if (strcmp(tmp, "\xEF\xBB\xBF") == 0)
	{
		buffer = buffer + 3;
		length -= 3;
	}

	int len = MultiByteToWideChar(CP_UTF8, 0, buffer, -1, NULL, 0);
	if (len > 0)
	{
		wchar_t* wchar = new wchar_t[len + 1];
		wmemset(wchar, 0, len + 1);
		int error = MultiByteToWideChar(CP_UTF8, 0, buffer, -1, wchar, len);
		if (error == len)
		{
			char* ch = WideCharToChar(wchar, wcslen(wchar));
			SAFE_DELETE_ARRAY(wchar);
			return ch;
		}
		else
		{
			SAFE_DELETE_ARRAY(wchar);
			return NULL;
		}
	}

	return NULL;
}

wchar_t* GetFileName(const char* filename)
{
	const char* p = strrchr(filename, '.');
	const char* q = strrchr(filename, '\\');
	if (p - 1 >= q + 1)	
		return CharToWideChar(q + 1, p - q - 1);
	else
		return NULL;
}

BOOL HttpRequest(HTTPREQ* req, const wchar_t* url, const wchar_t* header, DWORD header_length, const char* body, DWORD body_length)
{
	BOOL ret = FALSE;
	req->internet = WinHttpOpen(NULL, NULL, NULL, NULL, NULL);
	if (req->internet != NULL)
	{
		URL_COMPONENTS urlinfo = {0};
		urlinfo.dwStructSize = sizeof(URL_COMPONENTS);
		urlinfo.dwSchemeLength    = (DWORD)-1;
		urlinfo.dwUserNameLength  = (DWORD)-1;
		urlinfo.dwHostNameLength  = (DWORD)-1;
		urlinfo.dwUrlPathLength   = (DWORD)-1;
		urlinfo.dwExtraInfoLength = (DWORD)-1;
		urlinfo.dwPasswordLength  = (DWORD)-1;
		if (WinHttpCrackUrl(url, wcslen(url), 0, &urlinfo))
		{
			if (WinHttpSetTimeouts(req->internet, req->resolveTimeout, req->connectTimeout, req->sendTimeout, req->receiveTimeout))
			{
				TCHAR* host = new TCHAR[urlinfo.dwHostNameLength + 1];
				wmemset(host, 0, urlinfo.dwHostNameLength + 1);
				StrCpyN(host, urlinfo.lpszHostName, urlinfo.dwHostNameLength + 1);
				req->connect = WinHttpConnect(req->internet, host, urlinfo.nPort, 0);
				if (req->connect != NULL)
				{
					if (body == NULL || body_length == 0)
						req->request = WinHttpOpenRequest(req->connect, L"GET", urlinfo.lpszUrlPath, NULL, WINHTTP_NO_REFERER, WINHTTP_DEFAULT_ACCEPT_TYPES, 0);
					else
						req->request = WinHttpOpenRequest(req->connect, L"POST", urlinfo.lpszUrlPath, NULL, WINHTTP_NO_REFERER, WINHTTP_DEFAULT_ACCEPT_TYPES, 0);

					if (req->request != NULL)
					{						
						if (WinHttpSendRequest(req->request, header, header_length, (void*)body, body_length, body_length, 0) && WinHttpReceiveResponse(req->request, 0))
						{
							TCHAR status[16] = {0};
							DWORD size = sizeof(status);
							if (WinHttpQueryHeaders(req->request, WINHTTP_QUERY_STATUS_CODE, NULL, status, &size, 0))
							{
								if (StrCmp(status, L"200") == 0)
								{
									char buffer[4096] = {0};
									DWORD length = 0;
									
									while (TRUE)
									{
										if (WinHttpReadData(req->request, buffer, sizeof(buffer), &length) && length > 0)
										{
											AppendBuffer(&req->buffer, req->dataLength, req->bufferLength, buffer, length);
											length = 0;
										}
										else
											break;
									}				

									ret = TRUE;
								}
							}
						}
					}
				}

				SAFE_DELETE_ARRAY(host);
			}
		}
	}
	
	return ret;
}

void HttpClose(HTTPREQ* req, BOOL clear)
{
	if (req->request != NULL)
	{
		BOOL ret = WinHttpCloseHandle(req->request);
		req->request = NULL;
	}

	if (req->connect != NULL)
	{
		BOOL ret = WinHttpCloseHandle(req->connect);
		req->connect = NULL;
	}

	if (req->internet != NULL)
	{
		BOOL ret = WinHttpCloseHandle(req->internet);
		req->internet = NULL;
	}

	if (clear)
		SAFE_DELETE_ARRAY(req->buffer);

	req->dataLength = 0;
}

BOOL GetMillisecond(const char* buffer, int& ms)
{		
	char tmp[3] = {0};
	CopyMemory(tmp, buffer + 1, 2);
	if (!IsNumber(tmp))
		return FALSE;

	int minute = atoi(tmp);
	minute = minute * 60 * 1000;
	CopyMemory(tmp, buffer + 4, 2);
	if (!IsNumber(tmp))
	{
		ms = minute;
		return TRUE;
	}

	int second = atoi(tmp);
	second = second * 1000;
	CopyMemory(tmp, buffer + 7, 2);
	if (!IsNumber(tmp))
	{
		ms = minute + second;
		return TRUE;
	}

	int millisecond = atoi(tmp);
	millisecond = millisecond * 10;
	ms = minute + second + millisecond;
	return TRUE;
}

unsigned char RandomNum(unsigned short Num)
{		
	return (unsigned char)(rand() % Num);
}