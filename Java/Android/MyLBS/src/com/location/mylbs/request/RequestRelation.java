package com.location.mylbs.request;

import java.io.InputStream;

import com.location.mylbs.R;
import com.location.mylbs.entity.EntityLocation;
import com.location.mylbs.entity.EntityRelation;
import com.location.mylbs.entity.EntityRequest;
import com.location.mylbs.entity.EntityResponse;
import com.location.mylbs.handle.HandleRelation;
import com.location.mylbs.util.ThreadPool;

import android.content.Context;
import android.os.Message;

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
				
		final Message msg = mHandler.obtainMessage(0, entityLocation);
		msg.sendToTarget();				
	}		
	
	@Override
	public void destroy() {
		mHandle = null;
	}
}
