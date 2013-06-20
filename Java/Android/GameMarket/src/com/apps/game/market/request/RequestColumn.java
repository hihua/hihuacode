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
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityColumnClass;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.app.RequestAppHot;
import com.apps.game.market.request.app.RequestAppNew;
import com.apps.game.market.request.app.RequestAppRecommend;
import com.apps.game.market.request.app.RequestAppTag;
import com.apps.game.market.request.callback.RequestCallBackColumn;
import com.apps.game.market.util.Numeric;

public class RequestColumn extends RequestBase {
	private RequestCallBackColumn mCallBack;
	private final int mResId = R.string.request_column;
	private final String mBody = "<request version=\"2\"><screen_size>480#800</screen_size><platform>10</platform><feature></feature><feature_type>cpu</feature_type><match_type>1</match_type></request>";
	
	public RequestColumn(RequestCallBackColumn callBack) {
		mCallBack = callBack;
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
			mCallBack.onCallBackColumn(true);
		else
			mCallBack.onCallBackColumn(false);				
	}
	
	private void reqUU(EntityRequest req) {
		final String url = setUrl(R.string.request_uu_column);
				
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		
		List<EntityColumn> list = null;
		final EntityResponse resp = mHttpClass.request(req);
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
	
	private void reqGfan(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		
		List<EntityColumn> list = null;
		final EntityResponse resp = mHttpClass.request(req);
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
	
	private void setSubColumn(EntityColumn entityColumn) {
		final String body = "&id=" + entityColumn.getId();		
		String url = setUrl(R.string.request_uu_subcolumn);
		url += body;
		
		final EntityRequest req = new EntityRequest();
		req.setCharset("GBK");
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			JSONObject content = getContent(resp);
			parseSubColumn(content, entityColumn);
			resp.close();
		}
	}
	
	private void parseSubColumn(JSONObject content, EntityColumn entityColumn) {		
		if (content == null)
			return;
		
		JSONArray arrays = null;
		
		try {
			arrays = content.getJSONArray("lanmu");			
		} catch (JSONException e) {
			Log.e(getClass().getName(), e.toString());
			return;
		}
		
		final int length = arrays.length();
		if (length == 0)
			return;
		
		if (length == 1) {
			JSONObject array = null;
			
			try {
				array = arrays.getJSONObject(0);
			} catch (JSONException e) {				
				Log.e(getClass().getName(), e.toString());				
			}
			
			if (array != null) {
				try {
					final String id = array.getString("id");
					final String name = array.getString("name");
					
					if (!Numeric.isNumber(id) || !checkString(name))
						return;
										
					entityColumn.setDesc(name);
					entityColumn.setRequest(new RequestAppRecommend(Long.parseLong(id)));
				} catch (JSONException e) {
					Log.e(getClass().getName(), e.toString());
				}			
			}
		} else {
			entityColumn.setDesc(entityColumn.getName());
			final List<EntityColumnClass> columnClass = new Vector<EntityColumnClass>();
			
			for (int i = 0;i < length;i++) {
				JSONObject array = null;
				
				try {
					array = arrays.getJSONObject(i);
				} catch (JSONException e) {				
					Log.e(getClass().getName(), e.toString());				
				}
				
				if (array != null) {
					try {
						final String id = array.getString("id");
						final String name = array.getString("name");
						
						if (!Numeric.isNumber(id) || !checkString(name))
							return;										
						
						final EntityColumnClass entityColumnClass = new EntityColumnClass();
						entityColumnClass.setName(name);
						
						switch (i) {
							case 0: {
								final RequestApp request = new RequestAppHot(1);
								entityColumnClass.setRequest(request);
							}
							break;
							
							case 1: {
								final RequestApp request = new RequestAppHot(2);
								entityColumnClass.setRequest(request);
							}
							break;
							
							case 2: {
								final RequestApp request = new RequestAppNew(0);
								entityColumnClass.setRequest(request);
							}
							break;
							
							case 3: {
								final RequestApp request = new RequestAppNew(1);
								entityColumnClass.setRequest(request);
							}
							break;
						}
						
						columnClass.add(entityColumnClass);
					} catch (JSONException e) {
						Log.e(getClass().getName(), e.toString());
					}					
				}				
			}
						
			if (columnClass.size() > 0) {
				EntityColumnClass entityColumnClass = columnClass.get(0);
				entityColumn.setRequest(entityColumnClass.getRequest());
				entityColumn.setColumnClass(columnClass);
			}
		}		
	}
	
	private List<EntityColumn> parseUU(JSONObject content) {
		if (content == null)
			return null;
		
		JSONArray arrays = null;
		
		try {
			arrays = content.getJSONArray("top");			
		} catch (JSONException e) {
			Log.e(getClass().getName(), e.toString());
			return null;
		}
		
		if (arrays == null)
			return null;
		
		final List<EntityColumn> list = new Vector<EntityColumn>();		
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
				
				if (!Numeric.isNumber(id) || !checkString(name))
					continue;
				
				final EntityColumn entityColumn = new EntityColumn();
				if (i == 0)
					entityColumn.setSingle(true);
				else
					entityColumn.setSingle(false);
										
				entityColumn.setId(Long.parseLong(id));
				entityColumn.setName(name);
				setSubColumn(entityColumn);
				list.add(entityColumn);
			} catch (JSONException e) {
				Log.e(getClass().getName(), e.toString());				
			}
		}
		
		if (list.size() > 0)
			return list;
		else
			return null;
	}
	
	private List<EntityColumn> parseGfan(EntityResponse resp) {
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
		final Iterator<Element> elements = root.elementIterator("category");
		if (elements != null) {
			while (elements.hasNext()) {
				final Element element = elements.next();
				Attribute attr = element.attribute("category_code");
				if (attr != null) {
					String text = attr.getText();
					if (text != null && text.equals("game")) {						
						final Iterator<Element> childs = element.elementIterator("sub_category");						
						if (childs != null) {
							final List<EntityColumn> list = new Vector<EntityColumn>();
							int index = 0;
							while (childs.hasNext()) {
								try {
									final EntityColumn entityColumn = new EntityColumn();								
									final Element child = childs.next();
									attr = child.attribute("category_id");
									if (attr == null) 
										continue;
									
									text = attr.getText();
									if (text == null)
										continue;
									
									final long id = Long.parseLong(text);
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
										entityColumn.setDesc("排行榜");
										
										final List<EntityColumnClass> columnClass = new Vector<EntityColumnClass>();
										RequestApp request = null;
																				
										if (index == 1) {
											request = new RequestAppTag(189);
											EntityColumnClass entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周下载");
											entityColumnClass.setRequest(request);
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周总排名");
											entityColumnClass.setRequest(new RequestAppTag(159));
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周评分");
											entityColumnClass.setRequest(new RequestAppTag(395));
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周总评分");
											entityColumnClass.setRequest(new RequestAppTag(62));
											columnClass.add(entityColumnClass);
										} else {
											request = new RequestAppTag(374);
											EntityColumnClass entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周下载");
											entityColumnClass.setRequest(request);
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周总排名");
											entityColumnClass.setRequest(new RequestAppTag(381));
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周评分");
											entityColumnClass.setRequest(new RequestAppTag(58));
											columnClass.add(entityColumnClass);
											
											entityColumnClass = new EntityColumnClass();
											entityColumnClass.setName("本周总评分");
											entityColumnClass.setRequest(new RequestAppTag(442));											
										}
										
										entityColumn.setColumnClass(columnClass);
										entityColumn.setRequest(request);
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
	
	private List<EntityColumn> parse(EntityResponse resp) {
		if (mUU) {
			JSONObject content = getContent(resp);
			return parseUU(content);
		} else
			return parseGfan(resp);
	}
}
