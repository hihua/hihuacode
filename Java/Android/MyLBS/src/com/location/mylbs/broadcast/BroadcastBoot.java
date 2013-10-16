package com.location.mylbs.broadcast;

import com.location.mylbs.service.ServiceLocation;
import com.location.mylbs.util.TimerManager;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BroadcastBoot extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		final String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			final long triggerAtMillis = SystemClock.elapsedRealtime() + 1 * 60 * 1000;
	        final long intervalMillis = 5 * 60 * 1000;        
	        TimerManager.startAlarmManager(context, ServiceLocation.class, true, AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis);			
		}
	}
}
