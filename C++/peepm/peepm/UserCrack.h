#pragma once

#include "peepmCrack.h"

struct PeepmPassword
{
	char* passwordPath;
	FILE* file;	
	char* buffer;
	UINT total;
	UINT size;
	long length;
	char* p;
	BOOL success;
	long rows;
	long row;
};

class CUserCrack : public CpeepmCrack
{
public:
	CUserCrack(BOOL single, PeepmPassword* peepmPassword, char* username, UINT id, UINT threadMax, DWORD delay, CCriticalSection* section, CEvent* event, PeepmWrite* peepmWrite, CpeepmUI& peepmUI);
	virtual ~CUserCrack();

protected:
	BOOL OnStarted();
	BOOL OnProgress();
	void OnStoped();
	void OnRelease();

public:
	BOOL OpenPassword();

private:
	char* GetPassword();
	void SetSuccess();
	
private:
	PeepmPassword* m_PeepmPassword;
	char* m_Username;
	char* m_Password;
	BOOL m_Single;
	
public:
	static UINT InitPassword(LPVOID param);
};
