package com.location.mylbs.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class ThreadPool {
	private final HandlerThread mHandlerThread;
	private final Handler mHandler;
			
	public ThreadPool() {
		mHandlerThread = new HandlerThread("");  
		mHandlerThread.start();
		final Looper looper = mHandlerThread.getLooper();
        mHandler = new Handler(looper);
	}
	
	public void execute(final Runnable runnable) {
		mHandler.post(runnable);
	}
	
	public void close() {
		final Looper looper = mHandlerThread.getLooper();
		looper.quit();
	}
}
