package com.apps.game.market.broadcast;

import com.apps.game.market.service.ServiceManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastBoot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED))			
            ServiceManager.startUpgrade(context, 10000L, 7200000L);
	}
}
