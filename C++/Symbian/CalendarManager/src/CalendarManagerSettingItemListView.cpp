/*
========================================================================
 Name        : CalendarManagerSettingItemListView.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <akncontext.h>
#include <akntitle.h>
#include <aknnavide.h>
#include <aknnavi.h>
#include <stringloader.h>
#include <eikbtgpc.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "CalendarManager.hrh"
#include "CalendarManagerSettingItemListView.h"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
#include "CalendarManagerSettingItemList.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CCalendarManagerSettingItemListView::CCalendarManagerSettingItemListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iNaviDecorator_ = NULL;
	// ]]] end generated region [Generated Contents]
	g_CalendarClass = NULL;
	g_CalendarManagerXML = NULL;
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CCalendarManagerSettingItemListView::~CCalendarManagerSettingItemListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( iNaviDecorator_ != NULL )
		{
		delete iNaviDecorator_;
		iNaviDecorator_ = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CCalendarManagerSettingItemListView
 */
CCalendarManagerSettingItemListView* CCalendarManagerSettingItemListView::NewL()
	{
	CCalendarManagerSettingItemListView* self = CCalendarManagerSettingItemListView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CCalendarManagerSettingItemListView
 */
CCalendarManagerSettingItemListView* CCalendarManagerSettingItemListView::NewLC()
	{
	CCalendarManagerSettingItemListView* self = new ( ELeave ) CCalendarManagerSettingItemListView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CCalendarManagerSettingItemListView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_CALENDAR_MANAGER_SETTING_ITEM_LIST_CALENDAR_MANAGER_SETTING_ITEM_LIST_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid CCalendarManagerSettingItemListView::Id() const
	{
	return TUid::Uid( ECalendarManagerSettingItemListViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CCalendarManagerSettingItemListView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
	
		case EAknSoftkeySelect:
			commandHandled = HandleControlPaneMiddleSoftKeyPressedL( aCommand );
			break;
	
		case EAknSoftkeyOk:
			commandHandled = HandleControlPaneLeftSoftKeyPressedL( aCommand );
			break;
	
		case EAknSoftkeyCancel:
			commandHandled = HandleControlPaneRightSoftKeyPressedL( aCommand );
			break;
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyCancel )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	if ( !commandHandled )
		{
		switch ( aCommand )
			{
			case ECalendarManagerSettingItemListView_KeyCommand:
				SetTitleText();
				SetPanelText();
				break;
				
			default:
				break;
			}
		}
	}

/**
 *	Handles user actions during activation of the view, 
 *	such as initializing the content.
 */
void CCalendarManagerSettingItemListView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
				
	
	if ( iCalendarManagerSettingItemList == NULL )
		{
		iSettings = TCalendarManagerSettingItemListSettings::NewL();
		iCalendarManagerSettingItemList = new ( ELeave ) CCalendarManagerSettingItemList( *iSettings, this );
		iCalendarManagerSettingItemList->SetMopParent( this );
		iCalendarManagerSettingItemList->ConstructFromResourceL( R_CALENDAR_MANAGER_SETTING_ITEM_LIST_CALENDAR_MANAGER_SETTING_ITEM_LIST );
		iCalendarManagerSettingItemList->ActivateL();
		iCalendarManagerSettingItemList->LoadSettingValuesL();
		iCalendarManagerSettingItemList->LoadSettingsL();
		AppUi()->AddToStackL( *this, iCalendarManagerSettingItemList );		
		} 
	// ]]] end generated region [Generated Contents]
	iCalendarManagerSettingItemList->SetCalendarClass(g_CalendarClass);
	iCalendarManagerSettingItemList->SetXMLClass(g_CalendarManagerXML);
	iCalendarManagerSettingItemList->SetDefaultSetting();
	iCalendarManagerSettingItemList->LoadSettingsL();
	iCalendarManagerSettingItemList->HandleChangeInItemArrayOrVisibilityL();
	}

/**
 */
void CCalendarManagerSettingItemListView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iCalendarManagerSettingItemList != NULL )
		{
		AppUi()->RemoveFromStack( iCalendarManagerSettingItemList );
		delete iCalendarManagerSettingItemList;
		iCalendarManagerSettingItemList = NULL;
		delete iSettings;
		iSettings = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void CCalendarManagerSettingItemListView::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TVwsViewId view;
	AppUi()->GetActiveViewId( view );
	if ( view.iViewUid == Id() )
		{
		TInt result;
		TRAP( result, SetupStatusPaneL() );
		}
	
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}

// [[[ begin generated function: do not modify
void CCalendarManagerSettingItemListView::SetupStatusPaneL()
	{
	// reset the title pane
	TUid titlePaneUid = TUid::Uid( EEikStatusPaneUidTitle );
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = 
		StatusPane()->PaneCapabilities( titlePaneUid );
	if ( subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned() )
		{
		CAknTitlePane* title = static_cast< CAknTitlePane* >( 
			StatusPane()->ControlL( titlePaneUid ) );
		title->SetTextToDefaultL();
		}
	// reset the context pane
	TUid contextPaneUid = TUid::Uid( EEikStatusPaneUidContext );
	CEikStatusPaneBase::TPaneCapabilities subPaneContext = 
		StatusPane()->PaneCapabilities( contextPaneUid );
	if ( subPaneContext.IsPresent() && subPaneContext.IsAppOwned() )
		{
		CAknContextPane* context = static_cast< CAknContextPane* > ( 
			StatusPane()->ControlL( contextPaneUid ) );
		context->SetPictureToDefaultL();
		}
	
	// set the navi pane content
	TUid naviPaneUid = TUid::Uid( EEikStatusPaneUidNavi );
	CEikStatusPaneBase::TPaneCapabilities subPaneNavi = 
		StatusPane()->PaneCapabilities( naviPaneUid );
	if ( subPaneNavi.IsPresent() && subPaneNavi.IsAppOwned() )
		{
		CAknNavigationControlContainer* naviPane = 
			static_cast< CAknNavigationControlContainer* >( 
				StatusPane()->ControlL( naviPaneUid ) );
		if ( iNaviDecorator_ != NULL )
			{
			delete iNaviDecorator_;
			iNaviDecorator_ = NULL;
			}
				
		HBufC* labelText = StringLoader::LoadLC( R_CALENDAR_MANAGER_SETTING_ITEM_LIST_NAVI_TEXT1 );
		iNaviDecorator_ = naviPane->CreateNavigationLabelL( *labelText );
		CleanupStack::PopAndDestroy( labelText );			
				
		naviPane->PushL( *iNaviDecorator_ );
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CCalendarManagerSettingItemListView::CleanupStatusPane()
	{
	// reset the navi pane 
	TUid naviPaneUid = TUid::Uid( EEikStatusPaneUidNavi );
	CEikStatusPaneBase::TPaneCapabilities subPaneNavi = 
		StatusPane()->PaneCapabilities( naviPaneUid );
	if ( subPaneNavi.IsPresent() && subPaneNavi.IsAppOwned() )
		{
		CAknNavigationControlContainer* naviPane = 
			static_cast< CAknNavigationControlContainer* >( 
				StatusPane()->ControlL( naviPaneUid ) );
		if ( iNaviDecorator_ != NULL )
			{
			naviPane->Pop( iNaviDecorator_ );
			delete iNaviDecorator_;
			iNaviDecorator_ = NULL;
			}
		}
	
	}

// ]]] end generated function
/** 
 * Handle the leftSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerSettingItemListView::HandleControlPaneLeftSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement leftSoftKeyPressed event handler
	iCalendarManagerSettingItemList->SaveSetting();
	AppUi()->HandleCommandL( CalendarManager_ListBoxView_KeyCommand );
	return ETrue;
	}

/** 
 * Handle the middleSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerSettingItemListView::HandleControlPaneMiddleSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement middleSoftKeyPressed event handler
	iCalendarManagerSettingItemList->ChangeSelectedItemL();
	return ETrue;
	}
				
/** 
 * Handle the rightSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerSettingItemListView::HandleControlPaneRightSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement rightSoftKeyPressed event handler
	AppUi()->HandleCommandL( CalendarManager_ListBoxView_KeyCommand );
	return ETrue;
	}
				
void CCalendarManagerSettingItemListView::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerSettingItemListView::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;	
	}

void CCalendarManagerSettingItemListView::SetTitleText()
	{
	TBuf<20> i_Buf;
	
	if (g_CalendarManagerXML->g_SelectTime.Length() == 0)
		i_Buf.Append(_L("\x65B0\x5EFA"));
	else
		i_Buf.Append(_L("\x4FEE\x6539"));
	
	TUid titlePaneUid = TUid::Uid( EEikStatusPaneUidTitle );
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = StatusPane()->PaneCapabilities(titlePaneUid);
	if (subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned())
		{
		CAknTitlePane* title = static_cast<CAknTitlePane*>(StatusPane()->ControlL(titlePaneUid));
		title->SetTextToDefaultL();
		title->SetTextL(i_Buf);
		}		
	}

void CCalendarManagerSettingItemListView::SetPanelText()
	{		
	TUid naviPaneUid = TUid::Uid(EEikStatusPaneUidNavi);
	CEikStatusPaneBase::TPaneCapabilities subPaneNavi =	StatusPane()->PaneCapabilities( naviPaneUid );
	if (subPaneNavi.IsPresent() && subPaneNavi.IsAppOwned())
		{
		CAknNavigationControlContainer* naviPane = static_cast<CAknNavigationControlContainer*>(StatusPane()->ControlL(naviPaneUid));		
		if ( iNaviDecorator_ != NULL )
			{
			delete iNaviDecorator_;
			iNaviDecorator_ = NULL;
			}
		
		if (naviPane->Size().iWidth >= 198)						
			iNaviDecorator_ = naviPane->CreateNavigationLabelL(g_CalendarClass->GetSelectDateCN()->Des());
		else			
			iNaviDecorator_ = naviPane->CreateNavigationLabelL(g_CalendarClass->GetSelectDateEN()->Des());						
				
		iNaviDecorator_->MakeScrollButtonVisible(ETrue);
		iNaviDecorator_->SetScrollButtonDimmed(CAknNavigationDecorator::ELeftButton, EFalse);
		iNaviDecorator_->SetScrollButtonDimmed(CAknNavigationDecorator::ERightButton, EFalse);
		naviPane->PushL(*iNaviDecorator_);
		}	
	}

				
