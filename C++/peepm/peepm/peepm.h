#pragma once

#ifndef __AFXWIN_H__
	#error "�ڰ������ļ�֮ǰ������stdafx.h�������� PCH �ļ�"
#endif

#include "resource.h"

class CpeepmApp : public CWinAppEx
{
public:
	CpeepmApp();

public:
	virtual BOOL InitInstance();
	DECLARE_MESSAGE_MAP()	
};

extern CpeepmApp theApp;