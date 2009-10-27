/*
========================================================================
 Name        : CalendarManagerListBox.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <barsread.h>
#include <stringloader.h>
#include <aknlists.h>
#include <eikenv.h>
#include <akniconarray.h>
#include <eikclbd.h>
#include <aknviewappui.h>
#include <eikappui.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "CalendarManagerListBox.h"
#include "CalendarManagerListBoxView.h"
#include "CalendarManager.hrh"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

TInt CompareReal(const TReal& p_Value1, const TReal& p_Value2)
	{
	return p_Value1 > p_Value2 ? 1 : 0;
	}

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CCalendarManagerListBox::CCalendarManagerListBox()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iListBox = NULL;
	// ]]] end generated region [Generated Contents]
	g_CalendarClass = NULL;
	g_CalendarManagerXML = NULL;
	g_CalendarManagerSchedule = NULL;
	}
/** 
 * Destroy child controls.
 */
CCalendarManagerListBox::~CCalendarManagerListBox()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iListBox;
	iListBox = NULL;
	iControlEventDispatch.Close();		
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aRect bounding rectangle
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return initialized instance of CCalendarManagerListBox
 */
CCalendarManagerListBox* CCalendarManagerListBox::NewL( const TRect& aRect,	const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	CCalendarManagerListBox* self = CCalendarManagerListBox::NewLC( aRect, aParent,	aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aRect The rectangle for this window
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return new instance of CCalendarManagerListBox
 */
CCalendarManagerListBox* CCalendarManagerListBox::NewLC( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	CCalendarManagerListBox* self = new ( ELeave ) CCalendarManagerListBox();
	CleanupStack::PushL( self );
	self->ConstructL( aRect, aParent, aCommandObserver );
	return self;
	}
			
/**
 * Construct the control (second phase).
 *  Creates a window to contain the controls and activates it.
 * @param aRect bounding rectangle
 * @param aCommandObserver command observer
 * @param aParent owning parent, or NULL
 */ 
void CCalendarManagerListBox::ConstructL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	if ( aParent == NULL )
	    {
		CreateWindowL();
	    }
	else
	    {
	    SetContainerWindowL( *aParent );
	    }
	iFocusControl = NULL;
	iCommandObserver = aCommandObserver;
	InitializeControlsL();
	SetRect( aRect );
	ActivateL();
	// [[[ begin generated region: do not modify [Post-ActivateL initializations]
	// ]]] end generated region [Post-ActivateL initializations]
	
	}
			
/**
* Return the number of controls in the container (override)
* @return count
*/
TInt CCalendarManagerListBox::CountComponentControls() const
	{
	return ( int ) ELastControl;
	}
				
/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CCalendarManagerListBox::ComponentControl( TInt aIndex ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	switch ( aIndex )
		{
	case EListBox:
		return iListBox;
		}
	// ]]] end generated region [Generated Contents]
	
	// handle any user controls here...
	
	return NULL;
	}
				
/**
 *	Handle resizing of the container. This implementation will lay out
 *  full-sized controls like list boxes for any screen size, and will layout
 *  labels, editors, etc. to the size they were given in the UI designer.
 *  This code will need to be modified to adjust arbitrary controls to
 *  any screen size.
 */				
void CCalendarManagerListBox::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	// [[[ begin generated region: do not modify [Generated Contents]
			
	// ]]] end generated region [Generated Contents]
	SetTitleText();
	SetPanelText();	
	}
				
// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CCalendarManagerListBox::LayoutControls()
	{
	iListBox->SetExtent( TPoint( 0, 0 ), iListBox->MinimumSize() );
	}
// ]]] end generated function

/**
 *	Handle key events.
 */				
TKeyResponse CCalendarManagerListBox::OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( aKeyEvent.iCode == EKeyLeftArrow 
		|| aKeyEvent.iCode == EKeyRightArrow )
		{
		// Listbox takes all events even if it doesn't use them
		return EKeyWasNotConsumed;
		}
	
	// ]]] end generated region [Generated Contents]		
	if ( iFocusControl != NULL && iFocusControl->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}		
	
	return CCoeControl::OfferKeyEventL( aKeyEvent, aType );
	}
				
// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CCalendarManagerListBox::InitializeControlsL()
	{
	iListBox = new ( ELeave ) CAknSingleHeadingStyleListBox;
	iListBox->SetContainerWindowL( *this );
		{
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_CALENDAR_MANAGER_LIST_BOX_LIST_BOX );
		iListBox->ConstructFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
	// the listbox owns the items in the list and will free them
	iListBox->Model()->SetOwnershipType( ELbmOwnsItemArray );
	
	// setup the icon array so graphics-style boxes work
	SetupListBoxIconsL();
	
	
	// add list items
	iListBox->SetObserver( this );
	AddControlEventHandlerL( 
			iListBox, 
			EEventStateChanged, 
			&CCalendarManagerListBox::HandleListBoxStateChangedL );
	
	iListBox->SetFocus( ETrue );
	iFocusControl = iListBox;
	
	}
// ]]] end generated function

/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CCalendarManagerListBox::HandleResourceChange( TInt aType )
	{
	CCoeControl::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( ECalendarManagerListBoxViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Draw container contents.
 */				
void CCalendarManagerListBox::Draw( const TRect& aRect ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]
	
	}
				
// [[[ begin generated function: do not modify
/**
 *	Add a list box item to a list.
 */
void CCalendarManagerListBox::AddListBoxItemL( 
		CEikTextListBox* aListBox,
		const TDesC& aString )
	{
	CTextListBoxModel* model = aListBox->Model();
	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	itemArray->AppendL( aString );
	aListBox->HandleItemAdditionL();
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Get the array of selected item indices, with respect to the list model.
 * The array is sorted in ascending order.
 * The array should be destroyed with two calls to CleanupStack::PopAndDestroy(),
 * the first with no argument (referring to the internal resource) and the
 * second with the array pointer.
 * @return newly allocated array, which is left on the cleanup stack;
 *	or NULL for empty list. 
 */
RArray< TInt >* CCalendarManagerListBox::GetSelectedListBoxItemsLC( CEikTextListBox* aListBox )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( aListBox->Model() );
	if ( model->NumberOfItems() == 0 )
		return NULL;
		
	// get currently selected indices
	const CListBoxView::CSelectionIndexArray* selectionIndexes =
		aListBox->SelectionIndexes();
	TInt selectedIndexesCount = selectionIndexes->Count();
	if ( selectedIndexesCount == 0 )
		return NULL;
		
	// copy the indices and sort numerically
	RArray<TInt>* orderedSelectedIndices = 
		new (ELeave) RArray< TInt >( selectedIndexesCount );
	
	// push the allocated array
	CleanupStack::PushL( orderedSelectedIndices );
	
	// dispose the array resource
	CleanupClosePushL( *orderedSelectedIndices );
	
	// see if the search field is enabled
	CAknListBoxFilterItems* filter = model->Filter();
	if ( filter != NULL )
		{
		// when filtering enabled, translate indices back to underlying model
		for ( TInt idx = 0; idx < selectedIndexesCount; idx++ )
			{
			TInt filteredItem = ( *selectionIndexes ) [ idx ];
			TInt actualItem = filter->FilteredItemIndex ( filteredItem );
			orderedSelectedIndices->InsertInOrder( actualItem );
			}
		}
	else
		{
		// the selection indices refer directly to the model
		for ( TInt idx = 0; idx < selectedIndexesCount; idx++ )
			orderedSelectedIndices->InsertInOrder( ( *selectionIndexes ) [ idx ] );
		}	
		
	return orderedSelectedIndices;
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Delete the selected item or items from the list box.
 */
void CCalendarManagerListBox::DeleteSelectedListBoxItemsL( CEikTextListBox* aListBox )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( aListBox->Model() );
	if ( model->NumberOfItems() == 0 )
		return;
	
	RArray< TInt >* orderedSelectedIndices = GetSelectedListBoxItemsLC( aListBox );		
	if ( !orderedSelectedIndices )
		return;
		
	// Delete selected items from bottom up so indices don't change on us
	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	TInt currentItem = 0;
	
	for ( TInt idx = orderedSelectedIndices->Count(); idx-- > 0; ) 
		{
		currentItem = ( *orderedSelectedIndices )[ idx ];
		itemArray->Delete ( currentItem );
		}
	
	// dispose the array resources
	CleanupStack::PopAndDestroy();
	
	// dispose the array pointer
	CleanupStack::PopAndDestroy( orderedSelectedIndices );
	
	// refresh listbox's cursor now that items are deleted
	AknListBoxUtils::HandleItemRemovalAndPositionHighlightL(
		aListBox, currentItem, ETrue );
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Get the listbox.
 */
CAknSingleHeadingStyleListBox* CCalendarManagerListBox::ListBox()
	{
	return iListBox;
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Create a list box item with the given column values.
 */
void CCalendarManagerListBox::CreateListBoxItemL( TDes& aBuffer, 
		const TDesC& aHeadingText,
		const TDesC& aMainText )
	{
	_LIT ( KStringHeader, "%S\t%S" );
	
	aBuffer.Format( KStringHeader(), &aHeadingText, &aMainText );
	} 
				
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Add an item to the list by reading the text items from the array resource
 *	and setting a single image property (if available) from an index
 *	in the list box's icon array.
 *	@param aResourceId id of an ARRAY resource containing the textual
 *	items in the columns
 *	
 */
void CCalendarManagerListBox::AddListBoxResourceArrayItemL( TInt aResourceId )
	{
	CDesCArray* array = iCoeEnv->ReadDesCArrayResourceL( aResourceId );
	CleanupStack::PushL( array );
	// This is intended to be large enough, but if you get 
	// a USER 11 panic, consider reducing string sizes.
	TBuf<512> listString; 
	CreateListBoxItemL( listString, ( *array ) [ 0 ], ( *array ) [ 1 ] );
	AddListBoxItemL( iListBox, listString );
	CleanupStack::PopAndDestroy( array );
	} 
				
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Set up the list's icon array.
 */
void CCalendarManagerListBox::SetupListBoxIconsL()
	{
	CArrayPtr< CGulIcon >* icons = NULL;		
	
	if ( icons != NULL )
		{
		iListBox->ItemDrawer()->ColumnData()->SetIconArray( icons );
		}
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/** 
 *	Handle commands relating to markable lists.
 */
TBool CCalendarManagerListBox::HandleMarkableListCommandL( TInt aCommand )
	{
	return EFalse;
	}

// ]]] end generated function

/** 
 * Override of the HandleListBoxEventL virtual function
 */
void CCalendarManagerListBox::HandleListBoxEventL( 
		CEikListBox* aListBox,
		TListBoxEvent anEventType )
	{
	for (int i = 0; i < iListBoxEventDispatch.Count(); i++)
		{
		const TListBoxEventDispatch& currEntry = iListBoxEventDispatch[i];
		if ( currEntry.src == aListBox && currEntry.event == anEventType )
			{
			( this->*currEntry.handler )( aListBox, anEventType );
			break;
			}
		}
	}

/** 
 * Helper function to register MEikListBoxObserver event handlers
 */
void CCalendarManagerListBox::AddListBoxEventHandlerL( 
		CEikListBox* aListBox,
		TListBoxEvent anEvent,
		ListBoxEventHandler aHandler )
	{
	TListBoxEventDispatch entry;
	entry.src = aListBox;
	entry.event = anEvent;
	entry.handler = aHandler;
	TInt err = iListBoxEventDispatch.Append( entry );
	User::LeaveIfError( err );
	}

/** 
 * Override of the HandleControlEventL virtual function
 */
void CCalendarManagerListBox::HandleControlEventL( 
		CCoeControl* aControl, 
		TCoeEvent anEventType )
	{
	for (int i = 0; i < iControlEventDispatch.Count(); i++)
		{
		const TControlEventDispatch& currEntry = iControlEventDispatch[i];
		if ( currEntry.src == aControl && currEntry.event == anEventType )
			{
			( this->*currEntry.handler )( aControl, anEventType );
			break;
			}
		}
	}
/** 
 * Helper function to register MCoeControlObserver event handlers
 */
void CCalendarManagerListBox::AddControlEventHandlerL( 
		CCoeControl* aControl, 
		TCoeEvent anEvent, 
		ControlEventHandler aHandler )
	{
	TControlEventDispatch entry;
	entry.src = aControl;
	entry.event = anEvent;
	entry.handler = aHandler;
	TInt err = iControlEventDispatch.Append( entry );
	User::LeaveIfError( err );
	}
			
/** 
 * Handle the EEventStateChanged event.
 */
void CCalendarManagerListBox::HandleListBoxStateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler	
	SetSelectTimeContent();
	SetPanelText();
	}
				
void CCalendarManagerListBox::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerListBox::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;
	}

void CCalendarManagerListBox::SetListBox()
	{
	g_CalendarManagerSchedule = g_CalendarManagerXML->g_ScheduleMap.Find(g_CalendarClass->GetSelectDateEN()->Des());		
	if (g_CalendarManagerSchedule != NULL)
		{		
		TLinearOrder<TReal> i_Order(CompareReal);		
		g_CalendarManagerSchedule->g_Array.Sort(i_Order);		
		for (TInt i = 0;i < g_CalendarManagerSchedule->g_Array.Count();i++)
			{						
			TReal i_Real = g_CalendarManagerSchedule->g_Array[i];
			TBuf<16> i_ScheduleTime;
						
			TRealFormat i_RealFormat;
			i_RealFormat.iWidth = 16;
			
			if (i_Real < 10)
				i_ScheduleTime.Append(_L("0"));
									
			i_ScheduleTime.AppendNum(i_Real, i_RealFormat);
									
			for (TInt i = 0;i < i_ScheduleTime.Length();i++)
				{
				if (i_ScheduleTime.Mid(i, 1).Compare(_L(".")) == 0)					
					i_ScheduleTime.Replace(i, 1, _L(":"));													
				}
			
			if (i_ScheduleTime.Length() < 5)
				i_ScheduleTime.Append(_L("0"));
						
			if (i == 0)				
				g_CalendarManagerXML->g_SelectTime.Copy(i_ScheduleTime);
							
			const CalendarManagerInfo* i_CalendarManagerInfo = g_CalendarManagerSchedule->g_ScheduleMap.Find(i_ScheduleTime);			
			if (i_CalendarManagerInfo != NULL && i_CalendarManagerInfo->g_Schedule_Content != NULL)
				{				
				HBufC* i_ListString = HBufC::NewL(i_ScheduleTime.Length() + i_CalendarManagerInfo->g_Schedule_Content->Length() + 8);
				TPtr i_ListStringPtr(i_ListString->Des());
				i_ListStringPtr.Append(i_ScheduleTime);
				i_ListStringPtr.Append(_L("\t"));
				i_ListStringPtr.Append(i_CalendarManagerInfo->g_Schedule_Content->Des());				
				AddListBoxItemL(iListBox, i_ListString->Des());				
				SafeRelease(i_ListString);
				}					
			}			
		
		SetSelectTimeContent();
		SetPanelText();
		}		
	}

void CCalendarManagerListBox::SetSelectTimeContent()
	{	
	TPtrC i_GetSelectItem = GetSelectItem(0);		
	g_CalendarManagerXML->g_SelectTime.Copy(i_GetSelectItem);
	g_CalendarManagerXML->g_SelectCalendarManagerInfo = g_CalendarManagerSchedule->g_ScheduleMap.Find(g_CalendarManagerXML->g_SelectTime);
	}

TPtrC CCalendarManagerListBox::GetSelectItem(TInt p_Items)
	{	
	CTextListBoxModel* i_ListBoxModel = iListBox->Model();	
	if (i_ListBoxModel->NumberOfItems() <= 0)
		return _L("");
	
	TInt i_Length = i_ListBoxModel->ItemText(iListBox->CurrentItemIndex()).Length();
	TInt i_Pos = i_ListBoxModel->ItemText(iListBox->CurrentItemIndex()).Find(_L("\t"));
		
	if (p_Items == 0)				
		return i_ListBoxModel->ItemText(iListBox->CurrentItemIndex()).Mid(0, i_Pos);		
	else				
		return i_ListBoxModel->ItemText(iListBox->CurrentItemIndex()).Mid(i_Pos , i_Length - i_Pos);							
	}

void CCalendarManagerListBox::SetTitleText()
	{
	CAknViewAppUi* i_AppUi = static_cast<CAknViewAppUi*>(iCoeEnv->AppUi());
	CAknView* i_View = i_AppUi->View(TUid::Uid(ECalendarManagerListBoxViewId));
	i_View->HandleCommandL(ECalendarManagerListBoxView_KeyCommand);	
	}

void CCalendarManagerListBox::SetPanelText()
	{
	CTextListBoxModel* i_ListBoxModel = iListBox->Model();	
	if (i_ListBoxModel->NumberOfItems() <= 0)
		return;
	
	CAknViewAppUi* i_AppUi = static_cast<CAknViewAppUi*>(iCoeEnv->AppUi());
	CAknView* i_View = i_AppUi->View(TUid::Uid(ECalendarManagerListBoxViewId));
	i_View->HandleCommandL(ECalendarManagerListBoxView_Key1Command);
	}
