/*
========================================================================
 Name        : CalendarManagerContainer.h
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
#ifndef CALENDARMANAGERCONTAINER_H
#define CALENDARMANAGERCONTAINER_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <coecntrl.h>		
// ]]] end generated region [Generated Includes]
#include <AknsDrawUtils.h>
#include <AknsBasicBackgroundControlContext.h>
#include "CalendarClass.h"
#include "CalendarManagerXML.h"
// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
// ]]] end generated region [Generated Forward Declarations]

/**
 * Container class for CalendarManagerContainer
 * 
 * @class	CCalendarManagerContainer CalendarManagerContainer.h
 */
class CCalendarManagerContainer : public CCoeControl
	{
public:
	// constructors and destructor
	CCalendarManagerContainer();
	static CCalendarManagerContainer* NewL( const TRect& aRect,	const CCoeControl* aParent,	MEikCommandObserver* aCommandObserver );
	static CCalendarManagerContainer* NewLC( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver );
	void ConstructL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver );
	virtual ~CCalendarManagerContainer();

public:
	// from base class CCoeControl
	TInt CountComponentControls() const;
	CCoeControl* ComponentControl( TInt aIndex ) const;
	TKeyResponse OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType );
	void HandleResourceChange( TInt aType );
	void SetCalendarClass(CalendarClass* p_CalendarClass);
	void SetXMLClass(CalendarManagerXML* p_CalendarManagerXML);
	
protected:
	// from base class CCoeControl
	void SizeChanged();

private:
	// from base class CCoeControl
	void Draw( const TRect& aRect ) const;	
	TTypeUid::Ptr MopSupplyObject(TTypeUid aId);
	TInt GetScheduleCount() const;
	void DrawSchedule(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const;
	void DrawRoundRect(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Grid_Color2) const;
	void DrawWeekName(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const;	
	void DrawGrid(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Grid_Color1) const;
	void DrawDay(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const TRgb& p_Default_Font_Color2, const CFont& p_Font) const;
	void DrawCurrentDay(CWindowGc& gc, MAknsSkinInstance* p_BgSkin, const TRgb& p_Default_Select_Color, const CFont& p_Font) const;
	void DrawWeek(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const;

private:
	void InitializeControlsL();
	void LayoutControls();
	CCoeControl* iFocusControl;
	MEikCommandObserver* iCommandObserver;
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	// ]]] end generated region [Generated Instance Variables]
	mutable TInt g_Space_X;
	mutable TInt g_Space_Y;
	mutable TInt g_Pos_Y;
	CAknsBasicBackgroundControlContext* g_BgContext;
	CalendarClass* g_CalendarClass;
	CalendarManagerXML* g_CalendarManagerXML;
		
	// [[[ begin [Overridden Methods]
protected: 
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
public: 
	enum TControls
		{
		// [[[ begin generated region: do not modify [Generated Contents]
		
		// ]]] end generated region [Generated Contents]
		
		// add any user-defined entries here...
		
		ELastControl
		};
	};
				
#endif // CALENDARMANAGERCONTAINER_H
