/*
========================================================================
 Name        : CalendarManagerApplication.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERAPPLICATION_H
#define CALENDARMANAGERAPPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidCalendarManagerApplication = { 0xEF74AC8F };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	CCalendarManagerApplication CalendarManagerApplication.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class CCalendarManagerApplication : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // CALENDARMANAGERAPPLICATION_H		
