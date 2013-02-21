package com.apps.game.market.request;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.util.HttpClass;
import com.apps.game.market.util.ThreadPool;

@SuppressLint("HandlerLeak")
public abstract class RequestBase implements Runnable, Callback {	
	protected GlobalObject mGlobalObject;
	protected GlobalData mGlobalData;
	protected HttpClass mHttpClass;	
	protected Handler mHandler;
	protected Context mContext;
	private EntityRequest mEntityRequest = null;
			
	protected RequestBase() {
		mGlobalObject = GlobalObject.globalObject;
		mGlobalData = GlobalData.globalData;
		mHttpClass = new HttpClass();		
		mContext = mGlobalObject.getContext();		
		mEntityRequest = new EntityRequest();
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler; 
	}
	
	public void request() {
		if (mHandler == null)
			mHandler = new Handler(this);
		
		ThreadPool pool = ThreadPool.getInstance();
		pool.submit(this);
	}
	
	protected String setUrl(String url) {
		Context context = mGlobalObject.getContext();
		Resources resources = context.getResources();
		String host = resources.getString(R.string.request_host);
		return host + url;
	}
	
	protected String setUrl(int resId) {
		Context context = mGlobalObject.getContext();
		Resources resources = context.getResources();
		String host = resources.getString(R.string.request_host);
		String url = resources.getString(resId);
		return host + url;
	}

	@Override
	public void run() {		
		mEntityRequest.addHeader("G-Header", "HTC+HD2/2.3.7/aMarket2.0/0.9.6/9/352666045835313/89860071190345504487/00:23:76:5C:9D:09");		
		onTask(mEntityRequest);				
	}
	
	public boolean handleMessage(Message msg) {		
		onMessage(msg);
		return true;
	} 
		
	protected abstract void onTask(EntityRequest req);
	protected abstract void onMessage(Message msg);
}
