/*
========================================================================
 Name        : CalendarManagerContainerView.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERCONTAINERVIEW_H
#define CALENDARMANAGERCONTAINERVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
// ]]] end generated region [Generated Includes]
#include "CalendarClass.h"
#include "CalendarManagerXML.h"
// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CAknNavigationDecorator;
class CCalendarManagerContainer;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for CalendarManagerContainerView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CCalendarManagerContainerView CalendarManagerContainerView.h
 */						
			
class CCalendarManagerContainerView : public CAknView
	{		
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	CCalendarManagerContainerView();
	static CCalendarManagerContainerView* NewL();
	static CCalendarManagerContainerView* NewLC();        
	void ConstructL();
	virtual ~CCalendarManagerContainerView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	CCalendarManagerContainer* CreateContainerL();
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
	TBool HandleControlPaneMiddleSoftKeyPressedL( TInt aCommand );
	TBool Handle_MenuItemSelectedL( TInt aCommand );
	TBool Handle_MenuItem1SelectedL( TInt aCommand );
	TBool Handle_MenuItem2SelectedL( TInt aCommand );
	TBool Handle_MenuItem3SelectedL( TInt aCommand );
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	void SetTitleText();
	void SetPanelText();
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	// any current navi decorator
	CAknNavigationDecorator* iNaviDecorator_;
	CCalendarManagerContainer* iCalendarManagerContainer;
	// ]]] end generated region [Generated Instance Variables]
	CalendarClass* g_CalendarClass;
	CalendarManagerXML* g_CalendarManagerXML;
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // CALENDARMANAGERCONTAINERVIEW_H
