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
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.request.callback.RequestCallBackTag;

public class RequestTag extends RequestBase {
	private RequestCallBackTag mCallBack;	
	private final int mResId = R.string.request_tag;
	private final String mBody = "<request version=\"2\"><screen_size>480#800</screen_size><match_type>1</match_type><platform>10</platform></request>";
	
	public RequestTag(RequestCallBackTag callBack) {
		mCallBack = callBack;
	}

	@Override
	protected void onTask(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		
		List<EntityTag> list = null;
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			list = parse(resp);
			resp.close();									
		}
		
		if (list != null) {
			mGlobalData.addTags(list);
			mHandler.sendEmptyMessage(0);
		} else
			mHandler.sendEmptyMessage(1);
	}

	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackTag(true);
		else
			mCallBack.onCallBackTag(false);
	}
	
	private List<EntityTag> parse(EntityResponse resp) {
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
		Iterator<Element> elements = root.elementIterator("group");
		if (elements != null) {
			while (elements.hasNext()) {
				Element element = elements.next();
				Attribute attr = element.attribute("group_id");
				if (attr != null) {
					String text = attr.getText();
					if (text != null && text.equals("4")) {
						Iterator<Element> childs = element.elementIterator("tag");
						if (childs != null) {
							List<EntityTag> list = new Vector<EntityTag>();
							while (childs.hasNext()) {
								try {
									EntityTag entityTag = new EntityTag();
									Element child = childs.next();
									attr = child.attribute("tag_id");
									if (attr == null)
										continue;
									
									text = attr.getText();
									long id = Long.parseLong(text);
									entityTag.setId(id);
									
									attr = child.attribute("tag_name");
									if (attr == null)
										continue;
									
									text = attr.getText();
									if (text == null)
										continue;
																	
									entityTag.setName(text);
									
									attr = child.attribute("app_count");
									if (attr == null)
										continue;
									
									text = attr.getText();
									if (text == null)
										continue;
																	
									long count = Long.parseLong(text);
									entityTag.setCount(count);
									
									int index = 0;
									for (EntityTag tag : list) {
										if (count > tag.getCount())
											break;
										else
											index++;
									}
									
									list.add(index, entityTag);	
								} catch (Exception e) {
									Log.e(getClass().getName(), e.toString());
								}															
							}
							
							if (list.size() > 0) {
								if (list.size() > 10)
									return list.subList(0, 10);
								else
									return list;
							}														
						}
						
						break;
					}
				}
			}
		}
		
		return null;
	}
}
