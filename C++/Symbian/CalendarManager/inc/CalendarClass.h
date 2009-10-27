/*
 * CalendarClass.h
 *
 *  Created on: 2009-9-27
 *      Author: Administrator
 */

#include <e32base.h>
#include "CalendarManager.h"

#ifndef CALENDARCLASS_H_
#define CALENDARCLASS_H_

class CalendarClass
	{
public:
	CalendarClass();
	static CalendarClass* NewL();
	static CalendarClass* NewLC();
	void ConstructL();
	virtual ~CalendarClass();
	
public:
	void GetDate();
	void GetFirstWeek();	
	void GetMaxDay();
	void GetWeek();
	HBufC* GetSelectDateEN();
	HBufC* GetSelectDateCN();
	HBufC* GetSelectMonthCN();
	TBool IsLeapYear(TInt p_Year);
	
public:
	TInt g_Year;
	TInt g_Month;
	TInt g_Day;
	TInt g_SelectYear;
	TInt g_SelectMonth;
	TInt g_SelectDay;
	TInt g_FirstWeek;
	TInt g_MaxDay;
	TInt g_MaxDay_PreMonth;
	TInt g_Week[6];
	
private:
	TInt GetFirstWeek(TInt p_Year, TInt p_Month, TInt p_Day);
	
private:
	HBufC* g_SelectDate_EN;
	HBufC* g_SelectDate_CN;
	HBufC* g_SelectMonth_CN;
	};

#endif /* CALENDARCLASS_H_ */
