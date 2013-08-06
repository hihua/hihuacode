package com.apps.game.market.service;

import java.util.Timer;

import com.apps.game.market.task.TaskUpgrade;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiceUpgrade extends Service {
	private Timer mTimer = new Timer();
	private UpgradeBind mUpgradeBind = new UpgradeBind();
	private TaskUpgrade mTask;

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("ServiceUpgrade", "----onBind-----");
		return mUpgradeBind;
	}

	@Override
	public void onCreate() {
		Log.d("ServiceUpgrade", "----onCreate-----");
		super.onCreate();				
	}

	@Override
	public void onDestroy() {	
		Log.d("ServiceUpgrade", "----onDestroy-----");		
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {		
		final long delay = intent.getLongExtra("delay", 0);
		final long period = intent.getLongExtra("period", 0);
		final boolean show = intent.getBooleanExtra("show", false);		
		stopTask();
		if (show)
			startTask(delay, period, show);
		
		super.onStart(intent, startId);
	}
	
	public void startTask(long delay, long period, boolean show) {
		final Context context = getApplicationContext();
		if (mTask == null)
			mTask = new TaskUpgrade(context);						
		
		if (mTimer == null)
			mTimer = new Timer();
		
		mTimer.scheduleAtFixedRate(mTask, delay, period);
	}
	
	public void stopTask() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
			mTask = null;
		}		
	}
	
	public class UpgradeBind extends Binder {
				
		public ServiceUpgrade getService() {
			return ServiceUpgrade.this;
		}
	}
}
