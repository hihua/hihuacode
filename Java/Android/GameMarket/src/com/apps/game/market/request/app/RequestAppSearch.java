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
		mBody = String.format(mContent, mKeyword, postion, count);
		request();
	}

	@Override
	protected void onTask(EntityRequest req) {
		req.setString(false);
		req.setUrl(setUrl(mResId));
		req.setBody(mBody);
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			List<EntityApp> list = parse(resp);
			resp.close();
			if (list != null) {
				Message msg = mHandler.obtainMessage();
				msg.what = 0;
				msg.obj = list;
				mHandler.sendMessage(msg);
			} else
				mHandler.sendEmptyMessage(1);
		} else
			mHandler.sendEmptyMessage(2);
	}
}
