package com.location.hls.request;

import java.io.InputStream;

import android.content.Context;
import android.os.Message;

import com.location.hls.R;
import com.location.hls.entity.EntityLocation;
import com.location.hls.entity.EntityRelation;
import com.location.hls.entity.EntityRequest;
import com.location.hls.entity.EntityResponse;
import com.location.hls.handle.HandleRelation;
import com.location.hls.util.ThreadPool;

public final class RequestRelation extends RequestBase {
	private HandleRelation mHandle;
	private boolean mAuto;
	private StringBuilder mParameter = new StringBuilder(); 

	public RequestRelation(final Context context, final ThreadPool threadPool, final HandleRelation handle) {
		super(context, threadPool);
		mHandle = handle;		
	}
	
	public void request(final EntityRelation entityRelation, final boolean auto) {
		mAuto = auto;
		
		final String to = entityRelation.getTo();		
		mParameter.setLength(0);
		mParameter.append("name=");
		mParameter.append(to);
		
		super.request();
	}

	@Override
	public boolean handleMessage(final Message msg) {
		if (mHandle != null) {
			if (msg.obj != null) {
				final EntityLocation entityLocation = (EntityLocation) msg.obj;
				mHandle.onRelation(entityLocation, mAuto);
			} else
				mHandle.onRelation(null, mAuto);
		}				
		
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		final String url = setUrl(R.string.request_relation, mParameter.toString());
		entityRequest.setUrl(url);
		entityRequest.setString(false);
		
		EntityLocation entityLocation = null;
		final EntityResponse entityResponse = mHttpClass.request(entityRequest);
		if (entityResponse != null) {
			final InputStream in = entityResponse.getStream();			
			entityLocation = EntityLocation.parseLocation(in);
			entityResponse.close();
		}
				
		if (mHandler != null) {
			final Message msg = mHandler.obtainMessage(0, entityLocation);
			msg.sendToTarget();
		}						
	}		
	
	@Override
	public void destroy() {
		mHandler = null;
	}
}
