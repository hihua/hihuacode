package com.location.hls.request;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.os.Message;

import com.location.hls.R;
import com.location.hls.entity.EntityRequest;
import com.location.hls.entity.EntityResponse;
import com.location.hls.entity.EntitySelect;
import com.location.hls.handle.HandleSelects;
import com.location.hls.util.ThreadPool;

public final class RequestSelects extends RequestBase {
	private final HandleSelects mHandle;
	
	public RequestSelects(final Context context, final ThreadPool threadPool, final HandleSelects handle) {		
		super(context, threadPool);		
		mHandle = handle;
	}
		
	@Override
	public boolean handleMessage(final Message msg) {		
		if (mHandle != null) {
			if (msg.obj != null) {
				final List<EntitySelect> entitySelects = (List<EntitySelect>) msg.obj;
				mHandle.onSelects(entitySelects);
			} else
				mHandle.onSelects(null);
		}
		
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		final String url = setUrl(R.string.request_selects, null);
		entityRequest.setUrl(url);
		entityRequest.setString(false);
		
		List<EntitySelect> entitySelects = null;
		final EntityResponse entityResponse = mHttpClass.request(entityRequest);
		if (entityResponse != null) {
			final InputStream in = entityResponse.getStream();
			entitySelects = EntitySelect.parseSelects(in);
			entityResponse.close();
		}
		
		if (mHandler != null) {
			final Message msg = mHandler.obtainMessage(0, entitySelects);
			msg.sendToTarget();
		}		
	}

	@Override
	public void destroy() {
		mHandler = null;
	}	
}
