#include "stdafx.h"
#include "peepm.h"
#include "peepmDlg.h"
#include "peepmUtil.h"
#include "UserCrack.h"
#include "UsersCrack.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

BEGIN_MESSAGE_MAP(CpeepmDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_COMMAND(ID_EXIT, &CpeepmDlg::OnExit)
	ON_WM_CLOSE()
	ON_WM_SIZE()
	ON_WM_CTLCOLOR()
	ON_COMMAND(ID_USERS_START, &CpeepmDlg::OnUsersStart)
	ON_COMMAND(ID_USER_START, &CpeepmDlg::OnUserStart)
	ON_COMMAND(ID_TASK_PAUSE, &CpeepmDlg::OnTaskPause)
	ON_COMMAND(ID_TASK_STOP, &CpeepmDlg::OnTaskStop)
	ON_COMMAND(ID_DATE_BUILD, &CpeepmDlg::OnDateBuild)		
	ON_COMMAND(ID_USERS_BROWSER, &CpeepmDlg::OnUsersBrowser)
	ON_UPDATE_COMMAND_UI(ID_THREADS_1, &CpeepmDlg::OnUpdateThreads)
	ON_UPDATE_COMMAND_UI(ID_THREADS_2, &CpeepmDlg::OnUpdateThreads)
	ON_UPDATE_COMMAND_UI(ID_THREADS_3, &CpeepmDlg::OnUpdateThreads)
	ON_UPDATE_COMMAND_UI(ID_THREADS_4, &CpeepmDlg::OnUpdateThreads)
	ON_UPDATE_COMMAND_UI(ID_THREADS_5, &CpeepmDlg::OnUpdateThreads)
	ON_UPDATE_COMMAND_UI(ID_USERS_SINGLE, &CpeepmDlg::OnUpdateUsersSingle)
	ON_UPDATE_COMMAND_UI(ID_DELAY_1, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_5, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_20, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_50, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_100, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_200, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_500, &CpeepmDlg::OnUpdateDelay)
	ON_UPDATE_COMMAND_UI(ID_DELAY_1000, &CpeepmDlg::OnUpdateDelay)	
END_MESSAGE_MAP()

BEGIN_MESSAGE_MAP(CCodeDlg, CDialog)
	ON_WM_PAINT()
END_MESSAGE_MAP()

BEGIN_MESSAGE_MAP(CUsernameDlg, CDialog)
	ON_WM_PAINT()
END_MESSAGE_MAP()

CCodeDlg::CCodeDlg(CWnd* pParent /*=NULL*/) : CDialog(CCodeDlg::IDD, pParent)
{
	m_Image = NULL;
	m_Code = NULL;
	m_Stream = NULL;
	m_Id = 0;
}

CCodeDlg::~CCodeDlg()
{	
	ReleaseObject(m_Image);
	GlobalFree(m_Buffer);
	if (m_Stream != NULL)
		m_Stream->Release();
}

void CCodeDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_CODE_EDIT, m_Edit);	
}

BOOL CCodeDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	TCHAR text[16] = {0};
	wsprintf(text, _T("线程%d"), m_Id);
	SetWindowText(text);
	m_Edit.SetFocus();
	return TRUE;
}

BOOL CCodeDlg::SetCode(UINT id, const Request& request, const Response& response)
{
	m_Id = id;
	char* body = response.body;
	UINT length = response.bodyLength;
	HRESULT result = S_OK;

	m_Buffer = GlobalAlloc(GMEM_MOVEABLE, length);
	void* lock = GlobalLock(m_Buffer);
	CopyMemory(lock, body, length);
	GlobalUnlock(m_Buffer);	
	result = CreateStreamOnHGlobal(m_Buffer, FALSE, &m_Stream);		
	if (result != S_OK && m_Stream == NULL)
		return FALSE;
	
	m_Image = Image::FromStream(m_Stream);		
	if (m_Image == NULL)
		return FALSE;
	else
		return TRUE;
}

char* CCodeDlg::GetCode()
{	
	return m_Code;
}

void CCodeDlg::OnPaint()
{		
	CPaintDC dc(this);
	Graphics graphics(dc.m_hDC);	
	RECT rect;
	GetWindowRect(&rect);
	int w = (rect.right - rect.left - m_Image->GetWidth()) / 2;
	if (w < 0)
		return;

	int h = 10;
	Rect r(w, h, m_Image->GetWidth(), m_Image->GetHeight());
	Status s = graphics.DrawImage(m_Image, r);	
}

void CCodeDlg::OnOK()
{
	CString content = _T("");
	m_Edit.GetWindowText(content);	
	m_Code = CpeepmUtil::WideCharToChar(content, content.GetLength());	
	CDialog::OnOK();
}

//
CUsernameDlg::CUsernameDlg(CWnd* pParent /*=NULL*/) : CDialog(CUsernameDlg::IDD, pParent)
{
	m_Username = NULL;
	m_Single = FALSE;
}

CUsernameDlg::~CUsernameDlg()
{	

}

void CUsernameDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_USERNAME_EDIT, m_Edit);
	DDX_Control(pDX, IDC_USERNAME_CHECK, m_CheckBox);
}

BOOL CUsernameDlg::OnInitDialog()
{
	CDialog::OnInitDialog();	
	m_Edit.SetFocus();
	return TRUE;
}

void CUsernameDlg::OnPaint()
{
	CDialog::OnPaint();	
}

void CUsernameDlg::OnOK()
{	
	CString content = _T("");
	m_Edit.GetWindowText(content);	
	m_Username = CpeepmUtil::WideCharToChar(content, content.GetLength());
	if (m_CheckBox.GetCheck() == 0)
		m_Single = FALSE;
	else
		m_Single = TRUE;

	CDialog::OnOK();
}

char* CUsernameDlg::GetUsername()
{	
	return m_Username;
}

BOOL CUsernameDlg::GetSingle()
{
	return m_Single;
}

//
CpeepmDlg::CpeepmDlg(CWnd* pParent /*=NULL*/) : CDialog(CpeepmDlg::IDD, pParent)
{	
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);	
	m_Crack = NULL;
	m_ThreadMax = 0;
	m_ThreadFinish = 0;
	m_Quit = FALSE;
}

CpeepmDlg::~CpeepmDlg()
{
	GdiplusShutdown(m_pGdiToken);
}

void CpeepmDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BOOL CpeepmDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
				
	SetIcon(m_hIcon, TRUE);
	SetIcon(m_hIcon, FALSE);

	m_Menu.LoadMenu(IDR_MENU);
	SetMenu(&m_Menu);
	m_Menu.EnableMenuItem(ID_TASK_PAUSE, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
	m_Menu.EnableMenuItem(ID_TASK_STOP, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);

	CRect rect, rectStatusBar;
	GetClientRect(rect);

	int width[] = {320, 500, -1};
	m_StatusBar.Create(WS_CHILD | WS_VISIBLE, CRect(0, 0, 0, 0), this, IDR_STATUSBAR);	
	m_StatusBar.SetParts(sizeof(width) / sizeof(UINT), &width[0]);
	m_StatusBar.GetClientRect(rectStatusBar);

	m_Edit.Create(WS_CHILD | WS_VISIBLE | ES_MULTILINE | WS_TABSTOP | WS_VSCROLL, CRect(0, 0, rect.Width(), rect.Height() - rectStatusBar.Height()), this, IDR_EDIT);
	m_Edit.ModifyStyleEx(0, WS_EX_CLIENTEDGE, SWP_DRAWFRAME);	
	m_Edit.SetReadOnly(TRUE);
	m_Edit.SetLimitText(35 * 1024);
	SetFont();

	m_Browser.Create(IDD_BROWSER_DIALOG, this);
		
	GdiplusStartupInput startupInput;	
	GdiplusStartup(&m_pGdiToken, &startupInput, NULL);
	
	return TRUE;
}

void CpeepmDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this);

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

HCURSOR CpeepmDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CpeepmDlg::SetFont()
{
	LOGFONT logfont = {0};
	logfont.lfHeight = 15;
	_tcscpy(logfont.lfFaceName, _T("宋体"));

	m_Font.CreateFontIndirect(&logfont);
	m_Edit.SetFont(&m_Font, FALSE);
}

void CpeepmDlg::OnExit()
{
	if (CloseTask())
		CDialog::OnOK();
}

void CpeepmDlg::OnClose()
{	
	if (CloseTask())
		CDialog::OnClose();
}

void CpeepmDlg::OnSize(UINT nType, int cx, int cy)
{
	CRect rect, rectStatusBar;
	GetClientRect(rect);

	if (m_StatusBar.GetSafeHwnd() != NULL)
	{		
		m_StatusBar.GetClientRect(rectStatusBar);	
		m_StatusBar.MoveWindow(0, cy - rectStatusBar.Height(), rect.Width(), rectStatusBar.Height());		
	}

	if (m_Edit.GetSafeHwnd() != NULL)	
		m_Edit.MoveWindow(0, 0, rect.Width(), rect.Height() - rectStatusBar.Height());
}

HBRUSH CpeepmDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	HBRUSH hbru = CreateSolidBrush(RGB(255, 255, 255));

	if (pWnd->GetDlgCtrlID() == IDR_EDIT) 
	{ 		
		pDC->SetTextColor(RGB(0, 0, 0));
		pDC->SetBkColor(RGB(255, 255, 255));
		return hbru;
	} 

	return hbr;
}

void CpeepmDlg::OnUsersStart()
{		
	StartUsersCrack();
}

void CpeepmDlg::OnUserStart()
{
	StartUserCrack();
}

void CpeepmDlg::OnTaskStop()
{
	CloseTask();
}

void CpeepmDlg::OnDateBuild()
{
	TCHAR tmp[1024] = {0};
	GetModuleFileName(NULL, tmp, sizeof(tmp));
	CString path = tmp;
	path = path.Left(path.ReverseFind('\\'));
	path.Append(_T("\\"));

	CFileDialog save(FALSE);
	save.m_ofn.lpstrTitle = _T("保存路径");
	save.m_ofn.lpstrInitialDir = path;
	save.m_ofn.lpstrFilter = _T("TXT(*.txt)");
	INT_PTR resp = save.DoModal();
	if (resp == IDOK)
	{
		CString savePath = save.GetPathName();
		if (savePath.Right(4) != ".txt")
			savePath += ".txt";

		char* p = CpeepmUtil::WideCharToChar(savePath, savePath.GetLength());
		FILE* file = fopen(p, "a+");
		
		for (UINT i = 1970;i < 1990;i++)
		{
			for (UINT j = 1;j < 13;j++)
			{
				for (UINT k = 1;k < 32;k++)
				{				
					char buffer[16] = {0};
					//sprintf(buffer, "%02d%02d\r\n", i, j);
					//sprintf(buffer, "%d%02d\r\n", i, j);
					sprintf(buffer, "%d%d%02d\r\n", i, j, k);
					fwrite(buffer, strlen(buffer), 1, file);				
				}
			}
		}
		

		fclose(file);
		ReleaseArray(p);
		MessageBox(_T("生成成功"));
	}
}

void CpeepmDlg::OnThreadStart(UINT id)
{	
	char buffer[16] = {0};
	sprintf(buffer, "线程%d启动", id);
	InsertText(buffer);	
}

void CpeepmDlg::OnThreadConnect(UINT id, BOOL success)
{	
	char buffer[32] = {0};
	if (success)
		sprintf(buffer, "线程%d连接成功", id);
	else
		sprintf(buffer, "线程%d连接失败", id);

	InsertText(buffer);
}

BOOL CpeepmDlg::OnThreadCode(UINT id, Request& request, Response& response)
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

void CpeepmDlg::OnThreadLogin(UINT id, UINT ret)
{
	char buffer[64] = {0};
	sprintf(buffer, "线程%d登录返回: %d", id, ret);
	InsertText(buffer);
}

void CpeepmDlg::OnThreadReLogin(UINT id)
{
	char buffer[64] = {0};
	sprintf(buffer, "线程%d重登录");
	InsertText(buffer);
}

void CpeepmDlg::OnThreadErrorTimes(UINT id)
{
	char buffer[64] = {0};
	sprintf(buffer, "线程%d返回登录失败次数错误", id);
	InsertText(buffer);
}

void CpeepmDlg::OnThreadFinish(UINT id)
{
	char buffer[32] = {0};
	sprintf(buffer, "线程%d 完成", id);
	InsertText(buffer);
	m_ThreadFinish++;
	if (m_ThreadFinish == m_ThreadMax)
	{
		CpeepmCrack* crack = m_Crack[0];
		crack->Release();

		for (UINT i = 0;i < m_ThreadMax;i++)
			ReleaseObject(m_Crack[i]);

		m_ThreadFinish = 0;
		m_ThreadMax = 0;
		m_Quit = FALSE;
		m_Crack = NULL;
		ModifyMenu(&m_Menu, ID_TASK_PAUSE, _T("暂停(&P)"));
		m_Menu.EnableMenuItem(ID_TASK_PAUSE, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
		m_Menu.EnableMenuItem(ID_TASK_STOP, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
		m_Menu.EnableMenuItem(ID_USERS_START, MF_ENABLED | MF_BYCOMMAND);
		m_Menu.EnableMenuItem(ID_USER_START, MF_ENABLED | MF_BYCOMMAND);		
		MessageBox(_T("所有任务完成"));
	}
}

void CpeepmDlg::OnThreadSuccess(const char* username, const char* password)
{
	UINT len = strlen(username) + strlen(password) + 1;
	char* buffer = new char[len + 1];
	ZeroMemory(buffer, len + 1);
	strcat(buffer, username);
	strcat(buffer, "\t");
	strcat(buffer, password);
	InsertText(buffer);
	ReleaseArray(buffer);
}

void CpeepmDlg::OnThreadProgress(UINT id, const char* username, const char* password, const char* progress)
{	
	SetStatusText(password, 0);
	SetStatusText(username, 1);
	if (progress == NULL)
	{
		char buffer[16] = {0};
		sprintf(buffer, "线程%d", id);
		SetStatusText(buffer, 2);
	}
	else
		SetStatusText(progress, 2);
}

BOOL CpeepmDlg::CloseTask()
{
	if (m_ThreadMax > 0 && !m_Quit)
	{
		m_Quit = TRUE;
		for (UINT i = 0;i < m_ThreadMax;i++)
			m_Crack[i]->Stop();
	}
			
	return TRUE;
}

void CpeepmDlg::InsertText(const char* text)
{	
	m_Section.Lock();
	if (m_Edit.GetWindowTextLength() > 30 * 1024)
	{		
		m_Edit.SetWindowText(_T(""));
		m_Edit.UpdateData(TRUE);
	}

	SYSTEMTIME now = {0};
	GetLocalTime(&now);

	char buffer[30] = {0};
	sprintf(buffer, "%d-%02d-%02d %02d:%02d:%02d", now.wYear, now.wMonth, now.wDay, now.wHour, now.wMinute, now.wSecond);

	char* content = NULL;
	UINT dataLength = 0, bufferLength = 0;
	CpeepmUtil::AppendBuffer(&content, dataLength, bufferLength, buffer, strlen(buffer));
	CpeepmUtil::AppendBuffer(&content, dataLength, bufferLength, "\t", 1);
	CpeepmUtil::AppendBuffer(&content, dataLength, bufferLength, text, strlen(text));
	CpeepmUtil::AppendBuffer(&content, dataLength, bufferLength, "\r\n", 2);

	wchar_t* wchar = CpeepmUtil::CharToWideChar(content, strlen(content));
	if (wchar != NULL)
	{				
		int len = m_Edit.GetWindowTextLength();		
		m_Edit.SetSel(len, len); 
		m_Edit.ReplaceSel(wchar);	
		ReleaseArray(wchar);
	}

	ReleaseArray(content);
	m_Section.Unlock();
}

void CpeepmDlg::SetStatusText(const char* text, int panel)
{
	wchar_t* wchar = CpeepmUtil::CharToWideChar(text, strlen(text));
	if (wchar != NULL)
	{
		m_StatusBar.SetText(wchar, 0, panel);
		ReleaseArray(wchar);
	}
}

void CpeepmDlg::OnThreadUsername(UINT id, const char* username)
{	
	UINT len = strlen(username) + 32;
	char* buffer = new char[len + 1];
	ZeroMemory(buffer, len + 1);
	sprintf(buffer, "线程%d获取用户名, %s", id, username);
	InsertText(buffer);
	ReleaseArray(buffer);
}

void CpeepmDlg::OnThreadDelay(UINT id, DWORD delay)
{
	char buffer[32] = {0};
	sprintf(buffer, "线程%d修改延迟时间, %dms", id, delay);
	InsertText(buffer);	
}

void CpeepmDlg::StartUsersCrack()
{	
	TCHAR tmp[1024] = {0};
	GetModuleFileName(NULL, tmp, sizeof(tmp));
	CString path = tmp;
	path = path.Left(path.ReverseFind('\\'));
	path.Append(_T("\\"));

	CFileDialog open(TRUE);
	open.m_ofn.lpstrTitle = _T("选择用户名");
	open.m_ofn.lpstrInitialDir = path;
	INT_PTR resp = open.DoModal();
	if (resp == IDOK)
	{
		CString usernamePath = open.GetPathName();		
		open.m_ofn.lpstrTitle = _T("选择密码");
		open.m_ofn.lpstrInitialDir = path;
		resp = open.DoModal();
		if (resp == IDOK)
		{
			CString passwordPath = open.GetPathName();
			CFileDialog save(FALSE);
			save.m_ofn.lpstrTitle = _T("保存账号路径");
			save.m_ofn.lpstrInitialDir = path;
			save.m_ofn.lpstrFilter = _T("TXT(*.txt)");
			resp = save.DoModal();
			if (resp == IDOK)
			{
				CString savePath = save.GetPathName();
				if (savePath.Right(4) != ".txt")
					savePath += ".txt";

				char* u = CpeepmUtil::WideCharToChar(usernamePath, usernamePath.GetLength());
				char* p = CpeepmUtil::WideCharToChar(passwordPath, passwordPath.GetLength());
				char* s = CpeepmUtil::WideCharToChar(savePath, savePath.GetLength());

				m_Quit = FALSE;
				m_ThreadFinish = 0;
				m_ThreadMax = GetThreads();
				DWORD delay = GetDelay();				
				if (m_ThreadMax > 0 && delay > 0)
				{
					m_Edit.SetWindowText(_T(""));
					m_Edit.UpdateData(FALSE);
					BOOL single = FALSE;
					UINT state = m_Menu.GetMenuState(ID_USERS_SINGLE, MF_CHECKED);
					if (state == MF_CHECKED)
						single = TRUE;						

					CCriticalSection* section = new CCriticalSection();				
					CEvent* event = new CEvent(FALSE, TRUE, NULL, NULL);
					PeepmUsername* peepmUsername = new PeepmUsername();
					PeepmWrite* peepmWrite = new PeepmWrite();
					peepmWrite->lock = new CCriticalSection();
					peepmWrite->writePath = s;

					m_Crack = new CpeepmCrack*[m_ThreadMax];

					for (UINT i = 0;i < m_ThreadMax;i++)
					{										
						CpeepmCrack* crack = new CUsersCrack(single, peepmUsername, u, i, m_ThreadMax, delay, section, event, peepmWrite, p, *this);						
						m_Crack[i] = crack;
						CWinThread* thread = AfxBeginThread(CpeepmCrack::OnThread, crack);
						crack->SetThread(thread);
					}

					CWinThread* thread = AfxBeginThread(CUsersCrack::InitUsername, m_Crack[0]);
					m_Menu.EnableMenuItem(ID_USERS_START, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
					m_Menu.EnableMenuItem(ID_USER_START, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
					m_Menu.EnableMenuItem(ID_TASK_PAUSE, MF_ENABLED | MF_BYCOMMAND);
					m_Menu.EnableMenuItem(ID_TASK_STOP, MF_ENABLED | MF_BYCOMMAND);	
				}				
			}
		}
	}
}

void CpeepmDlg::StartUserCrack()
{
	CUsernameDlg dialog;	
	INT_PTR resp = dialog.DoModal();
	if (resp == IDOK)
	{
		char* username = dialog.GetUsername();
		BOOL single = dialog.GetSingle();
		if (username != NULL)
		{
			TCHAR tmp[1024] = {0};
			GetModuleFileName(NULL, tmp, sizeof(tmp));
			CString path = tmp;
			path = path.Left(path.ReverseFind('\\'));
			path.Append(_T("\\"));

			CFileDialog open(TRUE);
			open.m_ofn.lpstrTitle = _T("选择密码");
			open.m_ofn.lpstrInitialDir = path;
			resp = open.DoModal();
			if (resp == IDOK)
			{
				CString passwordPath = open.GetPathName();
				CFileDialog save(FALSE);
				save.m_ofn.lpstrTitle = _T("保存账号路径");
				save.m_ofn.lpstrInitialDir = path;
				save.m_ofn.lpstrFilter = _T("TXT(*.txt)");
				resp = save.DoModal();
				if (resp == IDOK)
				{
					CString savePath = save.GetPathName();
					if (savePath.Right(4) != ".txt")
						savePath += ".txt";

					char* p = CpeepmUtil::WideCharToChar(passwordPath, passwordPath.GetLength());
					char* s = CpeepmUtil::WideCharToChar(savePath, savePath.GetLength());

					m_Quit = FALSE;
					m_ThreadFinish = 0;
					m_ThreadMax = GetThreads();
					DWORD delay = GetDelay();					
					if (m_ThreadMax > 0 && delay > 0)
					{
						m_Edit.SetWindowText(_T(""));
						m_Edit.UpdateData(FALSE);
						CCriticalSection* section = new CCriticalSection();				
						CEvent* event = new CEvent(FALSE, TRUE, NULL, NULL);
						PeepmPassword* peepmPassword = new PeepmPassword();
						peepmPassword->passwordPath = p;
						peepmPassword->file = NULL;
						peepmPassword->buffer = new char[readmax + 1];
						ZeroMemory(peepmPassword->buffer, readmax + 1);
						peepmPassword->total = readmax;
						peepmPassword->size = peepmPassword->total;
						peepmPassword->length = 0;
						peepmPassword->p = peepmPassword->buffer;
						peepmPassword->success = FALSE;
						peepmPassword->rows = 0;
						peepmPassword->row = 0;

						PeepmWrite* peepmWrite = new PeepmWrite();
						peepmWrite->lock = new CCriticalSection();
						peepmWrite->writePath = s;

						m_Crack = new CpeepmCrack*[m_ThreadMax];

						for (UINT i = 0;i < m_ThreadMax;i++)
						{										
							CpeepmCrack* crack = new CUserCrack(single, peepmPassword, username, i, m_ThreadMax, delay, section, event, peepmWrite, *this);
							m_Crack[i] = crack;
							CWinThread* thread = AfxBeginThread(CpeepmCrack::OnThread, crack);
							crack->SetThread(thread);
						}

						CWinThread* thread = AfxBeginThread(CUserCrack::InitPassword, m_Crack[0]);
						m_Menu.EnableMenuItem(ID_USERS_START, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
						m_Menu.EnableMenuItem(ID_USER_START, MF_DISABLED | MF_BYCOMMAND | MF_GRAYED);
						m_Menu.EnableMenuItem(ID_TASK_PAUSE, MF_ENABLED | MF_BYCOMMAND);
						m_Menu.EnableMenuItem(ID_TASK_STOP, MF_ENABLED | MF_BYCOMMAND);						
						return;
					}					
				}
			}

			ReleaseArray(username);			
		}
	}
}

void CpeepmDlg::OnUpdateThreads(CCmdUI *pCmdUI)
{
	UINT id = pCmdUI->m_nID;	
	CMenu* menu = m_Menu.GetSubMenu(2);
	if (menu == NULL)
		return;

	CMenu* sub = menu->GetSubMenu(1);
	if (sub == NULL)
		return;
		
	UINT count = sub->GetMenuItemCount();
	for (UINT i = 0;i < count;i++)
	{
		UINT idx = sub->GetMenuItemID(i);
		if (idx == id)
			sub->CheckMenuItem(idx, MF_BYCOMMAND | MF_CHECKED);
		else
			sub->CheckMenuItem(idx, MF_BYCOMMAND | MF_UNCHECKED);
	}
}

UINT CpeepmDlg::GetThreads()
{
	CMenu* menu = m_Menu.GetSubMenu(2);
	if (menu == NULL)
		return 0;

	CMenu* sub = menu->GetSubMenu(1);
	if (sub == NULL)
		return 0;

	UINT thread = 1;
	UINT count = sub->GetMenuItemCount();
	for (UINT i = 0;i < count;i++)
	{
		UINT idx = sub->GetMenuItemID(i);
		UINT state = sub->GetMenuState(idx, MF_CHECKED);
		if (state == MF_CHECKED)
			return thread;
		else
			thread++;
	}

	return thread;
}

DWORD CpeepmDlg::GetDelay()
{
	CMenu* menu = m_Menu.GetSubMenu(2);
	if (menu == NULL)
		return 0;

	CMenu* sub = menu->GetSubMenu(0);
	if (sub == NULL)
		return 0;
	
	UINT count = sub->GetMenuItemCount();
	for (UINT i = 0;i < count;i++)
	{
		UINT idx = sub->GetMenuItemID(i);
		UINT state = sub->GetMenuState(idx, MF_CHECKED);
		if (state == MF_CHECKED)
		{
			switch(idx)
			{
			case ID_DELAY_1:
				return 1;

			case ID_DELAY_5:
				return 5;

			case ID_DELAY_20:
				return 20;

			case ID_DELAY_50:
				return 50;

			case ID_DELAY_100:
				return 100;

			case ID_DELAY_200:
				return 200;

			case ID_DELAY_500:
				return 500;

			case ID_DELAY_1000:
				return 1000;
			}
		}			
	}

	return 0;
}

void CpeepmDlg::OnUpdateUsersSingle(CCmdUI *pCmdUI)
{		
	UINT state = m_Menu.GetMenuState(ID_USERS_SINGLE, MF_CHECKED);
	if (state == MF_CHECKED)
		m_Menu.CheckMenuItem(ID_USERS_SINGLE, MF_BYCOMMAND | MF_UNCHECKED);
	else
		m_Menu.CheckMenuItem(ID_USERS_SINGLE, MF_BYCOMMAND | MF_CHECKED);
}

void CpeepmDlg::OnUpdateDelay(CCmdUI *pCmdUI)
{
	UINT id = pCmdUI->m_nID;	
	CMenu* menu = m_Menu.GetSubMenu(2);
	if (menu == NULL)
		return;

	CMenu* sub = menu->GetSubMenu(0);
	if (sub == NULL)
		return;

	UINT count = sub->GetMenuItemCount();
	for (UINT i = 0;i < count;i++)
	{
		UINT idx = sub->GetMenuItemID(i);
		if (idx == id)		
			sub->CheckMenuItem(idx, MF_BYCOMMAND | MF_CHECKED);			
		else
			sub->CheckMenuItem(idx, MF_BYCOMMAND | MF_UNCHECKED);
	}

	SetDelay(id);
}

void CpeepmDlg::OnThreadInfo(UINT id, const char* content)
{
	char* buffer = NULL;	
	if (content != NULL)
	{
		UINT len = 10 + strlen(content);	
		buffer = new char[len + 1];
		ZeroMemory(buffer, len + 1);
		sprintf(buffer, "线程%d %s", id, content);
	}
	else
	{
		UINT len = 15;
		buffer = new char[len + 1];
		ZeroMemory(buffer, len + 1);
		sprintf(buffer, "线程%d %s", id, "NULL");
	}
	
	InsertText(buffer);
	ReleaseArray(buffer);
}

void CpeepmDlg::SetDelay(UINT id)
{
	DWORD delay = 0;

	switch(id)
	{
	case ID_DELAY_1:
		delay = 1;
		break;

	case ID_DELAY_5:
		delay = 5;
		break;

	case ID_DELAY_20:
		delay = 20;
		break;

	case ID_DELAY_50:
		delay = 50;
		break;

	case ID_DELAY_100:
		delay = 100;
		break;

	case ID_DELAY_200:
		delay = 200;
		break;

	case ID_DELAY_500:
		delay = 500;
		break;

	case ID_DELAY_1000:
		delay = 1000;
		break;
	}

	if (delay > 0 && m_ThreadMax > 0)
	{
		for (UINT i = 0;i < m_ThreadMax;i++)
		{
			CpeepmCrack* crack = m_Crack[i];
			crack->SetDelay(delay);
		}
	}
}

void CpeepmDlg::OnUsersBrowser()
{	
	m_Browser.ShowWindow(SW_SHOW);	
}

void CpeepmDlg::OnTaskPause()
{
	CString content = _T("");
	m_Menu.GetMenuString(ID_TASK_PAUSE, content, MF_BYCOMMAND);
	if (content.GetLength() > 0 && m_ThreadMax > 0)
	{
		if (content == _T("暂停(&P)"))
		{
			for (UINT i = 0;i < m_ThreadMax;i++)
			{
				CpeepmCrack* crack = m_Crack[i];
				crack->SetPause(TRUE);
			}
			
			ModifyMenu(&m_Menu, ID_TASK_PAUSE, _T("恢复(&R)"));
			return;
		}

		if (content == _T("恢复(&R)"))
		{
			for (UINT i = 0;i < m_ThreadMax;i++)
			{
				CpeepmCrack* crack = m_Crack[i];
				crack->SetPause(FALSE);
			}

			ModifyMenu(&m_Menu, ID_TASK_PAUSE, _T("暂停(&P)"));
			return;
		}
	}
}

void CpeepmDlg::ModifyMenu(CMenu* menu, UINT id, const wchar_t* name)
{
	if (menu == NULL)
		return;

	UINT count = menu->GetMenuItemCount();
	if (count == 0)
		return;

	for (UINT i = 0;i < count;i++)
	{
		if (menu->GetMenuItemID(i) == id)		
			menu->ModifyMenu(i, MF_BYPOSITION, id, name);
		
		CMenu* sub = menu->GetSubMenu(i);
		ModifyMenu(sub, id, name);
	}
}