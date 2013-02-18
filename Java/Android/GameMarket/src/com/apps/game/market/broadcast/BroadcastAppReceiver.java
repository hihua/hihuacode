package com.apps.game.market.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class BroadcastAppReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {			
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
		}
	}
}
