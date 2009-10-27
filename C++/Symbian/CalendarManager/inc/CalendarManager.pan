
#ifndef CALENDARMANAGER_PAN_H
#define CALENDARMANAGER_PAN_H

/** CalendarManager application panic codes */
enum TCalendarManagerPanics
	{
	ECalendarManagerUi = 1
	// add further panics here
	};

inline void Panic(TCalendarManagerPanics aReason)
	{
	_LIT(applicationName,"CalendarManager");
	User::Panic(applicationName, aReason);
	}

#endif // CALENDARMANAGER_PAN_H
