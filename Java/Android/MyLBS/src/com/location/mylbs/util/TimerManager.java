package com.location.mylbs.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TimerManager {
	
	public static void startAlarmManager(final Context context, final Class<?> cls, final boolean repeat, final int type, final long triggerAtMillis, final long intervalMillis) {		
		final Intent intent = new Intent(context, cls);
		final PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);		
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (repeat)
			alarmManager.setRepeating(type, triggerAtMillis, intervalMillis, pendingIntent);
		else
			alarmManager.set(type, triggerAtMillis, pendingIntent);
	}
	
	public static void stopAlarmManager(final Context context, final Class<?> cls) {
		final Intent intent = new Intent(context, cls);
		final PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
}
