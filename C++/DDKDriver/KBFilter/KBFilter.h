///////////////////////////////////////////////////////////////////////////////
///
/// Copyright (c) 2011 - <company name here>
///
/// Original filename: KBFilter.h
/// Project          : KBFilter
/// Date of creation : <see KBFilter.cpp>
/// Author(s)        : <see KBFilter.cpp>
///
/// Purpose          : <see KBFilter.cpp>
///
/// Revisions:         <see KBFilter.cpp>
///
///////////////////////////////////////////////////////////////////////////////

// $Id$

#ifndef __KBFILTER_H_VERSION__
#define __KBFILTER_H_VERSION__ 100

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

#include "drvcommon.h"
#include "drvversion.h"

#define DEVICE_NAME			L"\\Device\\KBFILTER_DeviceName"
#define SYMLINK_NAME		L"\\??\\KBFILTER_DeviceName"

#ifndef FILE_DEVICE_KBFILTER
#define FILE_DEVICE_KBFILTER 0x8000
#endif

typedef struct _DEVICE_EXTENSION
{	
	PDEVICE_OBJECT TopOfStack;
	PIRP Irp;
	BOOLEAN Waitfor;

} DEVICE_EXTENSION, *PDEVICE_EXTENSION;

typedef struct _KEYBUFFER
{
	UCHAR Buffer[100];
	ULONG Length;
	KSPIN_LOCK SpinLock;

} KEYBUFFER, *PKEYBUFFER;

const ULONG IOCTL_KBFILTER_OPERATION = CTL_CODE(FILE_DEVICE_KBFILTER, 0x01, METHOD_BUFFERED, FILE_READ_DATA | FILE_WRITE_DATA);

#endif // __KBFILTER_H_VERSION__
