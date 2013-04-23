#include "StdAfx.h"
#include "UsersCrack.h"
#include "peepmUtil.h"

CUsersCrack::CUsersCrack(BOOL single, PeepmUsername* peepmUsername, char* usernamePath, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, char* passwordPath, CpeepmUI& peepmUI) : CpeepmCrack(id, threadMax, delay, section, event, peepmWrite, peepmUI)
{	
	m_Single = single;
	m_PeepmUsername = peepmUsername;
	m_UsernamePath = usernamePath;
	m_Username = NULL;
	m_Password = NULL;
	m_PeepmPrev = peepmUsername;
	m_PeepmPasswords.passwordPath = passwordPath;
	m_PeepmPasswords.file = NULL;
	m_PeepmPasswords.password = NULL;	
	m_PeepmPasswords.buffer = new char[readmax + 1];
	ZeroMemory(m_PeepmPasswords.buffer, readmax + 1);
	m_PeepmPasswords.total = readmax;
	m_PeepmPasswords.size = m_PeepmPasswords.total;
	m_PeepmPasswords.length = 0;
	m_PeepmPasswords.p = m_PeepmPasswords.buffer;
}

CUsersCrack::~CUsersCrack()
{

}

BOOL CUsersCrack::OnStarted()
{	
	if (!GetUsername())
		return FALSE;

	PeepmUsername* peepmUsername = GetPeepmUsername();
	if (peepmUsername == NULL)
		return FALSE;

	if (!OpenPassword())
		return FALSE;

	if (!InitSocket())
		return FALSE;

	ConnectSocket();
	if (!GetCode())
		return FALSE;
	
	m_Username = peepmUsername->username;
	m_Password = peepmUsername->username;
	m_PeepmUI.OnThreadUsername(m_Id, m_Username);
	return TRUE;
}

BOOL CUsersCrack::OnProgress()
{	
	char* username = m_Username;
	char* password = m_Password;
	char* p = password;
	if (m_Single)	
		p = AppendPassword(username, password);
	
	m_PeepmUI.OnThreadProgress(m_Id, username, p, NULL);

	UINT ret = GetLogin(username, p, TRUE);
	if (m_Single)
		ReleaseArray(p);

	if (ret == 2)
	{
		if (GetCode())
			return TRUE;
		else
			return FALSE;
	}

	if (ret == 1 && GetPassword())
	{	
		m_Password = m_PeepmPasswords.password;
		return TRUE;
	}
	
	fseek(m_PeepmPasswords.file, 0, SEEK_SET);
	m_PeepmPasswords.size = m_PeepmPasswords.total;
	m_PeepmPasswords.length = 0;
	m_PeepmPasswords.p = m_PeepmPasswords.buffer;
	if (GetUsername())
	{
		PeepmUsername* peepmUsername = GetPeepmUsername();
		if (peepmUsername == NULL)
			return FALSE;

		m_Username = peepmUsername->username;
		m_Password = peepmUsername->username;
		m_PeepmUI.OnThreadUsername(m_Id, m_Username);
	}
	else
		return FALSE;
	
	return TRUE;
}

void CUsersCrack::OnStoped()
{
	fclose(m_PeepmPasswords.file);
	ReleaseArray(m_PeepmPasswords.buffer);
	ReleaseArray(m_PeepmPasswords.password);
}

void CUsersCrack::OnRelease()
{
	ReleaseArray(m_PeepmPasswords.passwordPath);
	ReleaseArray(m_UsernamePath);
	PeepmUsername* peepmUsername = m_PeepmPrev;
	if (peepmUsername != NULL)
	{
		CCriticalSection* section = peepmUsername->section;
		ReleaseObject(section);

		while (peepmUsername != NULL)
		{
			ReleaseArray(peepmUsername->username);
			PeepmUsername* current = peepmUsername;
			peepmUsername = peepmUsername->next;
			ReleaseObject(current);
		}
	}	
}

PeepmUsername* CUsersCrack::GetPeepmUsername()
{
	return m_PeepmUsername;
}

char* CUsersCrack::GetUsernamePath()
{
	return m_UsernamePath;
}

void CUsersCrack::SetPeepmUsername(PeepmUsername* peepmUsername)
{
	m_PeepmUsername = peepmUsername;
}

BOOL CUsersCrack::GetUsername()
{	
	PeepmUsername* peepmUsername = GetPeepmUsername();
	CCriticalSection* section = peepmUsername->section;
	if (!section->Lock())
		return FALSE;	

	while (peepmUsername->use)
	{
		peepmUsername = peepmUsername->next;
		if (peepmUsername == NULL)
		{
			section->Unlock();
			return FALSE;
		}
	}	

	peepmUsername->use = TRUE;
	SetPeepmUsername(peepmUsername);
	section->Unlock();

	return TRUE;
}

UINT CUsersCrack::InitUsername(LPVOID param)
{
	CUsersCrack* crack = (CUsersCrack *)param;
	PeepmUsername* peepmUsername = crack->GetPeepmUsername();
	char* path = crack->GetUsernamePath();
	UINT threadMax = crack->GetThreadMax();
	CEvent* event = crack->GetEvent();
	FILE* file = fopen(path, "rb");	
	if (file == NULL)
	{
		fclose(file);
		event->SetEvent();

		return 0;
	}

	CCriticalSection* section = new CCriticalSection();		
	PeepmUsername* prev = NULL;

	char* buffer = new char[readmax + 1];	
	ZeroMemory(buffer, readmax + 1);

	UINT size = readmax;
	long len = 0;
	char* p = buffer;

	while (!feof(file) || len > 0)
	{			
		BOOL flag = FALSE;

		if (len == 0)
		{
			long start = ftell(file);
			int ret = fread(p, size, 1, file);
			long end = ftell(file);
			len = end - start;
			p[len] = '\0';
			len += p - buffer;
			p = buffer;					
		}		

		char* q = p;

		while (q - p < len)
		{
			if (*q == '\r' || *q == '\n')
			{
				if (flag)
					break;			
				else
				{
					p++;					
					len--;					
				}
			}
			else				
				flag = TRUE;

			q++;
		}

		if (flag)
		{	
			if (q - p == len && !feof(file))
			{
				CopyMemory(buffer, p, len);
				p = buffer;				
				p = buffer + len;				
				size = readmax - len;
				len = 0;
				continue;
			}

			UINT total = q - p;
			char* username = new char[total + 1];				
			CopyMemory(username, p, total);
			username[total] = '\0';

			if (prev == NULL)
			{
				prev = peepmUsername;
				prev->username = username;
				prev->use = FALSE;
				prev->section = section;
				prev->next = NULL;				
			}
			else
			{
				PeepmUsername* next = new PeepmUsername();
				ZeroMemory(next, sizeof(PeepmUsername));

				next->username = username;			
				next->use = FALSE;
				next->section = section;
				next->next = NULL;
				prev->next = next;
				prev = next;
			}

			len -= total;
			p = q;
			q++;			
			while (q - p < len)
			{
				if (*q == '\r' || *q == '\n')
					q++;
				else
					break;
			}

			if (q - p < len)
			{
				len = len - (q - p);
				p = q;
				continue;			
			}
		}

		len = 0;
		p = buffer;
		size = readmax;		
	}

	fclose(file);
	ReleaseArray(buffer);
	event->SetEvent();

	return 1;
}

BOOL CUsersCrack::OpenPassword()
{
	char* path = m_PeepmPasswords.passwordPath;	
	if (!m_Section->Lock())
		return FALSE;

	FILE* file = fopen(path, "rb");
	if (file == NULL)
	{
		m_Section->Unlock();
		return FALSE;
	}

	m_PeepmPasswords.file = file;
	m_Section->Unlock();

	return TRUE;
}

BOOL CUsersCrack::GetPassword()
{
	ReleaseArray(m_PeepmPasswords.password);

	FILE* file = m_PeepmPasswords.file;
	char* buffer = m_PeepmPasswords.buffer;
	UINT total = m_PeepmPasswords.total;
	UINT* size = &m_PeepmPasswords.size;
	long* length = &m_PeepmPasswords.length;	
	char** p = &m_PeepmPasswords.p;

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
			char* username = new char[len + 1];	
			CopyMemory(username, *p, len);
			username[len] = '\0';

			m_PeepmPasswords.password = username;

			*length -= len;
			*p = q;
			q++;			
			while (q - *p < *length)
			{
				if (*q == '\r' || *q == '\n')
					q++;
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

			return TRUE;
		}
		else
		{
			*length = 0;
			*p = buffer;
			*size = total;
		}
	}

	return FALSE;
}
