#include <ntifs.h>
#include <ntdddisk.h>

#define DEVICE_NAME			L"\\FileSystem\\Filters\\Device_FileFilter"
#define LINK_NAME			L"\\FileSystem\\Filters\\Link_FileFilter"
#define MAX_DEVNAME_LENGTH	64
#define SFLT_POOL_TAG		'tlFS'

#define SFDEBUG_DISPLAY_ATTACHMENT_NAMES    0x00000001  //display names of device objects we attach to
#define SFDEBUG_DISPLAY_CREATE_NAMES        0x00000002  //get and display names during create
#define SFDEBUG_GET_CREATE_NAMES            0x00000004  //get name (don't display) during create
#define SFDEBUG_DO_CREATE_COMPLETION        0x00000008  //do create completion routine, don't get names
#define SFDEBUG_ATTACH_TO_FSRECOGNIZER      0x00000010  //do attach to FSRecognizer device objects
#define SFDEBUG_ATTACH_TO_SHADOW_COPIES     0x00000020  //do attach to ShadowCopy Volume device objects -- they are only around on Windows XP and later

#define DELAY_ONE_MICROSECOND   (-10)
#define DELAY_ONE_MILLISECOND   (DELAY_ONE_MICROSECOND*1000)
#define DELAY_ONE_SECOND        (DELAY_ONE_MILLISECOND*1000)

PDRIVER_OBJECT gSFilterDriverObject			= NULL;
PDEVICE_OBJECT gSFilterControlDeviceObject	= NULL;
FAST_MUTEX gSfilterAttachLock;
ULONG SfDebug = 0;

UNICODE_STRING DeviceString;
UNICODE_STRING LinString;

typedef NTSTATUS (*PSF_REGISTER_FILE_SYSTEM_FILTER_CALLBACKS)(IN PDRIVER_OBJECT DriverObject, IN PFS_FILTER_CALLBACKS Callbacks);
typedef NTSTATUS (*PSF_ENUMERATE_DEVICE_OBJECT_LIST)(IN PDRIVER_OBJECT DriverObject, IN PDEVICE_OBJECT *DeviceObjectList, IN ULONG DeviceObjectListSize, OUT PULONG ActualNumberDeviceObjects);
typedef NTSTATUS (*PSF_ATTACH_DEVICE_TO_DEVICE_STACK_SAFE)(IN PDEVICE_OBJECT SourceDevice, IN PDEVICE_OBJECT TargetDevice, OUT PDEVICE_OBJECT *AttachedToDeviceObject);
typedef PDEVICE_OBJECT (*PSF_GET_LOWER_DEVICE_OBJECT)(IN PDEVICE_OBJECT DeviceObject);
typedef PDEVICE_OBJECT (*PSF_GET_DEVICE_ATTACHMENT_BASE_REF)(IN PDEVICE_OBJECT DeviceObject);
typedef NTSTATUS (*PSF_GET_DISK_DEVICE_OBJECT)(IN PDEVICE_OBJECT FileSystemDeviceObject, OUT PDEVICE_OBJECT *DiskDeviceObject);
typedef PDEVICE_OBJECT (*PSF_GET_ATTACHED_DEVICE_REFERENCE)(IN PDEVICE_OBJECT DeviceObject);
typedef NTSTATUS (*PSF_GET_VERSION)(IN OUT PRTL_OSVERSIONINFOW VersionInformation);

typedef struct _FSCTRL_COMPLETION_CONTEXT 
{    
    WORK_QUEUE_ITEM WorkItem;
    PDEVICE_OBJECT DeviceObject;    
    PIRP Irp;    
    PDEVICE_OBJECT NewDeviceObject;
    
} FSCTRL_COMPLETION_CONTEXT, *PFSCTRL_COMPLETION_CONTEXT;

typedef struct _SFILTER_DEVICE_EXTENSION 
{    
    PDEVICE_OBJECT AttachedToDeviceObject;
    PDEVICE_OBJECT StorageStackDeviceObject;
    UNICODE_STRING DeviceName;
    WCHAR DeviceNameBuffer[MAX_DEVNAME_LENGTH];

} SFILTER_DEVICE_EXTENSION, *PSFILTER_DEVICE_EXTENSION;

typedef struct _SF_DYNAMIC_FUNCTION_POINTERS 
{
    PSF_REGISTER_FILE_SYSTEM_FILTER_CALLBACKS RegisterFileSystemFilterCallbacks;
    PSF_ATTACH_DEVICE_TO_DEVICE_STACK_SAFE AttachDeviceToDeviceStackSafe;
    PSF_ENUMERATE_DEVICE_OBJECT_LIST EnumerateDeviceObjectList;
    PSF_GET_LOWER_DEVICE_OBJECT GetLowerDeviceObject;
    PSF_GET_DEVICE_ATTACHMENT_BASE_REF GetDeviceAttachmentBaseRef;
    PSF_GET_DISK_DEVICE_OBJECT GetDiskDeviceObject;
    PSF_GET_ATTACHED_DEVICE_REFERENCE GetAttachedDeviceReference;
    PSF_GET_VERSION GetVersion;

} SF_DYNAMIC_FUNCTION_POINTERS, *PSF_DYNAMIC_FUNCTION_POINTERS;

SF_DYNAMIC_FUNCTION_POINTERS gSfDynamicFunctions = {0};

typedef struct _GET_NAME_CONTROL 
{
    PCHAR allocatedBuffer;
    CHAR smallBuffer[256];
    
} GET_NAME_CONTROL, *PGET_NAME_CONTROL;

#ifndef Add2Ptr
#define Add2Ptr(P,I) ((PVOID)((PUCHAR)(P) + (I)))
#endif

#define IS_MY_CONTROL_DEVICE_OBJECT(_devObj) \
    (((_devObj) == gSFilterControlDeviceObject) ? \
            (ASSERT(((_devObj)->DriverObject == gSFilterDriverObject) && \
                    ((_devObj)->DeviceExtension == NULL)), TRUE) : FALSE)

#define IS_MY_DEVICE_OBJECT(_devObj) \
    (((_devObj) != NULL) && \
     ((_devObj)->DriverObject == gSFilterDriverObject) && \
      ((_devObj)->DeviceExtension != NULL))

#define VALID_FAST_IO_DISPATCH_HANDLER(_FastIoDispatchPtr, _FieldName) \
    (((_FastIoDispatchPtr) != NULL) && \
     (((_FastIoDispatchPtr)->SizeOfFastIoDispatch) >= (FIELD_OFFSET(FAST_IO_DISPATCH, _FieldName) + sizeof(void *))) && \
     ((_FastIoDispatchPtr)->_FieldName != NULL))

#define IS_DESIRED_DEVICE_TYPE(_type) \
    (((_type) == FILE_DEVICE_DISK_FILE_SYSTEM) || \
     ((_type) == FILE_DEVICE_CD_ROM_FILE_SYSTEM) || \
     ((_type) == FILE_DEVICE_NETWORK_FILE_SYSTEM))

#define GET_DEVICE_TYPE_NAME( _type ) \
	((((_type) > 0) && ((_type) < (sizeof(DeviceTypeNames) / sizeof(PCHAR)))) ? \
		DeviceTypeNames[ (_type) ] : "[Unknown]")

#define SF_LOG_PRINT( _dbgLevel, _string ) \
    (FlagOn(SfDebug,(_dbgLevel)) ? DbgPrint _string : ((void)0))

static const PCHAR DeviceTypeNames[] = {
    "",
    "BEEP",
    "CD_ROM",
    "CD_ROM_FILE_SYSTEM",
    "CONTROLLER",
    "DATALINK",
    "DFS",
    "DISK",
    "DISK_FILE_SYSTEM",
    "FILE_SYSTEM",
    "INPORT_PORT",
    "KEYBOARD",
    "MAILSLOT",
    "MIDI_IN",
    "MIDI_OUT",
    "MOUSE",
    "MULTI_UNC_PROVIDER",
    "NAMED_PIPE",
    "NETWORK",
    "NETWORK_BROWSER",
    "NETWORK_FILE_SYSTEM",
    "NULL",
    "PARALLEL_PORT",
    "PHYSICAL_NETCARD",
    "PRINTER",
    "SCANNER",
    "SERIAL_MOUSE_PORT",
    "SERIAL_PORT",
    "SCREEN",
    "SOUND",
    "STREAMS",
    "TAPE",
    "TAPE_FILE_SYSTEM",
    "TRANSPORT",
    "UNKNOWN",
    "VIDEO",
    "VIRTUAL_DISK",
    "WAVE_IN",
    "WAVE_OUT",
    "8042_PORT",
    "NETWORK_REDIRECTOR",
    "BATTERY",
    "BUS_EXTENDER",
    "MODEM",
    "VDM",
    "MASS_STORAGE",
    "SMB",
    "KS",
    "CHANGER",
    "SMARTCARD",
    "ACPI",
    "DVD",
    "FULLSCREEN_VIDEO",
    "DFS_FILE_SYSTEM",
    "DFS_VOLUME",
    "SERENUM",
    "TERMSRV",
    "KSEC"
};

