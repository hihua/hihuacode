/*
 * CalendarManagerSchedule.h
 *
 *  Created on: 2009-10-13
 *      Author: Administrator
 */

#ifndef CALENDARMANAGERSCHEDULE_H_
#define CALENDARMANAGERSCHEDULE_H_

#include <e32base.h>
#include <e32hashtab.h>
#include "CalendarManager.h"
#include "CalendarManagerInfo.h"

class CalendarManagerSchedule
	{
public:
	CalendarManagerSchedule();
	static CalendarManagerSchedule* NewL();
	static CalendarManagerSchedule* NewLC();
	virtual ~CalendarManagerSchedule();
		
public:
	//RHashMap<const HBufC*, const HBufC*> g_ScheduleMap;	
	RPtrHashMap<TDesC, CalendarManagerInfo> g_ScheduleMap;
	RPointerArray<HBufC> g_PointerArray_Key;
	RPointerArray<CalendarManagerInfo> g_PointerArray_Value;	
	RArray<TReal> g_Array;
	};

#endif /* CALENDARMANAGERSCHEDULE_H_ */
