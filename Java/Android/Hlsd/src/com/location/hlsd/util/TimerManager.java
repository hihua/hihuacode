package com.location.hlsd.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TimerManager {
	
	public static void startAlarmManager(final Intent intent, final Context context, final Class<?> cls, final String pending, final boolean repeat, final int type, final long triggerAtMillis, final long intervalMillis) {
		intent.setClass(context, cls);
		PendingIntent pendingIntent = null;
		
		if (pending.equals("activity"))
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		if (pending.equals("broadcast"))
			pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		if (pending.equals("service"))
			pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		
		if (pendingIntent == null)
			return;
		
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (repeat)
			alarmManager.setRepeating(type, triggerAtMillis, intervalMillis, pendingIntent);
		else
			alarmManager.set(type, triggerAtMillis, pendingIntent);		
	}
	
	public static void stopAlarmManager(final Intent intent, final Context context, final Class<?> cls, final String pending) {
		intent.setClass(context, cls);
		PendingIntent pendingIntent = null;
		
		if (pending.equals("activity"))
			pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		if (pending.equals("broadcast"))
			pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		if (pending.equals("service"))
			pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		
		if (pendingIntent == null)
			return;
		
		final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
}
