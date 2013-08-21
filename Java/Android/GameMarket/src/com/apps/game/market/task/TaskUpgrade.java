package com.apps.game.market.task;

import java.util.TimerTask;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityEntry;
import com.apps.game.market.entity.EntityUpgrade;
import com.apps.game.market.request.RequestUpgrade;
import com.apps.game.market.request.callback.RequestCallBackUpgrade;
import com.apps.game.market.service.ServiceVersion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class TaskUpgrade extends TimerTask implements RequestCallBackUpgrade {
	private final Context mContext;
	private final RequestUpgrade mRequestUpgrade;
	
	public TaskUpgrade(Context context) {
		mContext = context;
		final EntityEntry entityEntry = EntityEntry.getSrcEntry(context);
		if (entityEntry != null)
			mRequestUpgrade = new RequestUpgrade(context, entityEntry, this);
		else
			mRequestUpgrade = null;
	}
	
	@Override
	public void run() {
		if (mRequestUpgrade != null)
			mRequestUpgrade.request();
	}

	@Override
	public void onCallBackUpgrade(EntityUpgrade entityUpgrade) {
		if (entityUpgrade != null) {			
			final int upgradeVersionCode = entityUpgrade.getUpgradeVersionCode();
			final String upgradeVersionName = entityUpgrade.getUpgradeVersionName();
			final String upgradeUrl = entityUpgrade.getUpgradeUrl();
			final long upgradeFileSize = entityUpgrade.getUpgradeFileSize();
			final int upgradeForce = entityUpgrade.getUpgradeForce();
			final int entryTable = entityUpgrade.getEntryTable();
			
			final Intent intent = new Intent(mContext, ServiceVersion.class);				
			intent.putExtra("upgrade_version_code", upgradeVersionCode);
			intent.putExtra("upgrade_version_name", upgradeVersionName);
			intent.putExtra("upgrade_url", upgradeUrl);
			intent.putExtra("upgrade_filesize", upgradeFileSize);
			intent.putExtra("upgrade_force", upgradeForce);
			intent.putExtra("entry_table", entryTable);
														
			if (upgradeForce == 0) {
				final PackageManager packageManager = mContext.getPackageManager();
				final ApplicationInfo applicationInfo = mContext.getApplicationInfo();		
				final String name = applicationInfo.loadLabel(packageManager).toString();
				
				final String title = mContext.getString(R.string.welcome_version, upgradeVersionName);
				final String content = mContext.getString(R.string.welcome_click_download);
												
				final int notificationId = 10;
				final PendingIntent contentIntent = PendingIntent.getService(mContext, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
				final Notification notification = new Notification(android.R.drawable.stat_sys_download, name + title, System.currentTimeMillis());
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				notification.setLatestEventInfo(mContext, name + title, content, contentIntent);
				final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
				notificationManager.notify(notificationId, notification);
			} else
				mContext.startService(intent);
		}				
	}	
}
