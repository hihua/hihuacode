package com.apps.game.market.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.EntitySearchWord;
import com.apps.game.market.request.callback.RequestCallBackSearchWord;

public class RequestSearchWord extends RequestBase {
	private RequestCallBackSearchWord mCallBack;
	private String mWord = "";
		
	public RequestSearchWord(RequestCallBackSearchWord callBack) {
		mCallBack = callBack;
	}
	
	public void request(String word) {		
		try {
			mWord = URLEncoder.encode(word, "UTF-8");
			super.request();
		} catch (UnsupportedEncodingException e) {
			Log.e("requestSearchWord", e.toString());
		}
	}

	@Override
	protected void onTask(EntityRequest req) {
		String url = mContext.getString(R.string.request_searchword) + mWord;
		req.setString(true);
		req.setUrl(url);
		
		List<EntitySearchWord> list = null;
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			list = parse(resp);
			resp.close();
		}
		
		Message msg = mHandler.obtainMessage();
		if (list != null) {
			msg.what = 0;
			msg.obj = list;
		} else
			msg.what = 1;
		
		mHandler.sendMessage(msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onMessage(Message msg) {
		int what = msg.what;
		if (what == 0) {
			List<EntitySearchWord> list = (List<EntitySearchWord>) msg.obj; 
			mCallBack.onCallBackSearchWord(list);
		} else
			mCallBack.onCallBackSearchWord(null);
	}
	
	private List<EntitySearchWord> parse(EntityResponse resp) {
		JSONArray array = null;
		try {
			String body = resp.getBody();
			array = new JSONArray(body);			
		} catch (JSONException e) {
			Log.e("parseSearchWord", e.toString());
			return null;
		}
		
		List<EntitySearchWord> list = new Vector<EntitySearchWord>();
		int length = array.length();
		for (int i = 0;i < length;i++) {							
			try {
				JSONObject json = array.getJSONObject(i);
				Object obj = json.get("hotWords");
				if (obj == null)
					continue;
				
				String hotWords = String.valueOf(obj);
				
				obj = json.get("resultNumber");
				if (obj == null)
					continue;
				
				Long resultNumber = json.getLong("resultNumber");
				
				EntitySearchWord searchWord = new EntitySearchWord();
				searchWord.setHotWords(hotWords);
				searchWord.setResultNumber(resultNumber);
				list.add(searchWord);
			} catch (JSONException e) {
				Log.e("parseSearchWord", e.toString());
				continue;
			}
		}
		
		if (list.size() > 0)
			return list;
		else
			return null;
	}	
}
