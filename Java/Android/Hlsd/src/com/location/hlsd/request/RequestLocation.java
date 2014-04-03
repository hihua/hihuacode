package com.location.hlsd.request;

import android.content.Context;
import android.os.Message;

import com.location.hlsd.R;
import com.location.hlsd.entity.EntityLocations;
import com.location.hlsd.entity.EntityRelation;
import com.location.hlsd.entity.EntityRequest;
import com.location.hlsd.entity.EntityResponse;
import com.location.hlsd.handle.HandleLocation;
import com.location.hlsd.util.ThreadPool;

public final class RequestLocation extends RequestBase {
	private final StringBuilder mParameter = new StringBuilder();
	private final HandleLocation mHandleLocation;

	public RequestLocation(final Context context, final ThreadPool threadPool, final HandleLocation handleLocation) {
		super(context, threadPool);
		mHandleLocation = handleLocation;
	}
	
	public void request(final EntityRelation entityRelation, final EntityLocations entityLocations) {
		final String from = entityRelation.getFrom();
		final long clientTime = entityLocations.getClientTime();
		final double latitude = entityLocations.getLatitude();
		final double longitude = entityLocations.getLongitude();
		final float radius = entityLocations.getRadius();
		final float direction = entityLocations.getDirection();
		final String map = entityLocations.getMap();
		
		mParameter.setLength(0);
		mParameter.append("name=");
		mParameter.append(from);
		mParameter.append("&client_time=");
		mParameter.append(clientTime);
		mParameter.append("&latitude=");
		mParameter.append(latitude);
		mParameter.append("&longitude=");
		mParameter.append(longitude);
		mParameter.append("&radius=");
		mParameter.append(radius);
		mParameter.append("&direction=");
		mParameter.append(direction);
		mParameter.append("&map=");
		mParameter.append(map);
		
		super.request();
	}

	@Override
	public boolean handleMessage(final Message msg) {
		final int what = msg.what;
		if (what == 0)
			mHandleLocation.onLocation(true);
		else
			mHandleLocation.onLocation(false);
		
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		final String url = setUrl(R.string.request_location, mParameter.toString());
		entityRequest.setUrl(url);
				
		final EntityResponse entityResponse = mHttpClass.request(entityRequest);
		if (entityResponse != null) {			
			entityResponse.close();
			if (mHandleLocation != null)
				mHandler.sendEmptyMessage(0);
		} else {
			if (mHandleLocation != null)
				mHandler.sendEmptyMessage(1);
		}
	}

	@Override
	public void destroy() {
		
	}
}
