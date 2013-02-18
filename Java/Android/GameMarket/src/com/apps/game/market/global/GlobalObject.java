package com.apps.game.market.global;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.apps.game.market.broadcast.BroadcastAppReceiver;
import com.apps.game.market.util.DeviceInfo;
import com.apps.game.market.util.FileManager;

public class GlobalObject {
	public static GlobalObject globalObject = null;
	private Context mContext;
	private BroadcastAppReceiver mBroadcastAppReceiver;
	private DeviceInfo mDeviceInfo;
	private FileManager mFileManager;
			
	public GlobalObject(Context context) {
		mContext = context;
		globalObject = this;
	}
	
	public void init() {
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
			IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
	        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
	        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
	        filter.addDataScheme("package");
	        mContext.registerReceiver(mBroadcastAppReceiver, filter);
		}		        
	}
}