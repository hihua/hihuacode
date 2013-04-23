#include "AudioPlayer.h"

void AppendBuffer(char** buffer, DWORD & dataLength, DWORD & bufferLength, const char* data, DWORD size);
BOOL LoadImage(const HINSTANCE& inst, DWORD res, const TCHAR* name, IMGINFO* img_info, const HDC* mdc);
BOOL IsNumber(const char* buffer);
BOOL IsNumber(const wchar_t* buffer);
void Split(const wchar_t* content, const wchar_t* string, wchar_t** first, wchar_t** second);
BOOL CharToWideChar(const char* in, DWORD in_length, wchar_t* out, DWORD out_length);
wchar_t* CharToWideChar(const char* buffer, DWORD length);
char* WideCharToChar(const wchar_t* buffer, DWORD length);
char* ANSItoUTF8(const char* buffer, DWORD length);
wchar_t* UTF8toWideChar(const char* buffer, DWORD length);
char* WideChartoUTF8(const wchar_t* buffer, DWORD length);
char* CharToHex(const char* buffer, DWORD length);
wchar_t* WideCharToHex(const wchar_t* buffer, DWORD length);
char* UTF8toANSI(const char* buffer, DWORD length);
wchar_t* GetFileName(const char* filename);
BOOL HttpRequest(HTTPREQ* req, const wchar_t* url, const wchar_t* header, DWORD header_length, const char* body, DWORD body_length);
void HttpClose(HTTPREQ* req, BOOL clear);
BOOL GetMillisecond(const char* buffer, int& ms);
unsigned char RandomNum(unsigned short Num);