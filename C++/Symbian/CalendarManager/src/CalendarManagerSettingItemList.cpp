/*
========================================================================
 Name        : CalendarManagerSettingItemList.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <avkon.hrh>
#include <avkon.rsg>
#include <eikmenup.h>
#include <aknappui.h>
#include <eikcmobs.h>
#include <barsread.h>
#include <stringloader.h>
#include <gdi.h>
#include <eikmfne.h>
#include <eikenv.h>
#include <eikedwin.h>
#include <aknpopupfieldtext.h>
#include <eikappui.h>
#include <aknviewappui.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "CalendarManagerSettingItemList.h"
#include "CalendarManagerSettingItemListSettings.h"
#include "CalendarManager.hrh"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
#include "CalendarManagerSettingItemListView.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Construct the CCalendarManagerSettingItemList instance
 * @param aCommandObserver command observer
 */ 
CCalendarManagerSettingItemList::CCalendarManagerSettingItemList( TCalendarManagerSettingItemListSettings& aSettings, MEikCommandObserver* aCommandObserver ) : iSettings( aSettings ), iCommandObserver( aCommandObserver )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
/** 
 * Destroy any instance variables
 */
CCalendarManagerSettingItemList::~CCalendarManagerSettingItemList()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Handle system notification that the container's size has changed.
 */
void CCalendarManagerSettingItemList::SizeChanged()
	{
	if ( ListBox() ) 
		{
		ListBox()->SetRect( Rect() );
		}
	
	CAknViewAppUi* i_AppUi = static_cast<CAknViewAppUi*>(iCoeEnv->AppUi());
	CAknView* i_View = i_AppUi->View(TUid::Uid(ECalendarManagerSettingItemListViewId));
	i_View->HandleCommandL(ECalendarManagerSettingItemListView_KeyCommand);	
	}

/**
 * Create one setting item at a time, identified by id.
 * CAknSettingItemList calls this method and takes ownership
 * of the returned value.  The CAknSettingItem object owns
 * a reference to the underlying data, which EditItemL() uses
 * to edit and store the value.
 */
CAknSettingItem* CCalendarManagerSettingItemList::CreateSettingItemL( TInt aId )
	{
	switch ( aId )
		{
	// [[[ begin generated region: do not modify [Initializers]
		case ECalendarManagerSettingItemListViewSchedule_Date:
			{			
			CAknTimeOrDateSettingItem* item = new ( ELeave ) 
				CAknTimeOrDateSettingItem( 
					aId,
					CAknTimeOrDateSettingItem::EDate,
					iSettings.Schedule_Date() );			
			return item;
			}
		case ECalendarManagerSettingItemListViewSchedule_Time:
			{			
			CAknTimeOrDateSettingItem* item = new ( ELeave ) 
				CAknTimeOrDateSettingItem( 
					aId,
					CAknTimeOrDateSettingItem::ETime,
					iSettings.Schedule_Time() );
			return item;
			}
		case ECalendarManagerSettingItemListViewSchedule_Content:
			{			
			CAknTextSettingItem* item = new ( ELeave ) 
				CAknTextSettingItem( 
					aId,
					iSettings.Schedule_Content() );
			return item;
			}
		case ECalendarManagerSettingItemListViewSchedule_Reminder:
			{			
			CAknEnumeratedTextPopupSettingItem* item = new ( ELeave ) 
				CAknEnumeratedTextPopupSettingItem( 
					aId,
					iSettings.Schedule_Reminder() );
			return item;
			}
	// ]]] end generated region [Initializers]
	
		}
		
	return NULL;
	}
	
/**
 * Edit the setting item identified by the given id and store
 * the changes into the store.
 * @param aIndex the index of the setting item in SettingItemArray()
 * @param aCalledFromMenu true: a menu item invoked editing, thus
 *	always show the edit page and interactively edit the item;
 *	false: change the item in place if possible, else show the edit page
 */
void CCalendarManagerSettingItemList::EditItemL ( TInt aIndex, TBool aCalledFromMenu )
	{
	aIndex = SettingItemArray()->ItemIndexFromVisibleIndex(aIndex);	
	CAknSettingItem* item = ( *SettingItemArray() )[aIndex];
	switch ( item->Identifier() )
		{
	// [[[ begin generated region: do not modify [Editing Started Invoker]
	// ]]] end generated region [Editing Started Invoker]
	
		}
	
	CAknSettingItemList::EditItemL( aIndex, aCalledFromMenu );
	
	TBool storeValue = ETrue;
	switch ( item->Identifier() )
		{
	// [[[ begin generated region: do not modify [Editing Stopped Invoker]
	// ]]] end generated region [Editing Stopped Invoker]
	
		}
		
	if ( storeValue )
		{
		item->StoreL();
		SaveSettingValuesL();
		}	
	}
/**
 *	Handle the "Change" option on the Options menu.  This is an
 *	alternative to the Selection key that forces the settings page
 *	to come up rather than changing the value in place (if possible).
 */
void CCalendarManagerSettingItemList::ChangeSelectedItemL()
	{
	if ( ListBox()->CurrentItemIndex() >= 0 )
		{
		EditItemL( ListBox()->CurrentItemIndex(), ETrue );		
		}
	}

/**
 *	Load the initial contents of the setting items.  By default,
 *	the setting items are populated with the default values from
 * 	the design.  You can override those values here.
 *	<p>
 *	Note: this call alone does not update the UI.  
 *	LoadSettingsL() must be called afterwards.
 */
void CCalendarManagerSettingItemList::LoadSettingValuesL()
	{
	// load values into iSettings	
	}
	
/**
 *	Save the contents of the setting items.  Note, this is called
 *	whenever an item is changed and stored to the model, so it
 *	may be called multiple times or not at all.
 */
void CCalendarManagerSettingItemList::SaveSettingValuesL()
	{
	// store values from iSettings
	}


/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CCalendarManagerSettingItemList::HandleResourceChange( TInt aType )
	{
	CAknSettingItemList::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( ECalendarManagerSettingItemListViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle key event (override)
 * @param aKeyEvent key event
 * @param aType event code
 * @return EKeyWasConsumed if the event was handled, else EKeyWasNotConsumed
 */
TKeyResponse CCalendarManagerSettingItemList::OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	if ( aKeyEvent.iCode == EKeyLeftArrow || aKeyEvent.iCode == EKeyRightArrow )
		{
		// allow the tab control to get the arrow keys
		return EKeyWasNotConsumed;
		}
	
	return CAknSettingItemList::OfferKeyEventL( aKeyEvent, aType );
	}

void CCalendarManagerSettingItemList::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerSettingItemList::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;
	}

void CCalendarManagerSettingItemList::SetDefaultSetting()
	{
	TTime i_Date;
	i_Date.Parse(g_CalendarClass->GetSelectDateEN()->Des());
	iSettings.SetSchedule_Date(i_Date);
	
	if (g_CalendarManagerXML->g_SelectTime.Length() > 0)
		{
		(*SettingItemArray())[0]->SetHidden(ETrue);
																
		TTime i_Time;
		i_Time.Parse(g_CalendarManagerXML->g_SelectTime);
				
		iSettings.SetSchedule_Time(i_Time);
		
		if (g_CalendarManagerXML->g_SelectCalendarManagerInfo != NULL)
			{
			iSettings.SetSchedule_Content(g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Content->Des());
			iSettings.SetSchedule_Reminder(g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Reminder);
			}				
		}	
	}

void CCalendarManagerSettingItemList::SaveSetting()
	{
	if (g_CalendarManagerXML->g_SelectTime.Length() > 0)
		{
		if (g_CalendarManagerXML->g_SelectCalendarManagerInfo != NULL)
			{
			TTime i_Time = iSettings.Schedule_Time();			
			TInt i_Reminder = iSettings.Schedule_Reminder();
						
			//时间
			TBuf<sizeof(g_CalendarManagerXML->g_SelectTime)> Schedule_Time;
			_LIT(KTimeFormat, "%H:%T");
			i_Time.FormatL(Schedule_Time, KTimeFormat);
						
			HBufC8* i_Schedule_Time = HBufC8::NewL(sizeof(Schedule_Time));
			i_Schedule_Time->Des().SetLength(sizeof(Schedule_Time));
			i_Schedule_Time->Des().FillZ();
			i_Schedule_Time->Des().Zero();
			
			TPtr8 i_Schedule_TimePtr(i_Schedule_Time->Des());						
			CnvUtfConverter::ConvertFromUnicodeToUtf8(i_Schedule_TimePtr, Schedule_Time);
						
			//内容
			HBufC8* i_Schedule_Content = HBufC8::NewL(iSettings.Schedule_Content().Length() * 3 + 1);
			i_Schedule_Content->Des().SetLength(iSettings.Schedule_Content().Length() * 3 + 1);
			i_Schedule_Content->Des().FillZ();
			i_Schedule_Content->Des().Zero();
						
			TPtr8 i_Schedule_ContentPtr(i_Schedule_Content->Des());
			CnvUtfConverter::ConvertFromUnicodeToUtf8(i_Schedule_ContentPtr, iSettings.Schedule_Content());
			
			g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_XMLElement->SetAttribute("Schedule_Time", (char *)i_Schedule_Time->Ptr());
			g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_XMLElement->SetAttribute("Schedule_Reminder", i_Reminder);
			g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_XMLElement->LastChild()->SetValue((char *)i_Schedule_Content->Ptr());			
			
			g_CalendarManagerXML->g_XMLDocument.SaveFile(g_CalendarManagerXML->g_FileName);
			SaveSchedule(i_Schedule_Time, i_Schedule_Content, i_Reminder);
			
			SafeRelease(i_Schedule_Time);
			SafeRelease(i_Schedule_Content);
			}				
		}
	else
		{
		
		}
	}

void CCalendarManagerSettingItemList::SaveSchedule(HBufC8* p_Schedule_Time, HBufC8* p_Schedule_Content, TInt p_Schedule_Reminder)
	{
	TReal i_Time;		
	for (TInt i = 0;i < g_CalendarManagerXML->g_SelectTime.Length();i++)
		{
		if (g_CalendarManagerXML->g_SelectTime.Mid(i, 1).Compare(_L(":")) == 0)
			{
			g_CalendarManagerXML->g_SelectTime.Replace(i, 1, _L("."));
			TLex i_Lex(g_CalendarManagerXML->g_SelectTime);			
			i_Lex.Val(i_Time);						
			g_CalendarManagerXML->g_SelectTime.Replace(i, 1, _L(":"));
			break;
			}						
		}
			
	delete g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Content;
	
	HBufC* i_Schedule_Time = CnvUtfConverter::ConvertToUnicodeFromUtf8L(p_Schedule_Time->Des());
	HBufC* i_Schedule_Content = CnvUtfConverter::ConvertToUnicodeFromUtf8L(p_Schedule_Content->Des());
	
	g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Content = i_Schedule_Content;
	g_CalendarManagerXML->g_SelectCalendarManagerInfo->g_Schedule_Reminder = p_Schedule_Reminder;
	
	TReal o_Time;
	for (TInt i = 0;i < i_Schedule_Time->Des().Length();i++)
		{
		if (i_Schedule_Time->Des().Mid(i, 1).Compare(_L(":")) == 0)
			{
			i_Schedule_Time->Des().Replace(i, 1, _L("."));
			TLex i_Lex(i_Schedule_Time->Des());			
			i_Lex.Val(o_Time);						
			i_Schedule_Time->Des().Replace(i, 1, _L(":"));
			break;
			}						
		}
				
	CalendarManagerSchedule* i_CalendarManagerSchedule = g_CalendarManagerXML->g_ScheduleMap.Find(g_CalendarClass->GetSelectDateEN()->Des());
	if (i_CalendarManagerSchedule != NULL)
		{
		for (TInt i = 0;i < i_CalendarManagerSchedule->g_PointerArray_Key.Count();i++)
			{
			if (i_CalendarManagerSchedule->g_PointerArray_Key[i] != NULL && i_CalendarManagerSchedule->g_PointerArray_Key[i]->Des() == i_Schedule_Time->Des())
				{
				TPtr i_Schedule_TimePtr(i_CalendarManagerSchedule->g_PointerArray_Key[i]->Des());
				i_Schedule_TimePtr.Copy(i_Schedule_Time->Des());				
				}
			}
		
		for (TInt i = 0;i < i_CalendarManagerSchedule->g_Array.Count();i++)
			{
			TReal i_Real = i_CalendarManagerSchedule->g_Array[i];
			if (i_Real == i_Time)				
				{
				i_CalendarManagerSchedule->g_Array[i] = o_Time;
				break;
				}
			}
						
		RPtrHashMap<TDesC, CalendarManagerInfo>::TIter i_ScheduleMapIter(i_CalendarManagerSchedule->g_ScheduleMap);
		for ( ; ; )
			{
			const TDesC* i_Time = i_ScheduleMapIter.NextKey();
			if (i_Time != NULL)
				{
				if ((*i_Time).Compare(g_CalendarManagerXML->g_SelectTime) == 0)
					{										
					break;
					}
				}
			}		
		}
	
	g_CalendarManagerXML->g_SelectTime.Copy(i_Schedule_Time->Des());
	SafeRelease(i_Schedule_Time);
	}
				
