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
	private ActivityBase mActivityBase = null;
		
	public void setActivity(ActivityBase activityBase) {		
		mActivityBase = activityBase;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {	
			GlobalData globalData = GlobalData.globalData;			
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
			if (!globalData.appInstalled(packageName)) {
				EntityAppInfo appInfo = ApkManager.getApp(context, packageName);
				if (appInfo != null)
					globalData.addLocalApp(appInfo);								
			}
			
			EntityApp entityApp = globalData.getRemoteApp(packageName);
			if (entityApp != null) {
				if (entityApp.getStatus() != EnumAppStatus.INSTALLED) {
					entityApp.setStatus(EnumAppStatus.INSTALLED);
					if (mActivityBase != null)
						mActivityBase.onAppStatus(entityApp);
				}				
			}
			
			return;
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
			GlobalData globalData = GlobalData.globalData;
			GlobalObject globalObject = GlobalObject.globalObject;
			FileManager fileManager = globalObject.getFileManager();
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
			globalData.removeLocalApp(packageName);				
			EntityApp entityApp = globalData.getRemoteApp(packageName);
			if (entityApp != null) {
				if (fileManager.appExists(packageName))
					entityApp.setStatus(EnumAppStatus.INSTALL);
				else
					entityApp.setStatus(EnumAppStatus.NOINSTALL);
				
				if (mActivityBase != null)
					mActivityBase.onAppStatus(entityApp);
			}
			
			return;
		}
		
		if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
			Uri uri = intent.getData();
			String packageName = uri.getSchemeSpecificPart();			
		}
	}
}
