/*
 * CalendarManagerInfo.h
 *
 *  Created on: 2009-10-19
 *      Author: Administrator
 */

#include <e32base.h>
#include <utf.h>
#include <string.h>
#include "CalendarManager.h"
#include "CalendarManagerSchedule.h"
#include "XML\tinyxml.h"

#ifndef CALENDARMANAGERINFO_H_
#define CALENDARMANAGERINFO_H_

class CalendarManagerInfo
	{
public:
	CalendarManagerInfo();
	static CalendarManagerInfo* NewL();
	static CalendarManagerInfo* NewLC();		
	virtual ~CalendarManagerInfo();
	
	TInt g_Schedule_Reminder;
	HBufC* g_Schedule_Content;
	TiXmlElement* g_XMLElement;	
	};

#endif /* CALENDARMANAGERINFO_H_ */
