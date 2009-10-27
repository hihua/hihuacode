/*
========================================================================
 Name        : CalendarManagerAppUi.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERAPPUI_H
#define CALENDARMANAGERAPPUI_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknviewappui.h>
// ]]] end generated region [Generated Includes]
#include <bautils.h>
#include <eikapp.h>
#include "CalendarManager.h"
#include "CalendarClass.h"
#include "CalendarManagerXML.h"

#include <libc\stdio.h>
#include <libc\stdlib.h>
// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CCalendarManagerContainerView;
class CCalendarManagerListBoxView;
class CCalendarManagerSettingItemListView;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	CCalendarManagerAppUi CalendarManagerAppUi.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *        view management and the default menu, control pane, and status pane.
 */
class CCalendarManagerAppUi : public CAknViewAppUi
	{
public: 
	// constructor and destructor
	CCalendarManagerAppUi();
	virtual ~CCalendarManagerAppUi();
	void ConstructL();

public:
	// from CCoeAppUi
	TKeyResponse HandleKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType );

	// from CEikAppUi
	void HandleCommandL( TInt aCommand );
	void HandleResourceChangeL( TInt aType );

	// from CAknAppUi
	void HandleViewDeactivation( const TVwsViewId& aViewIdToBeDeactivated, const TVwsViewId& aNewlyActivatedViewId );

private:
	void InitializeContainersL();
	void GetXMLFile();
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CCalendarManagerContainerView* iCalendarManagerContainerView;
	CCalendarManagerListBoxView* iCalendarManagerListBoxView;
	CCalendarManagerSettingItemListView* iCalendarManagerSettingItemListView;
	// ]]] end generated region [Generated Instance Variables]
	CalendarClass* g_CalendarClass;
	CalendarManagerXML* g_CalendarManagerXML;
	CalendarManagerXML o_CalendarManagerXML;
	HBufC8* g_ScheduleFilePath;
	
	RFs g_Fs;		
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
	};

#endif // CALENDARMANAGERAPPUI_H			
