package com.location.hlsd.request;

import com.location.hlsd.util.ThreadPool;

import android.os.Handler;
import android.os.Handler.Callback;

public abstract class RequestBase implements Runnable, Callback {	
	private final ThreadPool mThreadPool;	
	protected final Handler mHandler;
		
	protected RequestBase(final ThreadPool threadPool) {		
		mThreadPool = threadPool;
		mHandler = new Handler(this);		
	}
	
	protected void request() {
		mThreadPool.execute(this);
	}
	
	@Override
	public void run() {
		onTask();
	}
	
	protected abstract void onTask();
	public abstract void destroy();
}
