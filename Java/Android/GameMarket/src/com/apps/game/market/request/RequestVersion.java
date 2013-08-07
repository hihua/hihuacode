package com.apps.game.market.request;

import java.io.IOException;
import java.io.InputStream;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityDownload;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.EntityUpgrade;
import com.apps.game.market.request.callback.RequestCallBackVersion;
import com.apps.game.market.util.ApkManager;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.util.Numeric;

public class RequestVersion extends RequestBase {
	private final RequestCallBackVersion mCallBack;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private int mNotificationId = 0;
	private EntityDownload mEntityDownload;
	private EntityUpgrade mEntityUpgrade;
		
	public RequestVersion(Context context, RequestCallBackVersion callBack) {
		mContext = context;
		mCallBack = callBack;
		mHandler = new Handler(this);
	}
	
	public void request(EntityUpgrade entityUpgrade) {
		mEntityUpgrade = entityUpgrade;
		final PackageManager packageManager = mContext.getPackageManager();
		final ApplicationInfo applicationInfo = mContext.getApplicationInfo();		
		final String name = applicationInfo.loadLabel(packageManager).toString();
		final Drawable drawable = applicationInfo.loadIcon(packageManager);		
		final BitmapDrawable bitmap = (BitmapDrawable) drawable;
				
		mNotificationId = 10;
		mNotification = new Notification(android.R.drawable.stat_sys_download, name + " 开始下载", System.currentTimeMillis());		  
		mNotification.contentView = new RemoteViews(mContext.getPackageName(), R.layout.notify_download);
		mNotification.flags = Notification.FLAG_NO_CLEAR;		
		mNotification.contentView.setImageViewBitmap(R.id.notify_download_app_icon, bitmap.getBitmap());
		mNotification.contentView.setProgressBar(R.id.notify_download_progressbar, 100, 0, false);  
		mNotification.contentView.setTextViewText(R.id.notify_download_app_name, name);
		mNotification.contentView.setTextViewText(R.id.notify_download_progress, "0%");			
		mNotification.contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0); 
		
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		super.request();
	}

	@Override
	protected void onTask(EntityRequest req) {
		final int upgradeVersionCode = mEntityUpgrade.getUpgradeVersionCode();
		final long filesize = mEntityUpgrade.getUpgradeFileSize();
		final String url = mEntityUpgrade.getUpgradeUrl();		
		final int entryTable = mEntityUpgrade.getEntryTable();		
		final String filename = "gamemarket_" + entryTable + "_" + upgradeVersionCode + ".apk";				
		final FileManager fileManager = new FileManager(mContext);						
		mEntityDownload = fileManager.getDownloadStream(fileManager.getUpgradePath(), filename, filesize);
		if (mEntityDownload != null) {
			if (mEntityDownload.getFinish())	
				mHandler.sendEmptyMessage(0);
			else {
				req.setString(false);
				req.setUrl(url);
				req.setBody(null);
				
				final EntityResponse resp = mHttpClass.request(req);
				if (resp != null) {
					final byte[] buffer = new byte[8192];
					final InputStream stream = resp.getStream();
					long position = mEntityDownload.getPosition();								
					boolean error = false;
					int precent = 0;
					int last = 0;
					int step = Numeric.rndNumber(3, 7);
					
					while (true) {					
						try {
							final int len = stream.read(buffer);
							if (len > 0) {						
								if (mEntityDownload.write(buffer, 0, len)) {
									position += len;
									mEntityDownload.setPosition(position);
									precent = (int) (((double) position / (double) filesize) * 100);
									if (precent - last >= step) {
										last = precent;
										step = Numeric.rndNumber(5, 7);
										final Message msg = mHandler.obtainMessage();
										msg.what = 1;
										msg.obj = precent;
										mHandler.sendMessage(msg);
									}
								} else {
									error = true;
									break;
								}
							} else
								break;
						} catch (IOException e) {
							error = true;
							break;
						}									
					}
					
					mEntityDownload.setAppend(false);
					mEntityDownload.setFinish(true);
					
					resp.close();
					mEntityDownload.close();
				
					if (error)					
						mHandler.sendEmptyMessage(5);
					else
						mHandler.sendEmptyMessage(0);							
				} else
					mHandler.sendEmptyMessage(2);
			}
		} else
			mHandler.sendEmptyMessage(4);
	}

	@Override
	protected void onMessage(Message msg) {
		final ApplicationInfo applicationInfo = mContext.getApplicationInfo();
		final String name = applicationInfo.name;
		
		final int what = msg.what;
		switch (what) {
			case 0: {				
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "下载完成", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);				
				ApkManager.installApk(mContext, mEntityDownload.getFile());		
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(true, mEntityUpgrade);
			}
			break;
			
			case 1: {
				final Integer precent = (Integer) msg.obj;				
				mNotification.contentView.setProgressBar(R.id.notify_download_progressbar, 100, precent, false);
				mNotification.contentView.setTextViewText(R.id.notify_download_progress, String.valueOf(precent) + "%");
				mNotificationManager.notify(mNotificationId, mNotification);				
			}
			break;
			
			case 2: {
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "请求下载链接失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);		
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(false, mEntityUpgrade);
			}
			break;
			
			case 3: {
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "获取下载文件名错误", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);	
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(false, mEntityUpgrade);
			}
			break;
			
			case 4: {
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "初始化下载失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);	
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(false, mEntityUpgrade);
			}
			break;
			
			case 5: {
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "文件写入失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);	
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(false, mEntityUpgrade);
			}
			break;
			
			default: {
				final PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, new Intent(), 0);
				mNotification.setLatestEventInfo(mContext, name, "下载失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);	
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackVersion(false, mEntityUpgrade);
			}
			break;
		}
	}
}
