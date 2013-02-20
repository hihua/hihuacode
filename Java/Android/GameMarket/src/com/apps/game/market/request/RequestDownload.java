package com.apps.game.market.request;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityDownload;
import com.apps.game.market.entity.EntityImage;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.enums.EnumDownloadStatus;
import com.apps.game.market.request.callback.RequestCallBackDownload;
import com.apps.game.market.util.ApkManager;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.util.Numeric;

public class RequestDownload extends RequestBase {
	private String mBody = "";
	private boolean mCancel = false;
	private boolean mBusy = false;
	private final int mResId = R.string.request_download;
	private final String mContent = "<request version=\"2\"><uid></uid><p_id>%d</p_id><source_type>0</source_type></request>";	
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private int mNotificationId = Numeric.rndNumber(500, 2000);
	private EntityApp mEntityApp;	
	private EntityDownload mEntityDownload;
	private RequestCallBackDownload mCallBack;
	
	public RequestDownload(RequestCallBackDownload callBack) {		
		mCallBack = callBack;
	}
	
	public EntityApp getEntityApp() {
		return mEntityApp;
	}
	
	public void setEntityApp(EntityApp entityApp) {
		mEntityApp = entityApp;
	}
	
	public boolean getBusy() {
		return mBusy;
	}
	
	public void setBusy(boolean busy) {
		mBusy = busy;
	}
	
	public void request(EntityApp entityApp) {
		mBusy = true;
		mEntityApp = entityApp;
		mEntityApp.setStatus(EnumAppStatus.DOWNLOADING);
		String icon = mEntityApp.getIcon();		
		ImageCache imageCache = ImageCache.getInstance();
		Drawable drawable = imageCache.get(icon);
		if (drawable == null) {
			mHandler = new Handler(this);
			RequestImage requestImage = new RequestImage(icon, null, true);
			requestImage.setHandler(mHandler);
			requestImage.request();			
		} else
			request(drawable);
	}
	
	private void request(Drawable drawable) {
		String name = mEntityApp.getName();		
		long pid = mEntityApp.getPid();
		mNotificationId = Numeric.rndNumber(500, 2000);
		mNotification = new Notification(android.R.drawable.stat_sys_download, name + " 开始下载", System.currentTimeMillis());		  
		mNotification.contentView = new RemoteViews(mContext.getPackageName(), R.layout.notify_download);
		
		BitmapDrawable bitmap = (BitmapDrawable) drawable;
		mNotification.contentView.setImageViewBitmap(R.id.notify_download_app_icon, bitmap.getBitmap());				
		mNotification.contentView.setProgressBar(R.id.notify_download_progressbar, 100, 0, false);  
		mNotification.contentView.setTextViewText(R.id.notify_download_app_name, name);
		mNotification.contentView.setTextViewText(R.id.notify_download_progress, "0%");			
		mNotification.contentIntent = PendingIntent.getActivity(mContext, 0, null, 0); 
		
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(mNotificationId, mNotification);
		mBody = String.format(mContent, pid);
		super.request();
	}
	
	public void cancel() {
		mCancel = true;
	}
			
	@Override
	protected void onTask(EntityRequest req) {	
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {						
			String url = parse(resp);
			resp.close();
			if (url != null) {								
				FileManager fileManager = mGlobalObject.getFileManager();
				String filename = mEntityApp.getPackageName() + ".apk";
				if (filename != null) {
					long size = mEntityApp.getSize();
					mEntityDownload = fileManager.getDownloadStream(filename, size);
					if (mEntityDownload != null) {
						if (mEntityDownload.getFinish())	
							mHandler.sendEmptyMessage(0);
						else {
							req.setString(false);
							req.setUrl(url);
							req.setBody(null);
							
							resp = mHttpClass.request(req);
							if (resp != null) {
								byte[] buffer = new byte[8192];
								InputStream stream = resp.getStream();
								long position = mEntityDownload.getPosition();								
								boolean error = false;
								int precent = 0;
								int last = 0;
								int step = Numeric.rndNumber(3, 7);
								
								while (true) {					
									try {
										int len = stream.read(buffer);
										if (len > 0) {
											if (mCancel)
												break;
											
											if (mEntityDownload.write(buffer, 0, len)) {
												position += len;
												mEntityDownload.setPosition(position);
												precent = (int) (((double) position / (double) size) * 100);
												if (precent - last >= step) {
													last = precent;
													step = Numeric.rndNumber(5, 7);
													Message msg = mHandler.obtainMessage();
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
								
								if (mCancel)
									mHandler.sendEmptyMessage(6);
								else {
									if (error)					
										mHandler.sendEmptyMessage(5);
									else
										mHandler.sendEmptyMessage(0);
								}							
							} else
								mHandler.sendEmptyMessage(2);
						}
					} else
						mHandler.sendEmptyMessage(4);
				}
			} else
				mHandler.sendEmptyMessage(2);
		} else
			mHandler.sendEmptyMessage(2);	
	}

	@Override
	protected void onMessage(Message msg) {
		int what = msg.what;
		switch (what) {
			case -1: {				
				EntityImage entityImage = (EntityImage) msg.obj;
				Drawable drawable = entityImage.getDrawable();
				request(drawable);							
			}
			break;
		
			case 0: {
				String name = mEntityApp.getName();
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, name, "下载完成", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);				
				ApkManager.installApk(mContext, mEntityDownload.getFile());			
				mNotificationManager.cancel(mNotificationId);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FINISH, mEntityApp);
			}
			break;
									
			case 1: {
				Integer precent = (Integer) msg.obj;				
				mNotification.contentView.setProgressBar(R.id.notify_download_progressbar, 100, precent, false);
				mNotification.contentView.setTextViewText(R.id.notify_download_progress, String.valueOf(precent) + "%");
				mNotificationManager.notify(mNotificationId, mNotification);
			}
			break;
			
			case 2: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "获取下载链接失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);				
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FAILED, mEntityApp);
			}
			break;
			
			case 3: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "获取下载文件名错误", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FAILED, mEntityApp);
			}
			break;
			
			case 4: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "初始化下载失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FAILED, mEntityApp);
			}
			break;
			
			case 5: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "文件写入失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FAILED, mEntityApp);
			}
			break;
			
			case 6: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "用户取消", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.CANCEL, mEntityApp);
			}
			break;
				
			default: {
				PendingIntent contentIntent = PendingIntent.getActivity(mContext, mNotificationId, null, 0);
				mNotification.setLatestEventInfo(mContext, mEntityApp.getName(), "下载失败", contentIntent);
				mNotificationManager.notify(mNotificationId, mNotification);
				if (mCallBack != null)
					mCallBack.onCallBackDownload(this, EnumDownloadStatus.FAILED, mEntityApp);
			}
			break;
		}
	}
	
	private String parse(EntityResponse resp) {
		InputStream stream = resp.getStream();
		Document document = null;
		
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(stream);			
		} catch (DocumentException e) {			
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		Element root = document.getRootElement();
		Element parent = root.element("download_info");
		if (parent == null)
			return null;
		
		Attribute attr = parent.attribute("url");
		if (attr == null)
			return null;
		
		String url = attr.getText();
		return url;
	}
}
