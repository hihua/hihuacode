#include "stdafx.h"
#include "peepm.h"
#include "peepmDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

BEGIN_MESSAGE_MAP(CpeepmApp, CWinAppEx)
	ON_COMMAND(ID_HELP, &CWinApp::OnHelp)	
END_MESSAGE_MAP()

CpeepmApp::CpeepmApp()
{

}

CpeepmApp theApp;

BOOL CpeepmApp::InitInstance()
{
	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);
	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinAppEx::InitInstance();
	AfxEnableControlContainer();

	if (!AfxSocketInit())
	{
		AfxMessageBox(IDP_SOCKETS_INIT_FAILED);
		return FALSE;
	}
	
	SetRegistryKey(_T("应用程序向导生成的本地应用程序"));

	CpeepmDlg dlg;
	m_pMainWnd = &dlg;
	INT_PTR nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{
		
	}
	else if (nResponse == IDCANCEL)
	{
		
	}

	return FALSE;
}