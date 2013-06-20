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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.util.Numeric;

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
		if (mUU)
			reqUU(req);
		else
			reqGfan(req);
	}
	
	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackAd(true);
		else
			mCallBack.onCallBackAd(false);
	}
	
	private void reqUU(EntityRequest req) {
		final String body = "&id=" + mColumnId;		
		String url = setUrl(R.string.request_uu_ad);
		url += body;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		
		List<EntityAd> list = null;
		final EntityResponse resp = mHttpClass.request(req);
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
	
	private void reqGfan(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		
		List<EntityAd> list = null;
		final EntityResponse resp = mHttpClass.request(req);
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
	
	private List<EntityAd> parseUU(JSONObject content) {
		if (content == null)
			return null;
		
		JSONArray arrays = null;
		
		try {
			arrays = content.getJSONArray("topad");			
		} catch (JSONException e) {
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		if (arrays == null)
			return null;
		
		final List<EntityAd> list = new Vector<EntityAd>();		
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
				final String pic = array.getString("pic");
				final String gameid = array.getString("gameid");
				final String res = array.getString("res");
				
				if (!Numeric.isNumber(id) || !checkString(pic) || !Numeric.isNumber(gameid))
					continue;
				
				final EntityAd ad = new EntityAd();
				ad.setId(Long.parseLong(id));
				ad.setUrl(pic);
				ad.setGameId(Long.parseLong(gameid));
				ad.setName(res);
				list.add(ad);
			} catch (JSONException e) {
				Log.e(getClass().getName(), e.toString());
			}
		}
		
		if (list.size() > 0)
			return list;
		else
			return null;
	}
	
	private List<EntityAd> parseGfan(EntityResponse resp) {
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
		final Iterator<Element> elements = root.elementIterator("recommend");
		if (elements != null) {
			final List<EntityAd> list = new Vector<EntityAd>();
			while (elements.hasNext()) {
				try {
					final EntityAd ad = new EntityAd();
					final Element element = elements.next();
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
	
	private List<EntityAd> parse(EntityResponse resp) {
		if (mUU) {
			JSONObject content = getContent(resp);
			return parseUU(content);
		} else
			return parseGfan(resp);
	}	
}
