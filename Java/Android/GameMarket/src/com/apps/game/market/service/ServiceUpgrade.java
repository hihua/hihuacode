package com.apps.game.market.service;

import java.util.Timer;

import com.apps.game.market.task.TaskUpgrade;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ServiceUpgrade extends Service {
	private Timer mTimer;
	private UpgradeBind mUpgradeBind = new UpgradeBind();
	private TaskUpgrade mTask;

	@Override
	public IBinder onBind(Intent intent) {		
		return mUpgradeBind;
	}

	@Override
	public void onCreate() {		
		super.onCreate();				
	}

	@Override
	public void onDestroy() {				
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
	
	private void startTask(long delay, long period, boolean show) {
		if (mTask == null)
			mTask = new TaskUpgrade(this);						
		
		if (mTimer == null)
			mTimer = new Timer();
		
		mTimer.scheduleAtFixedRate(mTask, delay, period);
	}
	
	private void stopTask() {
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
