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
import com.apps.game.market.entity.app.EntityComment;
import com.apps.game.market.request.callback.RequestCallBackComment;
import com.apps.game.market.util.DateTime;

public class RequestComment extends RequestBase {	
	private RequestCallBackComment mCallBack;
	private final int mResId = R.string.request_comment;	
	private String mBody = "";
	private long mPostion = 0;
	private final String mContent = "<request version=\"2\"><start_position>%d</start_position><p_id>%d</p_id><size>%d</size></request>";
	
	public RequestComment(RequestCallBackComment callBack) {
		mCallBack = callBack;
	}
	
	public void request(long pid, long postion, long size) {
		mPostion = postion;
		mBody = String.format(mContent, postion, pid, size);		
		request();
	}

	@Override
	protected void onTask(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			final List<EntityComment> list = parse(resp);
			resp.close();
			if (list != null) {
				final Message msg = mHandler.obtainMessage();
				msg.what = 0;
				msg.obj = list;
				mHandler.sendMessage(msg);
			} else
				mHandler.sendEmptyMessage(1);
		} else
			mHandler.sendEmptyMessage(2);
	}

	@Override
	protected void onMessage(Message msg) {
		switch (msg.what) {
			case 0: {				
				@SuppressWarnings("unchecked")
				List<EntityComment> list = (List<EntityComment>) msg.obj;
				if (mCallBack != null)
					mCallBack.onCallBackComment(list, false);
			}
			break;
			
			case 1: {
				if (mCallBack != null)
					mCallBack.onCallBackComment(null, true);
			}
			break;
			
			case 2: {
				if (mCallBack != null)
					mCallBack.onCallBackComment(null, false);
				
			}
			break;
		}	
	}
	
	private List<EntityComment> parse(EntityResponse resp) {
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
		final Element parent = root.element("comments");
		if (parent == null)
			return null;
		
		Attribute attr = parent.attribute("total_size");
		if (attr == null)
			return null;
				
		long total = 0;		
		String text = attr.getText();
		if (text == null)
			return null;
		
		try {
			total = Long.parseLong(text);
		} catch (Exception e) {
			return null;
		}
		
		if (total == 0)
			return null;
		
		final Iterator<Element> elements = parent.elementIterator("comment");
		if (elements != null) {
			final List<EntityComment> list = new Vector<EntityComment>();
			while (elements.hasNext()) {
				try {
					final EntityComment entityComment = new EntityComment();
					final Element element = elements.next();
					attr = element.attribute("comment_id");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityComment.setId(Long.parseLong(text));
					
					attr = element.attribute("author");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityComment.setAuthor(text);
					
					attr = element.attribute("date");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
					
					entityComment.setDate(DateTime.getTimeStamp(Long.parseLong(text), "yyyy-MM-dd HH:mm:ss"));
					
					attr = element.attribute("comment");
					if (attr == null)
						continue;
					
					text = attr.getText();
					if (text == null)
						continue;
										
					entityComment.setComment(text);
					
					final int length = text.length();					
					float rating = length % 5;
					if (length % 2 == 0)
						rating += 1.0f;
					else
						rating += 0.5f;
										
					entityComment.setRating(rating);
					
					final long floor = total - mPostion;
					entityComment.setFloor(floor);
					mPostion++;
					
					list.add(entityComment);
				}  catch (Exception e) {
					Log.e(getClass().getName(), e.toString());
				}
			}
			
			if (list.size() > 0)
				return list;
		}
		
		return null;
	}
}
