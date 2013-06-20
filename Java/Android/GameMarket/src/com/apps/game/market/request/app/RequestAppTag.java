package com.apps.game.market.request.app;

import java.util.List;

import android.os.Message;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.app.EntityApp;

public class RequestAppTag extends RequestApp {
	private long mTagId = 0;
	private final int mResId = R.string.request_app_tag;
	private final String mContent = "<request version=\"2\"><start_position>%d</start_position><screen_size>480#800</screen_size><platform>10</platform><match_type>1</match_type><tag_id>%d</tag_id><size>%d</size></request>";

	public RequestAppTag() {
		
	}
	
	public RequestAppTag(final long tagId) {
		mTagId = tagId;
	}
		
	@Override
	public void request(long postion, long count) {
		request(mTagId, postion, count);
	}

	@Override
	public void request(long id, long postion, long count) {
		if (mUU)
			mBody = String.format("&id=%d&p=%d&ps=%d", id, postion, count);
		else
			mBody = String.format(mContent, postion, id, count);
		
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
		String url = setUrl(R.string.request_uu_app_tag);		
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
