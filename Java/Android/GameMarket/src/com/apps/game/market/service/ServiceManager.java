package com.apps.game.market.service;

import android.content.Context;
import android.content.Intent;

public class ServiceManager {
	public static boolean Upgrade = true;
	
	public static void startUpgrade(Context context, long delay, long period) {		
		if (!Upgrade)
			return;
		
		final Intent intent = new Intent(context, ServiceUpgrade.class);
		intent.putExtra("delay", delay);
		intent.putExtra("period", period);
		intent.putExtra("show", true);
		context.startService(intent);
	}
	
	public static void stopUpgrade(Context context) {	
		final Intent intent = new Intent(context, ServiceUpgrade.class);
		intent.putExtra("delay", 10000L);
		intent.putExtra("period", 7200000L);
		intent.putExtra("show", false);
		context.startService(intent);
	}
}
