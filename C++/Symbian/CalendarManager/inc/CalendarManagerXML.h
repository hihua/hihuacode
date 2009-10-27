/*
 * CalendarManagerXML.h
 *
 *  Created on: 2009-10-13
 *      Author: Administrator
 */

#ifndef CALENDARMANAGERXML_H_
#define CALENDARMANAGERXML_H_

#include <e32base.h>
#include <e32hashtab.h>
#include <e32cmn.h>
#include <utf.h>
#include <string.h>
#include <f32file.h>

#include "CalendarManager.h"
#include "CalendarManagerSchedule.h"
#include "CalendarManagerInfo.h"
#include "XML\tinyxml.h"

#include <libc\ctype.h>
#include <libc\stdio.h>
#include <libc\stdlib.h>
#include <libc\string.h>
#include <libc\reent.h>
#include <libc\sys\file.h>

class CalendarManagerXML
	{	
public:
	CalendarManagerXML();
	static CalendarManagerXML* NewL(RFs& p_Fs, const TDes8& p_FilePath);
	static CalendarManagerXML* NewLC(RFs& p_Fs, const TDes8& p_FilePath);
	void ConstructL(RFs& p_Fs, const TDes8& p_FilePath);
	virtual ~CalendarManagerXML();
		
public:
	RPtrHashMap<TDesC, CalendarManagerSchedule> g_ScheduleMap;
	TBuf<20> g_SelectTime;
	CalendarManagerInfo* g_SelectCalendarManagerInfo;
	TiXmlDocument g_XMLDocument;
	char* g_FileName;
		
private:
	RPointerArray<HBufC> g_PointerArray_Key;
	RPointerArray<CalendarManagerSchedule> g_PointerArray_Value;
		
	};

#endif /* CALENDARMANAGERXML_H_ */
