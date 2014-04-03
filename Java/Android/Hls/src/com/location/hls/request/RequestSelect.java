package com.location.hls.request;

import java.io.InputStream;

import android.content.Context;
import android.os.Message;

import com.location.hls.R;
import com.location.hls.entity.EntityRequest;
import com.location.hls.entity.EntityResponse;
import com.location.hls.entity.EntitySelect;
import com.location.hls.handle.HandleSelect;
import com.location.hls.util.ThreadPool;

public final class RequestSelect extends RequestBase {
	private HandleSelect mHandle;
	private StringBuilder mParameter = new StringBuilder(); 

	public RequestSelect(final Context context, final ThreadPool threadPool, final HandleSelect handle) {		
		super(context, threadPool);
		mHandle = handle;
	}
	
	public void request(final String name) {
		mParameter.setLength(0);
		mParameter.append("name=");
		mParameter.append(name);
		
		super.request();
	}

	@Override
	public boolean handleMessage(final Message msg) {		
		if (mHandle != null) {
			if (msg.obj != null) {
				final EntitySelect entitySelect = (EntitySelect) msg.obj;
				mHandle.onSelect(entitySelect);
			} else
				mHandle.onSelect(null);
		}
		
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		final String url = setUrl(R.string.request_select, mParameter.toString());
		entityRequest.setUrl(url);
		entityRequest.setString(false);
		
		EntitySelect entitySelect = null;
		final EntityResponse entityResponse = mHttpClass.request(entityRequest);
		if (entityResponse != null) {
			final InputStream in = entityResponse.getStream();
			entitySelect = EntitySelect.parseSelect(in);
			entityResponse.close();
		}
		
		if (mHandler != null) {
			final Message msg = mHandler.obtainMessage(0, entitySelect);
			msg.sendToTarget();
		}		
	}

	@Override
	public void destroy() {
		mHandler = null;
	}
}
