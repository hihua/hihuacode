package com.apps.game.market.broadcast;

import com.apps.game.market.activity.ActivityBase;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.util.ApkManager;
import com.apps.game.market.util.FileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BroadcastAppReceiver extends BroadcastReceiver {	
	private ActivityBase mActivityBase;
		
	public void setActivity(ActivityBase activityBase) {		
		mActivityBase = activityBase;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {	
			final GlobalData globalData = GlobalData.globalData;
			if (globalData != null) {
				final Uri uri = intent.getData();
				final String packageName = uri.getSchemeSpecificPart();			
				if (!globalData.appInstalled(packageName)) {
					final EntityAppInfo appInfo = ApkManager.getApp(context, packageName);
					if (appInfo != null)
						globalData.addLocalApp(appInfo);								
				}
				
				final EntityApp entityApp = globalData.getRemoteApp(packageName);
				if (entityApp != null) {
					if (entityApp.getStatus() != EnumAppStatus.INSTALLED) {
						entityApp.setStatus(EnumAppStatus.INSTALLED);
						if (mActivityBase != null)
							mActivityBase.onAppStatus(entityApp);
					}				
				}
			}			
			
			return;
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
			final GlobalData globalData = GlobalData.globalData;
			final GlobalObject globalObject = GlobalObject.globalObject;
			if (globalObject != null && globalData != null) {
				final FileManager fileManager = globalObject.getFileManager();
				final Uri uri = intent.getData();
				final String packageName = uri.getSchemeSpecificPart();			
				globalData.removeLocalApp(packageName);				
				final EntityApp entityApp = globalData.getRemoteApp(packageName);
				if (entityApp != null) {
					final long size = entityApp.getSize();
					final long length = fileManager.appExists(packageName);
					if (length > -1) {
						if (length == size)
							entityApp.setStatus(EnumAppStatus.INSTALL);
						else
							entityApp.setStatus(EnumAppStatus.NOINSTALL);
					} else
						entityApp.setStatus(EnumAppStatus.NOINSTALL);
					
					if (mActivityBase != null)
						mActivityBase.onAppStatus(entityApp);
				}
			}			
			
			return;
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
		}
	}
}
