package com.apps.game.market.global;

import android.content.Context;
import android.content.IntentFilter;

import com.apps.game.market.activity.ActivityBase;
import com.apps.game.market.broadcast.BroadcastAppReceiver;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.util.DeviceInfo;
import com.apps.game.market.util.FileManager;

public class GlobalObject {
	public static GlobalObject globalObject = null;
	private Context mContext;
	private BroadcastAppReceiver mBroadcastAppReceiver;
	private DeviceInfo mDeviceInfo;
	private FileManager mFileManager;
	private TaskDownload mTaskDownload;
			
	public GlobalObject(Context context) {	
		mContext = context;
		globalObject = this;
	}
	
	public void init() {
		mTaskDownload = new TaskDownload();
		mFileManager = new FileManager(mContext);
		initDeviceInfo();
		registerBroadcastAppReceiver();
	}
	
	public void initDeviceInfo() {
		if (mDeviceInfo == null)
			mDeviceInfo = new DeviceInfo(mContext);	
	}
	
	public FileManager getFileManager() {
		return mFileManager;
	}
	
	public DeviceInfo getDeviceInfo() {
		return mDeviceInfo;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public void setContext(Context context) {
		mContext = context;
	}
	
	public TaskDownload getTaskDownload() {
		return mTaskDownload;
	}
	
	public void setActivity(ActivityBase activityBase) {
		mTaskDownload.setActivity(activityBase);
		mBroadcastAppReceiver.setActivity(activityBase);
	}
	
	public void close() {
		unRegisterBroadcastAppReceiver();
	}
	
	public void unRegisterBroadcastAppReceiver() {
		if (mBroadcastAppReceiver != null) {
			mContext.unregisterReceiver(mBroadcastAppReceiver);
			mBroadcastAppReceiver = null;			
		}
	}
	
	public void registerBroadcastAppReceiver() {
		if (mBroadcastAppReceiver == null) {
			mBroadcastAppReceiver = new BroadcastAppReceiver();			
	        mContext.registerReceiver(mBroadcastAppReceiver, new IntentFilter());	        
		}		        
	}
}
