#pragma once

#include "peepmUI.h"
#include "peepmBrowser.h"

class CpeepmCrack;

class CCodeDlg : public CDialog
{
public:
	CCodeDlg(CWnd* pParent = NULL);
	virtual ~CCodeDlg();
	enum { IDD = IDD_CODE_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);

protected:
	Image* m_Image;	
	HGLOBAL m_Buffer;
	IStream* m_Stream;
	CEdit m_Edit;
	char* m_Code;
	UINT m_Id;
	
protected:
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	DECLARE_MESSAGE_MAP()

private:	
	afx_msg void OnPaint();

public:
	BOOL SetCode(UINT id, const Request& request, const Response& response);
	char* GetCode();	
};

class CUsernameDlg : public CDialog
{
public:
	CUsernameDlg(CWnd* pParent = NULL);
	virtual ~CUsernameDlg();
	enum { IDD = IDD_USERNAME_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);

protected:	
	CEdit m_Edit;
	CButton m_CheckBox;
	char* m_Username;
	BOOL m_Single;
	
protected:
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	DECLARE_MESSAGE_MAP()

private:	
	afx_msg void OnPaint();

public:	
	char* GetUsername();
	BOOL GetSingle();
};

class CpeepmDlg : public CDialog, CpeepmUI
{
public:
	CpeepmDlg(CWnd* pParent = NULL);
	virtual ~CpeepmDlg();
	enum { IDD = IDD_PEEPM_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);

private:
	CMenu m_Menu;
	CStatusBarCtrl m_StatusBar;
	CEdit m_Edit;
	CFont m_Font;
	CCriticalSection m_Section;
	CpeepmBrowser m_Browser;
	
protected:
	ULONG m_pGdiToken;
	HICON m_hIcon;	
	virtual BOOL OnInitDialog();		
	DECLARE_MESSAGE_MAP()

public:
	UINT GetThreads();
	DWORD GetDelay();

private:
	void SetFont();
	BOOL CloseTask();
	void InsertText(const char* text);
	void SetStatusText(const char* text, int panel);		
	void SetDelay(UINT id);
	void ModifyMenu(CMenu* menu, UINT id, const wchar_t* name);

private:
	CpeepmCrack** m_Crack;
	UINT m_ThreadMax;
	UINT m_ThreadFinish;
	BOOL m_Quit;

private:
	void StartUserCrack();
	void StartUsersCrack();
	
public:
	virtual void OnThreadStart(UINT id);
	virtual void OnThreadConnect(UINT id, BOOL success);
	virtual BOOL OnThreadCode(UINT id, Request& request, Response& response);
	virtual void OnThreadLogin(UINT id, UINT ret);
	virtual void OnThreadReLogin(UINT id);
	virtual void OnThreadErrorTimes(UINT id);
	virtual void OnThreadFinish(UINT id);
	virtual void OnThreadSuccess(const char* username, const char* password);
	virtual void OnThreadProgress(UINT id, const char* username, const char* password, const char* progress);
	virtual void OnThreadUsername(UINT id, const char* username);
	virtual void OnThreadDelay(UINT id, DWORD delay);
	virtual void OnThreadInfo(UINT id, const char* content);

public:	
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnExit();
	afx_msg void OnClose();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnUsersStart();
	afx_msg void OnUserStart();
	afx_msg void OnTaskStop();
	afx_msg void OnDateBuild();	
	afx_msg void OnUpdateThreads(CCmdUI *pCmdUI);
	afx_msg void OnUpdateUsersSingle(CCmdUI *pCmdUI);
	afx_msg void OnUpdateDelay(CCmdUI *pCmdUI);
	afx_msg void OnUsersBrowser();
	afx_msg void OnTaskPause();
};
