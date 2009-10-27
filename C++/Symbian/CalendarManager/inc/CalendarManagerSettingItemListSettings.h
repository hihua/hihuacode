/*
========================================================================
 Name        : CalendarManagerSettingItemListSettings.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERSETTINGITEMLISTSETTINGS_H
#define CALENDARMANAGERSETTINGITEMLISTSETTINGS_H
			
// [[[ begin generated region: do not modify [Generated Includes]
#include <e32std.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const int KSchedule_ContentMaxLength = 255;
// ]]] end generated region [Generated Constants]

/**
 * @class	TCalendarManagerSettingItemListSettings CalendarManagerSettingItemListSettings.h
 */
class TCalendarManagerSettingItemListSettings
	{
public:
	// construct and destroy
	static TCalendarManagerSettingItemListSettings* NewL();
	void ConstructL();
		
private:
	// constructor
	TCalendarManagerSettingItemListSettings();
	// [[[ begin generated region: do not modify [Generated Accessors]
public:
	TTime& Schedule_Date();
	void SetSchedule_Date(const TTime& aValue);
	TTime& Schedule_Time();
	void SetSchedule_Time(const TTime& aValue);
	TDes& Schedule_Content();
	void SetSchedule_Content(const TDesC& aValue);
	TInt& Schedule_Reminder();
	void SetSchedule_Reminder(const TInt& aValue);
	// ]]] end generated region [Generated Accessors]
	
	// [[[ begin generated region: do not modify [Generated Members]
protected:
	TTime iSchedule_Date;
	TTime iSchedule_Time;
	TBuf<KSchedule_ContentMaxLength> iSchedule_Content;
	TInt iSchedule_Reminder;
	// ]]] end generated region [Generated Members]
	
	};
#endif // CALENDARMANAGERSETTINGITEMLISTSETTINGS_H
