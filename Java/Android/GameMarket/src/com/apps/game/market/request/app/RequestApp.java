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

import android.os.Message;
import android.util.Log;

import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.request.RequestBase;
import com.apps.game.market.request.callback.RequestCallBackApp;

public abstract class RequestApp extends RequestBase {
	protected RequestCallBackApp mCallBack;
	protected String mBody = "";	
	
	public RequestApp() {
		
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
				List<EntityApp> list = (List<EntityApp>) msg.obj;
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
	
	protected List<EntityApp> parse(EntityResponse resp) {
		InputStream stream = resp.getStream();
		Document document = null;
		
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(stream);			
		} catch (DocumentException e) {			
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		Element root = document.getRootElement();
		Element parent = root.element("products");
		if (parent == null)
			return null;
				
		Iterator<Element> elements = parent.elementIterator("product");
		if (elements != null) {
			List<EntityApp> list = new Vector<EntityApp>();
			while (elements.hasNext()) {
				try {
					EntityApp entityApp = new EntityApp();
					Element element = elements.next();
					Attribute attr = element.attribute("p_id");
					if (attr == null)
						continue;
					
					String text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setPid(Long.parseLong(text));
					
					attr = element.attribute("sub_category");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityApp.setType(text);
					
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
						entityApp.setPrice("免费");
					else
						entityApp.setPrice("收费");
					
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
						entityApp.setStatus(EnumAppStatus.INSTALL);
					else
						entityApp.setStatus(EnumAppStatus.NOINSTALL);
											
					if (entityApp.getPcount() % 2 == 0)
						entityApp.setRating((float) (entityApp.getDcount() % 5 + 0.5));
					else
						entityApp.setRating((float) (entityApp.getDcount() % 5 + 1));
					
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
}
