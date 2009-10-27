/*
 * CalendarManagerInfo.cpp
 *
 *  Created on: 2009-10-19
 *      Author: Administrator
 */

#include "../inc/CalendarManagerInfo.h"

CalendarManagerInfo::CalendarManagerInfo()
	{
	// TODO Auto-generated constructor stub
	g_Schedule_Content = NULL;
	}

CalendarManagerInfo::~CalendarManagerInfo()
	{
	// TODO Auto-generated destructor stub
	SafeRelease(g_Schedule_Content);
	}

CalendarManagerInfo* CalendarManagerInfo::NewL()
	{	
	CalendarManagerInfo* self = CalendarManagerInfo::NewLC();
	CleanupStack::Pop(self);
	return self;
	}

CalendarManagerInfo* CalendarManagerInfo::NewLC()
	{	
	CalendarManagerInfo* self = new ( ELeave ) CalendarManagerInfo();	
	CleanupStack::PushL( self );	
	return self;
	}
