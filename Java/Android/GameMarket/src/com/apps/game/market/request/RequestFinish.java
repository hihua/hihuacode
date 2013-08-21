package com.apps.game.market.request;

import android.os.Message;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.util.DeviceInfo;

public class RequestFinish extends RequestBase {
	private String mBody = "";
	
	public RequestFinish() {
		
	}
	
	public void request(long pid, String type) {
		final DeviceInfo device = mGlobalObject.getDeviceInfo();		
		mBody = "&type=" + type + "&gid=" + pid + "&net=" + device.getNet(mContext) + "&mt=" + device.getMobileType();
		super.request();
	}

	@Override
	protected void onTask(EntityRequest req) {
		String url = setUrl(R.string.request_uu_app_status);
		url += mBody;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		mHttpClass.request(req);		
	}

	@Override
	protected void onMessage(Message msg) {
		
	}
}
