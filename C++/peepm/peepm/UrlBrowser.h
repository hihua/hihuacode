#pragma once

#include "peepmCrack.h"

//这些_nei容是付费的_nei容
static const char* RegexContent1 = "\xE8\xBF\x99\xE4\xBA\x9B\x5F\x6E\x65\x69\xE5\xAE\xB9\xE6\x98\xAF\xE4\xBB\x98\xE8\xB4\xB9\xE7\x9A\x84\x5F\x6E\x65\x69\xE5\xAE\xB9";
//系统隐藏了部分
static const char* RegexContent2 = "\xE7\xB3\xBB\xE7\xBB\x9F\xE9\x9A\x90\xE8\x97\x8F\xE4\xBA\x86\xE9\x83\xA8\xE5\x88\x86";

struct PeepmBrowser
{
	char* path;
	char* username;
	char* password;
	PeepmBrowser* next;
	BOOL use;
	CCriticalSection* section;
};

class CUrlBrowser : public CpeepmCrack
{
public:
	CUrlBrowser(PeepmBrowser* peepmBrowser, char* url, BOOL* success, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI);
	virtual ~CUrlBrowser();

private:
	PeepmBrowser* m_PeepmBrowser;
	PeepmBrowser* m_PeepmAccount;
	char* m_Url;
	BOOL* m_Success;

protected:
	BOOL OnStarted();
	BOOL OnProgress();
	void OnStoped();
	void OnRelease();

private:
	BOOL GetPeepmAccount();
	PeepmBrowser* GetAccount();
	void SetAccount(PeepmBrowser* peepmBrowser);
	BOOL CheckContent(const char* cookie);

public:
	BOOL GetSuccess();
	PeepmBrowser* GetPeepmBrowser();

public:
	static UINT InitAccount(LPVOID param);
};
