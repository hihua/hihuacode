/*
========================================================================
 Name        : CalendarManagerSettingItemListSettings.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
/**
 *	Generated helper class which manages the settings contained 
 *	in 'CalendarManagerSettingItemList'.  Each CAknSettingItem maintains
 *	a reference to data in this class so that changes in the setting
 *	item list can be synchronized with this storage.
 */
 
// [[[ begin generated region: do not modify [Generated Includes]
#include <e32base.h>
#include <stringloader.h>
#include <barsread.h>
#include <CalendarManager.rsg>
#include "CalendarManagerSettingItemListSettings.h"
// ]]] end generated region [Generated Includes]

/**
 * C/C++ constructor for settings data, cannot throw
 */
TCalendarManagerSettingItemListSettings::TCalendarManagerSettingItemListSettings()
	{
	}

/**
 * Two-phase constructor for settings data
 */
TCalendarManagerSettingItemListSettings* TCalendarManagerSettingItemListSettings::NewL()
	{
	TCalendarManagerSettingItemListSettings* data = new( ELeave ) TCalendarManagerSettingItemListSettings;
	CleanupStack::PushL( data );
	data->ConstructL();
	CleanupStack::Pop( data );
	return data;
	}
	
/**
 *	Second phase for initializing settings data
 */
void TCalendarManagerSettingItemListSettings::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Initializers]
	SetSchedule_Date( TTime( TDateTime( 2000, EJanuary, 0, 0, 0, 0, 0 ) ) );
	SetSchedule_Time( TTime( TDateTime( 0, EJanuary, 0, 0, 0, 0, 0 ) ) );
		{
		HBufC* text = StringLoader::LoadLC( R_CALENDAR_MANAGER_SETTING_ITEM_LIST_SCHEDULE_CONTENT );
		SetSchedule_Content( text->Des() );
		CleanupStack::PopAndDestroy( text );
		}
	SetSchedule_Reminder( 0 );
	// ]]] end generated region [Generated Initializers]
	
	}
	
// [[[ begin generated region: do not modify [Generated Contents]
TTime& TCalendarManagerSettingItemListSettings::Schedule_Date()
	{
	return iSchedule_Date;
	}

void TCalendarManagerSettingItemListSettings::SetSchedule_Date(const TTime& aValue)
	{
	iSchedule_Date = aValue;
	}

TTime& TCalendarManagerSettingItemListSettings::Schedule_Time()
	{
	return iSchedule_Time;
	}

void TCalendarManagerSettingItemListSettings::SetSchedule_Time(const TTime& aValue)
	{
	iSchedule_Time = aValue;
	}

TDes& TCalendarManagerSettingItemListSettings::Schedule_Content()
	{
	return iSchedule_Content;
	}

void TCalendarManagerSettingItemListSettings::SetSchedule_Content(const TDesC& aValue)
	{
	if ( aValue.Length() < KSchedule_ContentMaxLength)
		iSchedule_Content.Copy( aValue );
	else
		iSchedule_Content.Copy( aValue.Ptr(), KSchedule_ContentMaxLength);
	}

TInt& TCalendarManagerSettingItemListSettings::Schedule_Reminder()
	{
	return iSchedule_Reminder;
	}

void TCalendarManagerSettingItemListSettings::SetSchedule_Reminder(const TInt& aValue)
	{
	iSchedule_Reminder = aValue;
	}

// ]]] end generated region [Generated Contents]

