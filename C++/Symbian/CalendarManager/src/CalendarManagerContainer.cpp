/*
========================================================================
 Name        : CalendarManagerContainer.cpp
 Author      : 
 Copyright   : 
 Description : 
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikappui.h>
#include <CalendarManager.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "CalendarManagerContainer.h"
#include "CalendarManagerContainerView.h"
#include "CalendarManager.hrh"
#include "CalendarManagerContainer.hrh"
#include "CalendarManagerListBox.hrh"
#include "CalendarManagerSettingItemList.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CCalendarManagerContainer::CCalendarManagerContainer()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	g_BgContext = NULL;
	g_CalendarClass = NULL;
	g_CalendarManagerXML = NULL;
	}
/** 
 * Destroy child controls.
 */
CCalendarManagerContainer::~CCalendarManagerContainer()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	SafeRelease(g_BgContext);
	}
				
/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aRect bounding rectangle
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return initialized instance of CCalendarManagerContainer
 */
CCalendarManagerContainer* CCalendarManagerContainer::NewL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	CCalendarManagerContainer* self = CCalendarManagerContainer::NewLC( aRect, aParent, aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aRect The rectangle for this window
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return new instance of CCalendarManagerContainer
 */
CCalendarManagerContainer* CCalendarManagerContainer::NewLC( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	CCalendarManagerContainer* self = new ( ELeave ) CCalendarManagerContainer();
	CleanupStack::PushL( self );
	self->ConstructL( aRect, aParent, aCommandObserver );
	return self;
	}
			
/**
 * Construct the control (second phase).
 *  Creates a window to contain the controls and activates it.
 * @param aRect bounding rectangle
 * @param aCommandObserver command observer
 * @param aParent owning parent, or NULL
 */ 
void CCalendarManagerContainer::ConstructL( const TRect& aRect, const CCoeControl* aParent, MEikCommandObserver* aCommandObserver )
	{
	if ( aParent == NULL )
	    {
		CreateWindowL();
	    }
	else
	    {
	    SetContainerWindowL( *aParent );
	    }
	iFocusControl = NULL;
	iCommandObserver = aCommandObserver;
	InitializeControlsL();
	SetRect( aRect );
	ActivateL();
	
	// [[[ begin generated region: do not modify [Post-ActivateL initializations]
	// ]]] end generated region [Post-ActivateL initializations]
	g_BgContext = CAknsBasicBackgroundControlContext::NewL(KAknsIIDQsnBgAreaMain, aRect, ETrue);		
	}
			
/**
* Return the number of controls in the container (override)
* @return count
*/
TInt CCalendarManagerContainer::CountComponentControls() const
	{
	return ( int ) ELastControl;
	}
				
/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CCalendarManagerContainer::ComponentControl( TInt aIndex ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	switch ( aIndex )
		{
		}
	// ]]] end generated region [Generated Contents]
	
	// handle any user controls here...
	
	return NULL;
	}
				
/**
 *	Handle resizing of the container. This implementation will lay out
 *  full-sized controls like list boxes for any screen size, and will layout
 *  labels, editors, etc. to the size they were given in the UI designer.
 *  This code will need to be modified to adjust arbitrary controls to
 *  any screen size.
 */				
void CCalendarManagerContainer::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	// [[[ begin generated region: do not modify [Generated Contents]
			
	// ]]] end generated region [Generated Contents]
	if (g_BgContext)
		{
		g_BgContext->SetRect(Rect());
		if (&Window())
			{
			g_BgContext->SetParentPos(PositionRelativeToScreen());
			}
		}		
	
	DrawNow();
	}
				
// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CCalendarManagerContainer::LayoutControls()
	{
	}
// ]]] end generated function

/**
 *	Handle key events.
 */				
TKeyResponse CCalendarManagerContainer::OfferKeyEventL( const TKeyEvent& aKeyEvent,	TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	
	// ]]] end generated region [Generated Contents]
	
	if ( iFocusControl != NULL && iFocusControl->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}
	
	if (aType == EEventKey)
		{
		switch (aKeyEvent.iCode)
			{
			case EKeyLeftArrow: 
				g_CalendarClass->g_SelectDay--;	
				break;
				
			case EKeyRightArrow:
				g_CalendarClass->g_SelectDay++;				
				break;
			
			case EKeyUpArrow:
				g_CalendarClass->g_SelectDay -= 7;				
				break;
				
			case EKeyDownArrow:
				g_CalendarClass->g_SelectDay += 7;
				break;
			
			default:
				break;				
			}
		
		if (g_CalendarClass->g_SelectDay <= 0)
			{			
			g_CalendarClass->g_SelectMonth--;
			
			if (g_CalendarClass->g_SelectMonth <= 0)
				{
				g_CalendarClass->g_SelectYear--;
				g_CalendarClass->g_SelectMonth = 12;
				g_CalendarClass->g_SelectDay = 31;			
				}
			
			g_CalendarClass->GetFirstWeek();
			g_CalendarClass->GetMaxDay();
			g_CalendarClass->g_SelectDay = g_CalendarClass->g_MaxDay;
			g_CalendarClass->GetWeek();
			}
		
		if (g_CalendarClass->g_SelectDay > g_CalendarClass->g_MaxDay)
			{
			g_CalendarClass->g_SelectMonth++;
			
			if (g_CalendarClass->g_SelectMonth > 12)
				{
				g_CalendarClass->g_SelectYear++;
				g_CalendarClass->g_SelectMonth = 1;
				g_CalendarClass->g_SelectDay = 1;			
				}
			
			g_CalendarClass->GetFirstWeek();
			g_CalendarClass->GetMaxDay();
			g_CalendarClass->g_SelectDay = 1;
			g_CalendarClass->GetWeek();
			}	
			
		DrawDeferred();		
		}
	
	return CCoeControl::OfferKeyEventL( aKeyEvent, aType );
	}
				
// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CCalendarManagerContainer::InitializeControlsL()
	{
	
	}
// ]]] end generated function

/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CCalendarManagerContainer::HandleResourceChange( TInt aType )
	{
	CCoeControl::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( ECalendarManagerContainerViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Draw container contents.
 */				
void CCalendarManagerContainer::Draw( const TRect& aRect ) const
	{
	g_Space_X = (aRect.Width() - 7) / 8;
	g_Space_Y = (aRect.Height() - 8) / 8;
	g_Pos_Y = 0;
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]

	MAknsSkinInstance* i_BgSkin = AknsUtils::SkinInstance();
	MAknsControlContext* i_BgControl = AknsDrawUtils::ControlContext(this);			
	AknsDrawUtils::Background(i_BgSkin, i_BgControl, this, gc, aRect);	
	
	TRgb i_Default_Font_Color1;
	TRgb i_Default_Font_Color2;
	TRgb i_Default_Grid_Color1;
	TRgb i_Default_Grid_Color2;
	TRgb i_Default_Select_Color;
	AknsUtils::GetCachedColor(i_BgSkin, i_Default_Font_Color1, KAknsIIDQsnTextColors, EAknsCIQsnTextColorsCG22);
	AknsUtils::GetCachedColor(i_BgSkin, i_Default_Font_Color2, KAknsIIDQsnTextColors, EAknsCIQsnTextColorsCG21);	
	AknsUtils::GetCachedColor(i_BgSkin, i_Default_Grid_Color1, KAknsIIDQsnLineColors, EAknsCIQsnLineColorsCG1);
	AknsUtils::GetCachedColor(i_BgSkin, i_Default_Grid_Color2, KAknsIIDQsnLineColors, EAknsCIQsnLineColorsCG2);
	AknsUtils::GetCachedColor(i_BgSkin, i_Default_Select_Color, KAknsIIDQsnTextColors, EAknsCIQsnTextColorsCG11);		
		
	const CFont* i_Font = iEikonEnv->LegendFont();	
	gc.UseFont(i_Font);	
	DrawSchedule(gc, aRect, i_Default_Font_Color1, *i_Font);
	DrawRoundRect(gc, aRect, i_Default_Grid_Color2);
	DrawWeekName(gc, aRect, i_Default_Font_Color1, *i_Font);
	DrawGrid(gc, aRect, i_Default_Grid_Color1);
	DrawDay(gc, aRect, i_Default_Font_Color1, i_Default_Font_Color2, *i_Font);
	DrawCurrentDay(gc, i_BgSkin, i_Default_Select_Color, *i_Font);
	DrawWeek(gc, aRect, i_Default_Font_Color1, *i_Font);		
	gc.DiscardFont();
		
	CAknViewAppUi* i_AppUi = static_cast<CAknViewAppUi*>(iCoeEnv->AppUi());	
	CAknView* i_View = i_AppUi->View(TUid::Uid(ECalendarManagerContainerViewId));
	i_View->HandleCommandL(ECalendarManagerContainerView_KeyCommand);
		
//	TInt p = 20;
//	TInt q = 20;
//	TInt a = 0;
//	TInt b = 20;
//	
//	for (TInt i = 0;i <= 62;i++)
//		{
//		TRgb i_Color;
//		AknsUtils::GetCachedColor(i_BgSkin, i_Color, KAknsIIDQsnTextColors, i);
//		gc.SetPenColor(i_Color);
//		
//		TBuf<10> k;
//		k.AppendNum(i);
//				
//		gc.DrawText(k, TPoint(a, b));
//		a += p;
//		
//		if (i % 10 == 0 && i > 0)
//			{
//			a = 0;
//			b += q;
//			}
//		}
	}

TTypeUid::Ptr CCalendarManagerContainer::MopSupplyObject(TTypeUid aId)
	{
	if (g_BgContext)
		{
		return MAknsControlContext::SupplyMopObject(aId, g_BgContext);
		}
	return CCoeControl::MopSupplyObject(aId);
	}

void CCalendarManagerContainer::SetCalendarClass(CalendarClass* p_CalendarClass)
	{
	g_CalendarClass = p_CalendarClass;	
	}

void CCalendarManagerContainer::SetXMLClass(CalendarManagerXML* p_CalendarManagerXML)
	{
	g_CalendarManagerXML = p_CalendarManagerXML;
	}

TInt CCalendarManagerContainer::GetScheduleCount() const
	{
	if (g_CalendarManagerXML == NULL)		
		return 0;	
		
	CalendarManagerSchedule* i_CalendarManagerSchedule = g_CalendarManagerXML->g_ScheduleMap.Find(g_CalendarClass->GetSelectDateEN()->Des());
	
	if (i_CalendarManagerSchedule == NULL)
		return 0;
	
	return i_CalendarManagerSchedule->g_ScheduleMap.Count();	
	}

void CCalendarManagerContainer::DrawSchedule(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const
	{		
	g_Pos_Y += g_Space_Y;	
	gc.SetPenColor(p_Default_Font_Color1);
	TInt i_Count = GetScheduleCount();
	
	if (i_Count == 0)
		{		
		gc.DrawText(_L("\x6CA1\x6709\x65E5\x7A0B"), TRect(0, 0, aRect.Width(), g_Pos_Y), (g_Pos_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
		}			
	else
		{
		TBuf<20> i_NUM;
		i_NUM.Append(_L("\x6709"));
		i_NUM.AppendNum(i_Count);
		i_NUM.Append(_L("\x4E2A\x65E5\x7A0B"));
		
		gc.DrawText(i_NUM, TRect(0, 0, aRect.Width(), g_Pos_Y), (g_Pos_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
		}
	//gc.DrawLine(TPoint(0, g_Pos_Y), TPoint(aRect.Width(), g_Pos_Y));	
	}

void CCalendarManagerContainer::DrawRoundRect(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Grid_Color2) const
	{
	gc.SetPenColor(p_Default_Grid_Color2);
	gc.DrawRoundRect(TRect(0, g_Pos_Y, aRect.Width(), aRect.Height()), TSize(4, 4));
	g_Pos_Y += 1;
	}

void CCalendarManagerContainer::DrawWeekName(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const
	{
	//TInt i_Pos_X = g_Space_X + 1;
	TInt i_Pos_X = 0;
	TInt i_Width = 0;
	TInt i_Height = g_Pos_Y + g_Space_Y;
	
	gc.SetPenColor(p_Default_Font_Color1);
	i_Width = i_Pos_X + g_Space_X;
	gc.DrawText(_L("\x5468"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
	i_Pos_X += g_Space_X + 1;
	
	for (TInt i = 0;i < 7;i++)
		{
		switch (i)
			{
			case 0:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x65E5"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 1:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x4E00"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 2:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x4E8C"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 3:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x4E09"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 4:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x56DB"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 5:
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x4E94"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
				
			case 6:				
				i_Width = i_Pos_X + g_Space_X;
				gc.DrawText(_L("\x516D"), TRect(i_Pos_X, g_Pos_Y, i_Width, i_Height), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels(), CGraphicsContext::ECenter, 0);
				i_Pos_X += g_Space_X + 1;
				break;
			}			
		}
	
	g_Pos_Y += g_Space_Y;
	//gc.DrawLine(TPoint(0, g_Pos_Y), TPoint(aRect.Width(), g_Pos_Y));
	}

void CCalendarManagerContainer::DrawGrid(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Grid_Color1) const
	{
	gc.SetPenColor(p_Default_Grid_Color1);
	gc.SetPenSize(TSize(2, 2));	
	gc.DrawLine(TPoint(0, g_Pos_Y), TPoint(aRect.Width(), g_Pos_Y));
		
	gc.SetPenSize(TSize(1, 1));
	TInt i_Pos_X = g_Space_X;
	for (TInt i = 0; i < 7; i++)
		{
		gc.DrawLine(TPoint(i_Pos_X, g_Pos_Y), TPoint(i_Pos_X, aRect.Height()));
		i_Pos_X += g_Space_X + 1;
		}
	
	g_Pos_Y += 1;
	TInt i_Pos_Y = g_Pos_Y;	
	for (TInt i = 0; i < 6; i++)
		{
		gc.DrawLine(TPoint(g_Space_X, i_Pos_Y), TPoint(aRect.Width(), i_Pos_Y));
		i_Pos_Y += g_Space_Y + 2;
		}
	}

void CCalendarManagerContainer::DrawDay(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const TRgb& p_Default_Font_Color2, const CFont& p_Font) const
	{
	gc.SetPenColor(p_Default_Font_Color1);
	
	TInt i_Total = 0;
	TInt i_WeekPos = g_CalendarClass->g_FirstWeek;	
	//TInt i_Pos_X = g_Space_X * (i_WeekPos + 1) + i_WeekPos + 1;
	//TInt i_Pos_Y = g_Pos_Y;
	
	TInt i_Pos_X = g_Space_X + 1;
	TInt i_Pos_Y = g_Pos_Y;
	
	if (i_WeekPos != 7)
		{
		gc.SetPenColor(p_Default_Font_Color2);
		for (TInt i = g_CalendarClass->g_MaxDay_PreMonth - i_WeekPos + 1;i <= g_CalendarClass->g_MaxDay_PreMonth;i++)
			{
			TBuf<5> i_Num;
			i_Num.AppendNum(i);
			
			gc.DrawText(i_Num, TRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X, i_Pos_Y + g_Space_Y), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels() + 1, CGraphicsContext::ECenter, 0);
			
			i_Pos_X += g_Space_X + 1;
			i_Total++;
			}
		}
	
	i_Pos_X = g_Space_X * (i_WeekPos + 1) + i_WeekPos + 1;
	
	for (TInt i = 1; i <= g_CalendarClass->g_MaxDay; i++)
		{
		TBuf<5> i_Num;
		i_Num.AppendNum(i);
		
		if (i_WeekPos == 0)
			gc.SetPenColor(KRgbRed);
		else
			gc.SetPenColor(p_Default_Font_Color1);
			
		if (g_CalendarClass->g_SelectYear == g_CalendarClass->g_Year && g_CalendarClass->g_SelectMonth == g_CalendarClass->g_Month && i == g_CalendarClass->g_Day)			
			gc.SetUnderlineStyle(EUnderlineOn);		
		else			
			gc.SetUnderlineStyle(EUnderlineOff);			
		
		gc.DrawText(i_Num, TRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X, i_Pos_Y + g_Space_Y), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels() + 1, CGraphicsContext::ECenter, 0);
				
		i_WeekPos++;		
		if (i_WeekPos >= 7)
			{
			i_WeekPos = 0;			
			i_Pos_Y += g_Space_Y + 2;			
			}				
		
		i_Pos_X = g_Space_X * (i_WeekPos + 1) + i_WeekPos + 1;
		i_Total++;
		}
	
	TInt j = 1;
	gc.SetPenColor(p_Default_Font_Color2);
	for (TInt i = i_Total + 1;i <= 42;i++)
		{
		TBuf<5> i_Num;
		i_Num.AppendNum(j);
		
		gc.DrawText(i_Num, TRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X, i_Pos_Y + g_Space_Y), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels() + 1, CGraphicsContext::ECenter, 0);
		
		i_WeekPos++;		
		if (i_WeekPos >= 7)
			{
			i_WeekPos = 0;			
			i_Pos_Y += g_Space_Y + 2;
			}
			
		i_Pos_X = g_Space_X * (i_WeekPos + 1) + i_WeekPos + 1;		
		j++;
		}
	}

void CCalendarManagerContainer::DrawCurrentDay(CWindowGc& gc, MAknsSkinInstance* p_BgSkin, const TRgb& p_Default_Select_Color, const CFont& p_Font) const
	{
	TInt i_Col = (g_CalendarClass->g_SelectDay + g_CalendarClass->g_FirstWeek - 1) % 7 + 2;
	TInt i_Row = (g_CalendarClass->g_SelectDay + g_CalendarClass->g_FirstWeek - 1) / 7 + 1;
	
	TInt i_Pos_X = (i_Col - 1) * g_Space_X + i_Col - 1;
	TInt i_Pos_Y = (i_Row - 1) * g_Space_Y + (i_Row - 1) * 2 + g_Pos_Y;
		
	TRect i_OuterRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X + 1, i_Pos_Y + g_Space_Y + 2);
	i_OuterRect.Shrink(2, 2);
	TRect i_InnerRect(i_OuterRect);
	i_InnerRect.Shrink(4, 4);
	
	AknsDrawUtils::DrawFrame(p_BgSkin, gc, i_OuterRect, i_InnerRect, KAknsIIDQsnFrList, KAknsIIDQsnFrListCenter);
	
	gc.SetPenColor(p_Default_Select_Color);
	TBuf<5> i_Num;
	i_Num.AppendNum(g_CalendarClass->g_SelectDay);	
	
	if (g_CalendarClass->g_SelectYear == g_CalendarClass->g_Year && g_CalendarClass->g_SelectMonth == g_CalendarClass->g_Month && g_CalendarClass->g_SelectDay == g_CalendarClass->g_Day)		
		gc.SetUnderlineStyle(EUnderlineOn);		
	else				
		gc.SetUnderlineStyle(EUnderlineOff);
				
	gc.DrawText(i_Num, TRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X, i_Pos_Y + g_Space_Y), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels() + 1, CGraphicsContext::ECenter, 0);	
	}

void CCalendarManagerContainer::DrawWeek(CWindowGc& gc, const TRect& aRect, const TRgb& p_Default_Font_Color1, const CFont& p_Font) const
	{
	gc.SetPenColor(p_Default_Font_Color1);
	gc.SetUnderlineStyle(EUnderlineOff);
	
	TInt i_Pos_X = 0;
	TInt i_Pos_Y = g_Pos_Y;
	
	for (TInt i = 0;i < sizeof(g_CalendarClass->g_Week) / sizeof(TInt);i++)
		{
		TBuf<10> i_Num;
		i_Num.AppendNum(g_CalendarClass->g_Week[i]);
		
		gc.DrawText(i_Num, TRect(i_Pos_X, i_Pos_Y, i_Pos_X + g_Space_X, i_Pos_Y + g_Space_Y), (g_Space_Y - p_Font.HeightInPixels()) / 2 + p_Font.HeightInPixels() + 1, CGraphicsContext::ECenter, 0);
		i_Pos_Y += g_Space_Y + 2;
		}					
	}


				
