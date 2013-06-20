package com.apps.game.market.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private static ThreadPool mThreadPool;
	private ExecutorService mService;
	
	public synchronized static ThreadPool getInstance() {				
		if (mThreadPool == null)
			mThreadPool = new ThreadPool(15);
		
		return mThreadPool;
	}

	private ThreadPool(int total) {
		mService = Executors.newFixedThreadPool(total);		
	}
	
	public void submit(Runnable runnable) {
		mService.submit(runnable);
	}
	
	public void close() {
		mService.shutdown();
	}
}
