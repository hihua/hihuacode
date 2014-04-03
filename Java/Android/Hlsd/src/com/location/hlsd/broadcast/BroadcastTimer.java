package com.location.hlsd.broadcast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.location.hlsd.R;
import com.location.hlsd.entity.EntityLocation;
import com.location.hlsd.entity.EntityLocations;
import com.location.hlsd.entity.EntityRelation;
import com.location.hlsd.handle.HandleLocation;
import com.location.hlsd.request.RequestLocation;
import com.location.hlsd.util.DateTime;
import com.location.hlsd.util.ThreadPool;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

public class BroadcastTimer extends BroadcastReceiver implements AMapLocationListener, MKGeneralListener, HandleLocation {	
	private EntityRelation mEntityRelation;
	private EntityLocations mEntityLocations;
	private LocationManagerProxy mLocationManagerProxy;
	private ThreadPool mThreadPool;
	private RequestLocation mRequestLocation;
	private Context mContext;
	private BMapManager mBMapManager;
	
	@Override
	public void onReceive(final Context context, final Intent intent) {		
		mContext = context;
		if (checkStatus())
			startLocation();
	}	
	
	@Override
	public void onLocationChanged(final Location location) {
		
	}

	@Override
	public void onProviderDisabled(final String provider) {
		
	}

	@Override
	public void onProviderEnabled(final String provider) {
		
	}

	@Override
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {
		
	}

	@Override
	public void onLocationChanged(final AMapLocation location) {
		try {
			mLocationManagerProxy.removeUpdates(this);
			if (location != null) {
				if (mEntityLocations == null)
					mEntityLocations = new EntityLocations();
						
				final long clientTime = DateTime.getNow();
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				mEntityLocations.setMap("amap");
				
//				if (mBMapManager == null) {
//					final String appkey = mContext.getString(R.string.baidu_appkey);	
//					final Context context = mContext.getApplicationContext();
//					mBMapManager = new BMapManager(context);
//					if (!mBMapManager.init(appkey, this))
//						mBMapManager = null;
//				}
//				
//				if (mBMapManager != null) {
//					GeoPoint geoPoint = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
//					geoPoint = CoordinateConvert.fromGcjToBaidu(geoPoint);
//					if (geoPoint != null) {
//						latitude = (double) geoPoint.getLatitudeE6() / (double) 1E6;
//						longitude = (double) geoPoint.getLongitudeE6() / (double) 1E6;
//						mEntityLocations.setMap("bmap");
//					}				
//				}			
				
				mEntityLocations.setClientTime(clientTime);
				mEntityLocations.setLatitude(latitude);
				mEntityLocations.setLongitude(longitude);
				
				if (location.hasAccuracy()) {
					final float radius = location.getAccuracy();
					mEntityLocations.setRadius(radius);
				} else
					mEntityLocations.setRadius(-1);
				
				if (location.hasBearing()) {
					final float direction = location.getBearing();
					mEntityLocations.setDirection(direction);
				} else
					mEntityLocations.setDirection(-1);		
				
				if (mThreadPool == null)
					mThreadPool = new ThreadPool();
				
				if (mRequestLocation == null)
					mRequestLocation = new RequestLocation(mContext, mThreadPool, this);
				
				mRequestLocation.request(mEntityRelation, mEntityLocations);	
			}	
		} catch (final Exception e) {
			
		}			
	}
	
	@Override
	public void onGetNetworkState(final int state) {
		
	}

	@Override
	public void onGetPermissionState(final int state) {
		
	}	
	
	@Override
	public void onLocation(final boolean success) {
		
	}	
		
	private void startLocation() {
		try {
			if (mEntityRelation == null)
				mEntityRelation = EntityRelation.getRelation();			
			
			if (mEntityRelation != null) {						
				if (mLocationManagerProxy == null)
					mLocationManagerProxy = EntityLocation.initLocation(mContext);
				
				final long second = 5 * 60 * 1000;
				EntityLocation.requestLocation(mLocationManagerProxy, second, 0, this);			
			}
		} catch (final Exception e) {
			
		}		
	}
	
	private boolean checkStatus() {		
		if (getAirplaneMode()) {						
			if (getStatus())
				setAirplaneMode(false);
			
			return false;
		} else {
			if (getNetworkAvailable())
				return true;
			else {
				setMobileData(true);
				return false;
			}
		}	
	}
	
	private boolean getStatus() {
		final EntityRelation relation = EntityRelation.getRelation();
		if (relation != null) {
			final boolean status = relation.getStatus();
			return status;
		}
		
		return false;
	}
	
	private boolean getAirplaneMode() {
		final ContentResolver contentResolver = mContext.getContentResolver();
		final int airplaneMode = Settings.System.getInt(contentResolver, Settings.System.AIRPLANE_MODE_ON, 0);
		if (airplaneMode == 1)
			return true;
		else
			return false;
	}
	
	private void setAirplaneMode(final boolean enable) {
		final ContentResolver contentResolver = mContext.getContentResolver();
		Settings.System.putInt(contentResolver, Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
		final Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enable);
		mContext.sendBroadcast(intent);
	}
	
	private boolean getNetworkAvailable() {
		final Object object = mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		final ConnectivityManager cm = (ConnectivityManager) object;
		if (cm != null) {
			final NetworkInfo network = cm.getActiveNetworkInfo();
			if (network != null)
				return network.isAvailable();
		}
		
		return false;	
	}
	
	private void setMobileData(final boolean enable) {		
		final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);    
	    Class<?> cmClass = null;
	    Field field = null;    
	    Class<?> iClass = null;     
	    Method setMobileDataEnabled = null;
	    Object object = null;
	    
	    try {
	    	String name = cm.getClass().getName();
	    	cmClass = Class.forName(name);
	    	field = cmClass.getDeclaredField("mService");    
	    	field.setAccessible(true);
	    	object = field.get(cm);
	    	name = object.getClass().getName();
	    	iClass = Class.forName(name);
	    	setMobileDataEnabled = iClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    	setMobileDataEnabled.setAccessible(true);
	    	setMobileDataEnabled.invoke(object, enable);    
	    } catch (final ClassNotFoundException e) {
	    	
	    } catch (final NoSuchFieldException e) {
	    	
	    } catch (final SecurityException e) {
	    	
	    } catch (final NoSuchMethodException e) {
	    	
	    } catch (final IllegalArgumentException e) {
	    	
	    } catch (final IllegalAccessException e) { 
	    	
	    } catch (final InvocationTargetException e) {  
	    	
	    }
	}	
}
