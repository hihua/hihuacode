package com.apps.game.market.request;

import java.util.Map;
import java.util.TreeMap;

import android.os.Message;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityEntry;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.request.callback.RequestCallBackEntry;

public class RequestEntry extends RequestBase {
	private final RequestCallBackEntry mCallBack;
	private final EntityEntry mEntityEntry;
	private final String mRecordVersionName; 
	
	public RequestEntry(RequestCallBackEntry callBack, EntityEntry entityEntry, String recordVersionName) {
		mCallBack = callBack;
		mEntityEntry = entityEntry;
		mRecordVersionName = recordVersionName;
	}
	
	@Override
	protected void onTask(EntityRequest req) {
		req.setCharset("UTF-8");
		final String imei = getIMEI();
		String url = setSpUrl(R.string.request_sp_entry);
		final Map<String, String> tree = new TreeMap<String, String>();
		tree.put("entry_table", setString(mEntityEntry.getEntryTable()));
		tree.put("entry_open", setString(mEntityEntry.getEntryOpen()));
		tree.put("record_imei", imei);
		tree.put("record_version_name", mRecordVersionName);
		
		if (!setKey(tree)) {
			mHandler.sendEmptyMessage(1);
			return;
		}
		
		tree.put("record_imei", setString(imei));
		tree.put("record_version_name", setString(mRecordVersionName));
				
		final String s = setRequest(tree);
		url += "?" + s;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null && parse(resp))
			mHandler.sendEmptyMessage(0);
		else
			mHandler.sendEmptyMessage(1);
	}

	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0)
			mCallBack.onCallBackEntry(true, mEntityEntry);
		else
			mCallBack.onCallBackEntry(false, mEntityEntry);
	}
	
	private boolean parse(EntityResponse resp) {
		return checkSuccess(resp);
	}
}
