#pragma once

#include "peepmCrack.h"

struct PeepmUsername
{
	char* username;
	PeepmUsername* next;
	BOOL use;
	CCriticalSection* section;
};

struct PeepmPasswords
{
	char* passwordPath;
	FILE* file;	
	char* password;	
	char* buffer;
	UINT total;
	UINT size;
	long length;
	char* p;
};

class CUsersCrack : public CpeepmCrack
{
public:
	CUsersCrack(BOOL single, PeepmUsername* peepmUsername, char* usernamePath, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, char* passwordPath, CpeepmUI& peepmUI);
	virtual ~CUsersCrack();

protected:
	BOOL OnStarted();
	BOOL OnProgress();
	void OnStoped();
	void OnRelease();

private:
	PeepmPasswords m_PeepmPasswords;
	PeepmUsername* m_PeepmUsername;
	PeepmUsername* m_PeepmPrev;
	char* m_UsernamePath;
	char* m_Username;
	char* m_Password;
	BOOL m_Single;

private:
	PeepmUsername* GetPeepmUsername();
	void SetPeepmUsername(PeepmUsername* peepmUsername);
	char* GetUsernamePath();
	BOOL GetUsername();
	BOOL OpenPassword();
	BOOL GetPassword();

public:
	static UINT InitUsername(LPVOID param);
};
