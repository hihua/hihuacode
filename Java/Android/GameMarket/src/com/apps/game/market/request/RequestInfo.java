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
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.request.callback.RequestCallBackInfo;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.util.Numeric;

public class RequestInfo extends RequestBase {
	private RequestCallBackInfo mCallBack;
	private final int mResId = R.string.request_detail;
	private String mBody = "";
	private final long mId;
	private final String mContent = "<request version=\"2\" local_version=\"-1\"><p_id>%d</p_id><source_type>0</source_type></request>";
		
	public RequestInfo(RequestCallBackInfo callBack, long id) {
		mCallBack = callBack;
		mId = id;
	}
	
	public void request() {		
		if (mUU)
			mBody = "&id=" + mId;
		else
			mBody = String.format(mContent, mId);
		
		super.request();
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
		if (msg.what == 0) {
			final EntityApp entityApp = (EntityApp) msg.obj;
			mCallBack.onCallBackInfo(entityApp);
		} else
			mCallBack.onCallBackInfo(null);
	}
	
	private void reqUU(EntityRequest req) {		
		String url = setUrl(R.string.request_uu_detail);
		url += mBody;
		
		EntityApp entityApp = null;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null)
			entityApp = parse(resp);
		
		if (entityApp != null) {
			final Message msg = new Message();
			msg.what = 0;
			msg.obj = entityApp;
			mHandler.sendMessage(msg);
		} else
			mHandler.sendEmptyMessage(1);
	}
	
	private void reqGfan(EntityRequest req) {
		EntityApp entityApp = null;
		
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null)
			entityApp = parse(resp);
		
		if (entityApp != null) {
			final Message msg = new Message();
			msg.what = 0;
			msg.obj = entityApp;
			mHandler.sendMessage(msg);
		} else
			mHandler.sendEmptyMessage(1);
	}
	
	private EntityApp parseUU(JSONObject content) {
		if (content == null)
			return null;
		
		final FileManager fileManager = mGlobalObject.getFileManager(); 
		final EntityApp entityApp = new EntityApp();
		
		try {
			final String id = content.getString("id");
			final String name = content.getString("name");
			final String logo = content.getString("logo");
			final String pricetye = content.getString("pricetye");
			final String packagename = content.getString("package");
			final String size = content.getString("size");
			final String downs = content.getString("downs");
			final String score = content.getString("score");
			final String url = content.getString("url");			
			final String res = content.getString("res");
			
			if (!Numeric.isNumber(id) || !checkString(name) || !Numeric.isNumber(size) || !checkString(packagename) || !checkString(logo) || !checkString(url))
				return null;
			
			final long bytes = Long.parseLong(size);			
			entityApp.setPid(Long.parseLong(id));
			entityApp.setName(name);
			entityApp.setSize(bytes);
			entityApp.setPackageName(packagename);
			entityApp.setIcon(logo);
			entityApp.setUrl(url);
			
			final String free = mContext.getString(R.string.app_free);
			final String fee = mContext.getString(R.string.app_fee);
			if (checkString(pricetye)) {					
				if (pricetye.indexOf(free) > -1)
					entityApp.setPrice(free);
				else
					entityApp.setPrice(fee);
			} else
				entityApp.setPrice(free);					
			
			if (Numeric.isNumber(downs))
				entityApp.setDcount(Long.parseLong(downs));
			else
				entityApp.setDcount(0);
			
			if (Numeric.isNumber(score))
				entityApp.setPcount(Long.parseLong(score));
			else
				entityApp.setPcount(0);
			
			if (entityApp.getPcount() % 2 == 0)
				entityApp.setRating((float) (entityApp.getPcount() % 5 + 0.5));
			else
				entityApp.setRating((float) (entityApp.getPcount() % 5 + 1));
			
			if (mGlobalData.appInstalled(packagename))
				entityApp.setStatus(EnumAppStatus.INSTALLED);
			else {
				final long len = fileManager.appExists(packagename);
				if (len > -1) {					
					if (len == bytes)
						entityApp.setStatus(EnumAppStatus.INSTALL);
					else
						entityApp.setStatus(EnumAppStatus.NOINSTALL);
				} else
					entityApp.setStatus(EnumAppStatus.NOINSTALL);
			}
			
			if (checkString(res))
				entityApp.setDesc(res);
			
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
			entityApp.setImages(images);
		
		entityApp.setDetail(true);
		return entityApp;
	}
	
	private EntityApp parseGfan(EntityResponse resp) {
		final InputStream stream = resp.getStream();
		Document document = null;
		
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(stream);			
		} catch (DocumentException e) {			
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		final Element root = document.getRootElement();
		final Element parent = root.element("product");
		if (parent == null)
			return null;
		
		final EntityApp entityApp = new EntityApp();
		
		Attribute attr = parent.attribute("long_description");
		if (attr != null) {
			String text = attr.getText();
			if (text != null)
				entityApp.setDesc(text);
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
			entityApp.setImages(images);
		
		entityApp.setDetail(true);
		return entityApp;
	}
	
	private EntityApp parse(EntityResponse resp) {
		if (mUU) {
			JSONObject content = getContent(resp);
			return parseUU(content);
		} else
			return parseGfan(resp);
	}
}
