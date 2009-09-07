#include "FileFilter.h"

VOID SfGetObjectName(IN PVOID Object, IN OUT PUNICODE_STRING Name)
{
    NTSTATUS status;
    CHAR nibuf[512];        
    POBJECT_NAME_INFORMATION nameInfo = (POBJECT_NAME_INFORMATION)nibuf;
    ULONG retLength;

    status = ObQueryNameString(Object, nameInfo, sizeof(nibuf), &retLength);

    Name->Length = 0;
    if (NT_SUCCESS(status)) 
	{
        RtlCopyUnicodeString(Name, &nameInfo->Name);
    }
}

BOOLEAN SfIsAttachedToDeviceWXPAndLater(PDEVICE_OBJECT DeviceObject, PDEVICE_OBJECT *AttachedDeviceObject OPTIONAL)
{
    PDEVICE_OBJECT currentDevObj;
    PDEVICE_OBJECT nextDevObj;

    PAGED_CODE();
   
    ASSERT(gSfDynamicFunctions.GetAttachedDeviceReference != NULL);
    currentDevObj = (gSfDynamicFunctions.GetAttachedDeviceReference)(DeviceObject);

    do 
	{    
        if (IS_MY_DEVICE_OBJECT(currentDevObj)) 
		{
            if (ARGUMENT_PRESENT(AttachedDeviceObject)) 
			{
                *AttachedDeviceObject = currentDevObj;
            } 
			else 
			{
                ObDereferenceObject(currentDevObj);
            }

            return TRUE;
        }

        ASSERT(gSfDynamicFunctions.GetLowerDeviceObject != NULL);
        nextDevObj = (gSfDynamicFunctions.GetLowerDeviceObject)(currentDevObj);
        ObDereferenceObject(currentDevObj);
        currentDevObj = nextDevObj;
        
    }while (currentDevObj != NULL);
    
    if (ARGUMENT_PRESENT(AttachedDeviceObject)) 
	{
        *AttachedDeviceObject = NULL;
    }

    return FALSE;
} 

BOOLEAN SfIsAttachedToDevice(PDEVICE_OBJECT DeviceObject, PDEVICE_OBJECT *AttachedDeviceObject OPTIONAL)
{
    PAGED_CODE();

	ASSERT(gSfDynamicFunctions.GetLowerDeviceObject != NULL && gSfDynamicFunctions.GetDeviceAttachmentBaseRef != NULL);       
	return SfIsAttachedToDeviceWXPAndLater(DeviceObject, AttachedDeviceObject);
}

NTSTATUS SfIsShadowCopyVolume(IN PDEVICE_OBJECT StorageStackDeviceObject, OUT PBOOLEAN IsShadowCopy)
{			
	UNICODE_STRING volSnapDriverName;
	WCHAR buffer[MAX_DEVNAME_LENGTH];
	PUNICODE_STRING storageDriverName;
	ULONG returnedLength;
	NTSTATUS status;	

	PAGED_CODE();
	*IsShadowCopy = FALSE;
		
	if (StorageStackDeviceObject->DeviceType != FILE_DEVICE_DISK) 
	{
		return STATUS_SUCCESS;
	}

	storageDriverName = (PUNICODE_STRING)buffer;
	RtlInitEmptyUnicodeString(storageDriverName, Add2Ptr(storageDriverName, sizeof(UNICODE_STRING)), sizeof(buffer) - sizeof(UNICODE_STRING));

	status = ObQueryNameString(StorageStackDeviceObject, (POBJECT_NAME_INFORMATION)storageDriverName, storageDriverName->MaximumLength, &returnedLength);

	if (!NT_SUCCESS(status)) 
	{
		return status;
	}

	RtlInitUnicodeString(&volSnapDriverName, L"\\Driver\\VolSnap");

	if (RtlEqualUnicodeString(storageDriverName, &volSnapDriverName, TRUE)) 
	{
		*IsShadowCopy = TRUE;
	}
	
	return STATUS_SUCCESS;
}

NTSTATUS SfAttachDeviceToDeviceStack(IN PDEVICE_OBJECT SourceDevice, IN PDEVICE_OBJECT TargetDevice, IN OUT PDEVICE_OBJECT *AttachedToDeviceObject)
{
    PAGED_CODE();	
	ASSERT(gSfDynamicFunctions.AttachDeviceToDeviceStackSafe != NULL);
	return (gSfDynamicFunctions.AttachDeviceToDeviceStackSafe)(SourceDevice, TargetDevice, AttachedToDeviceObject);
}

NTSTATUS SfAttachToMountedDevice(IN PDEVICE_OBJECT DeviceObject, IN PDEVICE_OBJECT SFilterDeviceObject)
{
	PSFILTER_DEVICE_EXTENSION newDevExt = SFilterDeviceObject->DeviceExtension;
    NTSTATUS status;
    ULONG i;

    PAGED_CODE();
    ASSERT(IS_MY_DEVICE_OBJECT(SFilterDeviceObject));
	ASSERT(!SfIsAttachedToDevice(DeviceObject, NULL));

	if (FlagOn(DeviceObject->Flags, DO_BUFFERED_IO)) 
	{
        SetFlag(SFilterDeviceObject->Flags, DO_BUFFERED_IO);
    }

    if (FlagOn(DeviceObject->Flags, DO_DIRECT_IO)) 
	{
        SetFlag(SFilterDeviceObject->Flags, DO_DIRECT_IO);
    }

    for (i = 0; i < 8; i++) 
	{
        LARGE_INTEGER interval;

        status = SfAttachDeviceToDeviceStack(SFilterDeviceObject, DeviceObject, &newDevExt->AttachedToDeviceObject);
        if (NT_SUCCESS(status)) 
		{
            ClearFlag(SFilterDeviceObject->Flags, DO_DEVICE_INITIALIZING);
           
            return STATUS_SUCCESS;
        }

        interval.QuadPart = (500 * DELAY_ONE_MILLISECOND);      //delay 1/2 second
        KeDelayExecutionThread(KernelMode, FALSE, &interval);
    }

    return status;
}

VOID SfCleanupMountedDevice(IN PDEVICE_OBJECT DeviceObject)
{        
	KdPrint(("SfCleanupMountedDevice\n"));

    UNREFERENCED_PARAMETER(DeviceObject);
    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
}

NTSTATUS SfFsControlMountVolumeComplete(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp, IN PDEVICE_OBJECT NewDeviceObject)
{
	PVPB vpb;
    PSFILTER_DEVICE_EXTENSION newDevExt;
    PIO_STACK_LOCATION irpSp;
    PDEVICE_OBJECT attachedDeviceObject;
    NTSTATUS status;

    PAGED_CODE();

    newDevExt = NewDeviceObject->DeviceExtension;
    irpSp = IoGetCurrentIrpStackLocation(Irp);
        
    vpb = newDevExt->StorageStackDeviceObject->Vpb;
        
    if (NT_SUCCESS(Irp->IoStatus.Status)) 
	{       
        ExAcquireFastMutex(&gSfilterAttachLock);

        if (!SfIsAttachedToDevice(vpb->DeviceObject, &attachedDeviceObject)) 
		{
            status = SfAttachToMountedDevice(vpb->DeviceObject, NewDeviceObject);

            if (!NT_SUCCESS(status)) 
			{                 
                SfCleanupMountedDevice(NewDeviceObject);
                IoDeleteDevice(NewDeviceObject);
            }

            ASSERT(attachedDeviceObject == NULL);
        } 
		else 
		{
            SfCleanupMountedDevice(NewDeviceObject);
            IoDeleteDevice(NewDeviceObject);

            ObDereferenceObject(attachedDeviceObject);
        }
        
        ExReleaseFastMutex(&gSfilterAttachLock);
    } 
	else 
	{
        SfCleanupMountedDevice(NewDeviceObject);
        IoDeleteDevice(NewDeviceObject);
    }

    status = Irp->IoStatus.Status;

    IoCompleteRequest(Irp, IO_NO_INCREMENT);

    return status;
}

NTSTATUS SfFsControlCompletion(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp, IN PVOID Context)
{
	KdPrint(("SfFsControlCompletion\n"));

    UNREFERENCED_PARAMETER(DeviceObject);
    UNREFERENCED_PARAMETER(Irp);

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
    ASSERT(Context != NULL);
    KeSetEvent((PKEVENT)Context, IO_NO_INCREMENT, FALSE);

    return STATUS_MORE_PROCESSING_REQUIRED;
}

NTSTATUS SfCreateCompletion(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp, IN PVOID Context)
{
    PKEVENT event = Context;

	KdPrint(("SfCreateCompletion\n"));

    UNREFERENCED_PARAMETER(DeviceObject);
    UNREFERENCED_PARAMETER(Irp);

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));

    KeSetEvent(event, IO_NO_INCREMENT, FALSE);

    return STATUS_MORE_PROCESSING_REQUIRED;
}

PUNICODE_STRING SfGetFileName(IN PFILE_OBJECT FileObject, IN NTSTATUS CreateStatus, IN OUT PGET_NAME_CONTROL NameControl)
{
    POBJECT_NAME_INFORMATION nameInfo;
    NTSTATUS status;
    ULONG size;
    ULONG bufferSize;

    NameControl->allocatedBuffer = NULL;

    nameInfo = (POBJECT_NAME_INFORMATION)NameControl->smallBuffer;
    bufferSize = sizeof(NameControl->smallBuffer);
        
    status = ObQueryNameString((NT_SUCCESS(CreateStatus) ? (PVOID)FileObject : (PVOID)FileObject->DeviceObject), nameInfo, bufferSize, &size);
    if (status == STATUS_BUFFER_OVERFLOW) 
	{
        bufferSize = size + sizeof(WCHAR);

        NameControl->allocatedBuffer = ExAllocatePoolWithTag(NonPagedPool, bufferSize, SFLT_POOL_TAG);

        if (NameControl->allocatedBuffer == NULL) 
		{
            RtlInitEmptyUnicodeString((PUNICODE_STRING)&NameControl->smallBuffer, (PWCHAR)(NameControl->smallBuffer + sizeof(UNICODE_STRING)), (USHORT)(sizeof(NameControl->smallBuffer) - sizeof(UNICODE_STRING)));
            return (PUNICODE_STRING)&NameControl->smallBuffer;
        }
       
        nameInfo = (POBJECT_NAME_INFORMATION)NameControl->allocatedBuffer;

        status = ObQueryNameString(FileObject, nameInfo, bufferSize, &size);
    }

    if (NT_SUCCESS(status) && !NT_SUCCESS(CreateStatus)) 
	{
        ULONG newSize;
        PCHAR newBuffer;
        POBJECT_NAME_INFORMATION newNameInfo;
       
        newSize = size + FileObject->FileName.Length;

        if (FileObject->RelatedFileObject != NULL) 
		{
            newSize += FileObject->RelatedFileObject->FileName.Length + sizeof(WCHAR);
        }

        if (newSize > bufferSize) 
		{            
            newBuffer = ExAllocatePoolWithTag(NonPagedPool, newSize, SFLT_POOL_TAG);

            if (newBuffer == NULL) 
			{
                RtlInitEmptyUnicodeString((PUNICODE_STRING)&NameControl->smallBuffer, (PWCHAR)(NameControl->smallBuffer + sizeof(UNICODE_STRING)), (USHORT)(sizeof(NameControl->smallBuffer) - sizeof(UNICODE_STRING)));

                return (PUNICODE_STRING)&NameControl->smallBuffer;
            }

            newNameInfo = (POBJECT_NAME_INFORMATION)newBuffer;

            RtlInitEmptyUnicodeString(&newNameInfo->Name, (PWCHAR)(newBuffer + sizeof(OBJECT_NAME_INFORMATION)), (USHORT)(newSize - sizeof(OBJECT_NAME_INFORMATION)));

            RtlCopyUnicodeString(&newNameInfo->Name, &nameInfo->Name);

            if (NameControl->allocatedBuffer != NULL) 
			{
                ExFreePool(NameControl->allocatedBuffer);
            }

            NameControl->allocatedBuffer = newBuffer;
            bufferSize = newSize;
            nameInfo = newNameInfo;

        } 
		else 
		{
            nameInfo->Name.MaximumLength = (USHORT)(bufferSize - sizeof(OBJECT_NAME_INFORMATION));
        }
       
        if (FileObject->RelatedFileObject != NULL) 
		{
            RtlAppendUnicodeStringToString(&nameInfo->Name, &FileObject->RelatedFileObject->FileName);

            RtlAppendUnicodeToString(&nameInfo->Name, L"\\");
        }

        RtlAppendUnicodeStringToString(&nameInfo->Name, &FileObject->FileName);

        ASSERT(nameInfo->Name.Length <= nameInfo->Name.MaximumLength);
    }

    return &nameInfo->Name;
}

VOID SfGetFileNameCleanup(IN OUT PGET_NAME_CONTROL NameControl)
{
    if (NULL != NameControl->allocatedBuffer) 
	{
        ExFreePool( NameControl->allocatedBuffer);
        NameControl->allocatedBuffer = NULL;
    }
}

VOID SfDisplayCreateFileName(IN PIRP Irp)
{
    PIO_STACK_LOCATION irpSp;
    PUNICODE_STRING name;
    GET_NAME_CONTROL nameControl;

    irpSp = IoGetCurrentIrpStackLocation(Irp);

    name = SfGetFileName(irpSp->FileObject, Irp->IoStatus.Status, &nameControl);

    if (irpSp->Parameters.Create.Options & FILE_OPEN_BY_FILE_ID) 
	{        
		KdPrint(("%S\n", name));
    } 
	else 
	{
		KdPrint(("%S\n", name));
    }

    SfGetFileNameCleanup(&nameControl);
}

NTSTATUS FILEFILTER_DispatchCreate(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{   	
	NTSTATUS status;
	KEVENT waitEvent;
	KdPrint(("FILEFILTER_DispatchCreate\n"));
    
    PAGED_CODE();
    if (IS_MY_CONTROL_DEVICE_OBJECT(DeviceObject)) 
	{
		KdPrint(("IS_MY_CONTROL_DEVICE_OBJECT\n"));

        Irp->IoStatus.Status = STATUS_INVALID_DEVICE_REQUEST;
        Irp->IoStatus.Information = 0;

        IoCompleteRequest(Irp, IO_NO_INCREMENT);

        return STATUS_INVALID_DEVICE_REQUEST;
    }

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));

    /*if (!FlagOn(SfDebug, SFDEBUG_DO_CREATE_COMPLETION | SFDEBUG_GET_CREATE_NAMES | SFDEBUG_DISPLAY_CREATE_NAMES)) 
	{   
		KdPrint(("!FlagOn\n"));

        IoSkipCurrentIrpStackLocation(Irp);

        return IoCallDriver(((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject, Irp);
    } 
	else 
	{ */	
		KdPrint(("FlagOn\n"));
        
        KeInitializeEvent(&waitEvent, NotificationEvent, FALSE);

        IoCopyCurrentIrpStackLocationToNext(Irp);

        IoSetCompletionRoutine(Irp, SfCreateCompletion, &waitEvent, TRUE, TRUE, TRUE);

        status = IoCallDriver(((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject, Irp);

	    if (STATUS_PENDING == status) 
		{
            NTSTATUS localStatus = KeWaitForSingleObject(&waitEvent, Executive, KernelMode, FALSE, NULL);
		    ASSERT(STATUS_SUCCESS == localStatus);
	    }

        ASSERT(KeReadStateEvent(&waitEvent) || !NT_SUCCESS(Irp->IoStatus.Status));

        if (FlagOn(SfDebug, (SFDEBUG_GET_CREATE_NAMES | SFDEBUG_DISPLAY_CREATE_NAMES))) 
		{
            SfDisplayCreateFileName(Irp);
        }
		else
		{
            SfDisplayCreateFileName(Irp);
		}

		status = Irp->IoStatus.Status;

        IoCompleteRequest(Irp, IO_NO_INCREMENT);

        return status;
    /*}

	return status;*/
}

NTSTATUS FILEFILTER_CleanupClose(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
	KdPrint(("FILEFILTER_CleanupClose\n"));

    PAGED_CODE();

    ASSERT(!IS_MY_CONTROL_DEVICE_OBJECT(DeviceObject));

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
	
    IoSkipCurrentIrpStackLocation(Irp);

	return IoCallDriver(((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject, Irp);
}

NTSTATUS FILEFILTER_PassThrough(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
	KdPrint(("FILEFILTER_PassThrough In\n"));
	ASSERT(!IS_MY_CONTROL_DEVICE_OBJECT(DeviceObject));
	ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
	KdPrint(("FILEFILTER_PassThrough Out\n"));

    IoSkipCurrentIrpStackLocation(Irp);
	return IoCallDriver(((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject, Irp);
}

NTSTATUS SfFsControlMountVolume(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
    PSFILTER_DEVICE_EXTENSION devExt = DeviceObject->DeviceExtension;
    PIO_STACK_LOCATION irpSp = IoGetCurrentIrpStackLocation(Irp);
    PDEVICE_OBJECT newDeviceObject;
    PDEVICE_OBJECT storageStackDeviceObject;
    PSFILTER_DEVICE_EXTENSION newDevExt;
    NTSTATUS status;
    BOOLEAN isShadowCopyVolume;
	PFSCTRL_COMPLETION_CONTEXT completionContext;
	KEVENT waitEvent;
    
    PAGED_CODE();

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
    ASSERT(IS_DESIRED_DEVICE_TYPE(DeviceObject->DeviceType));

    storageStackDeviceObject = irpSp->Parameters.MountVolume.Vpb->RealDevice;

    status = SfIsShadowCopyVolume(storageStackDeviceObject, &isShadowCopyVolume);

    if (NT_SUCCESS(status) && isShadowCopyVolume && !FlagOn(SfDebug, SFDEBUG_ATTACH_TO_SHADOW_COPIES)) 
	{
        UNICODE_STRING shadowDeviceName;
        WCHAR shadowNameBuffer[MAX_DEVNAME_LENGTH];

        RtlInitEmptyUnicodeString(&shadowDeviceName, shadowNameBuffer, sizeof(shadowNameBuffer));

        SfGetObjectName(storageStackDeviceObject, &shadowDeviceName);
        
        IoSkipCurrentIrpStackLocation(Irp);
        return IoCallDriver(devExt->AttachedToDeviceObject, Irp);
    }

    status = IoCreateDevice(gSFilterDriverObject, sizeof(SFILTER_DEVICE_EXTENSION), NULL, DeviceObject->DeviceType, 0, FALSE, &newDeviceObject);

    if (!NT_SUCCESS(status)) 
	{
        KdPrint(("SFilter!SfFsControlMountVolume: Error creating volume device object, status=%08x\n", status));

        Irp->IoStatus.Information = 0;
        Irp->IoStatus.Status = status;
        IoCompleteRequest(Irp, IO_NO_INCREMENT);

        return status;
    }
    
    newDevExt = newDeviceObject->DeviceExtension;
    newDevExt->StorageStackDeviceObject = storageStackDeviceObject;

    RtlInitEmptyUnicodeString(&newDevExt->DeviceName, newDevExt->DeviceNameBuffer, sizeof(newDevExt->DeviceNameBuffer));

    SfGetObjectName(storageStackDeviceObject, &newDevExt->DeviceName);
	
    KeInitializeEvent(&waitEvent, NotificationEvent, FALSE);

    IoCopyCurrentIrpStackLocationToNext(Irp);
    IoSetCompletionRoutine(Irp, SfFsControlCompletion, &waitEvent, TRUE, TRUE, TRUE);

    status = IoCallDriver(devExt->AttachedToDeviceObject, Irp);
    if (STATUS_PENDING == status) 
	{
    	status = KeWaitForSingleObject(&waitEvent, Executive, KernelMode, FALSE, NULL);
    	ASSERT(STATUS_SUCCESS == status);
    }

    ASSERT(KeReadStateEvent(&waitEvent) || !NT_SUCCESS(Irp->IoStatus.Status));

    status = SfFsControlMountVolumeComplete(DeviceObject, Irp, newDeviceObject);
    
    return status;
}

NTSTATUS SfFsControlLoadFileSystemComplete(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
    PSFILTER_DEVICE_EXTENSION devExt;
    NTSTATUS status;

    PAGED_CODE();

    devExt = DeviceObject->DeviceExtension;
    
    if (!NT_SUCCESS(Irp->IoStatus.Status) && (Irp->IoStatus.Status != STATUS_IMAGE_ALREADY_LOADED)) 
	{
        SfAttachDeviceToDeviceStack(DeviceObject, devExt->AttachedToDeviceObject, &devExt->AttachedToDeviceObject);

        ASSERT(devExt->AttachedToDeviceObject != NULL);
    } 
	else 
	{
        SfCleanupMountedDevice(DeviceObject);
        IoDeleteDevice(DeviceObject);
    }

    status = Irp->IoStatus.Status;

    IoCompleteRequest(Irp, IO_NO_INCREMENT);

    return status;
}

NTSTATUS SfFsControlLoadFileSystem(IN PDEVICE_OBJECT DeviceObject, IN PIRP Irp)
{
    PSFILTER_DEVICE_EXTENSION devExt = DeviceObject->DeviceExtension;
    NTSTATUS status;
    PFSCTRL_COMPLETION_CONTEXT completionContext;
	KEVENT waitEvent;
        
    PAGED_CODE();	
	
	KeInitializeEvent(&waitEvent, NotificationEvent, FALSE);

	IoCopyCurrentIrpStackLocationToNext(Irp);
	
	IoSetCompletionRoutine(Irp,	SfFsControlCompletion, &waitEvent, TRUE, TRUE, TRUE);

	status = IoCallDriver(devExt->AttachedToDeviceObject, Irp);

    if (status == STATUS_PENDING) 
	{
    	status = KeWaitForSingleObject(&waitEvent, Executive, KernelMode, FALSE, NULL);
    	ASSERT(status == STATUS_SUCCESS);
    }

	ASSERT(KeReadStateEvent(&waitEvent) || !NT_SUCCESS(Irp->IoStatus.Status));

	status = SfFsControlLoadFileSystemComplete(DeviceObject, Irp);    

    return status;
}

NTSTATUS FILEFILTER_DispatchDeviceControl(IN PDEVICE_OBJECT	DeviceObject, IN PIRP Irp)
{	
	PIO_STACK_LOCATION irpSp = IoGetCurrentIrpStackLocation(Irp);

    KdPrint(("FILEFILTER_DispatchDeviceControl\n"));

    PAGED_CODE();
    
    ASSERT(!IS_MY_CONTROL_DEVICE_OBJECT(DeviceObject));

    ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
    
    switch (irpSp->MinorFunction) 
	{
        case IRP_MN_MOUNT_VOLUME:
            return SfFsControlMountVolume(DeviceObject, Irp);

        case IRP_MN_LOAD_FILE_SYSTEM:
            return SfFsControlLoadFileSystem(DeviceObject, Irp);

        case IRP_MN_USER_FS_REQUEST:
        {
            switch (irpSp->Parameters.FileSystemControl.FsControlCode) 
			{
                case FSCTL_DISMOUNT_VOLUME:
                {
                    PSFILTER_DEVICE_EXTENSION devExt = DeviceObject->DeviceExtension;
                    break;
                }
            }
            break;
        }
    }        

    IoSkipCurrentIrpStackLocation(Irp);
    return IoCallDriver(((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject, Irp);
}

VOID FILEFILTER_DriverUnload(IN PDRIVER_OBJECT DriverObject)
{
	PFAST_IO_DISPATCH fastIoDispatch;
	PDEVICE_OBJECT pdoNextDeviceObj = gSFilterDriverObject->DeviceObject;
	IoDeleteSymbolicLink(&LinString);
	
	while(pdoNextDeviceObj)
	{
		PDEVICE_OBJECT pdoThisDeviceObj = pdoNextDeviceObj;
		pdoNextDeviceObj = pdoThisDeviceObj->NextDevice;
		IoDeleteDevice(pdoThisDeviceObj);
	}

	fastIoDispatch = DriverObject->FastIoDispatch;
    DriverObject->FastIoDispatch = NULL;
    ExFreePool(fastIoDispatch);
}



BOOLEAN SfFastIoCheckIfPossible(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN BOOLEAN Wait, IN ULONG LockKey, IN BOOLEAN CheckForReadOperation, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoCheckIfPossible In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoCheckIfPossible)) 
		{
            return (fastIoDispatch->FastIoCheckIfPossible)(FileObject, FileOffset, Length, Wait, LockKey, CheckForReadOperation, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoCheckIfPossible Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoRead(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN BOOLEAN Wait, IN ULONG LockKey, OUT PVOID Buffer, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoRead In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));        
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoRead)) 
		{
            return (fastIoDispatch->FastIoRead)(FileObject, FileOffset, Length, Wait, LockKey, Buffer, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoRead Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoWrite(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN BOOLEAN Wait, IN ULONG LockKey, IN PVOID Buffer, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{
	PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoWrite In\n"));

    PAGED_CODE();
    
    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoWrite)) 
		{
            return (fastIoDispatch->FastIoWrite)(FileObject, FileOffset, Length, Wait, LockKey, Buffer, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoWrite Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoQueryBasicInfo(IN PFILE_OBJECT FileObject, IN BOOLEAN Wait, OUT PFILE_BASIC_INFORMATION Buffer, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoQueryBasicInfo In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION) DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoQueryBasicInfo)) 
		{
            return (fastIoDispatch->FastIoQueryBasicInfo)(FileObject, Wait, Buffer, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoQueryBasicInfo Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoQueryStandardInfo(IN PFILE_OBJECT FileObject, IN BOOLEAN Wait, OUT PFILE_STANDARD_INFORMATION Buffer, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoQueryStandardInfo In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoQueryStandardInfo)) 
		{
            return (fastIoDispatch->FastIoQueryStandardInfo)(FileObject, Wait, Buffer, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoQueryStandardInfo Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoLock(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN PLARGE_INTEGER Length, PEPROCESS ProcessId, ULONG Key, BOOLEAN FailImmediately, BOOLEAN ExclusiveLock, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoLock In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoLock))
		{
            return (fastIoDispatch->FastIoLock)(FileObject, FileOffset, Length, ProcessId, Key, FailImmediately, ExclusiveLock, IoStatus, nextDeviceObject);
        }
    }

    KdPrint(("SfFastIoLock Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoUnlockSingle(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN PLARGE_INTEGER Length, PEPROCESS ProcessId, ULONG Key, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoUnlockSingle In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoUnlockSingle)) 
		{
            return (fastIoDispatch->FastIoUnlockSingle)(FileObject, FileOffset, Length, ProcessId, Key, IoStatus, nextDeviceObject );
        }
    }

	KdPrint(("SfFastIoUnlockSingle Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoUnlockAll(IN PFILE_OBJECT FileObject, PEPROCESS ProcessId, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoUnlockAll In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;

        if (nextDeviceObject) 
		{
            fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

            if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoUnlockAll)) 
			{
                return (fastIoDispatch->FastIoUnlockAll)(FileObject, ProcessId, IoStatus, nextDeviceObject);
            }
        }
    }

    KdPrint(("SfFastIoUnlockAll Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoUnlockAllByKey(IN PFILE_OBJECT FileObject, PVOID ProcessId, ULONG Key, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{    
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoUnlockAllByKey In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoUnlockAllByKey)) 
		{
            return (fastIoDispatch->FastIoUnlockAllByKey)(FileObject, ProcessId, Key, IoStatus, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoUnlockAllByKey Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoDeviceControl(IN PFILE_OBJECT FileObject, IN BOOLEAN Wait, IN PVOID InputBuffer OPTIONAL, IN ULONG InputBufferLength, OUT PVOID OutputBuffer OPTIONAL, IN ULONG OutputBufferLength, IN ULONG IoControlCode, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoDeviceControl In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

		KdPrint(("SfFastIoDeviceControl DeviceExtension\n"));

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoDeviceControl)) 
		{
            return (fastIoDispatch->FastIoDeviceControl)(FileObject, Wait, InputBuffer, InputBufferLength, OutputBuffer, OutputBufferLength, IoControlCode, IoStatus, nextDeviceObject);
        }
    }

    KdPrint(("SfFastIoDeviceControl Out\n"));

    return FALSE;
}

VOID SfFastIoDetachDevice(IN PDEVICE_OBJECT SourceDevice, IN PDEVICE_OBJECT TargetDevice)
{	
    PSFILTER_DEVICE_EXTENSION devExt;
	KdPrint(("SfFastIoDetachDevice In\n"));

    PAGED_CODE();

    ASSERT(IS_MY_DEVICE_OBJECT(SourceDevice));
    devExt = (PSFILTER_DEVICE_EXTENSION)SourceDevice->DeviceExtension;
    SfCleanupMountedDevice(SourceDevice);

    IoDetachDevice(TargetDevice);
    IoDeleteDevice(SourceDevice);

	KdPrint(("SfFastIoDetachDevice Out\n"));
}

BOOLEAN SfFastIoQueryNetworkOpenInfo(IN PFILE_OBJECT FileObject, IN BOOLEAN Wait, OUT PFILE_NETWORK_OPEN_INFORMATION Buffer, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoQueryNetworkOpenInfo In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoQueryNetworkOpenInfo)) 
		{
            return (fastIoDispatch->FastIoQueryNetworkOpenInfo)(FileObject, Wait, Buffer, IoStatus, nextDeviceObject );
        }
    }

    KdPrint(("SfFastIoQueryNetworkOpenInfo Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoMdlRead(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN ULONG LockKey, OUT PMDL *MdlChain, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoMdlRead In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION) DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, MdlRead)) 
		{
            return (fastIoDispatch->MdlRead)(FileObject, FileOffset, Length, LockKey, MdlChain, IoStatus, nextDeviceObject);
        }
    }

    KdPrint(("SfFastIoMdlRead Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoMdlReadComplete(IN PFILE_OBJECT FileObject, IN PMDL MdlChain, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoMdlReadComplete In\n"));

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, MdlReadComplete)) 
		{
            return (fastIoDispatch->MdlReadComplete)(FileObject, MdlChain, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoMdlReadComplete Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoPrepareMdlWrite(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN ULONG LockKey, OUT PMDL *MdlChain, OUT PIO_STATUS_BLOCK IoStatus, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoPrepareMdlWrite In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, PrepareMdlWrite)) 
		{
            return (fastIoDispatch->PrepareMdlWrite)(FileObject, FileOffset, Length, LockKey, MdlChain, IoStatus, nextDeviceObject);
        }
    }

    KdPrint(("SfFastIoPrepareMdlWrite Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoMdlWriteComplete(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN PMDL MdlChain, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoMdlWriteComplete In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, MdlWriteComplete)) 
		{
            return (fastIoDispatch->MdlWriteComplete)(FileObject, FileOffset, MdlChain, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoMdlWriteComplete Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoReadCompressed(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN ULONG LockKey, OUT PVOID Buffer, OUT PMDL *MdlChain, OUT PIO_STATUS_BLOCK IoStatus, OUT struct _COMPRESSED_DATA_INFO *CompressedDataInfo, IN ULONG CompressedDataInfoLength, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoReadCompressed In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoReadCompressed)) 
		{
            return (fastIoDispatch->FastIoReadCompressed)(FileObject, FileOffset, Length, LockKey, Buffer, MdlChain, IoStatus, CompressedDataInfo, CompressedDataInfoLength, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoReadCompressed Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoWriteCompressed(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN ULONG Length, IN ULONG LockKey, IN PVOID Buffer, OUT PMDL *MdlChain, OUT PIO_STATUS_BLOCK IoStatus, IN struct _COMPRESSED_DATA_INFO *CompressedDataInfo, IN ULONG CompressedDataInfoLength, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoWriteCompressed In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoWriteCompressed)) 
		{
            return (fastIoDispatch->FastIoWriteCompressed)(FileObject, FileOffset, Length, LockKey, Buffer, MdlChain, IoStatus, CompressedDataInfo, CompressedDataInfoLength, nextDeviceObject);
        }
    }

    KdPrint(("SfFastIoWriteCompressed Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoMdlReadCompleteCompressed(IN PFILE_OBJECT FileObject, IN PMDL MdlChain, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoMdlReadCompleteCompressed In\n"));

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, MdlReadCompleteCompressed))
		{
            return (fastIoDispatch->MdlReadCompleteCompressed)(FileObject, MdlChain, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoMdlReadCompleteCompressed Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoMdlWriteCompleteCompressed(IN PFILE_OBJECT FileObject, IN PLARGE_INTEGER FileOffset, IN PMDL MdlChain, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;

	KdPrint(("SfFastIoMdlWriteCompleteCompressed In\n"));

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, MdlWriteCompleteCompressed)) 
		{
            return (fastIoDispatch->MdlWriteCompleteCompressed)(FileObject, FileOffset, MdlChain, nextDeviceObject);
        }
    }

	KdPrint(("SfFastIoMdlWriteCompleteCompressed Out\n"));

    return FALSE;
}

BOOLEAN SfFastIoQueryOpen(IN PIRP Irp, OUT PFILE_NETWORK_OPEN_INFORMATION NetworkInformation, IN PDEVICE_OBJECT DeviceObject)
{	
    PDEVICE_OBJECT nextDeviceObject;
    PFAST_IO_DISPATCH fastIoDispatch;
    BOOLEAN result;

	KdPrint(("SfFastIoQueryOpen In\n"));

    PAGED_CODE();

    if (DeviceObject->DeviceExtension) 
	{
        ASSERT(IS_MY_DEVICE_OBJECT(DeviceObject));
        nextDeviceObject = ((PSFILTER_DEVICE_EXTENSION)DeviceObject->DeviceExtension)->AttachedToDeviceObject;
        ASSERT(nextDeviceObject);

        fastIoDispatch = nextDeviceObject->DriverObject->FastIoDispatch;

		KdPrint(("SfFastIoQueryOpen\n"));

        if (VALID_FAST_IO_DISPATCH_HANDLER(fastIoDispatch, FastIoQueryOpen)) 
		{
            PIO_STACK_LOCATION irpSp = IoGetCurrentIrpStackLocation(Irp);
            irpSp->DeviceObject = nextDeviceObject;
            result = (fastIoDispatch->FastIoQueryOpen)(Irp, NetworkInformation, nextDeviceObject);
            irpSp->DeviceObject = DeviceObject;

            return result;
        }
    }

    KdPrint(("SfFastIoQueryOpen Out\n"));

    return FALSE;
}

NTSTATUS SfPreFsFilterPassThrough(IN PFS_FILTER_CALLBACK_DATA Data, OUT PVOID *CompletionContext)
{
	KdPrint(("SfPreFsFilterPassThrough In\n"));

    UNREFERENCED_PARAMETER(Data);
    UNREFERENCED_PARAMETER(CompletionContext);

    ASSERT(IS_MY_DEVICE_OBJECT(Data->DeviceObject));

    return STATUS_SUCCESS;
}

VOID SfPostFsFilterPassThrough(IN PFS_FILTER_CALLBACK_DATA Data, IN NTSTATUS OperationStatus, IN PVOID CompletionContext)
{
	KdPrint(("SfPostFsFilterPassThrough In\n"));

    UNREFERENCED_PARAMETER(Data);
    UNREFERENCED_PARAMETER(OperationStatus);
    UNREFERENCED_PARAMETER(CompletionContext);

    ASSERT(IS_MY_DEVICE_OBJECT(Data->DeviceObject));
}

VOID SfLoadDynamicFunctions()
{
    UNICODE_STRING functionName;
    RtlZeroMemory(&gSfDynamicFunctions, sizeof(gSfDynamicFunctions));

    RtlInitUnicodeString(&functionName, L"FsRtlRegisterFileSystemFilterCallbacks");
    gSfDynamicFunctions.RegisterFileSystemFilterCallbacks = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"IoAttachDeviceToDeviceStackSafe");
    gSfDynamicFunctions.AttachDeviceToDeviceStackSafe = MmGetSystemRoutineAddress(&functionName);
    
    RtlInitUnicodeString(&functionName, L"IoEnumerateDeviceObjectList");
    gSfDynamicFunctions.EnumerateDeviceObjectList = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"IoGetLowerDeviceObject");
    gSfDynamicFunctions.GetLowerDeviceObject = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"IoGetDeviceAttachmentBaseRef");
    gSfDynamicFunctions.GetDeviceAttachmentBaseRef = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"IoGetDiskDeviceObject");
    gSfDynamicFunctions.GetDiskDeviceObject = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"IoGetAttachedDeviceReference");
    gSfDynamicFunctions.GetAttachedDeviceReference = MmGetSystemRoutineAddress(&functionName);

    RtlInitUnicodeString(&functionName, L"RtlGetVersion");
    gSfDynamicFunctions.GetVersion = MmGetSystemRoutineAddress(&functionName);    
}

VOID SfGetBaseDeviceObjectName(IN PDEVICE_OBJECT DeviceObject, IN OUT PUNICODE_STRING Name)
{    
    ASSERT(gSfDynamicFunctions.GetDeviceAttachmentBaseRef != NULL);
    DeviceObject = (gSfDynamicFunctions.GetDeviceAttachmentBaseRef)(DeviceObject);
    SfGetObjectName(DeviceObject, Name);
    ObDereferenceObject(DeviceObject);
}

NTSTATUS SfEnumerateFileSystemVolumes(IN PDEVICE_OBJECT FSDeviceObject, IN PUNICODE_STRING Name) 
{
    PDEVICE_OBJECT newDeviceObject;
    PSFILTER_DEVICE_EXTENSION newDevExt;
    PDEVICE_OBJECT *devList;
    PDEVICE_OBJECT storageStackDeviceObject;
    NTSTATUS status;
    ULONG numDevices;
    ULONG i;
    BOOLEAN isShadowCopyVolume;

    PAGED_CODE();
   
    status = (gSfDynamicFunctions.EnumerateDeviceObjectList)(FSDeviceObject->DriverObject, NULL, 0, &numDevices);

    if (!NT_SUCCESS(status)) 
	{
        ASSERT(STATUS_BUFFER_TOO_SMALL == status);

        numDevices += 8;        //grab a few extra slots

        devList = ExAllocatePoolWithTag(NonPagedPool, (numDevices * sizeof(PDEVICE_OBJECT)), SFLT_POOL_TAG);
        if (devList == NULL) 
		{
            return STATUS_INSUFFICIENT_RESOURCES;
        }

        ASSERT(gSfDynamicFunctions.EnumerateDeviceObjectList != NULL);
        status = (gSfDynamicFunctions.EnumerateDeviceObjectList)(FSDeviceObject->DriverObject, devList, (numDevices * sizeof(PDEVICE_OBJECT)), &numDevices);

        if (!NT_SUCCESS(status))  
		{
            ExFreePool(devList);
            return status;
        }

        for (i = 0; i < numDevices; i++) 
		{
            storageStackDeviceObject = NULL;

            try 
			{               
                if ((devList[i] == FSDeviceObject) || (devList[i]->DeviceType != FSDeviceObject->DeviceType) || SfIsAttachedToDevice(devList[i], NULL)) 
				{
                    leave;
                }

                SfGetBaseDeviceObjectName(devList[i], Name);

                if (Name->Length > 0) 
				{
                    leave;
                }

                ASSERT(gSfDynamicFunctions.GetDiskDeviceObject != NULL);
                status = (gSfDynamicFunctions.GetDiskDeviceObject)(devList[i], &storageStackDeviceObject);

                if (!NT_SUCCESS(status)) 
				{
                    leave;
                }

                status = SfIsShadowCopyVolume(storageStackDeviceObject, &isShadowCopyVolume);

                if (NT_SUCCESS(status) && isShadowCopyVolume && !FlagOn(SfDebug,SFDEBUG_ATTACH_TO_SHADOW_COPIES)) 
				{
                    UNICODE_STRING shadowDeviceName;
                    WCHAR shadowNameBuffer[MAX_DEVNAME_LENGTH];

                    RtlInitEmptyUnicodeString(&shadowDeviceName, shadowNameBuffer, sizeof(shadowNameBuffer));

                    SfGetObjectName(storageStackDeviceObject, &shadowDeviceName);
                    
                    leave;
                }

                status = IoCreateDevice(gSFilterDriverObject, sizeof(SFILTER_DEVICE_EXTENSION), NULL, devList[i]->DeviceType, 0, FALSE, &newDeviceObject);

                if (!NT_SUCCESS(status)) 
				{
                    leave;
                }

                newDevExt = newDeviceObject->DeviceExtension;
                newDevExt->StorageStackDeviceObject = storageStackDeviceObject;
        
                RtlInitEmptyUnicodeString(&newDevExt->DeviceName, newDevExt->DeviceNameBuffer, sizeof(newDevExt->DeviceNameBuffer));

                SfGetObjectName(storageStackDeviceObject, &newDevExt->DeviceName);

                ExAcquireFastMutex(&gSfilterAttachLock);

                if (!SfIsAttachedToDevice(devList[i], NULL)) 
				{                    
                    status = SfAttachToMountedDevice(devList[i], newDeviceObject);
                    
                    if (!NT_SUCCESS(status)) 
					{ 
                        SfCleanupMountedDevice(newDeviceObject);
                        IoDeleteDevice(newDeviceObject);
                    }
                } 
				else 
				{                    
                    SfCleanupMountedDevice(newDeviceObject);
                    IoDeleteDevice(newDeviceObject);
                }

                ExReleaseFastMutex(&gSfilterAttachLock);

            } 
			finally 
			{          
                if (storageStackDeviceObject != NULL) 
				{
                    ObDereferenceObject(storageStackDeviceObject);
                }

                ObDereferenceObject(devList[i]);
            }
        }

        status = STATUS_SUCCESS;

        ExFreePool(devList);
    }

    return status;
}

NTSTATUS SfAttachToFileSystemDevice(IN PDEVICE_OBJECT DeviceObject, IN PUNICODE_STRING DeviceName)
{	
    PDEVICE_OBJECT newDeviceObject = NULL;
    PSFILTER_DEVICE_EXTENSION devExt = NULL;
    UNICODE_STRING fsrecName;
    NTSTATUS status;
    UNICODE_STRING fsName;
    WCHAR tempNameBuffer[MAX_DEVNAME_LENGTH];

	KdPrint(("SfAttachToFileSystemDevice In\n"));

    PAGED_CODE();

    if (!IS_DESIRED_DEVICE_TYPE(DeviceObject->DeviceType)) 
	{
		KdPrint(("!IS_DESIRED_DEVICE_TYPE\n"));
        return STATUS_SUCCESS;
    }

    RtlInitEmptyUnicodeString(&fsName, tempNameBuffer, sizeof(tempNameBuffer));

    if (!FlagOn(SfDebug, SFDEBUG_ATTACH_TO_FSRECOGNIZER)) 
	{
		KdPrint(("FlagOn SfDebug\n"));

		RtlInitUnicodeString(&fsrecName, L"\\FileSystem\\Fs_Rec");
        SfGetObjectName(DeviceObject->DriverObject, &fsName);
        if (RtlCompareUnicodeString(&fsName, &fsrecName, TRUE) == 0) 
		{
            return STATUS_SUCCESS;
        }
    }

    status = IoCreateDevice(gSFilterDriverObject, sizeof(SFILTER_DEVICE_EXTENSION), NULL, DeviceObject->DeviceType, 0, FALSE, &newDeviceObject);
    if (!NT_SUCCESS(status)) 
	{
		KdPrint(("IoCreateDevice Fail\n"));

        return status;
    }
    
    if (FlagOn(DeviceObject->Flags, DO_BUFFERED_IO)) 
	{
        SetFlag(newDeviceObject->Flags, DO_BUFFERED_IO);
    }

    if (FlagOn(DeviceObject->Flags, DO_DIRECT_IO)) 
	{
        SetFlag(newDeviceObject->Flags, DO_DIRECT_IO);
    }

    if (FlagOn(DeviceObject->Characteristics, FILE_DEVICE_SECURE_OPEN)) 
	{
        SetFlag(newDeviceObject->Characteristics, FILE_DEVICE_SECURE_OPEN);
    }
    
    devExt = newDeviceObject->DeviceExtension;
		    	
    status = SfAttachDeviceToDeviceStack(newDeviceObject, DeviceObject, &devExt->AttachedToDeviceObject);
    if (!NT_SUCCESS(status)) 
	{
        KdPrint(("SfAttachDeviceToDeviceStack Fail\n"));

        SfCleanupMountedDevice(newDeviceObject);
        IoDeleteDevice(newDeviceObject);
		return status;
    }

    RtlInitEmptyUnicodeString(&devExt->DeviceName, devExt->DeviceNameBuffer, sizeof(devExt->DeviceNameBuffer));

    RtlCopyUnicodeString(&devExt->DeviceName, DeviceName);

    ClearFlag(newDeviceObject->Flags, DO_DEVICE_INITIALIZING);	

	ASSERT(	gSfDynamicFunctions.EnumerateDeviceObjectList != NULL &&
			gSfDynamicFunctions.GetDiskDeviceObject != NULL &&
			gSfDynamicFunctions.GetDeviceAttachmentBaseRef != NULL &&
			gSfDynamicFunctions.GetLowerDeviceObject != NULL );

	status = SfEnumerateFileSystemVolumes(DeviceObject, &fsName);
	if (!NT_SUCCESS(status)) 
	{
		IoDetachDevice(devExt->AttachedToDeviceObject);
		SfCleanupMountedDevice(newDeviceObject);
        IoDeleteDevice(newDeviceObject);
		return status;
    } 

	KdPrint(("SfAttachToFileSystemDevice Out\n"));

    return STATUS_SUCCESS;
}

VOID SfDetachFromFileSystemDevice(IN PDEVICE_OBJECT DeviceObject)
{
    PDEVICE_OBJECT ourAttachedDevice;
    PSFILTER_DEVICE_EXTENSION devExt;

    PAGED_CODE();

    ourAttachedDevice = DeviceObject->AttachedDevice;

    while (ourAttachedDevice != NULL) 
	{
        if (IS_MY_DEVICE_OBJECT(ourAttachedDevice)) 
		{
            devExt = ourAttachedDevice->DeviceExtension;

            SfCleanupMountedDevice(ourAttachedDevice);
            IoDetachDevice(DeviceObject);
            IoDeleteDevice(ourAttachedDevice);

            return;
        }

        DeviceObject = ourAttachedDevice;
        ourAttachedDevice = ourAttachedDevice->AttachedDevice;
    }
}

VOID SfFsNotification(IN PDEVICE_OBJECT DeviceObject, IN BOOLEAN FsActive)
{
    UNICODE_STRING name;
    WCHAR nameBuffer[MAX_DEVNAME_LENGTH];

    PAGED_CODE();

    RtlInitEmptyUnicodeString(&name, nameBuffer, sizeof(nameBuffer));

    SfGetObjectName(DeviceObject, &name);

	if (FsActive) 
	{
        SfAttachToFileSystemDevice(DeviceObject, &name);
    } 
	else 
	{
        SfDetachFromFileSystemDevice(DeviceObject);
    }
}

NTSTATUS DriverEntry(IN OUT PDRIVER_OBJECT DriverObject, IN PUNICODE_STRING RegistryPath)
{	
	NTSTATUS status = STATUS_UNSUCCESSFUL;
	PFAST_IO_DISPATCH fastIoDispatch;
	UNICODE_STRING nameString;
	FS_FILTER_CALLBACKS fsFilterCallbacks = {0};
	PDEVICE_OBJECT rawDeviceObject;
    PFILE_OBJECT fileObject;
	ULONG i = 0;

	gSFilterDriverObject = DriverObject;

	RtlInitUnicodeString(&DeviceString, DEVICE_NAME);
	RtlInitUnicodeString(&LinString, LINK_NAME);

	KdPrint(("DriverEntry\n"));

	SfLoadDynamicFunctions();

	ExInitializeFastMutex(&gSfilterAttachLock);
	
	if (!NT_SUCCESS(status = IoCreateDevice(DriverObject, 0, &DeviceString, FILE_DEVICE_DISK_FILE_SYSTEM, FILE_DEVICE_SECURE_OPEN, FALSE, &gSFilterControlDeviceObject)))
	{
		KdPrint(("IoCreateDevice Fail\n"));

		return status;
	}

	KdPrint(("IoCreateDevice Success\n"));
	
	if (!NT_SUCCESS(status = IoCreateSymbolicLink(&LinString, &DeviceString)))
	{
		KdPrint(("IoCreateSymbolicLink Fail\n"));
		IoDeleteDevice(gSFilterControlDeviceObject);

		return status;
	}

	KdPrint(("IoCreateSymbolicLink Success\n"));

	for (i = 0;i <= IRP_MJ_MAXIMUM_FUNCTION;i++)
	{
		DriverObject->MajorFunction[i] = FILEFILTER_PassThrough;
	}

	DriverObject->MajorFunction[IRP_MJ_CREATE]				= FILEFILTER_DispatchCreate;	
	//DriverObject->MajorFunction[IRP_MJ_CREATE_NAMED_PIPE]	= FILEFILTER_DispatchCreate;
    //DriverObject->MajorFunction[IRP_MJ_CREATE_MAILSLOT]		= FILEFILTER_DispatchCreate;	
	DriverObject->MajorFunction[IRP_MJ_FILE_SYSTEM_CONTROL]	= FILEFILTER_DispatchDeviceControl;
	DriverObject->MajorFunction[IRP_MJ_CLEANUP]				= FILEFILTER_CleanupClose;
    DriverObject->MajorFunction[IRP_MJ_CLOSE]				= FILEFILTER_CleanupClose;
	
	DriverObject->DriverUnload								= FILEFILTER_DriverUnload;

	fastIoDispatch = (PFAST_IO_DISPATCH)ExAllocatePoolWithTag(NonPagedPool, sizeof(FAST_IO_DISPATCH), SFLT_POOL_TAG);
	if (!fastIoDispatch) 
	{
		KdPrint(("ExAllocatePoolWithTag Fail\n"));

		IoDeleteDevice(gSFilterControlDeviceObject);
		return STATUS_INSUFFICIENT_RESOURCES;
	}

	KdPrint(("ExAllocatePoolWithTag Success\n"));
    
	RtlZeroMemory(fastIoDispatch, sizeof(FAST_IO_DISPATCH));

	fastIoDispatch->SizeOfFastIoDispatch			= sizeof(FAST_IO_DISPATCH);
	fastIoDispatch->FastIoCheckIfPossible			= SfFastIoCheckIfPossible;
	fastIoDispatch->FastIoRead						= SfFastIoRead;
	fastIoDispatch->FastIoWrite						= SfFastIoWrite;
	fastIoDispatch->FastIoQueryBasicInfo			= SfFastIoQueryBasicInfo;
	fastIoDispatch->FastIoQueryStandardInfo			= SfFastIoQueryStandardInfo;
	fastIoDispatch->FastIoLock						= SfFastIoLock;
	fastIoDispatch->FastIoUnlockSingle				= SfFastIoUnlockSingle;
	fastIoDispatch->FastIoUnlockAll					= SfFastIoUnlockAll;
	fastIoDispatch->FastIoUnlockAllByKey			= SfFastIoUnlockAllByKey;
	fastIoDispatch->FastIoDeviceControl				= SfFastIoDeviceControl;
	fastIoDispatch->FastIoDetachDevice				= SfFastIoDetachDevice;
	fastIoDispatch->FastIoQueryNetworkOpenInfo		= SfFastIoQueryNetworkOpenInfo;
	fastIoDispatch->MdlRead							= SfFastIoMdlRead;
	fastIoDispatch->MdlReadComplete					= SfFastIoMdlReadComplete;
	fastIoDispatch->PrepareMdlWrite					= SfFastIoPrepareMdlWrite;
	fastIoDispatch->MdlWriteComplete				= SfFastIoMdlWriteComplete;
	fastIoDispatch->FastIoReadCompressed			= SfFastIoReadCompressed;
	fastIoDispatch->FastIoWriteCompressed			= SfFastIoWriteCompressed;
	fastIoDispatch->MdlReadCompleteCompressed		= SfFastIoMdlReadCompleteCompressed;
	fastIoDispatch->MdlWriteCompleteCompressed		= SfFastIoMdlWriteCompleteCompressed;
	fastIoDispatch->FastIoQueryOpen					= SfFastIoQueryOpen;

	DriverObject->FastIoDispatch = fastIoDispatch;

	if (gSfDynamicFunctions.RegisterFileSystemFilterCallbacks != NULL) 
	{
		KdPrint(("RegisterFileSystemFilterCallbacks Not NULL\n"));

		fsFilterCallbacks.SizeOfFsFilterCallbacks				= sizeof(FS_FILTER_CALLBACKS);
        fsFilterCallbacks.PreAcquireForSectionSynchronization	= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostAcquireForSectionSynchronization	= SfPostFsFilterPassThrough;
        fsFilterCallbacks.PreReleaseForSectionSynchronization	= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostReleaseForSectionSynchronization	= SfPostFsFilterPassThrough;
        fsFilterCallbacks.PreAcquireForCcFlush					= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostAcquireForCcFlush					= SfPostFsFilterPassThrough;
        fsFilterCallbacks.PreReleaseForCcFlush					= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostReleaseForCcFlush					= SfPostFsFilterPassThrough;
        fsFilterCallbacks.PreAcquireForModifiedPageWriter		= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostAcquireForModifiedPageWriter		= SfPostFsFilterPassThrough;
        fsFilterCallbacks.PreReleaseForModifiedPageWriter		= SfPreFsFilterPassThrough;
        fsFilterCallbacks.PostReleaseForModifiedPageWriter		= SfPostFsFilterPassThrough;

        status = (gSfDynamicFunctions.RegisterFileSystemFilterCallbacks)(DriverObject, &fsFilterCallbacks);

        if (!NT_SUCCESS(status)) 
		{            
            DriverObject->FastIoDispatch = NULL;
            ExFreePool(fastIoDispatch);
            IoDeleteDevice(gSFilterControlDeviceObject);

            return status;
        }

		KdPrint(("RegisterFileSystemFilterCallbacks Success\n"));
    } 

	status = IoRegisterFsRegistrationChange(DriverObject, SfFsNotification);
    if (!NT_SUCCESS(status)) 
	{
        KdPrint(("IoRegisterFsRegistrationChange Fail\n"));

        DriverObject->FastIoDispatch = NULL;
        ExFreePool(fastIoDispatch);
        IoDeleteDevice(gSFilterControlDeviceObject);
        return status;
    }	

    RtlInitUnicodeString(&nameString, L"\\Device\\RawDisk");

    status = IoGetDeviceObjectPointer(&nameString, FILE_READ_ATTRIBUTES, &fileObject, &rawDeviceObject);

    if (NT_SUCCESS(status)) 
	{
        SfFsNotification(rawDeviceObject, TRUE);
        ObDereferenceObject(fileObject);
    }

    RtlInitUnicodeString(&nameString, L"\\Device\\RawCdRom");

    status = IoGetDeviceObjectPointer(&nameString, FILE_READ_ATTRIBUTES, &fileObject, &rawDeviceObject);

    if (NT_SUCCESS(status)) 
	{
        SfFsNotification(rawDeviceObject, TRUE);
        ObDereferenceObject(fileObject);
    }

	ClearFlag(gSFilterControlDeviceObject->Flags, DO_DEVICE_INITIALIZING);

	KdPrint(("IoRegisterFsRegistrationChange Success\n"));
	
	return STATUS_SUCCESS;
}