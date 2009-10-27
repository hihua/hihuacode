/*
========================================================================
 Name        : CalendarManagerDocument.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERDOCUMENT_H
#define CALENDARMANAGERDOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	CCalendarManagerDocument CalendarManagerDocument.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class CCalendarManagerDocument : public CAknDocument
	{
public: 
	// constructor
	static CCalendarManagerDocument* NewL( CEikApplication& aApp );

private: 
	// constructors
	CCalendarManagerDocument( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // CALENDARMANAGERDOCUMENT_H
