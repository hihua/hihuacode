package com.apps.game.market.request;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.request.callback.RequestCallBackDetail;

public class RequestDetail extends RequestBase {
	private RequestCallBackDetail mCallBack;
	private final int mResId = R.string.request_detail;
	private EntityApp mEntityApp;
	private String mBody = "";
	private final String mContent = "<request version=\"2\" local_version=\"-1\"><p_id>%d</p_id><source_type>0</source_type></request>";
	
	public RequestDetail(RequestCallBackDetail callBack) {
		mCallBack = callBack;
	}
	
	public void request(EntityApp entityApp) {
		mEntityApp = entityApp;
		if (mUU)
			mBody = "&id=" + entityApp.getPid();
		else
			mBody = String.format(mContent, entityApp.getPid());
		
		request();
	}

	@Override
	protected void onTask(EntityRequest req) {
		if (mUU)
			reqUU(req);
		else
			reqGfan(req);
	}

	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackDetail(true);
		else
			mCallBack.onCallBackDetail(false);
	}
	
	private void reqUU(EntityRequest req) {		
		String url = setUrl(R.string.request_uu_detail);
		url += mBody;
		
		boolean success = false;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null)
			success = parse(resp);
		
		if (success)
			mHandler.sendEmptyMessage(0);
		else
			mHandler.sendEmptyMessage(1);
	}
	
	private void reqGfan(EntityRequest req) {
		boolean success = false;
		
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null)
			success = parse(resp);
		
		if (success)
			mHandler.sendEmptyMessage(0);
		else
			mHandler.sendEmptyMessage(1);
	}
	
	private boolean parseUU(JSONObject content) {
		if (content == null)
			return false;
		
		try {
			final String res = content.getString("res");
			if (checkString(res))
				mEntityApp.setDesc(res);
			
		} catch (JSONException e) {
			Log.e(getClass().getName(), e.toString());
		}
		
		final List<String> images = new Vector<String>();
		int i = 1;
		
		while (true) {
			try {
				final String pic = content.getString("pic" + i);
				if (checkString(pic)) {
					images.add(pic);
					i++;
				} else
					break;				
			} catch (JSONException e) {				
				break;
			}
		}
		
		if (images.size() > 0)
			mEntityApp.setImages(images);
		
		mEntityApp.setDetail(true);
		return true;
	}
	
	private boolean parseGfan(EntityResponse resp) {
		final InputStream stream = resp.getStream();
		Document document = null;
		
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(stream);			
		} catch (DocumentException e) {			
			Log.e(getClass().getName(), e.toString());
			return false;
		}
		
		final Element root = document.getRootElement();
		final Element parent = root.element("product");
		if (parent == null)
			return false;
		
		Attribute attr = parent.attribute("long_description");
		if (attr != null) {
			String text = attr.getText();
			if (text != null)
				mEntityApp.setDesc(text);
		}
		
		final List<String> images = new Vector<String>();
		int i = 1;
				
		while (true) {
			attr = parent.attribute("screenshot_" + i);
			if (attr != null) {
				final String text = attr.getText();
				if (text != null && text.length() > 0)
					images.add(text);
				
				i++;
			} else
				break;			
		}
		
		if (images.size() > 0)
			mEntityApp.setImages(images);
		
		mEntityApp.setDetail(true);
		return true;
	}
	
	private boolean parse(EntityResponse resp) {
		if (mUU) {
			JSONObject content = getContent(resp);
			return parseUU(content);
		} else
			return parseGfan(resp);
	}
}
