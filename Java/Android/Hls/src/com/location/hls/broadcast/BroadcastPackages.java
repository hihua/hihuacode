package com.location.hls.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BroadcastPackages extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {		
		final String name = "com.location.hlsd"; 
		final String action = intent.getAction();
		if (action != null && (action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REMOVED))) {
			final Uri uri = intent.getData();
			if (uri != null) {
				final String packageName = uri.getSchemeSpecificPart(); 
				if (packageName != null && packageName.equals(name))
					context.startService(new Intent(name + ".service.ServiceTimer"));					
			}			
		}
	}
}
