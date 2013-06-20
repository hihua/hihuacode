package com.apps.game.market.request.app;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.request.RequestBase;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.util.Numeric;

public abstract class RequestApp extends RequestBase {
	protected RequestCallBackApp mCallBack;
	protected String mBody = "";
	protected long mId;
			
	public RequestApp() {
		
	}
	
	public RequestApp(long id) {
		mId = id;
	}
	
	public void setCallBackApp(RequestCallBackApp callBack) {
		mCallBack = callBack;
	}
	
	public void request(long postion, long count) {
		
	}
	
	public void request(long id, long postion, long count) {
		
	}
	
	@Override
	protected void onMessage(Message msg) {
		switch (msg.what) {
			case 0: {				
				@SuppressWarnings("unchecked")
				final List<EntityApp> list = (List<EntityApp>) msg.obj;
				if (mCallBack != null)
					mCallBack.onCallBackApp(list, false);
			}
			break;
			
			case 1: {
				if (mCallBack != null)
					mCallBack.onCallBackApp(null, true);
			}
			break;
			
			case 2: {
				if (mCallBack != null)
					mCallBack.onCallBackApp(null, false);				
			}
			break;			
		}		
	}
	
	private List<EntityApp> parseUU(JSONObject content) {
		if (content == null)
			return null;
		
		JSONArray arrays = null;
		
		try {
			arrays = content.getJSONArray("game");			
		} catch (JSONException e) {
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		if (arrays == null)
			return null;
		
		final FileManager fileManager = mGlobalObject.getFileManager(); 
		final List<EntityApp> list = new Vector<EntityApp>();
		final int length = arrays.length();
		for (int i = 0;i < length;i++) {
			JSONObject array = null;
			
			try {
				array = arrays.getJSONObject(i);
			} catch (JSONException e) {				
				Log.e(getClass().getName(), e.toString());
				continue;
			}
			
			if (array == null)
				continue;
			
			try {
				final String id = array.getString("id");
				final String name = array.getString("name");
				final String logo = array.getString("logo");
				final String pricetye = array.getString("pricetye");
				final String packagename = array.getString("package");
				final String size = array.getString("size");
				final String downs = array.getString("downs");
				final String score = array.getString("score");
				final String url = array.getString("url");
				
				if (!Numeric.isNumber(id) || !checkString(name) || !Numeric.isNumber(size) || !checkString(packagename) || !checkString(logo) || !checkString(url))
					continue;
				
				final long bytes = Long.parseLong(size);
				final EntityApp entityApp = new EntityApp();
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
						mGlobalData.addDownloadApp(entityApp);
						if (len == bytes)
							entityApp.setStatus(EnumAppStatus.INSTALL);
						else
							entityApp.setStatus(EnumAppStatus.NOINSTALL);
					} else
						entityApp.setStatus(EnumAppStatus.NOINSTALL);
				}				
				
				mGlobalData.putRemoteApp(entityApp);
				list.add(entityApp);
			} catch (JSONException e) {
				Log.e(getClass().getName(), e.toString());
				continue;
			}
		}
		
		if (list.size() > 0)
			return list;
		else
			return null;
	}
	
	private List<EntityApp> parseGfan(EntityResponse resp) {
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
		final Element parent = root.element("products");
		if (parent == null)
			return null;
				
		final Iterator<Element> elements = parent.elementIterator("product");
		if (elements != null) {
			final FileManager fileManager = mGlobalObject.getFileManager(); 
			final List<EntityApp> list = new Vector<EntityApp>();
			while (elements.hasNext()) {
				try {
					final EntityApp entityApp = new EntityApp();
					final Element element = elements.next();
					Attribute attr = element.attribute("p_id");
					if (attr == null)
						continue;
					
					String text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setPid(Long.parseLong(text));
															
					attr = element.attribute("name");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setName(text);
					
					attr = element.attribute("price");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
										
					if (text.equals("0"))
						entityApp.setPrice(mContext.getString(R.string.app_free));
					else
						entityApp.setPrice(mContext.getString(R.string.app_fee));
					
					attr = element.attribute("rating");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setDcount(Long.parseLong(text));
					
					attr = element.attribute("icon_url");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setIcon(text);
					
					attr = element.attribute("app_size");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					long size = Long.parseLong(text);							
					entityApp.setSize(size);
					
					attr = element.attribute("ratings_count");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setPcount(Long.parseLong(text));
										
					attr = element.attribute("packagename");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setPackageName(text);
					if (mGlobalData.appInstalled(text))
						entityApp.setStatus(EnumAppStatus.INSTALLED);
					else {
						final long length = fileManager.appExists(text);
						if (length > -1) {
							mGlobalData.addDownloadApp(entityApp);
							if (length == size)
								entityApp.setStatus(EnumAppStatus.INSTALL);
							else
								entityApp.setStatus(EnumAppStatus.NOINSTALL);
						} else
							entityApp.setStatus(EnumAppStatus.NOINSTALL);
					}
											
					if (entityApp.getPcount() % 2 == 0)
						entityApp.setRating((float) (entityApp.getPcount() % 5 + 0.5));
					else
						entityApp.setRating((float) (entityApp.getPcount() % 5 + 1));
					
					mGlobalData.putRemoteApp(entityApp);
					list.add(entityApp);					
				} catch (Exception e) {
					Log.e(getClass().getName(), e.toString());
				}
			}
			
			if (list.size() > 0)
				return list;
			else
				return null;
		} else
			return null;
	}
	
	protected List<EntityApp> parse(EntityResponse resp) {
		if (mUU) {
			JSONObject content = getContent(resp);
			return parseUU(content);
		} else
			return parseGfan(resp);
	}
}
