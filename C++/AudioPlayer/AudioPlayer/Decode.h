#include "AudioPlayer.h"
#include "libtag/tag.h"

#ifdef DEBUG 
#pragma comment(lib, "libtag/libid3tag_debug.lib")
#else
#pragma comment(lib, "libtag/libid3tag_release.lib")
#endif

BOOL DecodeInit(const char* filename, PLAYERDECODE* decode);
BOOL DecodeCDInit(PLAYERINFO* playerinfo);
void WaveFormatInit(PWAVEFORMATEXTENSIBLE waveformatEx, PLAYERDECODE* context);
BOOL DecodeFrame(char* buffer, DWORD size, DWORD& count, PLAYERDECODE* decode);
BOOL DecodeSeek(PLAYERDECODE* decode, const int ms);
void DecodeRelease(PLAYERDECODE* decode);
void DecodeTag(PLAYERINFO* playerinfo);
void DecodeCDTag(PLAYERINFO* playerinfo, const wchar_t* letter, UCHAR index);
void DecodeCDTag(PLAYERINFO* playerinfo);
wchar_t* GetMP3Tag(const id3_tag* tag, const char* name);
wchar_t* GetMP3Text(id3_field_textencoding encoding, const id3_field* field, const id3_ucs4_t* ucs4);
void SplitTag(const char* filename, wchar_t** title, wchar_t** artist);
void SplitTitle(const wchar_t* content, wchar_t** title);