/*
========================================================================
 Name        : CalendarManagerAppUi.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <eikmenub.h>
#include <akncontext.h>
#include <akntitle.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "CalendarManagerAppUi.h"
#include "CalendarManager.hrh"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
#include "CalendarManagerContainerView.h"
#include "CalendarManagerListBoxView.h"
#include "CalendarManagerSettingItemListView.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Construct the CCalendarManagerAppUi instance
 */ 
CCalendarManagerAppUi::CCalendarManagerAppUi()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	g_CalendarClass = NULL;
	g_CalendarManagerXML = NULL;
	g_ScheduleFilePath = NULL;	
	}

/** 
 * The appui's destructor removes the container from the control
 * stack and destroys it.
 */
CCalendarManagerAppUi::~CCalendarManagerAppUi()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	g_Fs.Close();
	SafeRelease(g_CalendarClass);
	SafeRelease(g_CalendarManagerXML);
	SafeRelease(g_ScheduleFilePath);
	CloseSTDLIB();
	}

// [[[ begin generated function: do not modify
void CCalendarManagerAppUi::InitializeContainersL()
	{
	iCalendarManagerContainerView = CCalendarManagerContainerView::NewL();
	AddViewL( iCalendarManagerContainerView );
	SetDefaultViewL( *iCalendarManagerContainerView );
	iCalendarManagerListBoxView = CCalendarManagerListBoxView::NewL();
	AddViewL( iCalendarManagerListBoxView );
	iCalendarManagerSettingItemListView = CCalendarManagerSettingItemListView::NewL();
	AddViewL( iCalendarManagerSettingItemListView );
	}
// ]]] end generated function

/**
 * Handle a command for this appui (override)
 * @param aCommand command id to be handled
 */
void CCalendarManagerAppUi::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{ // code to dispatch to the AppUi's menu and CBA commands is generated here
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
		if ( aCommand == EAknSoftkeyExit || aCommand == EEikCmdExit )
			{
			Exit();
			}
		}
	// ]]] end generated region [Generated Code]
	if ( !commandHandled )
		{
		switch ( aCommand )
			{
			case CalendarManager_ContainerView_KeyCommand:
				const TUid CalendarManager_ContainerViewId = { ECalendarManagerContainerViewId };
				ActivateLocalViewL(CalendarManager_ContainerViewId);
				commandHandled = ETrue;
				break;
				
			case CalendarManager_ListBoxView_KeyCommand:				
				const TUid CalendarManager_ListBoxViewId = { ECalendarManagerListBoxViewId };
				ActivateLocalViewL(CalendarManager_ListBoxViewId);
				commandHandled = ETrue;
				break;
				
			case CalendarManager_SettingItemList_KeyCommand:
				const TUid CalendarManager_SettingItemListViewId = { ECalendarManagerSettingItemListViewId };
				ActivateLocalViewL(CalendarManager_SettingItemListViewId);
				commandHandled = ETrue;
				break;
				
			default:
				break;
			}
		}
	}


/** 
 * Override of the HandleResourceChangeL virtual function
 */
void CCalendarManagerAppUi::HandleResourceChangeL( TInt aType )
	{
	CAknViewAppUi::HandleResourceChangeL( aType );
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}
				
/** 
 * Override of the HandleKeyEventL virtual function
 * @return EKeyWasConsumed if event was handled, EKeyWasNotConsumed if not
 * @param aKeyEvent 
 * @param aType 
 */
TKeyResponse CCalendarManagerAppUi::HandleKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType )
	{
	// The inherited HandleKeyEventL is private and cannot be called
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	return EKeyWasNotConsumed;
	}

/** 
 * Override of the HandleViewDeactivation virtual function
 *
 * @param aViewIdToBeDeactivated 
 * @param aNewlyActivatedViewId 
 */
void CCalendarManagerAppUi::HandleViewDeactivation( const TVwsViewId& aViewIdToBeDeactivated, const TVwsViewId& aNewlyActivatedViewId )
	{
	CAknViewAppUi::HandleViewDeactivation( aViewIdToBeDeactivated, aNewlyActivatedViewId );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here. 
 */ 
void CCalendarManagerAppUi::ConstructL()
	{
	GetXMLFile();
	g_CalendarClass = CalendarClass::NewL();
	g_CalendarManagerXML = CalendarManagerXML::NewL(g_Fs, g_ScheduleFilePath->Des());	
	// [[[ begin generated region: do not modify [Generated Contents]
	
	BaseConstructL( EAknEnableSkin  | 
					 EAknEnableMSK ); 
	InitializeContainersL();
	// ]]] end generated region [Generated Contents]
	iCalendarManagerContainerView->SetCalendarClass(g_CalendarClass);
	iCalendarManagerContainerView->SetXMLClass(g_CalendarManagerXML);
	iCalendarManagerListBoxView->SetCalendarClass(g_CalendarClass);
	iCalendarManagerListBoxView->SetXMLClass(g_CalendarManagerXML);
	iCalendarManagerSettingItemListView->SetCalendarClass(g_CalendarClass);
	iCalendarManagerSettingItemListView->SetXMLClass(g_CalendarManagerXML);
	}

void CCalendarManagerAppUi::GetXMLFile()
	{
	TInt err;	
	g_Fs.Connect();
	
	TFileName i_PrivatePath;
	err = g_Fs.SessionPath(i_PrivatePath);	
	err = g_Fs.CreatePrivatePath(EDriveC);
	//err = g_Fs.PrivatePath(i_PrivatePath);
	err = g_Fs.SetSessionPath(i_PrivatePath);
		
	g_ScheduleFilePath = HBufC8::NewL(i_PrivatePath.Length() + 20);
	g_ScheduleFilePath->Des().SetLength(g_ScheduleFilePath->Des().MaxLength());
	g_ScheduleFilePath->Des().FillZ();
	g_ScheduleFilePath->Des().Zero();
	
	TPtr8 i_ScheduleFilePathPtr(g_ScheduleFilePath->Des());
	i_ScheduleFilePathPtr.Append(i_PrivatePath);
	i_ScheduleFilePathPtr.Append(_L("Schedule.xml"));
	
	HBufC* i_ScheduleFilePath = CnvUtfConverter::ConvertToUnicodeFromUtf8L(g_ScheduleFilePath->Des());	
	TBool i_FileExists = BaflUtils::FileExists(g_Fs, i_ScheduleFilePath->Des());
	if (!i_FileExists)
		{
		TParse i_parse;
		i_parse.Set(Application()->AppFullName(), NULL, NULL);
		
		HBufC* i_AppPath = HBufC::NewL(i_parse.DriveAndPath().Length() + 20);		
		TPtr i_AppPathPtr(i_AppPath->Des());
		i_AppPathPtr.Append(i_parse.DriveAndPath());
		i_AppPathPtr.Append(_L("Schedule.xml"));		
		
		err = BaflUtils::CopyFile(g_Fs, i_AppPath->Des(), i_ScheduleFilePath->Des());
		SafeRelease(i_AppPath);				
		}		
	
	SafeRelease(i_ScheduleFilePath);	
	}

