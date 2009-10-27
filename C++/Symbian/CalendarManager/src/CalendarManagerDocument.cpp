/*
========================================================================
 Name        : CalendarManagerDocument.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated User Includes]
#include "CalendarManagerDocument.h"
#include "CalendarManagerAppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
CCalendarManagerDocument::CCalendarManagerDocument( CEikApplication& anApplication )
	: CAknDocument( anApplication )
	{
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void CCalendarManagerDocument::ConstructL()
	{
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of CCalendarManagerDocument, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new CCalendarManagerDocument
 */
CCalendarManagerDocument* CCalendarManagerDocument::NewL( CEikApplication& aApp )
	{
	CCalendarManagerDocument* self = new ( ELeave ) CCalendarManagerDocument( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* CCalendarManagerDocument::CreateAppUiL()
	{
	return new ( ELeave ) CCalendarManagerAppUi;
	}
				
