#include "stdafx.h"
#include "DSP.h"
#include "Util.h"

DSP dsp;

BOOL DspInit(const MAINWND* main_wnd)
{	
	if (!SoundTouchDialogInit(main_wnd))
		return FALSE;

	dsp.status = FALSE;
	dsp.soundtouch.status = FALSE;
	return TRUE;
}

void DspSetStatus(BOOL status)
{
	dsp.status = status;
}

BOOL DspGetStatus()
{
	return dsp.status;
}

void DspSet(DWORD sampleRate, DWORD channels, WORD align)
{
	SoundTouchSet(sampleRate, channels, align);
}

//
DWORD DspInput(LPVOID in, UINT in_count)
{
	BOOL status = dsp.status;
	if (status)
	{
		status = dsp.soundtouch.status;
		if (status)		
			return SoundTouchInput(&dsp.soundtouch, in, in_count);
	}

	return in_count;
}

//
void DspOutput(LPVOID in, UINT in_count, LPVOID out1, UINT out_count1, LPVOID out2, UINT out_count2)
{
	SOUNDTOUCH* soundtouch = &dsp.soundtouch;
	UINT samples = SoundTouchSamples(soundtouch);
	if (samples > 0)
	{
		SoundTouchOutput(soundtouch, out1, out_count1);
		if (out2 != NULL && out_count2 > 0)		
			SoundTouchOutput(soundtouch, out2, out_count2);
		
		return;
	}
		
	DWORD count = in_count;
	if (in_count > out_count1)
		count = out_count1;
	
	CopyMemory(out1, in, count);
	in_count -= count;
	
	if (in_count > 0 && out2 != NULL && out_count2 > 0)
	{		
		if (in_count > out_count2)		
			CopyMemory(out2, (char*)in + count, out_count2);					
		else		
			CopyMemory(out2, (char*)in + count, in_count);		
	}		
}

void DspFlush(char* out, DWORD size)
{
	SoundTouchFlush(out, size);
}

void SoundTouchSet(DWORD sampleRate, DWORD channels, WORD align)
{
	SOUNDTOUCH* soundtouch = &dsp.soundtouch;
	SoundTouch* soundTouch = &soundtouch->soundTouch;
	soundtouch->align = align;
	soundtouch->channels = channels;
	soundTouch->setSampleRate(sampleRate);
	soundTouch->setChannels(channels > 2 ? 2 : channels);
}

UINT SoundTouchSamples(SOUNDTOUCH* soundtouch)
{
	SoundTouch* soundTouch = &soundtouch->soundTouch;
	WORD align = soundtouch->align;
	return soundTouch->numSamples() * align;
}

UINT SoundTouchInput(SOUNDTOUCH* soundtouch, LPVOID in, UINT in_count)
{
	SoundTouch* soundTouch = &soundtouch->soundTouch;
	SAMPLETYPE* inptr = (SAMPLETYPE*)in;
	WORD align = soundtouch->align;
	UINT num = in_count / align;
	soundTouch->putSamples(inptr, num);
	
	return soundTouch->numSamples() * align;
}

void SoundTouchOutput(SOUNDTOUCH* soundtouch, LPVOID out, UINT out_count)
{
	SoundTouch* soundTouch = &soundtouch->soundTouch;
	SAMPLETYPE* outptr = (SAMPLETYPE*)out;
	WORD align = soundtouch->align;	
	UINT count = out_count / align;
	UINT offset = 0, len = 0;
	
	do 
	{
		len = soundTouch->receiveSamples(outptr + offset, count);
		if (len > 0)
		{
			count -= len;
			offset += len * align;			
		}				
	} while (len > 0 && offset < out_count);
}

void SoundTouchFlush(char* out, DWORD size)
{
	SOUNDTOUCH* soundtouch = &dsp.soundtouch;
	SoundTouch* soundTouch = &soundtouch->soundTouch;
	SAMPLETYPE* outptr = (SAMPLETYPE*)out;
	WORD align = soundtouch->align;
	soundTouch->flush();

	DWORD num = size / align;
	DWORD max = num;
	DWORD offset = 0;
	int len = 0;

	while (TRUE)
	{
		len = soundTouch->receiveSamples(outptr + offset, num);
		if (len > 0)
		{
			num -= len;
			offset += len;
			if (offset >= max)
				offset = 0;
		}
		else
			break;
	}
}

BOOL SoundTouchDialogInit(const MAINWND* main_wnd)
{
	HRSRC hsrc = FindResource(main_wnd->inst, MAKEINTRESOURCE(IDD_DIALOG_SOUNDTOUCH), RT_DIALOG);
	if (hsrc == NULL)
		return FALSE;

	HGLOBAL htemp = LoadResource(main_wnd->inst, hsrc);
	if (htemp == NULL)
		return FALSE;

	LPCDLGTEMPLATE dialog = (LPCDLGTEMPLATE)LockResource(htemp);	
	dsp.soundtouch.hWnd = CreateDialogIndirect(main_wnd->inst, dialog, main_wnd->hWnd, SoundTouchProc);

	UnlockResource(htemp);
	FreeResource(htemp);
	return TRUE;
}

void SoundTouchShowDialog(BOOL show)
{
	if (show)
		ShowWindow(dsp.soundtouch.hWnd, SW_SHOW);
	else
		ShowWindow(dsp.soundtouch.hWnd, SW_HIDE);
}

void SoundTouchSet(int n, const wchar_t* im, const wchar_t* re)
{
	switch (n)
	{
	case 0:
		{
			if (im != NULL)
			{
				wchar_t buffer[10] = {0};
				wcscat_s(buffer, im);
				if (re != NULL && wcslen(re) > 0 && IsNumber(re))
				{
					wcscat_s(buffer, L".");
					wcscat_s(buffer, re);
				}
				else				
					wcscat_s(buffer, L".0");					
				
				FLOAT value = _wtof(buffer);
				if (value >= -95.0F && value <= 5000.0F)
				{
					SOUNDTOUCH* soundtouch = &dsp.soundtouch;
					SoundTouch* soundTouch = &soundtouch->soundTouch;
					soundTouch->setTempoChange(value);
				}				
			}			
		}
		break;

	case 1:
		{
			if (im != NULL)
			{
				wchar_t buffer[10] = {0};
				wcscat_s(buffer, im);
				if (re != NULL && wcslen(re) > 0 && IsNumber(re))
				{
					wcscat_s(buffer, L".");
					wcscat_s(buffer, re);
				}
				else				
					wcscat_s(buffer, L".0");					

				FLOAT value = _wtof(buffer);
				if (value >= -60.0F && value <= 60.0F)
				{
					SOUNDTOUCH* soundtouch = &dsp.soundtouch;
					SoundTouch* soundTouch = &soundtouch->soundTouch;
					soundTouch->setPitchSemiTones(value);
				}				
			}
		}
		break;

	case 2:
		{
			if (im != NULL)
			{
				wchar_t buffer[10] = {0};
				wcscat_s(buffer, im);
				if (re != NULL && wcslen(re) > 0 && IsNumber(re))
				{
					wcscat_s(buffer, L".");
					wcscat_s(buffer, re);
				}
				else				
					wcscat_s(buffer, L".0");					

				FLOAT value = _wtof(buffer);
				if (value >= -95.0F && value <= 5000.0F)
				{
					SOUNDTOUCH* soundtouch = &dsp.soundtouch;
					SoundTouch* soundTouch = &soundtouch->soundTouch;
					soundTouch->setRateChange(value);
				}				
			}			
		}
		break;
	}
}

int CALLBACK SoundTouchProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_INITDIALOG:
		{			
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_IM, 0, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_RE, 0, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_IM, 0, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_RE, 0, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_IM, 0, FALSE);
			SetDlgItemInt(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_RE, 0, FALSE);

			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_IM), EM_LIMITTEXT, 4, 0);
			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_RE), EM_LIMITTEXT, 1, 0);
			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_IM), EM_LIMITTEXT, 4, 0);
			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_RE), EM_LIMITTEXT, 1, 0);
			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_IM), EM_LIMITTEXT, 4, 0);		
			SendMessage(GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_RE), EM_LIMITTEXT, 1, 0);

			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_TEMPOCHANGE_IM), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(5000, -95));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_TEMPOCHANGE_IM), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_IM), 0);
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_TEMPOCHANGE_RE), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(9, 0));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_TEMPOCHANGE_RE), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_RE), 0);
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_PITCHSEMITONES_IM), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(60, -60));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_PITCHSEMITONES_IM), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_IM), 0);
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_PITCHSEMITONES_RE), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(9, 0));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_PITCHSEMITONES_RE), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_RE), 0);
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_RATECHANGE_IM), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(5000, -95));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_RATECHANGE_IM), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_IM), 0);
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_RATECHANGE_RE), UDM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(9, 0));
			SendMessage(GetDlgItem(hWnd, IDC_SPIN_SOUNDTOUCH_RATECHANGE_RE), UDM_SETBUDDY, (WPARAM)GetDlgItem(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_RE), 0);
		}
		break;

	case WM_COMMAND:
		{
			WORD id = LOWORD(wParam);
			WORD event = HIWORD(wParam);
			
			switch (event)
			{
			case EN_CHANGE:
				{
					switch(id)
					{
					case IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_IM:
					case IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_RE:
						{
							wchar_t im[10] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_IM, im, sizeof(im) / sizeof(wchar_t));
							if (wcslen(im) > 0)
							{	
								wchar_t re[10] = {0};
								GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_TEMPOCHANGE_RE, re, sizeof(re) / sizeof(wchar_t));

								if (IsNumber(im))								
									SoundTouchSet(0, im, re);								
								else
								{
									if (im[0] == '-' && IsNumber(im + 1))										
										SoundTouchSet(0, im, re);									
								}
							}
						}
						break;

					case IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_IM:
					case IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_RE:
						{
							wchar_t im[10] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_IM, im, sizeof(im) / sizeof(wchar_t));
							if (wcslen(im) > 0)
							{	
								wchar_t re[10] = {0};
								GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_PITCHSEMITONES_RE, re, sizeof(re) / sizeof(wchar_t));

								if (IsNumber(im))								
									SoundTouchSet(0, im, re);								
								else
								{
									if (im[0] == '-' && IsNumber(im + 1))										
										SoundTouchSet(1, im, re);									
								}
							}
						}
						break;

					case IDC_EDIT_SOUNDTOUCH_RATECHANGE_IM:
					case IDC_EDIT_SOUNDTOUCH_RATECHANGE_RE:
						{
							wchar_t im[10] = {0};
							GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_IM, im, sizeof(im) / sizeof(wchar_t));
							if (wcslen(im) > 0)
							{	
								wchar_t re[10] = {0};
								GetDlgItemText(hWnd, IDC_EDIT_SOUNDTOUCH_RATECHANGE_RE, re, sizeof(re) / sizeof(wchar_t));

								if (IsNumber(im))								
									SoundTouchSet(0, im, re);								
								else
								{
									if (im[0] == '-' && IsNumber(im + 1))										
										SoundTouchSet(2, im, re);									
								}
							}
						}
						break;
					}					
				}
				break;

			case BN_CLICKED:
				{
					switch (id)
					{
					case IDC_CHECK_SOUNDTOUCH_STATUS:
						{							
							HMENU menu = GetMenu(GetParent(hWnd));
							if (menu != NULL)
							{
								if (dsp.soundtouch.status)					
								{
									dsp.soundtouch.status = FALSE;
									CheckMenuItem(menu, IDC_CHECK_SOUNDTOUCH_STATUS, MF_UNCHECKED);
								}
								else
								{
									dsp.soundtouch.status = TRUE;
									CheckMenuItem(menu, IDC_CHECK_SOUNDTOUCH_STATUS, MF_CHECKED);
								}
							}
						}
						break;
					}
				}
				break;
			}
		}
		break;

	case WM_CLOSE:		
		SoundTouchShowDialog(FALSE);	
		break;
	}

	return 0;
}