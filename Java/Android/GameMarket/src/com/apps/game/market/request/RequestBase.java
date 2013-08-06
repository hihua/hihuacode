package com.apps.game.market.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

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
import com.apps.game.market.util.Numeric;
import com.apps.game.market.util.ThreadPool;

@SuppressLint("HandlerLeak")
public abstract class RequestBase implements Runnable, Callback {	
	protected final GlobalObject mGlobalObject;
	protected final GlobalData mGlobalData;
	protected final HttpClass mHttpClass;	
	protected Handler mHandler;
	protected Context mContext;
	private EntityRequest mEntityRequest;
	private final String mKey = "GameMarket_UU";
	protected final boolean mUU = true;
	private final char End = 0x03;
	private final char Over = 0x04;
				
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
	
	protected String setSpUrl(int resId) {
		final Context context = mGlobalObject.getContext();
		final Resources resources = context.getResources();
		final String host = resources.getString(R.string.request_sp_host);
		final String url = resources.getString(resId);
		return host + url;
	}
	
	protected String setString(int v) {
		return String.valueOf(v);
	}
	
	protected String setString(String s) {
		if (s == null)
			return "";
		else {
			try {
				final String charsetName = mEntityRequest.getCharset();
				return URLEncoder.encode(s, charsetName);
			} catch (UnsupportedEncodingException e) {
				return "";
			}			
		}
	}
	
	protected String setString(boolean b) {
		if (b)
			return "1";
		else
			return "0";
	}
	
	protected String getIMEI() {
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
	
	protected boolean checkSuccess(EntityResponse resp) {
		JSONObject json = null;
		
		try {
			final String body = resp.getBody();
			json = new JSONObject(body);
		} catch (JSONException e) {			
			return false;
		}
		
		if (checkSuccess(json))
			return true;
		else
			return false;
	}
	
	protected JSONObject getContent(EntityResponse resp) {
		JSONObject json = null;
		
		try {
			final String body = resp.getBody();
			json = new JSONObject(body);
		} catch (JSONException e) {			
			return null;
		}
		
		if (!checkSuccess(json))
			return null;
		
		try {
			return json.getJSONObject("content");			
		} catch (JSONException e) {			
			return null;
		}
	}
	
	protected boolean setKey(Map<String, String> tree) {
		final StringBuilder sb = new StringBuilder();
		for (final Entry<String, String> entry : tree.entrySet()) {
			final String keys = entry.getKey();
			final String values = entry.getValue();
			
			if (keys == null)
				continue;
			
			sb.append(keys);
			sb.append(":");			
			if (values != null)
				sb.append(values);
			
			sb.append(End);
		}
		
		sb.append(Over);
		sb.append(mKey);
		
		final String charsetName = mEntityRequest.getCharset();
		final String md5 = Numeric.md5(sb.toString(), charsetName);
		if (md5 != null) {
			tree.put("key", md5);
			return true;
		} else
			return false;
	}
	
	protected String setRequest(Map<String, String> tree) {
		final StringBuilder sb = new StringBuilder();
		for (final Entry<String, String> entry : tree.entrySet()) {
			final String keys = entry.getKey();
			final String values = entry.getValue();
			
			if (keys == null)
				continue;
			
			sb.append("&");
			sb.append(keys);
			sb.append("=");
			if (values != null)
				sb.append(values);						
		}
		
		if (sb.length() > 0) {
			sb.deleteCharAt(0);		
			return sb.toString();
		} else
			return null;
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
