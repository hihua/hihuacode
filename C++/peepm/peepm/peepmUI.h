#pragma once

struct Request;
struct Response;

class CpeepmUI
{	
public:
	virtual void OnThreadStart(UINT id);
	virtual void OnThreadConnect(UINT id, BOOL success);
	virtual BOOL OnThreadCode(UINT id, Request& request, Response& response) = 0;
	virtual void OnThreadLogin(UINT id, UINT ret);
	virtual void OnThreadReLogin(UINT id);
	virtual void OnThreadErrorTimes(UINT id);
	virtual void OnThreadFinish(UINT id) = 0;
	virtual void OnThreadSuccess(const char* username, const char* password);
	virtual void OnThreadProgress(UINT id, const char* username, const char* password, const char* progress);
	virtual void OnThreadUsername(UINT id, const char* username);
	virtual void OnThreadDelay(UINT id, DWORD delay);
	virtual void OnThreadInfo(UINT id, const char* content);

public:
	virtual void OnThreadBrowser(const char* url, char* cookie);
};
