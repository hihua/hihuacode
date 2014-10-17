package com.location.hlsd.broadcast;

import com.location.hlsd.service.ServiceSMSReceiver;
import com.location.hlsd.service.ServiceTimer;
import com.location.hlsd.util.TimerManager;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BroadcastBoot extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {				
		final String action = intent.getAction();		
		if (action != null && action.equals(Intent.ACTION_BOOT_COMPLETED)) {			
			context.startService(new Intent(context, ServiceSMSReceiver.class));
			
			final long triggerAtMillis = SystemClock.elapsedRealtime() + 3 * 60 * 1000;
			//final long triggerAtMillis = SystemClock.elapsedRealtime() + 30 * 1000;
	        final long intervalMillis = 5 * 60 * 1000;	        
	        //TimerManager.startAlarmManager(new Intent(), context, BroadcastTimer.class, "broadcast", true, AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis);
	        TimerManager.startAlarmManager(new Intent(), context, ServiceTimer.class, "service", true, AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis);	        
		}
	}
}
