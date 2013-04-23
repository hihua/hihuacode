#pragma once

#include "peepmUI.h"

#define readmax 1024 * 1000

static const char* Server = "www.q423.com";
static const char* Code = "/code.php";
static const char* Login = "/login.php?action=login";
static const USHORT Port = 80; 

static const char* Get = "GET ";
static const char* Post = "POST ";
static const char* Http = " HTTP/1.1\r\n";
static const char* Accept = "Accept: */*\r\n";
static const char* UserAgent = "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)\r\n";
static const char* ContentType = "Content-Type: application/x-www-form-urlencoded\r\n";
static const char* ContentLength = "Content-Length: ";
static const char* Host = "Host: ";
static const char* Connection = "Connection: ";
static const char* Close = "Close\r\n";
static const char* KeepAlive = "Keep-Alive\r\n";
static const char* Cache = "Cache-Control: no-cache\r\n";
static const char* Cookie = "Cookie: ";
static const char* Chunked = "Transfer-Encoding: chunked";
static const char* SetCookie = "Set-Cookie: ";

static const char* Username = "tusername=";
static const char* Password = "tpwd=";
static const char* Expire = "expire=";
static const char* Codes = "codes=";

static const char* MyUsername = "abc54278";
static const char* MyPassword = "abc54278";

//验证码错误
static const char* CodeError = "\xE9\xAA\x8C\xE8\xAF\x81\xE7\xA0\x81\xE9\x94\x99\xE8\xAF\xAF";
//登陆名或密码错误
static const char* LoginFail = "\xE7\x99\xBB\xE9\x99\x86\xE5\x90\x8D\xE6\x88\x96\xE5\xAF\x86\xE7\xA0\x81\xE9\x94\x99\xE8\xAF\xAF";
//还可以尝试
static const char* LoginRetry = "\xE8\xBF\x98\xE5\x8F\xAF\xE4\xBB\xA5\xE5\xB0\x9D\xE8\xAF\x95";
//登陆成功
static const char* LoginSuccess = "\xE7\x99\xBB\xE9\x99\x86\xE6\x88\x90\xE5\x8A\x9F";

struct Request
{
	SOCKET socket;	
	SOCKADDR_IN addr;
	char* buffer;
	UINT dataLength;
	UINT bufferLength;
	char* cookie;
	char* code;	
};

struct Response
{
	char code[4];
	UINT chunked;
	char* buffer;
	UINT dataLength;
	UINT bufferLength;
	char* head;
	UINT headLength;
	char* body;
	UINT bodyLength;
};

struct PeepmWrite
{
	CCriticalSection* lock;
	char* writePath;
};

class CpeepmCrack
{
public:
	CpeepmCrack(UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI);
	virtual ~CpeepmCrack();

protected:
	CpeepmUI& m_PeepmUI;

protected:	
	Request m_Request;
	Response m_Response;
	
protected:
	UINT m_Id;
	CWinThread* m_Thread;
	PeepmWrite* m_PeepmWrite;
	CCriticalSection* m_Section;
	DWORD m_Delay;

private:
	BOOL m_Stop;
	BOOL m_Stoped;
	BOOL m_Pause;
	UINT m_ThreadMax;
	CEvent* m_Event;
	
protected:
	BOOL InitSocket();
	BOOL ConnectSocket();
	BOOL GetCode();
	BOOL GetCookie();
	char* GetCookies();
	BOOL Send();
	BOOL Receive();
	BOOL ParseHead();
	BOOL ParseBody();
	BOOL GetUrlContent(char* url, const char* cookie);
	const Response& GetReponse();

protected:
	void WritePassword(const char* username, const char* password);	
	UINT GetLogin(const char* username, const char* password, BOOL write);
	void ReLogin();
	int GetTimes();
	char* AppendPassword(const char* username, const char* password);

public:
	void OnStart();
	void Stop();
	void SetPause(BOOL pause);
	void Release();
	CEvent* GetEvent();
	UINT GetThreadMax();
	CWinThread* GetThread();
	void SetThread(CWinThread* thread);
	void SetDelay(DWORD delay);
	
protected:
	virtual BOOL OnStarted() = 0;
	virtual BOOL OnProgress() = 0;
	virtual void OnStoped() = 0;
	virtual void OnRelease() = 0;
	
public:
	static UINT OnThread(LPVOID param);
};
