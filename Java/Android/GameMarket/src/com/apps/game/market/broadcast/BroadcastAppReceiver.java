package com.apps.game.market.broadcast;

import com.apps.game.market.activity.ActivityBase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BroadcastAppReceiver extends BroadcastReceiver {
	private ActivityBase mActivityBase = null;
	
	public void setActivity(ActivityBase activityBase) {
		mActivityBase = activityBase;
	}

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
