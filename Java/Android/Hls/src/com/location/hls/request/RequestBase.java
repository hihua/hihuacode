package com.location.hls.request;

import com.location.hls.R;
import com.location.hls.entity.EntityRequest;
import com.location.hls.util.HttpClass;
import com.location.hls.util.ThreadPool;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;

public abstract class RequestBase implements Runnable, Callback {
	protected final Context mContext;
	private final ThreadPool mThreadPool;
	protected final HttpClass mHttpClass;
	protected Handler mHandler;
	protected final EntityRequest mEntityRequest;
	
	protected RequestBase(final Context context, final ThreadPool threadPool) {
		mContext = context;
		mThreadPool = threadPool;		
		mHttpClass = new HttpClass();
		mHandler = new Handler(this);
		mEntityRequest = new EntityRequest();
		mEntityRequest.setContext(context);
	}
	
	public void request() {
		mThreadPool.execute(this);
	}
			
	protected String setUrl(final int resId, final String parameter) {
		if (parameter == null)
			return mContext.getString(R.string.request_host) + mContext.getString(resId);
		else
			return mContext.getString(R.string.request_host) + mContext.getString(resId) + "?" + parameter;
	}
	
	@Override
	public void run() {
		onTask(mEntityRequest);
	}
	
	protected abstract void onTask(final EntityRequest entityRequest);
	public abstract void destroy();
}
