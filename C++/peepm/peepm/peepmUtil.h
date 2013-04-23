#pragma once

#define ReleaseObject(x) if (x != NULL){delete x;x = NULL;}
#define ReleaseArray(x) if (x != NULL){delete[] x;x = NULL;}

class CpeepmUtil
{
public:
	static void AppendBuffer(char** buffer, UINT & dataLength, UINT & bufferLength, const char* data, UINT size);
	static wchar_t* CharToWideChar(const char* buffer, UINT length);
	static char* WideCharToChar(const wchar_t* buffer, UINT length);
	static char* GBtoUTF8(const char* buffer, UINT length);
	static char* UTF8toGB(const char* buffer, UINT length);
	static char* UrlEncode(const char* buffer, UINT length);
};
