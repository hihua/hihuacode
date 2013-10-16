package com.location.mylbs.request;

import com.location.mylbs.R;
import com.location.mylbs.entity.EntityLocations;
import com.location.mylbs.entity.EntityRelation;
import com.location.mylbs.entity.EntityRequest;
import com.location.mylbs.entity.EntityResponse;
import com.location.mylbs.util.ThreadPool;

import android.content.Context;
import android.os.Message;

public final class RequestLocation extends RequestBase {	
	private StringBuilder mParameter = new StringBuilder(); 

	public RequestLocation(final Context context, final ThreadPool threadPool) {
		super(context, threadPool);
	}
	
	public void request(final EntityRelation entityRelation, final EntityLocations entityLocations) {
		final String from = entityRelation.getFrom();
		final long clientTime = entityLocations.getClientTime();
		final double latitude = entityLocations.getLatitude();
		final double longitude = entityLocations.getLongitude();
		final float radius = entityLocations.getRadius();
		final float direction = entityLocations.getDirection();
		
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
		
		super.request();
	}

	@Override
	public boolean handleMessage(final Message msg) {
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		final String url = setUrl(R.string.request_location, mParameter.toString());
		entityRequest.setUrl(url);
		
		final EntityResponse entityResponse = mHttpClass.request(entityRequest);
		if (entityResponse != null)
			entityResponse.close();
	}
	
	@Override
	public void destroy() {
		
	}
}
