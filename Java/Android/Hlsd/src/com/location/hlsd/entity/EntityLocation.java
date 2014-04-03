package com.location.hlsd.entity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.location.hlsd.R;

import android.content.Context;

public class EntityLocation {
	private String color;
	private List<EntityLocations> locations;	

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public List<EntityLocations> getLocations() {
		return locations;
	}

	public void setLocations(final List<EntityLocations> locations) {
		this.locations = locations;
	}
	
	public static LocationManagerProxy initLocation(final Context context) {
		final LocationManagerProxy locationManagerProxy = LocationManagerProxy.getInstance(context);
		locationManagerProxy.setGpsEnable(true);
		return locationManagerProxy;
	}
	
	public static void requestLocation(final LocationManagerProxy locationManagerProxy, final long minTime, final float minDistance, final AMapLocationListener listener) {
		locationManagerProxy.requestLocationUpdates(LocationProviderProxy.AMapNetwork, minTime, minDistance, listener);		
	}

	public static LocationClient initLocation(final Context context, final int scanSpan, final BDLocationListener listener) {
		final String appkey = context.getString(R.string.baidu_appkey);
		final LocationClientOption locationClientOption = new LocationClientOption();		
		locationClientOption.setScanSpan(scanSpan);		
		locationClientOption.setCoorType("bd09ll");
		locationClientOption.disableCache(true);

		final LocationClient locationClient = new LocationClient(context);		
		locationClient.setLocOption(locationClientOption);
		locationClient.registerLocationListener(listener);
		locationClient.start();		
		return locationClient;
	}
	
	public static void requestLocation(final LocationClient locationClient) {
		if (!locationClient.isStarted())
			locationClient.start();
		
		locationClient.requestLocation();
	}
			
	public static EntityLocation parseLocation(final InputStream in) {
		Document document = null;
						
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);
		} catch (final DocumentException e) {
			return null;
		}
		
		if (document == null)
			return null;
		
		final Element root = document.getRootElement();
		final Element nCode = root.element("code");
		if (nCode == null)
			return null;
		
		final String code = nCode.getText();
		if (code == null || !code.equals("0"))
			return null;
		
		final Element nColor = root.element("color");
		if (nColor == null)
			return null;
		
		final String color = nColor.getText();
		if (color == null)
			return null;
		
		try {
			final Iterator<Element> nLocations = root.elementIterator("locations");
			if (nLocations != null) {
				final EntityLocation entityLocation = new EntityLocation();
				final List<EntityLocations> locations = new Vector<EntityLocations>();
				while (nLocations.hasNext()) {
					final Element nLocation = nLocations.next();					
					final Element nServerTime = nLocation.element("server_time");
					final Element nClientTime = nLocation.element("client_time");
					final Element nLatitude = nLocation.element("latitude");
					final Element nLongitude = nLocation.element("longitude");
					final Element nRadius = nLocation.element("radius");
					final Element nDirection = nLocation.element("direction");
					final Element nMap = nLocation.element("map");
					
					if (nServerTime != null && nClientTime != null && nLatitude != null && nLongitude != null && nRadius != null && nDirection != null && nMap != null) {
						final String serverTime = nServerTime.getText();
						final String clientTime = nClientTime.getText();
						final String latitude = nLatitude.getText();
						final String longitude = nLongitude.getText();
						final String radius = nRadius.getText();
						final String direction = nDirection.getText();
						final String map = nMap.getText();
						
						final EntityLocations entityLocations = new EntityLocations();
						entityLocations.setServerTime(Long.parseLong(serverTime));
						entityLocations.setClientTime(Long.parseLong(clientTime));
						entityLocations.setLatitude(Double.parseDouble(latitude));
						entityLocations.setLongitude(Double.parseDouble(longitude));
						entityLocations.setRadius(Float.parseFloat(radius));
						entityLocations.setDirection(Float.parseFloat(direction));
						entityLocations.setMap(map);
						locations.add(entityLocations);
					}
				}
				
				if (locations.size() > 0) {
					entityLocation.setColor(color);
					entityLocation.setLocations(locations);
					return entityLocation;
				}
			}			
		} catch (final Exception e) {
			
		}
		
		return null;
	}
}
