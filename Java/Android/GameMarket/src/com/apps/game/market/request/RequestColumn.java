package com.apps.game.market.request;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityColumnClass;
import com.apps.game.market.request.app.RequestAppHot;
import com.apps.game.market.request.app.RequestAppNew;
import com.apps.game.market.request.app.RequestAppRecommend;
import com.apps.game.market.request.callback.RequestCallBackColumn;

public class RequestColumn extends RequestBase {
	private RequestCallBackColumn mCallBack;	
	private final int mResId = R.string.request_column;
	private final String mBody = "<request version=\"2\"><screen_size>480#800</screen_size><platform>10</platform><feature></feature><feature_type>cpu</feature_type><match_type>1</match_type></request>";
	
	public RequestColumn(RequestCallBackColumn callBack) {
		mCallBack = callBack;
	}
	
	@Override
	protected void onTask(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		
		List<EntityColumn> list = null;
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			list = parse(resp);
			resp.close();			
		}
		
		if (list != null) {				
			mGlobalData.addColumns(list);
			mHandler.sendEmptyMessage(0);
		} else
			mHandler.sendEmptyMessage(1);
	}

	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackColumn(true);
		else
			mCallBack.onCallBackColumn(false);
	}
	
	private List<EntityColumn> parse(EntityResponse resp) {
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
		Iterator<Element> elements = root.elementIterator("category");
		if (elements != null) {
			while (elements.hasNext()) {
				Element element = elements.next();
				Attribute attr = element.attribute("category_code");
				if (attr != null) {
					String text = attr.getText();
					if (text != null && text.equals("game")) {						
						Iterator<Element> childs = element.elementIterator("sub_category");						
						if (childs != null) {
							List<EntityColumn> list = new Vector<EntityColumn>();
							int index = 0;
							while (childs.hasNext()) {
								try {
									EntityColumn entityColumn = new EntityColumn();								
									Element child = childs.next();
									attr = child.attribute("category_id");
									if (attr == null) 
										continue;
									
									text = attr.getText();
									if (text == null)
										continue;
									
									long id = Long.parseLong(text);
									entityColumn.setId(id);
									
									attr = child.attribute("category_name");
									if (attr == null)
										continue;
									
									text = attr.getText();
									if (text == null)
										continue;
																	
									entityColumn.setName(text);
									
									if (index == 0) {
										entityColumn.setSingle(true);
										entityColumn.setDesc("小编吐血推荐");
										entityColumn.setRequest(new RequestAppRecommend());
									} else {
										entityColumn.setSingle(false);
										List<EntityColumnClass> columnClass = new Vector<EntityColumnClass>();
										EntityColumnClass entityColumnClass = new EntityColumnClass();
										entityColumnClass.setName("按下载");
										Map<String, String> urls = new LinkedHashMap<String, String>();
										
										if (index == 1) {
											urls.put("本周", "tagApps?id=189");
											urls.put("总排名", "tagApps?id=77");
										} else {
											urls.put("本周", "tagApps?id=374");
											urls.put("总排名", "tagApps?id=381");
										}
										
										entityColumnClass.setUrls(urls);
										columnClass.add(entityColumnClass);
										
										entityColumnClass = new EntityColumnClass();
										entityColumnClass.setName("按评分");
										urls = new LinkedHashMap<String, String>();
										
										if (index == 1) {
											urls.put("本周", "tagApps?id=395");
											urls.put("总排名", "tagApps?id=62");
											entityColumn.setRequest(new RequestAppHot());
										} else {
											urls.put("本周", "tagApps?id=395");
											urls.put("总排名", "tagApps?id=442");
											entityColumn.setRequest(new RequestAppNew());
										}
										
										entityColumnClass.setUrls(urls);
										columnClass.add(entityColumnClass);
										entityColumn.setColumnClass(columnClass);
									}
									
									list.add(entityColumn);
									index++;
									
									if (index == 3)
										break;	
								} catch (Exception e) {
									Log.e(getClass().getName(), e.toString());
								}														
							}
							
							if (list.size() > 0)
								return list;
							else
								return null;
						}					
					}
				}
			}
		}
		
		return null;
	}
}
