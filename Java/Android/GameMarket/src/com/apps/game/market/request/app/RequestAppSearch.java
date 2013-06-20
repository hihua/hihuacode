package com.apps.game.market.request.app;

import java.util.List;

import android.os.Message;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;

public class RequestAppSearch extends RequestApp {
	private String mKeyword = "";
	private final int mResId = R.string.request_search;
	private final String mContent = "<request version=\"2\"><platform>10</platform><feature_type>cpu</feature_type><match_type>1</match_type><keyword>%s</keyword><screen_size>480#800</screen_size><start_position>%d</start_position><feature></feature><orderby>0</orderby><size>%d</size></request>";
	
	public RequestAppSearch(String keyword) {
		mKeyword = keyword;
	}
	
	@Override
	public void request(long postion, long count) {
		if (mUU)
			mBody = String.format("key=%s&p=%d&ps=%d", mKeyword, postion, count);
		else
			mBody = String.format(mContent, mKeyword, postion, count);
		
		request();
	}
		
	@Override
	protected void onTask(EntityRequest req) {
		if (mUU)
			reqUU(req);
		else
			reqGfan(req);
	}
	
	private void reqUU(EntityRequest req) {
		String url = setUrl(R.string.request_uu_search);
		url += mBody;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			final List<EntityApp> list = parse(resp);
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
	
	private void reqGfan(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			final List<EntityApp> list = parse(resp);
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
}
