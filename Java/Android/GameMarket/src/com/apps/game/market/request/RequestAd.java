package com.apps.game.market.request;

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

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.request.callback.RequestCallBackAd;

public class RequestAd extends RequestBase {
	private RequestCallBackAd mCallBack;	
	private final int mResId = R.string.request_ad;
	private final String mBody = "<request version=\"2\"><screen_size>480#800</screen_size><platform>10</platform><feature></feature><feature_type>cpu</feature_type><match_type>1</match_type></request>";
	private long mColumnId = 0;
	
	public RequestAd(RequestCallBackAd callBack) {
		mCallBack = callBack;
	}
	
	public void request(long columnId) {
		mColumnId = columnId; 
		super.request();
	}
	
	@Override
	protected void onTask(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		
		List<EntityAd> list = null;
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			list = parse(resp);
			resp.close();									
		}
		
		if (list != null) {
			mGlobalData.putAds(mColumnId, list);
			mHandler.sendEmptyMessage(0);
		} else
			mHandler.sendEmptyMessage(1);
	}
	
	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackAd(true);
		else
			mCallBack.onCallBackAd(false);
	}
	
	private List<EntityAd> parse(EntityResponse resp) {
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
		Iterator<Element> elements = root.elementIterator("recommend");
		if (elements != null) {
			List<EntityAd> list = new Vector<EntityAd>();
			while (elements.hasNext()) {
				try {
					EntityAd ad = new EntityAd();
					Element element = elements.next();
					Attribute attr = element.attribute("name");
					if (attr == null)
						continue;
					
					String text = attr.getText();
					if (text == null)
						continue;
					
					ad.setName(text);
					attr = element.attribute("pic_url");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					ad.setUrl(text);
					list.add(ad);
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
