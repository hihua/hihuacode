/*
 * CalendarManagerSchedule.cpp
 *
 *  Created on: 2009-10-13
 *      Author: Administrator
 */

#include "../inc/CalendarManagerSchedule.h"

CalendarManagerSchedule::CalendarManagerSchedule()
	{
	// TODO Auto-generated constructor stub

	}

CalendarManagerSchedule::~CalendarManagerSchedule()
	{
	// TODO Auto-generated destructor stub
	for (TInt i = 0;i < g_PointerArray_Key.Count();i++)
		{
		SafeRelease(g_PointerArray_Key[i]);
		SafeRelease(g_PointerArray_Value[i]);
		}		
	
	g_Array.Close();
	g_ScheduleMap.Close();	
	g_PointerArray_Key.Close();
	g_PointerArray_Value.Close();	
	}

CalendarManagerSchedule* CalendarManagerSchedule::NewL()
	{	
	CalendarManagerSchedule* self = CalendarManagerSchedule::NewLC();
	CleanupStack::Pop(self);
	return self;
	}

CalendarManagerSchedule* CalendarManagerSchedule::NewLC()
	{	
	CalendarManagerSchedule* self = new ( ELeave ) CalendarManagerSchedule();	
	CleanupStack::PushL( self );	
	return self;
	}

