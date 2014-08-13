package com.location.hlsd.entity;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class EntityLocation {
	
	public static LocationClient initLocation(final Context context, final BDLocationListener listener) {
		final LocationClientOption locationOption = new LocationClientOption();
		locationOption.setCoorType("bd09ll");
		locationOption.setLocationMode(LocationMode.Hight_Accuracy);
		locationOption.setIsNeedAddress(false);
		locationOption.setNeedDeviceDirect(true);
				
		final LocationClient locationClient = new LocationClient(context);
		locationClient.setLocOption(locationOption);
		locationClient.registerLocationListener(listener);
		locationClient.start();
		
		return locationClient;
	}
}
