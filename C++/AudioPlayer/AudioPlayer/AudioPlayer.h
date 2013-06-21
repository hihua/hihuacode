#pragma once

#include "resource.h"
#include "FastFourierTransform.h"

#define BANDCOUNT		40
#define BANDWIDTH		6
#define BANDSPACE		1
#define SAMPLESIZE		2048

#define MAX_LOADSTRING	100
#define TRACK_PROGRESS	100

#define WM_CDROM		0x200
#define RAW_SECTOR_SIZE	2352
#define CD_READ_SIZE	2048
#define CD_ALIGN		4
#define CD_SAMPLE		44100
#define CD_BYTES_SECOND	CD_SAMPLE * CD_ALIGN

enum PLAYERSTATUS { ID_STATUS_START = 1, ID_STATUS_STOP, ID_STATUS_PAUSE, ID_STATUS_EXIT };

struct IMGINFO
{
	HDC hdc;	
	Gdiplus::Image* image;
};

struct PLAYERBUFFER
{
	char* buffer;
	DWORD total;
	DWORD half;
};

struct LYRIC
{
	DWORD id;
	wchar_t* artist;
	wchar_t* title;
	FLOAT asame;
	FLOAT tsame;
	DWORD aword;
	DWORD tword;
	int n;
	LYRIC* next;
};

struct LYRICINFO
{
	int ms;
	wchar_t* content;
	DWORD length;
	int delay;
	LYRICINFO* next;
};

struct PLAYERDECODE
{
	AVIOContext* io;	
	AVFormatContext* format;
	AVCodecContext* context;
	AVCodec* codec;
	AVFrame* frame;
	AVPacket packet;	
	BOOL swred;
	SwrContext* swr;
	int channels;
	int layout;
	int bits;
	int total;
	int left;
	AVSampleFormat fmt;	
	uint8_t* orgdata;
	int orgsize;
	uint8_t* ptr;
	//DECLARE_ALIGNED(16, uint8_t, buffer)[AVCODEC_MAX_AUDIO_FRAME_SIZE * 4];	
	unsigned char* buffer;
};

struct PLAYERDX
{		
	LPDIRECTSOUNDBUFFER dbuffer;
	DSBUFFERDESC desc;
	WAVEFORMATEXTENSIBLE waveformatEx;	
};

struct PLAYERTAG
{
	BOOL read;
	wchar_t ext[8];
	wchar_t* artist;
	wchar_t* title;
	wchar_t timer[32];
	wchar_t sample[10];
	wchar_t bitrate[10];
	wchar_t channels[10];
};

struct CDTrack
{
	wchar_t* driver;
	HANDLE hfile;
	DWORD sector_start;
	DWORD sector_end;
	DWORD total_length;
	BOOL header;	
	PLAYERBUFFER cdbuffer;
	DWORD start;
	DWORD left;	
};

struct PLAYERINFO
{	
	BOOL cd;
	CDTrack cdtrack;
	char* filename;
	PLAYERDECODE decode;
	PLAYERDX dx;	
	PLAYERBUFFER buffer;
	PLAYERBUFFER temp;
	int seek;
	int n;
	DWORD duration;
	int ms;	
	BOOL playing;
	BOOL pause;
	PLAYERTAG tag;
	LYRIC* lyric;
	DWORD lyricid;
	LYRICINFO* lyricinfo;
	PLAYERINFO* next;
	CRITICAL_SECTION lock_lyric;
	CRITICAL_SECTION lock_lyricinfo;
};

struct PLAYERENTRY
{
	PLAYERINFO* current;
	PLAYERINFO* next;
	BOOL entry;
	BOOL stop;
};

struct MAINWND
{
	HINSTANCE inst;								// 当前实例
	TCHAR szTitle[MAX_LOADSTRING];				// 标题栏文本
	TCHAR szWindowClass[MAX_LOADSTRING];		// 主窗口类名
	HWND hWnd;	
	HWND hTrack;
	HANDLE thread;
	HANDLE handle;
	DWORD thread_id;
	DWORD track;
	BOOL lock_track;
	BOOL spectrumline;
	ULONG_PTR gdiToken;
};

struct HTTPREQ
{
	HINTERNET internet;
	HINTERNET connect;
	HINTERNET request;
	char* buffer;
	DWORD dataLength;
	DWORD bufferLength;
	int resolveTimeout;
	int connectTimeout;
	int sendTimeout;
	int receiveTimeout;
};

struct LYRICWMD
{
	TCHAR* name;	
	HWND hWnd;	
	RECT rect;	
	HDC mdc;	
	HDC bdc;	
	HBITMAP mbitmap;	
	LOGFONT logfont;
	Gdiplus::Font* font;	
	COLORREF mask;
	COLORREF foreground;
	COLORREF background;
	BLENDFUNCTION blend;
	PLAYERSTATUS* status;
	PLAYERENTRY* player;
	HTTPREQ httpreq;
	HANDLE thread;	
	HANDLE handle;
	HANDLE stop;
	HANDLE exit;
	DWORD thread_id;	
	BOOL run;
	BOOL automatic;
	LYRICINFO* current;
	LYRICINFO* next;
};

struct CLOCKINFO
{
	TCHAR* name;
	HWND hWnd;
	RECT rect;
	HDC mdc;
	HDC bdc;
	HBITMAP bitmap;
	HBRUSH black;
	LOGFONT logfont;
	HFONT hfont;
	IMGINFO imginfo[11];	
	PLAYERSTATUS* status;
	PLAYERENTRY* player;
	HANDLE thread;	
	HANDLE handle;
	HANDLE stop;
	HANDLE exit;
	DWORD thread_id;
};

struct SONGLIST
{
	HWND hWnd;
	RECT rect;
	HDC mdc;
	HDC bdc;
	HBITMAP bitmap;
	IMGINFO imgplay;
	IMGINFO imgpause;
	BOOL scan;
	HANDLE thread;
	HANDLE exit;
	DWORD thread_id;
	BOOL quit;
};

struct SPECTRUM
{
	TCHAR* name;
	HWND hWnd;
	RECT rect;
	HDC mdc;
	HDC bdc;
	HDC gdc;
	HDC pdc;
	HDC hdc;
	HBITMAP mbitmap;
	HBITMAP gbitmap;
	HBITMAP pbitmap;
	HBITMAP hbitmap;
	HBRUSH black;
	CFastFourierTransform* fft;
	LONG max[BANDCOUNT];
	LONG peek[BANDCOUNT];	
	LONG top[BANDCOUNT];
	LONG step[BANDCOUNT];
	PLAYERSTATUS* status;
	PLAYERENTRY* player;
	char buffer[SAMPLESIZE];
	float pcm[SAMPLESIZE];	
	HANDLE thread;	
	HANDLE handle;
	HANDLE stop;
	HANDLE exit;
	DWORD thread_id;
	DWORD delay;	
};

struct LYRICSEARCH
{	
	HWND hWnd;
	PLAYERSTATUS* status;
	PLAYERENTRY* player;
	HANDLE thread;
	HANDLE exit;
	DWORD thread_id;	
	HTTPREQ httpreq;	
};

struct SOUNDTOUCH
{
	HWND hWnd;
	BOOL status;
	SoundTouch soundTouch;
	FLOAT tempoDelta;
	FLOAT pitchDelta;
	FLOAT rateDelta;
	int quick;
	int antiAlias;
	BOOL speech;
	WORD align;
	WORD channels;
};

struct DSP
{
	BOOL status;
	SOUNDTOUCH soundtouch;
};

struct CDROM
{
	wchar_t letter[8];
	wchar_t driver[16];
	wchar_t* name;
	DWORD msgId;
	CDROM* next;
};

ATOM MainWndReg(HINSTANCE hInstance, TCHAR* szWindowClass);
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow, MAINWND* main_wnd);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
BOOL CALLBACK FontColorProc(HWND, UINT, WPARAM, LPARAM);
void SetLyricDelay(int delay);
void SetFPS(UINT id);
void SetWindowText(PLAYERINFO* playerinfo);
void TrackSetProgress(int pos);
void TrackProgress(int ms, DWORD total);
void TrackEnable(BOOL enable);
void TrackLock(BOOL lock);
DWORD WINAPI MainThread(LPVOID param);
void PlayInfoPrepare(PLAYERINFO* playerinfo);
void PlayInfoRelease(PLAYERINFO* playerinfo, BOOL all);
CDROM* GetCDROM();
void CDROMRelease();
BOOL GetLyricStatus();
void SetLyricStatus(BOOL status);
BOOL GetSpectrumLine();
void SetSpectrumLine(BOOL status);