package com.location.hlsd.service;

import com.location.hlsd.broadcast.BroadcastSMSReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ServiceSMSReceiver extends Service {	
	
	@Override
	public IBinder onBind(final Intent intent) {		
		return null;
	}

	@Override
	public void onCreate() {		
		super.onCreate();
				
		final IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		intentFilter.setPriority(Integer.MAX_VALUE);
		final BroadcastSMSReceiver smsReceiver = new BroadcastSMSReceiver();
		registerReceiver(smsReceiver, intentFilter);		
	}

	@Override
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);		
	}	
}
