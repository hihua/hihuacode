#include "stdafx.h"
#include "Decode.h"
#include "Util.h"
#include "Cdrom.h"

BOOL DecodeInit(const char* filename, PLAYERDECODE* decode)
{	
	int ret = avformat_open_input(&decode->format, filename, NULL, NULL);
	if (ret == 0)
	{		
		ret = avformat_find_stream_info(decode->format, NULL);
		if (ret >= 0)
		{			
			int index = -1;
			for (unsigned int i = 0; i < decode->format->nb_streams; i++)
			{
				if (decode->format->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO)
				{
					decode->context = decode->format->streams[i]->codec;
					index = i;
					break;
				}
			}

			if (index > -1)
			{
				decode->codec = avcodec_find_decoder(decode->context->codec_id);
				if (decode->codec != NULL)
				{
					ret = avcodec_open2(decode->context, decode->codec, NULL);
					if (ret == 0)
					{					
						av_init_packet(&decode->packet);
						return TRUE;
					}					
				}				
			}			
		}
	}

	return FALSE;
}

BOOL DecodeCDInit(PLAYERINFO* playerinfo)
{
	PLAYERDECODE* decode = &playerinfo->decode;
	CDTrack* cdtrack = &playerinfo->cdtrack;
	CDTrackInit(cdtrack);
	
	decode->io = avio_alloc_context(NULL, 0, 0, cdtrack, ReadCDPacket, NULL, SeekCDPacket);
	if (decode->io != NULL)
	{
		AVInputFormat* input = NULL;
		int ret = av_probe_input_buffer(decode->io, &input, NULL, NULL, 0, 0);
		if (ret == 0)
		{
			decode->format = avformat_alloc_context();
			if (decode->format != NULL)
			{
				decode->format->pb = decode->io;
				ret = avformat_open_input(&decode->format, NULL, input, NULL);				
				if (ret == 0)
				{		
					ret = avformat_find_stream_info(decode->format, NULL);
					if (ret >= 0)
					{			
						int index = -1;
						for (unsigned int i = 0; i < decode->format->nb_streams; i++)
						{
							if (decode->format->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO)
							{
								decode->context = decode->format->streams[i]->codec;
								index = i;
								break;
							}
						}

						if (index > -1)
						{
							decode->codec = avcodec_find_decoder(decode->context->codec_id);
							if (decode->codec != NULL)
							{
								ret = avcodec_open2(decode->context, decode->codec, NULL);
								if (ret == 0)
								{					
									av_init_packet(&decode->packet);
									return TRUE;
								}					
							}				
						}			
					}
				}
			}
		}
	}

	return FALSE;
}

void WaveFormatInit(PWAVEFORMATEXTENSIBLE waveformatEx, PLAYERDECODE* decode)
{
	decode->fmt = AV_SAMPLE_FMT_S16;
	int bits = av_get_bytes_per_sample(decode->fmt);

	PWAVEFORMATEX waveformat = &waveformatEx->Format;	
	waveformat->wFormatTag		= WAVE_FORMAT_PCM;
	waveformat->wBitsPerSample	= bits * 8;	
	waveformat->nChannels		= decode->context->channels;	
	waveformat->nBlockAlign		= waveformat->wBitsPerSample * waveformat->nChannels / 8;
	waveformat->nSamplesPerSec	= decode->context->sample_rate;	
	waveformat->nAvgBytesPerSec	= waveformat->nSamplesPerSec * waveformat->nBlockAlign;		
	
	decode->channels = waveformat->nChannels;
	decode->layout = av_get_default_channel_layout(decode->channels);
	decode->bits = bits;
}

BOOL DecodeFrame(char* buffer, DWORD size, DWORD& count, PLAYERDECODE* decode)
{
	DWORD p = 0;

	if (decode->left > 0)
	{	
		int len = decode->left;		
		uint8_t* a = decode->ptr + decode->total - decode->left;
		if (decode->left > size)
			len = size;
				
		CopyMemory(buffer, a, len);
		decode->left -= len;
		p += len;
		size -= len;
		count += len;
	}

	int ret = 0;
	int got = 0;
	BOOL err = FALSE;

	while (size > 0)
	{				
		if (decode->packet.size == 0)
		{
			ret = av_read_frame(decode->format, &decode->packet);
			if (ret != 0)
				return FALSE;

			decode->orgdata = decode->packet.data;
			decode->orgsize = decode->packet.size;			
		}	

		while (decode->packet.size > 0)
		{			
			if (decode->frame == NULL)
			{
				decode->frame = avcodec_alloc_frame();
				if (decode->frame == NULL)
					return FALSE;			
			} else
				avcodec_get_frame_defaults(decode->frame);

			int len = avcodec_decode_audio4(decode->context, decode->frame, &got, &decode->packet);
			if (len < 0)
			{
				err = TRUE;
				break;
			}

			decode->packet.size -= len;
			decode->packet.data += len;

			if (got > 0) 
			{					
				int data_size = av_samples_get_buffer_size(NULL, decode->frame->channels, decode->frame->nb_samples, decode->context->sample_fmt, 1);
				if (data_size > 0)
				{	
					//int src_layout = (decode->frame->channel_layout > 0 && decode->frame->channels == av_get_channel_layout_nb_channels(decode->frame->channel_layout)) ? decode->frame->channel_layout : av_get_default_channel_layout(decode->frame->channels);
					
					if (!decode->swred && decode->context->sample_fmt != decode->fmt)
					{
						swr_free(&decode->swr);
						decode->swr = swr_alloc_set_opts(NULL, decode->layout, decode->fmt, decode->context->sample_rate, decode->layout, decode->context->sample_fmt, decode->frame->sample_rate, 0, NULL);
						if (decode->swr == NULL || (ret = swr_init(decode->swr)) < 0)
							return FALSE;

						decode->buffer = new unsigned char[AVCODEC_MAX_AUDIO_FRAME_SIZE * 4];
						decode->swred = TRUE;
					}

					if (decode->swr != NULL)
					{
						uint8_t* out = decode->buffer;
						int out_count = AVCODEC_MAX_AUDIO_FRAME_SIZE * 4 / decode->channels / av_get_bytes_per_sample(decode->fmt);
						
						len = swr_convert(decode->swr, &out, out_count, (const uint8_t**)decode->frame->extended_data, decode->frame->nb_samples);
						if (len < 0)
							return FALSE;

						if (len == out_count)
							swr_init(decode->swr);

						decode->ptr = decode->buffer;
						decode->total = len * decode->channels * av_get_bytes_per_sample(decode->fmt);
					}
					else
					{
						decode->ptr = decode->frame->data[0];
						decode->total = data_size;
					}

					len = decode->total;
					if (decode->total > size)
					{
						len = size;
						decode->left = decode->total - size;
					}
										
					CopyMemory(buffer + p, decode->ptr, len);
					p += len;
					size -= len;
					count += len;
				}
				else
					break;

				if (size == 0)
					break;
			}
		}

		if (decode->packet.size == 0 || err)
		{
			decode->packet.data = decode->orgdata;
			decode->packet.size = decode->orgsize;
			av_free_packet(&decode->packet);
			decode->orgdata = NULL;
			decode->orgsize = 0;
		}				
	}

	return TRUE;
}

BOOL DecodeSeek(PLAYERDECODE* decode, const int ms)
{		
	AVFormatContext* format = decode->format;
	AVCodecContext* context = decode->context;	
	int ret = av_seek_frame(format, -1, ms * 1000, AVSEEK_FLAG_ANY);	
	avcodec_flush_buffers(context);
	if (ret >= 0)
		return TRUE;
	else
		return FALSE;
}

void DecodeRelease(PLAYERDECODE* decode)
{
	if (decode->swr != NULL)
	{
		swr_free(&decode->swr);
		decode->swr = NULL;
	}

	SAFE_DELETE_ARRAY(decode->buffer);
	
	if (decode->orgsize > 0)
	{
		decode->packet.data = decode->orgdata;
		decode->packet.size = decode->orgsize;
		av_free_packet(&decode->packet);
		decode->orgdata = NULL;
		decode->orgsize = 0;
	}
	
	if (decode->frame != NULL)
	{
		avcodec_free_frame(&decode->frame);
		decode->frame = NULL;
	}
	
	if (decode->context != NULL)
	{
		avcodec_close(decode->context);
		decode->context = NULL;
	}

	if (decode->format != NULL)
	{			
		//avformat_free_context(decode->format);
		avformat_close_input(&decode->format);		
	}
	
	decode->swred = FALSE;
	decode->channels = 0;
	decode->layout = 0;
	decode->bits = 0;
	decode->total = 0;
	decode->left = 0;
	decode->ptr = NULL;
}

void DecodeTag(PLAYERINFO* playerinfo)
{
	const PLAYERDECODE* decode = &playerinfo->decode;
	AVFormatContext* format = decode->format;
	AVCodecContext* context = decode->context;
	PLAYERTAG* playertag = &playerinfo->tag;
	if (playertag->read)
		return;

	if (format != NULL && context != NULL)
	{
		const AVCodecDescriptor* descriptor = context->codec_descriptor;
		const char* name = descriptor->name;
		if (name != NULL)
		{
			CharToWideChar(name, 3, playertag->ext, 3);
			_wcsupr_s(playertag->ext, sizeof(playertag->ext) / sizeof(wchar_t));
		}
		
		if (StrCmp(playertag->ext, L"MP3") == 0)
		{
			const char* filename = playerinfo->filename;
			id3_file* file = id3_file_open(filename, ID3_FILE_MODE_READONLY);
			id3_tag* tag = id3_file_tag(file);
			if (tag != NULL && tag->nframes > 0)
			{
				wchar_t* title = GetMP3Tag(tag, ID3_FRAME_TITLE);
				if (title == NULL)		
					title = GetMP3Tag(tag, ID3_FRAME_ALBUM);

				wchar_t* artist = GetMP3Tag(tag, ID3_FRAME_ARTIST);

				playertag->artist = artist;
				playertag->title = title;
			}

			SAFE_DELETE_ARRAY(file->path);
			id3_file_close(file);
		}
		else
		{
			AVDictionary* dictionary = format->metadata;
			if (dictionary != NULL)
			{
				AVDictionaryEntry* entry = av_dict_get(dictionary, "title", NULL, 0);
				if (entry != NULL && entry->value != NULL)
				{
					wchar_t* title = UTF8toWideChar(entry->value, strlen(entry->value));
					playertag->title = title;
				}
				else
				{
					entry = av_dict_get(dictionary, "album", NULL, 0);
					if (entry != NULL && entry->value != NULL)
					{
						wchar_t* title = UTF8toWideChar(entry->value, strlen(entry->value));
						playertag->title = title;
					}
				}

				entry = av_dict_get(dictionary, "artist", NULL, 0);
				if (entry != NULL && entry->value != NULL)
				{
					wchar_t* artist = UTF8toWideChar(entry->value, strlen(entry->value));
					playertag->artist = artist;
				}
			}					
		}

		if (StrCmp(playertag->ext, L"PCM") == 0)		
			StrCpy(playertag->ext, L"WAV");
		
		if (playertag->title == NULL)
		{
			SAFE_DELETE_ARRAY(playertag->artist);
			SplitTag(playerinfo->filename, &playertag->title, &playertag->artist);
			if (playertag->title == NULL)
			{
				wchar_t* title = GetFileName(playerinfo->filename);
				if (title != NULL)
				{
					SplitTitle(title, &playertag->title);
					if (playertag->title == NULL)
						playertag->title = title;
					else
						SAFE_DELETE_ARRAY(title);
				}
			}
		}
		
		long duration = format->duration / 1000;
		playerinfo->duration = duration;
		duration = duration / 1000;
		long minute = duration / 60;
		long second = duration % 60;
		wsprintf(playertag->timer, L"%d:%02d", minute, second);
		wsprintf(playertag->sample, L"%dHz", context->sample_rate);

		if (context->bit_rate > 0)
			wsprintf(playertag->bitrate, L"%dkbps", context->bit_rate / 1000);
		else
			wsprintf(playertag->bitrate, L"%dkbps", format->bit_rate / 1000);

		switch (context->channels)
		{
		case 1:
			StrCpy(playertag->channels, L"单声道");
			break;

		case 4:
			StrCpy(playertag->channels, L"四声道");
			break;

		case 5:
			StrCpy(playertag->channels, L"4.1声道");
			break;

		case 6:
			StrCpy(playertag->channels, L"5.1声道");
			break;

		case 7:
			StrCpy(playertag->channels, L"6.1声道");
			break;

		case 8:
			StrCpy(playertag->channels, L"7.1声道");
			break;

		default:
			StrCpy(playertag->channels, L"立体声");
			break;
		}		
	}
	
	playertag->read = TRUE;
}

void DecodeCDTag(PLAYERINFO* playerinfo, const wchar_t* letter, UCHAR index)
{
	PLAYERTAG* playertag = &playerinfo->tag;
	if (playertag->read)
		return;

	DWORD length = 8;
	playertag->artist = new wchar_t[length + 1];
	wmemset(playertag->artist, 0, length + 1);
	StrCat(playertag->artist, letter);

	length = 16;
	playertag->title = new wchar_t[length + 1];
	wmemset(playertag->title, 0, length + 1);
	wsprintf(playertag->title, L"CD音轨%02d", index);

	long minute = playerinfo->duration / 60;
	long second = playerinfo->duration % 60;
	wsprintf(playertag->timer, L"%d:%02d", minute, second);
	playertag->read = TRUE;
}

void DecodeCDTag(PLAYERINFO* playerinfo)
{
	const PLAYERDECODE* decode = &playerinfo->decode;
	AVFormatContext* format = decode->format;
	AVCodecContext* context = decode->context;
	PLAYERTAG* playertag = &playerinfo->tag;

	StrCpy(playertag->ext, L"CD ");
	long duration = format->duration / 1000;
	playerinfo->duration = duration;
	wsprintf(playertag->sample, L"%dHz", context->sample_rate);

	if (context->bit_rate > 0)
		wsprintf(playertag->bitrate, L"%dkbps", context->bit_rate / 1000);
	else
		wsprintf(playertag->bitrate, L"%dkbps", format->bit_rate / 1000);

	switch (context->channels)
	{
	case 1:
		StrCpy(playertag->channels, L"单声道");
		break;

	case 4:
		StrCpy(playertag->channels, L"四声道");
		break;

	case 5:
		StrCpy(playertag->channels, L"4.1声道");
		break;

	case 6:
		StrCpy(playertag->channels, L"5.1声道");
		break;

	case 7:
		StrCpy(playertag->channels, L"6.1声道");
		break;

	case 8:
		StrCpy(playertag->channels, L"7.1声道");
		break;

	default:
		StrCpy(playertag->channels, L"立体声");
		break;
	}

	if (context->channels == 6)	
		StrCat(playertag->ext, L"DTS");	
	else
		StrCat(playertag->ext, L"WAV");
}

wchar_t* GetMP3Tag(const id3_tag* tag, const char* name)
{
	wchar_t* content = NULL;
	id3_frame* frame = id3_tag_findframe(tag, name, 0);
	if (frame != NULL)
	{
		id3_field* field = id3_frame_field(frame, 0);
		id3_field_textencoding encoding = id3_field_gettextencoding(field);
		field = id3_frame_field(frame, 1);

		switch (id3_field_type(field))
		{
		case ID3_FIELD_TYPE_STRING:
			content = GetMP3Text(encoding, field, id3_field_getstring(field));
			break;

		case ID3_FIELD_TYPE_STRINGFULL:						
			content = GetMP3Text(encoding, field, id3_field_getfullstring(field));
			break;

		case ID3_FIELD_TYPE_STRINGLIST:
			{				
				DWORD dataLength = 0, bufferLength = 0;
				unsigned int n = id3_field_getnstrings(field);
				for (unsigned int i = 0; i < n; i++)
				{
					wchar_t* p = GetMP3Text(encoding, field, id3_field_getstrings(field, i));
					if (p == NULL)
						continue;

					AppendBuffer((char**)&content, dataLength, bufferLength, (const char*)p, wcslen(p) * 2 + 1);
					SAFE_DELETE_ARRAY(p);
				}
			}
			break;
		}
	}

	return content;
}

wchar_t* GetMP3Text(id3_field_textencoding encoding, const id3_field* field, const id3_ucs4_t* ucs4)
{
	if (ucs4 == NULL)
		return NULL;

	wchar_t* content = NULL;
	switch(encoding)
	{
	case ID3_FIELD_TEXTENCODING_ISO_8859_1:
		{
			char* p = (char*)id3_ucs4_latin1duplicate(ucs4);
			content = CharToWideChar(p, strlen(p));
			free(p);
		}		
		break;

	case ID3_FIELD_TEXTENCODING_UTF_8:
		{
			char* p = (char*)id3_ucs4_utf8duplicate(ucs4);
			if (p != NULL)
			{
				content = UTF8toWideChar(p, strlen(p));				
				free(p);
			}
		}		
		break;

	case ID3_FIELD_TEXTENCODING_UTF_16:
		{
			char* p = (char*)id3_ucs4_utf8duplicate(ucs4);
			if (p != NULL)
			{
				content = UTF8toWideChar(p, strlen(p));
				free(p);
			}			
		}		
		break;

	default:
		break;
	}

	return content;
}

void SplitTag(const char* filename, wchar_t** title, wchar_t** artist)
{
	wchar_t* name = GetFileName(filename);
	if (name == NULL)
		return;

	wchar_t* p = name + wcslen(name);	
	while (p > name)
	{
		if (*p == '.')
		{
			wmemset(p, 0, 1);
			break;
		}
		else
			p--;
	}
	
	wchar_t* t = NULL;
	wchar_t* a = NULL;	
	Split(name, L"-", &t, &a);
	SAFE_DELETE_ARRAY(name);
	DWORD length = 0;
	
	if (a != NULL)
	{
		length = wcslen(a);
		*artist = new wchar_t[length + 1];
		wmemset(*artist, 0, length + 1);
		StrNCat(*artist, a, length + 1);
		SAFE_DELETE_ARRAY(a);
	}

	if (t != NULL)
	{
		SplitTitle(t, title);
		SAFE_DELETE_ARRAY(t);
	}
}

void SplitTitle(const wchar_t* content, wchar_t** title)
{
	wchar_t* t1 = NULL;
	wchar_t* t2 = NULL;
	Split(content, L" ", &t1, &t2);	
	DWORD length = 0;

	if (t1 != NULL && t2 != NULL)
	{
		length = wcslen(t2);
		*title = new wchar_t[length + 1];
		wmemset(*title, 0, length + 1);
		StrNCat(*title, t2, length + 1);
		SAFE_DELETE_ARRAY(t1);
		SAFE_DELETE_ARRAY(t2);
		return;
	}

	if (t1 != NULL)
	{
		length = wcslen(t1);
		*title = new wchar_t[length + 1];
		wmemset(*title, 0, length + 1);
		StrNCat(*title, t1, length + 1);
		SAFE_DELETE_ARRAY(t1);
		SAFE_DELETE_ARRAY(t2);
		return;
	}

	if (t2 != NULL)
	{
		length = wcslen(t2);
		*title = new wchar_t[length + 1];
		wmemset(*title, 0, length + 1);
		StrNCat(*title, t2, length + 1);
		SAFE_DELETE_ARRAY(t1);
		SAFE_DELETE_ARRAY(t2);			
		return;
	}
}