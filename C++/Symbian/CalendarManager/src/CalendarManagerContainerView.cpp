/*
========================================================================
 Name        : CalendarManagerContainerView.cpp
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
#include "CalendarManagerContainerView.h"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
#include "CalendarManagerContainer.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CCalendarManagerContainerView::CCalendarManagerContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iNaviDecorator_ = NULL;
	iCalendarManagerContainer = NULL;
	// ]]] end generated region [Generated Contents]
	g_CalendarClass = NULL;
	g_CalendarManagerXML = NULL;
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CCalendarManagerContainerView::~CCalendarManagerContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( iNaviDecorator_ != NULL )
		{
		delete iNaviDecorator_;
		iNaviDecorator_ = NULL;
		}
	delete iCalendarManagerContainer;
	iCalendarManagerContainer = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CCalendarManagerContainerView
 */
CCalendarManagerContainerView* CCalendarManagerContainerView::NewL()
	{
	CCalendarManagerContainerView* self = CCalendarManagerContainerView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CCalendarManagerContainerView
 */
CCalendarManagerContainerView* CCalendarManagerContainerView::NewLC()
	{
	CCalendarManagerContainerView* self = new ( ELeave ) CCalendarManagerContainerView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CCalendarManagerContainerView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_CALENDAR_MANAGER_CONTAINER_CALENDAR_MANAGER_CONTAINER_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid CCalendarManagerContainerView::Id() const
	{
	return TUid::Uid( ECalendarManagerContainerViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CCalendarManagerContainerView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
	
		case EAknSoftkeySelect:
			commandHandled = HandleControlPaneMiddleSoftKeyPressedL( aCommand );
			break;
		case ECalendarManagerContainerView_MenuItemCommand:
			commandHandled = Handle_MenuItemSelectedL( aCommand );
			break;
		case ECalendarManagerContainerView_MenuItem1Command:
			commandHandled = Handle_MenuItem1SelectedL( aCommand );
			break;
		case ECalendarManagerContainerView_MenuItem2Command:
			commandHandled = Handle_MenuItem2SelectedL( aCommand );
			break;
		case ECalendarManagerContainerView_MenuItem3Command:
			commandHandled = Handle_MenuItem3SelectedL( aCommand );
			break;
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyExit )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	if ( !commandHandled )
		{
		switch ( aCommand )
			{
			case ECalendarManagerContainerView_KeyCommand:
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
void CCalendarManagerContainerView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
				
	
	if ( iCalendarManagerContainer == NULL )
		{
		iCalendarManagerContainer = CreateContainerL();
		iCalendarManagerContainer->SetMopParent( this );
		AppUi()->AddToStackL( *this, iCalendarManagerContainer );
		} 
	// ]]] end generated region [Generated Contents]
	iCalendarManagerContainer->SetCalendarClass(g_CalendarClass);
	iCalendarManagerContainer->SetXMLClass(g_CalendarManagerXML);
	}

/**
 */
void CCalendarManagerContainerView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iCalendarManagerContainer != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iCalendarManagerContainer );
		delete iCalendarManagerContainer;
		iCalendarManagerContainer = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void CCalendarManagerContainerView::HandleStatusPaneSizeChange()
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
void CCalendarManagerContainerView::SetupStatusPaneL()
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
				
		HBufC* labelText = StringLoader::LoadLC( R_CALENDAR_MANAGER_CONTAINER_NAVI_TEXT1 );
		iNaviDecorator_ = naviPane->CreateNavigationLabelL( *labelText );
		CleanupStack::PopAndDestroy( labelText );			
				
		naviPane->PushL( *iNaviDecorator_ );
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CCalendarManagerContainerView::CleanupStatusPane()
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
 *	contents and the CCalendarManagerContainer::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CCalendarManagerContainer
 */
CCalendarManagerContainer* CCalendarManagerContainerView::CreateContainerL()
	{
	return CCalendarManagerContainer::NewL( ClientRect(), NULL, this );
	}

/** 
 * Handle the middleSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerContainerView::HandleControlPaneMiddleSoftKeyPressedL( TInt aCommand )
	{
	// TODO: implement middleSoftKeyPressed event handler
	AppUi()->HandleCommandL(CalendarManager_ListBoxView_KeyCommand);
	return ETrue;
	}

void CCalendarManagerContainerView::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerContainerView::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;	
	}

void CCalendarManagerContainerView::SetTitleText()
	{	
	TUid titlePaneUid = TUid::Uid(EEikStatusPaneUidTitle);
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = StatusPane()->PaneCapabilities(titlePaneUid);
	if (subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned())
		{
		CAknTitlePane* title = static_cast<CAknTitlePane*>(StatusPane()->ControlL(titlePaneUid));
		title->SetTextToDefaultL();
		title->SetTextL(g_CalendarClass->GetSelectMonthCN()->Des());		
		}	
	}

void CCalendarManagerContainerView::SetPanelText()
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
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerContainerView::Handle_MenuItemSelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler
	g_CalendarManagerXML->g_SelectTime.Zero();
	g_CalendarManagerXML->g_SelectCalendarManagerInfo = NULL;
	AppUi()->HandleCommandL(CalendarManager_SettingItemList_KeyCommand);
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerContainerView::Handle_MenuItem1SelectedL( TInt aCommand )
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
TBool CCalendarManagerContainerView::Handle_MenuItem2SelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler	
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CCalendarManagerContainerView::Handle_MenuItem3SelectedL( TInt aCommand )
	{
	// TODO: implement selected event handler
	return ETrue;
	}
				
