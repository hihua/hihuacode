#pragma once

#include "resource.h"
#include "peepmUI.h"
#include "browser_explorer.h"

class CpeepmCrack;

class CpeepmBrowser : public CDialog, CpeepmUI
{
public:
	CpeepmBrowser(CWnd* pParent = NULL);
	virtual ~CpeepmBrowser();
	enum { IDD = IDD_BROWSER_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);

private:	
	CEdit m_Edit;
	CButton m_Button;
	CBrowser_explorer m_Browser;
	long m_Bottom;

private:
	CpeepmCrack** m_Crack;
	UINT m_ThreadMax;
	UINT m_ThreadFinish;
	BOOL m_Quit;
	
public:
	void OnThreadBrowser(const char* url, char* cookie);

private:
	BOOL CloseTask();
	char* GetCookie();
	void SaveCookie(const wchar_t* host, const char* name, UINT nameLength, const char* value, UINT valueLength);
	void SaveCookie(char* cookie);

private:
	virtual void OnThreadProgress(UINT id, const char* username, const char* password, const char* progress);
	virtual BOOL OnThreadCode(UINT id, Request& request, Response& response);
	virtual void OnThreadFinish(UINT id);

protected:
	virtual BOOL OnInitDialog();
	DECLARE_MESSAGE_MAP()

private:	
	afx_msg void OnPaint();	
	afx_msg void OnBnClickedBrowserButton();	
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnClose();
	afx_msg void OnEnSetfocusBrowserEdit();
};
