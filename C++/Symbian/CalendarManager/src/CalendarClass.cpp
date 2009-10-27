/*
 * CalendarClass.cpp
 *
 *  Created on: 2009-9-27
 *      Author: Administrator
 */

#include "CalendarClass.h"

CalendarClass::CalendarClass()
	{
	// TODO Auto-generated constructor stub

	}

CalendarClass::~CalendarClass()
	{
	// TODO Auto-generated destructor stub
	SafeRelease(g_SelectDate_EN);
	SafeRelease(g_SelectDate_CN);
	SafeRelease(g_SelectMonth_CN);
	}

CalendarClass* CalendarClass::NewL()
	{	
	CalendarClass* self = CalendarClass::NewLC();
	CleanupStack::Pop(self);
	return self;
	}

CalendarClass* CalendarClass::NewLC()
	{	
	CalendarClass* self = new (ELeave) CalendarClass();
	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
	}

void CalendarClass::ConstructL()
	{	
	g_SelectDate_EN = HBufC::NewL(20);		
	g_SelectDate_CN = HBufC::NewL(20);		
	g_SelectMonth_CN = HBufC::NewL(10);
		
	GetDate();
	g_SelectYear = g_Year;
	g_SelectMonth = g_Month;
	g_SelectDay = g_Day;
	GetFirstWeek();
	GetMaxDay();	
	GetWeek();
	}

void CalendarClass::GetDate()
	{
	TTime iTime;
	iTime.HomeTime();	
	TDateTime iDate = iTime.DateTime();
	
	g_Year = iDate.Year();	
	g_Month = iDate.Month() + 1;
	g_Day = iDate.Day() + 1;
	}

void CalendarClass::GetFirstWeek()
	{
	g_FirstWeek = GetFirstWeek(g_SelectYear, g_SelectMonth, 1);
	}

TInt CalendarClass::GetFirstWeek(TInt p_Year, TInt p_Month, TInt p_Day)
	{
	TInt i_Year = p_Year;
	TInt i_Month = p_Month;
	TInt i_Day = p_Day;
	
	if (i_Month < 3)
		{
		i_Month += 12;
		if (IsLeapYear(i_Year))			
			i_Day--;			
		}
	else		
		i_Day += 1;		
	
	TInt i_FirstWeek = (i_Day + 2 * i_Month + 3 * (i_Month + 1) / 5 + i_Year + i_Year / 4 - i_Year / 100 + i_Year / 400) % 7;
	return i_FirstWeek;
	}

TBool CalendarClass::IsLeapYear(TInt p_Year)
	{
	if (p_Year % 400 == 0 || p_Year % 100 != 0 && p_Year % 4 == 0)
		return ETrue;
	else
		return EFalse;
	}

void CalendarClass::GetMaxDay()
	{
	switch (g_SelectMonth)
		{
		case 1:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 2:
			if (IsLeapYear(g_SelectYear))
				g_MaxDay = 29;
			else
				g_MaxDay = 28;
			
			g_MaxDay_PreMonth = 31;
			break;
			
		case 3:
			g_MaxDay = 31;
			
			if (IsLeapYear(g_SelectYear))
				g_MaxDay_PreMonth = 29;
			else
				g_MaxDay_PreMonth = 28;
			break;
			
		case 4:
			g_MaxDay = 30;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 5:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 30;
			break;
			
		case 6:
			g_MaxDay = 30;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 7:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 30;
			break;
			
		case 8:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 9:
			g_MaxDay = 30;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 10:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 30;
			break;
			
		case 11:
			g_MaxDay = 30;
			g_MaxDay_PreMonth = 31;
			break;
			
		case 12:
			g_MaxDay = 31;
			g_MaxDay_PreMonth = 30;
			break;
		}		
	}

void CalendarClass::GetWeek()
	{
	TInt MonthDay[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	if (g_SelectMonth == 1)
		{
		for (TInt i = 0;i < 6;i++)
			{
			g_Week[i] = i + 1; 
			}
		
		return;
		}
	
	TInt i_TotalDay = 0;
	for (TInt i = 0;i < g_SelectMonth - 1;i++)
		{
		i_TotalDay += MonthDay[i];
		}
	
	if (IsLeapYear(g_SelectYear))
		i_TotalDay++;
	
	TInt i_WeekPos = GetFirstWeek(g_SelectYear, 1, 1); 
	i_WeekPos = i_WeekPos % 7;
	
	i_TotalDay += i_WeekPos;
	g_Week[0] = i_TotalDay / 7 + 1;
	
	for (TInt i = 1;i < 6;i++)
		{
		g_Week[i] = g_Week[0] + i;
		}
	}

HBufC* CalendarClass::GetSelectDateEN()
	{	
	TPtr i_SelectDatePtr(g_SelectDate_EN->Des());
	i_SelectDatePtr.Zero();
	
	i_SelectDatePtr.AppendNum(g_SelectYear);
	i_SelectDatePtr.Append(_L("-"));
	i_SelectDatePtr.AppendNum(g_SelectMonth);
	i_SelectDatePtr.Append(_L("-"));
	i_SelectDatePtr.AppendNum(g_SelectDay);
		
	return g_SelectDate_EN;
	}

HBufC* CalendarClass::GetSelectDateCN()
	{		
	TPtr i_SelectDatePtr(g_SelectDate_CN->Des());
	i_SelectDatePtr.Zero();
	
	i_SelectDatePtr.AppendNum(g_SelectYear);
	i_SelectDatePtr.Append(_L("\x5E74"));
	i_SelectDatePtr.AppendNum(g_SelectMonth);
	i_SelectDatePtr.Append(_L("\x6708"));
	i_SelectDatePtr.AppendNum(g_SelectDay);
	i_SelectDatePtr.Append(_L("\x65E5"));
	
	return g_SelectDate_CN;
	}

HBufC* CalendarClass::GetSelectMonthCN()
	{	
	TPtr i_SelectMonthPtr(g_SelectMonth_CN->Des());
	i_SelectMonthPtr.Zero();
			
	switch (g_SelectMonth)
		{
		case 1:
			i_SelectMonthPtr.Append(_L("\x4E00"));
			break;
			
		case 2:
			i_SelectMonthPtr.Append(_L("\x4E8C"));
			break;
			
		case 3:
			i_SelectMonthPtr.Append(_L("\x4E09"));
			break;
			
		case 4:
			i_SelectMonthPtr.Append(_L("\x56DB"));
			break;
			
		case 5:
			i_SelectMonthPtr.Append(_L("\x4E94"));
			break;
			
		case 6:
			i_SelectMonthPtr.Append(_L("\x516D"));
			break;
			
		case 7:
			i_SelectMonthPtr.Append(_L("\x4E03"));
			break;
			
		case 8:
			i_SelectMonthPtr.Append(_L("\x516B"));
			break;
			
		case 9:
			i_SelectMonthPtr.Append(_L("\x4E5D"));
			break;
						
		case 10:
			i_SelectMonthPtr.Append(_L("\x5341"));
			break;
			
		case 11:
			i_SelectMonthPtr.Append(_L("\x5341\x4E00"));
			break;
			
		case 12:
			i_SelectMonthPtr.Append(_L("\x5341\x4E8C"));
			break;
		}	
		
	i_SelectMonthPtr.Append(_L("\x6708"));
			
	return g_SelectMonth_CN;
	}
