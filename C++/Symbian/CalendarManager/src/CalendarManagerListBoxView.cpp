/*
========================================================================
 Name        : CalendarManagerListBoxView.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <barsread.h>
#include <stringloader.h>
#include <aknlists.h>
#include <eikenv.h>
#include <akniconarray.h>
#include <eikclbd.h>
#include <akncontext.h>
#include <akntitle.h>
#include <aknnavide.h>
#include <aknnavi.h>
#include <eikbtgpc.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "CalendarManager.hrh"
#include "CalendarManagerListBoxView.h"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
#include "CalendarManagerListBox.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CCalendarManagerListBoxView::CCalendarManagerListBoxView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iNaviDecorator_ = NULL;
	iCalendarManagerListBox = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CCalendarManagerListBoxView::~CCalendarManagerListBoxView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( iNaviDecorator_ != NULL )
		{
		delete iNaviDecorator_;
		iNaviDecorator_ = NULL;
		}
	delete iCalendarManagerListBox;
	iCalendarManagerListBox = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CCalendarManagerListBoxView
 */
CCalendarManagerListBoxView* CCalendarManagerListBoxView::NewL()
	{
	CCalendarManagerListBoxView* self = CCalendarManagerListBoxView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CCalendarManagerListBoxView
 */
CCalendarManagerListBoxView* CCalendarManagerListBoxView::NewLC()
	{
	CCalendarManagerListBoxView* self = new ( ELeave ) CCalendarManagerListBoxView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CCalendarManagerListBoxView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_CALENDAR_MANAGER_LIST_BOX_CALENDAR_MANAGER_LIST_BOX_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid CCalendarManagerListBoxView::Id() const
	{
	return TUid::Uid( ECalendarManagerListBoxViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CCalendarManagerListBoxView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
	
		case EAknSoftkeySelect:
			commandHandled = HandleControlPaneMiddleSoftKeyPressedL( aCommand );
			break;
	
		case EAknSoftkeyBack:
			commandHandled = HandleControlPaneRightSoftKeyPressedL( aCommand );
			break;
		case ECalendarManagerListBoxView_MenuItemCommand:
			commandHandled = Handle_MenuItemSelectedL( aCommand );
			break;
		case ECalendarManagerListBoxView_MenuItem1Command:
			commandHandled = Handle_MenuItem1SelectedL( aCommand );
			break;
		case ECalendarManagerListBoxView_MenuItem2Command:
			commandHandled = Handle_MenuItem2SelectedL( aCommand );
			break;
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyBack )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	if ( !commandHandled )
		{
		switch ( aCommand )
			{
			case ECalendarManagerListBoxView_KeyCommand:
				SetTitleText();
				commandHandled = ETrue;
				break;
			
			case ECalendarManagerListBoxView_Key1Command:
				SetPanelText();
				commandHandled = ETrue;
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
void CCalendarManagerListBoxView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
				
	
	if ( iCalendarManagerListBox == NULL )
		{
		iCalendarManagerListBox = CreateContainerL();
		iCalendarManagerListBox->SetMopParent( this );
		AppUi()->AddToStackL( *this, iCalendarManagerListBox );
		} 
	// ]]] end generated region [Generated Contents]
	iCalendarManagerListBox->SetCalendarClass(g_CalendarClass);
	iCalendarManagerListBox->SetXMLClass(g_CalendarManagerXML);
	iCalendarManagerListBox->SetListBox();
	}

/**
 */
void CCalendarManagerListBoxView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iCalendarManagerListBox != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iCalendarManagerListBox );
		delete iCalendarManagerListBox;
		iCalendarManagerListBox = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void CCalendarManagerListBoxView::HandleStatusPaneSizeChange()
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
void CCalendarManagerListBoxView::SetupStatusPaneL()
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
				
		HBufC* labelText = StringLoader::LoadLC( R_CALENDAR_MANAGER_LIST_BOX_NAVI_TEXT1 );
		iNaviDecorator_ = naviPane->CreateNavigationLabelL( *labelText );
		CleanupStack::PopAndDestroy( labelText );			
				
		naviPane->PushL( *iNaviDecorator_ );
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CCalendarManagerListBoxView::CleanupStatusPane()
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
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CCalendarManagerListBox::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CCalendarManagerListBox
 */
CCalendarManagerListBox* CCalendarManagerListBoxView::CreateContainerL()
	{
	return CCalendarManagerListBox::NewL( ClientRect(), NULL, this );
	}

/** 
 * Handle the middleSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerListBoxView::HandleControlPaneMiddleSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement middleSoftKeyPressed event handler	
	CalendarManagerSchedule* i_CalendarManagerSchedule = g_CalendarManagerXML->g_ScheduleMap.Find(g_CalendarClass->GetSelectDateEN()->Des());
	if (i_CalendarManagerSchedule != NULL)
		{
		iCalendarManagerListBox->SetSelectTimeContent();
		AppUi()->HandleCommandL( CalendarManager_SettingItemList_KeyCommand );
		}
	return ETrue;
	}

/** 
 * Handle the rightSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerListBoxView::HandleControlPaneRightSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement rightSoftKeyPressed event handler
	g_CalendarManagerXML->g_SelectCalendarManagerInfo = NULL;
	AppUi()->HandleCommandL( CalendarManager_ContainerView_KeyCommand );
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerListBoxView::Handle_MenuItemSelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler
	g_CalendarManagerXML->g_SelectTime.Zero();
	g_CalendarManagerXML->g_SelectCalendarManagerInfo = NULL;
	AppUi()->HandleCommandL( CalendarManager_SettingItemList_KeyCommand );
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerListBoxView::Handle_MenuItem1SelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler
	HandleControlPaneMiddleSoftKeyPressedL(EAknSoftkeySelect);
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerListBoxView::Handle_MenuItem2SelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler
	return ETrue;
	}
				
void CCalendarManagerListBoxView::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerListBoxView::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;	
	}

void CCalendarManagerListBoxView::SetTitleText()
	{
	TUid titlePaneUid = TUid::Uid(EEikStatusPaneUidTitle);
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = StatusPane()->PaneCapabilities(titlePaneUid);
	if (subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned())
		{
		CAknTitlePane* title = static_cast<CAknTitlePane*>(StatusPane()->ControlL(titlePaneUid));
		title->SetTextToDefaultL();
		title->SetTextL(g_CalendarClass->GetSelectDateCN()->Des());
		}
	}

void CCalendarManagerListBoxView::SetPanelText()
	{			
	TUid naviPaneUid = TUid::Uid(EEikStatusPaneUidNavi);
	CEikStatusPaneBase::TPaneCapabilities subPaneNavi =	StatusPane()->PaneCapabilities(naviPaneUid);
	if (subPaneNavi.IsPresent() && subPaneNavi.IsAppOwned())
		{
		CAknNavigationControlContainer* naviPane = static_cast<CAknNavigationControlContainer*>(StatusPane()->ControlL(naviPaneUid));		
		if (iNaviDecorator_ != NULL)
			{
			delete iNaviDecorator_;
			iNaviDecorator_ = NULL;
			}
		
		TPtrC i_ReminderText = GetReminderText();	
		iNaviDecorator_ = naviPane->CreateNavigationLabelL(i_ReminderText);
						
		iNaviDecorator_->MakeScrollButtonVisible(ETrue);
		iNaviDecorator_->SetScrollButtonDimmed(CAknNavigationDecorator::ELeftButton, EFalse);
		iNaviDecorator_->SetScrollButtonDimmed(CAknNavigationDecorator::ERightButton, EFalse);
		naviPane->PushL(*iNaviDecorator_);				
		}	
	}

TPtrC CCalendarManagerListBoxView::GetReminderText()
	{	
	if (g_CalendarManagerXML->g_SelectCalendarManagerInfo == NULL)
		{		
		return _L("");
		}
	
	TInt i_Reminder = g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Reminder;
	switch (i_Reminder)
		{
		case 0:
			return _L("\x65E0");		
			
		case 1:
			return _L("\x94C3\x58F0");			
						
		case 2:
			return _L("\x632F\x52A8");			
			
		case 3:
			return _L("\x94C3\x58F0\x002B\x632F\x52A8");			
			
		default:
			return _L("");			
		}	
	}
