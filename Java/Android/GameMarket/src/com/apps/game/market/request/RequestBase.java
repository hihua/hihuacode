package com.apps.game.market.request;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.util.DeviceInfo;
import com.apps.game.market.util.HttpClass;
import com.apps.game.market.util.ThreadPool;

@SuppressLint("HandlerLeak")
public abstract class RequestBase implements Runnable, Callback {	
	protected final GlobalObject mGlobalObject;
	protected final GlobalData mGlobalData;
	protected final HttpClass mHttpClass;	
	protected Handler mHandler;
	protected final Context mContext;
	private EntityRequest mEntityRequest;
	protected final boolean mUU = true;
			
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
		
		final ThreadPool pool = ThreadPool.getInstance();
		pool.submit(this);
	}
	
	protected String setUrl(String url) {		
		final Resources resources = mContext.getResources();
		if (mUU) {
			final String host = resources.getString(R.string.request_uu_host);
			final String imei = getIMEI();
			if (imei == null)
				return host + url;
			else
				return host + url + imei;
		} else {
			final String host = resources.getString(R.string.request_host);
			return host + url;
		}		
	}
	
	protected String setUrl(int resId) {
		final Context context = mGlobalObject.getContext();
		final Resources resources = context.getResources();
		if (mUU) {
			final String host = resources.getString(R.string.request_uu_host);
			final String url = resources.getString(resId);
			final String imei = getIMEI();
			if (imei == null)
				return host + url;
			else
				return host + url + imei;
		} else {
			final String host = resources.getString(R.string.request_host);
			final String url = resources.getString(resId);
			return host + url;
		}		
	}
	
	private String getIMEI() {
		DeviceInfo deviceInfo = mGlobalObject.getDeviceInfo();
		if (deviceInfo == null)
			return null;			
		else
			return deviceInfo.getDeviceId();
	}
	
	protected boolean checkSuccess(JSONObject json) {
		try {
			int code = json.getInt("code");
			if (code == 0)
				return true;
			else
				return false;
		} catch (JSONException e) {
			Log.e("checkSuccess", e.toString());
			return false;
		}
	}
	
	protected JSONObject getContent(EntityResponse resp) {
		JSONObject json = null;
		
		try {
			final String body = resp.getBody();
			json = new JSONObject(body);
		} catch (JSONException e) {
			Log.e("getContent", e.toString());
			return null;
		}
		
		if (!checkSuccess(json))
			return null;
		
		try {
			return json.getJSONObject("content");			
		} catch (JSONException e) {
			Log.e("getContent", e.toString());
			return null;
		}
	}
	
	protected boolean checkString(String s) {
		if (s != null && s.length() > 0)
			return true;
		else
			return false;
	}

	@Override
	public void run() {
		if (mUU)
			mEntityRequest.setCharset("GBK");
		else
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
