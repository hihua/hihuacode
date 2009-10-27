/*
========================================================================
 Name        : CalendarManagerListBoxView.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERLISTBOXVIEW_H
#define CALENDARMANAGERLISTBOXVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
// ]]] end generated region [Generated Includes]
#include "CalendarClass.h"
#include "CalendarManagerXML.h"
#include "CalendarManagerSchedule.h"
// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CAknNavigationDecorator;
class CCalendarManagerListBox;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for CalendarManagerListBoxView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CCalendarManagerListBoxView CalendarManagerListBoxView.h
 */						
			
class CCalendarManagerListBoxView : public CAknView
	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	CCalendarManagerListBoxView();
	static CCalendarManagerListBoxView* NewL();
	static CCalendarManagerListBoxView* NewLC();        
	void ConstructL();
	virtual ~CCalendarManagerListBoxView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	CCalendarManagerListBox* CreateContainerL();
	// ]]] end generated region [Generated Methods]
	void SetCalendarClass(CalendarClass* p_CalendarClass);
	void SetXMLClass(CalendarManagerXML* p_CalendarManagerXML);
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	// from base class CAknView
	void DoActivateL(
		const TVwsViewId& aPrevViewId,
		TUid aCustomMessageId,
		const TDesC8& aCustomMessage );
	void DoDeactivate();
	void HandleStatusPaneSizeChange();
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	TBool HandleControlPaneRightSoftKeyPressedL( TInt aCommand );
	TBool HandleControlPaneMiddleSoftKeyPressedL( TInt aCommand );
	TBool Handle_MenuItemSelectedL( TInt aCommand );
	TBool Handle_MenuItem1SelectedL( TInt aCommand );
	TBool Handle_MenuItem2SelectedL( TInt aCommand );
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	void SetTitleText();
	void SetPanelText();
	TPtrC GetReminderText();
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	// any current navi decorator
	CAknNavigationDecorator* iNaviDecorator_;
	CCalendarManagerListBox* iCalendarManagerListBox;
	// ]]] end generated region [Generated Instance Variables]
	CalendarClass* g_CalendarClass;
	CalendarManagerXML* g_CalendarManagerXML;
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // CALENDARMANAGERLISTBOXVIEW_H
