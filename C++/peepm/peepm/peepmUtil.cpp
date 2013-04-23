#include "StdAfx.h"
#include "peepmUtil.h"

void CpeepmUtil::AppendBuffer(char** buffer, UINT & dataLength, UINT & bufferLength, const char* data, UINT size)
{
	if (*buffer == NULL)
	{		
		UINT total = size;
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
			UINT total = bufferLength + size;
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

wchar_t* CpeepmUtil::CharToWideChar(const char* buffer, UINT length)
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
			ReleaseArray(wchar);
			return NULL;
		}
	}
	else
		return NULL;
}

char* CpeepmUtil::WideCharToChar(const wchar_t* buffer, UINT length)
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
			ReleaseArray(ch);
			return NULL;
		}
	}
	else
		return NULL;
}

char* CpeepmUtil::GBtoUTF8(const char* buffer, UINT length)
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
		ReleaseArray(wchar);
		if (error == len)
			return ch;
		else
		{
			ReleaseArray(ch);
			return NULL;
		}
	}

	ReleaseArray(wchar);
	return NULL;
}

char* CpeepmUtil::UTF8toGB(const char* buffer, UINT length)
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
		{
			char* ch = WideCharToChar(wchar, wcslen(wchar));
			ReleaseArray(wchar);
			return ch;
		}
		else
		{
			ReleaseArray(wchar);
			return NULL;
		}
	}

	return NULL;
}

char* CpeepmUtil::UrlEncode(const char* buffer, UINT length)
{
	UINT len = length * 3;
	char* code = new char[len + 1];
	ZeroMemory(code, len + 1);
	UINT j = 0;

	for (UINT i = 0;i < length;i++)
	{
		char ch = buffer[i];
		if (ch >= 'A' && ch <= 'Z')
			code[j++] = ch;
		else
		{
			if (ch >= 'a' && ch <= 'z')
				code[j++] = ch;
			else
			{
				if (ch >= '0' && ch <= '9')
					code[j++] = ch;
				else
				{
					if (ch == ' ')
						code[j++] = '+';
					else
					{
						sprintf(code + j, "%%%02X", (unsigned char)ch);
						j += 3;
					}
				}
			}
		}
	}

	return code;
}