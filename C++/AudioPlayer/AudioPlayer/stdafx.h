#pragma once

#include "targetver.h"

#define WIN32_LEAN_AND_MEAN 
// Windows 头文件:
#include <windows.h>
#include <math.h>

// C 运行时头文件
#include <stdlib.h>
#include <malloc.h>
#include <memory.h>
#include <tchar.h>
#include <shlwapi.h>
#include <commdlg.h>
#include <GdiPlus.h>
#include <GdiplusEnums.h>

#include <mmsystem.h>
#include <mmreg.h>
#include <dsound.h>
#include <ks.h>
#include <KsMedia.h>

#include <commctrl.h>
#include <shellapi.h>
#include <shlobj.h>
#include <comdef.h>
#include <gdiplus.h>
#include <winhttp.h>
#include <winsock2.h>

#include <winioctl.h>
#include <ntddscsi.h>
#include <ntddcdrm.h>

#pragma comment(lib, "wsock32.lib")
#pragma comment(lib, "comctl32.lib")
#pragma comment(lib, "gdiplus.lib")
#pragma comment(lib, "dsound.lib")
#pragma comment(lib, "dxguid.lib")
#pragma comment(lib, "winmm.lib")
#pragma comment(lib, "msimg32.lib")
#pragma comment(lib, "shlwapi.lib")
#pragma comment(lib, "winhttp.lib")

#ifdef __cplusplus
extern "C" 
{
#endif

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswresample/swresample.h>

//#pragma comment(lib, "avcodec.lib")
//#pragma comment(lib, "avformat.lib")
//#pragma comment(lib, "avdevice.lib")
//#pragma comment(lib, "avutil.lib")
//#pragma comment(lib, "swscale.lib")
//#pragma comment(lib, "swresample.lib")
//#pragma comment(lib, "avfilter.lib")

#pragma comment(lib, "libgcc.a")
#pragma comment(lib, "libmingwex.lib")
#pragma comment(lib, "libcoldname.a")

#pragma comment(lib, "libavcodec.a")
#pragma comment(lib, "libavformat.a")
#pragma comment(lib, "libavdevice.a")
#pragma comment(lib, "libavutil.a")
#pragma comment(lib, "libswscale.a")
#pragma comment(lib, "libswresample.a")
#pragma comment(lib, "libavfilter.a")
#ifdef __cplusplus
}
#endif

#ifdef _DEBUG
#pragma comment(lib, "libzip/zlib_debug.lib")
#pragma comment(lib, "soundtouch/SoundTouchD.lib")
#else
#pragma comment(lib, "libzip/zlib_release.lib")
#pragma comment(lib, "soundtouch/SoundTouch.lib")
#endif

#define DPRINT(x) if (x != NULL) {OutputDebugString(x);OutputDebugString(L"\n");} else {OutputDebugString(L"\n");}
#define DPRINT_D(x) {wchar_t buffer[32] = {0};wsprintf(buffer, L"%d", x);DPRINT(buffer);}

#define SAFE_FREE(x) if (x != NULL) {free(x);x = NULL;}
#define SAFE_DELETE(x) if (x != NULL) {delete x;x = NULL;}
#define SAFE_DELETE_ARRAY(x) if (x != NULL) {delete[] x;x = NULL;}
#define SAFE_RELEASE(x) if (x != NULL) {x->Release();x = NULL;}

#include "soundtouch/SoundTouch.h"
//#include "libvld/vld.h"

using namespace soundtouch;
using namespace std;