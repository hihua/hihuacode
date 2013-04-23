#include "StdAfx.h"
#include "peepmBrowser.h"
#include "peepmUtil.h"
#include "Wininet.h"
#include "Mshtml.h"
#include "peepmCrack.h"
#include "peepmDlg.h"
#include "UrlBrowser.h"

BEGIN_MESSAGE_MAP(CpeepmBrowser, CDialog)
	ON_WM_PAINT()
	ON_BN_CLICKED(IDC_BROWSER_BUTTON, &CpeepmBrowser::OnBnClickedBrowserButton)
	ON_WM_SIZE()
	ON_WM_CLOSE()
	ON_EN_SETFOCUS(IDC_BROWSER_EDIT, &CpeepmBrowser::OnEnSetfocusBrowserEdit)
END_MESSAGE_MAP()

CpeepmBrowser::CpeepmBrowser(CWnd* pParent /*=NULL*/) : CDialog(CpeepmBrowser::IDD, pParent)
{
	m_Crack = NULL;
	m_ThreadMax = 0;
	m_ThreadFinish = 0;
	m_Quit = FALSE;
}

CpeepmBrowser::~CpeepmBrowser()
{

}

void CpeepmBrowser::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_BROWSER_EDIT, m_Edit);
	DDX_Control(pDX, IDC_BROWSER_BUTTON, m_Button);			
	DDX_Control(pDX, IDC_BROWSER_EXPLORER, m_Browser);
}

BOOL CpeepmBrowser::OnInitDialog()
{
	CDialog::OnInitDialog();	
	m_Edit.SetFocus();	
	m_Browser.Navigate(_T("about:blank"), NULL, NULL, NULL, NULL);
	RECT rect, rectBrowser;
	GetClientRect(&rect);
	m_Browser.GetWindowRect(&rectBrowser);
	ScreenToClient(&rectBrowser);
	m_Bottom = rect.bottom - rectBrowser.bottom;
	return TRUE;
}

void CpeepmBrowser::OnPaint()
{
	CDialog::OnPaint();	
}

void CpeepmBrowser::OnBnClickedBrowserButton()
{		
	CStringA tmp = "http://";
	tmp.Append(Server);
	CString server(tmp);

	CString content = _T("");
	m_Edit.GetWindowText(content);
	if (content.GetLength() > 0 && content.Find(server) > -1)
	{
		TCHAR tmp[1024] = {0};
		GetModuleFileName(NULL, tmp, sizeof(tmp));
		CString path = tmp;
		path = path.Left(path.ReverseFind('\\'));
		path.Append(_T("\\"));

		CFileDialog open(TRUE);
		open.m_ofn.lpstrTitle = _T("选择账号");
		open.m_ofn.lpstrInitialDir = path;
		INT_PTR resp = open.DoModal();
		if (resp == IDOK)
		{
			CString path = open.GetPathName();

			char* url = CpeepmUtil::WideCharToChar(content, content.GetLength());
			if (url == NULL)
				return;

			char* p = CpeepmUtil::WideCharToChar(path, path.GetLength());

			CpeepmDlg* parent = (CpeepmDlg*)GetParent();
			m_ThreadMax = 1;
			DWORD delay = parent->GetDelay();

			BOOL* success = new BOOL;
			*success = FALSE;
			CCriticalSection* section = new CCriticalSection();				
			CEvent* event = new CEvent(FALSE, TRUE, NULL, NULL);
			PeepmBrowser* browser = new PeepmBrowser();
			browser->path = p;
			browser->username = NULL;
			browser->password = NULL;
			browser->next = NULL;
			browser->use = FALSE;
			browser->section = NULL;

			m_Crack = new CpeepmCrack*[m_ThreadMax];

			for (UINT i = 0;i < m_ThreadMax;i++)
			{										
				CpeepmCrack* crack = new CUrlBrowser(browser, url, success, i, m_ThreadMax, delay, section, event, NULL, *this);						
				m_Crack[i] = crack;
				CWinThread* thread = AfxBeginThread(CpeepmCrack::OnThread, crack);
				crack->SetThread(thread);
			}

			CWinThread* thread = AfxBeginThread(CUrlBrowser::InitAccount, m_Crack[0]);
			m_Button.EnableWindow(FALSE);
		}
	}
}

void CpeepmBrowser::OnSize(UINT nType, int cx, int cy)
{	
	RECT rect, rectButton, rectEdit, rectBrowser;	
	GetClientRect(&rect);
	
	if (m_Button.GetSafeHwnd() != NULL)
	{
		m_Button.GetWindowRect(&rectButton);
		ScreenToClient(&rectButton);
	}
	else	
		return;
	
	if (m_Edit.GetSafeHwnd() != NULL)
	{
		m_Edit.GetWindowRect(&rectEdit);
		ScreenToClient(&rectEdit);
	}
	else
		return;	

	if (m_Browser.GetSafeHwnd() != NULL)
	{
		m_Browser.GetWindowRect(&rectBrowser);
		ScreenToClient(&rectBrowser);
	}
	else
		return;
	
	long left = rectEdit.left;
	long width = rectButton.right - rectButton.left;	
	rectButton.right = cx - left;
	rectButton.left = rectButton.right - width;
	rectEdit.right = rectButton.left - 2;
	rectBrowser.right = cx - left;
	rectBrowser.bottom = cy - m_Bottom;

	m_Button.MoveWindow(&rectButton);
	m_Edit.MoveWindow(&rectEdit);	
	m_Browser.MoveWindow(&rectBrowser);
}

void CpeepmBrowser::OnThreadBrowser(const char* url, char* cookie)
{		
	wchar_t* u = CpeepmUtil::CharToWideChar(url, strlen(url));
	if (u == NULL)
		return;

	char* o = GetCookie();
	SaveCookie(cookie);
	m_Browser.Navigate(u, NULL, NULL, NULL, NULL);
	Sleep(1000);
	SaveCookie(o);
	ReleaseArray(o);
	ReleaseArray(u);	
}

char* CpeepmBrowser::GetCookie()
{	
	const char* http = "http://";
	UINT len = strlen(http) + strlen(Server);
	char* buffer = new char[len + 1];
	ZeroMemory(buffer, len + 1);
	strcat(buffer, http);
	strcat(buffer, Server);
	wchar_t* host = CpeepmUtil::CharToWideChar(buffer, len);
	ReleaseArray(buffer);
	if (host == NULL)
		return NULL;

	wchar_t tmp[1] = {0};
	DWORD size = 0;
	InternetGetCookie(host, NULL, tmp, &size);
	if (size == 0)
	{
		ReleaseArray(host);
		return NULL;
	}

	wchar_t* cookie = new wchar_t[size / 2 + 1];
	wmemset(cookie, 0, size / 2 + 1);
	InternetGetCookie(host, NULL, cookie, &size);
	ReleaseArray(host);
	len = wcslen(cookie);
	if (len > 0)	
	{
		char* c = CpeepmUtil::WideCharToChar(cookie, len);
		ReleaseArray(cookie);
		return c;	
	}
	else
	{
		ReleaseArray(cookie);
		return NULL;
	}
}

void CpeepmBrowser::SaveCookie(const wchar_t* host, const char* name, UINT nameLength, const char* value, UINT valueLength)
{
	wchar_t* n = CpeepmUtil::CharToWideChar(name, nameLength);
	wchar_t* v = CpeepmUtil::CharToWideChar(value, valueLength);

	if (n != NULL && v != NULL)	
		InternetSetCookie(host, n, v);	
	
	ReleaseArray(n);
	ReleaseArray(v);
}

void CpeepmBrowser::SaveCookie(char* cookie)
{
	if (cookie == NULL)
		return;

	const char* http = "http://";
	UINT len = strlen(http) + strlen(Server);
	char* buffer = new char[len + 1];
	ZeroMemory(buffer, len + 1);
	strcat(buffer, http);
	strcat(buffer, Server);
	wchar_t* host = CpeepmUtil::CharToWideChar(buffer, len);
	ReleaseArray(buffer);
	if (host == NULL)
		return;

	char* p = cookie;
	char* n = p;
	char* v = p;	
	UINT nameLength = 0;
	UINT valueLength = 0;
		
	while (*p != '\0')
	{				
		if (*p == '=')
		{
			while (n < p)
			{
				if (*n != '=' && *n != ';' && *n != ' ')
					break;

				n++;
			}

			nameLength = p - n;
			v = p + 1;
		}

		if (*p == ';' && nameLength > 0)
		{
			valueLength = p - v;
			SaveCookie(host, n, nameLength, v, valueLength);
			nameLength = 0;
			valueLength = 0;
			n = p;
		}
		
		p++;
	}

	if (nameLength > 0 && v < p)
	{
		valueLength = p - v;
		SaveCookie(host, n, nameLength, v, valueLength);
	}

	ReleaseArray(host);
}

BOOL CpeepmBrowser::OnThreadCode(UINT id, Request& request, Response& response)
{
	char* body = response.body;
	UINT length = response.bodyLength;
	if (length > 0)
	{
		CCodeDlg dialog;
		if (!dialog.SetCode(id, request, response))
			return FALSE;

		INT_PTR resp = dialog.DoModal();
		if (resp == IDOK)
		{
			char* code = dialog.GetCode();
			if (code == NULL)
				return FALSE;
			else
			{
				request.code = code;
				return TRUE;
			}
		}
	}
	
	return FALSE;
}

void CpeepmBrowser::OnThreadFinish(UINT id)
{
	m_ThreadFinish++;
	if (m_ThreadFinish == m_ThreadMax)
	{		
		CUrlBrowser* crack = (CUrlBrowser *)m_Crack[0];		
		crack->Release();			

		for (UINT i = 0;i < m_ThreadMax;i++)
			ReleaseObject(m_Crack[i]);

		m_ThreadFinish = 0;
		m_ThreadMax = 0;
		m_Quit = FALSE;
		m_Crack = NULL;
		SetWindowText(_T("所有任务完成"));
		m_Button.EnableWindow(TRUE);
	}
}

void CpeepmBrowser::OnClose()
{	
	CloseTask();
	__super::OnClose();
}

BOOL CpeepmBrowser::CloseTask()
{
	if (m_ThreadMax > 0 && !m_Quit)
	{
		m_Quit = TRUE;
		for (UINT i = 0;i < m_ThreadMax;i++)
			m_Crack[i]->Stop();
	}

	return TRUE;
}

void CpeepmBrowser::OnThreadProgress(UINT id, const char* username, const char* password, const char* progress)
{
	UINT len = strlen(username) + strlen(password) + 3;
	char* buffer = new char[len + 1];
	ZeroMemory(buffer, len + 1);
	strcat(buffer, username);
	strcat(buffer, " - ");
	strcat(buffer, password);
	wchar_t* text = CpeepmUtil::CharToWideChar(buffer, strlen(buffer));
	ReleaseArray(buffer);
	if (text != NULL)
	{
		SetWindowText(text);
		ReleaseArray(text);
	}
}

void CpeepmBrowser::OnEnSetfocusBrowserEdit()
{
	if (m_Edit.GetWindowTextLength() > 0)	
		m_Edit.PostMessage(EM_SETSEL, 0, -1);
}
