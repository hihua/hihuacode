/*
========================================================================
 Name        : CalendarManagerListBox.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERLISTBOX_H
#define CALENDARMANAGERLISTBOX_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <coecntrl.h>		
#include <coecobs.h>
// ]]] end generated region [Generated Includes]
#include "CalendarClass.h"
#include "CalendarManagerXML.h"
#include "CalendarManagerSchedule.h"
// [[[ begin [Event Handler Includes]
#include <eiklbo.h>
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
class CAknSingleHeadingStyleListBox;
class CEikTextListBox;
// ]]] end generated region [Generated Forward Declarations]
TInt CompareReal(const TReal& p_Value1, const TReal& p_Value2);
/**
 * Container class for CalendarManagerListBox
 * 
 * @class	CCalendarManagerListBox CalendarManagerListBox.h
 */
class CCalendarManagerListBox : public CCoeControl
	,MEikListBoxObserver	,MCoeControlObserver	{
public:
	// constructors and destructor
	CCalendarManagerListBox();
	static CCalendarManagerListBox* NewL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver );
	static CCalendarManagerListBox* NewLC( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver );
	void ConstructL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver );
	virtual ~CCalendarManagerListBox();

public:
	// from base class CCoeControl
	TInt CountComponentControls() const;
	CCoeControl* ComponentControl( TInt aIndex ) const;
	TKeyResponse OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType );
	void HandleResourceChange( TInt aType );
	void SetCalendarClass(CalendarClass* p_CalendarClass);
	void SetXMLClass(CalendarManagerXML* p_CalendarManagerXML);
	void SetListBox();
	void SetSelectTimeContent();
	TPtrC GetSelectItem(TInt p_Items);
	
protected:
	// from base class CCoeControl
	void SizeChanged();

private:
	// from base class CCoeControl
	void Draw( const TRect& aRect ) const;

private:
	void InitializeControlsL();
	void LayoutControls();
	void SetPanelText();
	void SetTitleText();
		
	CCoeControl* iFocusControl;
	MEikCommandObserver* iCommandObserver;
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	static void AddListBoxItemL( 
			CEikTextListBox* aListBox,
			const TDesC& aString );
	static RArray< TInt >* GetSelectedListBoxItemsLC( CEikTextListBox* aListBox );
	static void DeleteSelectedListBoxItemsL( CEikTextListBox* aListBox );
	CAknSingleHeadingStyleListBox* ListBox();
	static void CreateListBoxItemL( TDes& aBuffer, 
			const TDesC& aHeadingText,
			const TDesC& aMainText );
	void AddListBoxResourceArrayItemL( TInt aResourceId );
	void SetupListBoxIconsL();
	TBool HandleMarkableListCommandL( TInt aCommand );
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CAknSingleHeadingStyleListBox* iListBox;
	// ]]] end generated region [Generated Instance Variables]	
	CalendarClass* g_CalendarClass;
	CalendarManagerXML* g_CalendarManagerXML;
	CalendarManagerSchedule* g_CalendarManagerSchedule;
	
	// [[[ begin [Overridden Methods]
protected: 
	void HandleListBoxEventL( 
			CEikListBox* aListBox,
			TListBoxEvent anEventType );
	void HandleControlEventL( 
			CCoeControl* aControl, 
			TCoeEvent anEventType );
	// ]]] end [Overridden Methods]
		
	// [[[ begin [User Handlers]
protected: 
	void HandleListBoxEnterKeyPressedL( 
			CEikListBox* aListBox,
			TListBoxEvent anEventType );
	void HandleListBoxStateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	// ]]] end [User Handlers]
	
public: 
	enum TControls
		{
		// [[[ begin generated region: do not modify [Generated Contents]
		EListBox,
		
		// ]]] end generated region [Generated Contents]
		
		// add any user-defined entries here...
		
		ELastControl
		};
	enum TListBoxImages
		{
		// [[[ begin generated region: do not modify [Generated Enums]
		EListBoxFirstUserImageIndex
		
		// ]]] end generated region [Generated Enums]
		
		};
	
	// [[[ begin [MEikListBoxObserver support]
private: 
	typedef void ( CCalendarManagerListBox::*ListBoxEventHandler )( 
			CEikListBox* aListBox, 
			TListBoxEvent anEvent );
	
	void AddListBoxEventHandlerL( 
			CEikListBox* aListBox, 
			TListBoxEvent anEvent, 
			ListBoxEventHandler aHandler );
	
	struct TListBoxEventDispatch 
		{ 
		CEikListBox* src; 
		TListBoxEvent event; 
		ListBoxEventHandler handler;
		};
		
	RArray< TListBoxEventDispatch > iListBoxEventDispatch;
	// ]]] end [MEikListBoxObserver support]
	
	// [[[ begin [MCoeControlObserver support]
private: 
	typedef void ( CCalendarManagerListBox::*ControlEventHandler )( 
			CCoeControl* aControl, TCoeEvent anEvent );
	
	void AddControlEventHandlerL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent, 
			ControlEventHandler aHandler );
	
	class TControlEventDispatch 
		{
	public: 
		CCoeControl* src; 
		TCoeEvent event; 
		ControlEventHandler handler;
		};
		
	RArray< TControlEventDispatch > iControlEventDispatch;
	// ]]] end [MCoeControlObserver support]
	
	};
				
#endif // CALENDARMANAGERLISTBOX_H
