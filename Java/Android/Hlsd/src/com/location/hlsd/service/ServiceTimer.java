package com.location.hlsd.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.Settings;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.location.hlsd.entity.EntityLocation;
import com.location.hlsd.entity.EntityLocations;
import com.location.hlsd.entity.EntityRelation;
import com.location.hlsd.handle.HandleLocation;
import com.location.hlsd.request.RequestLocation;
import com.location.hlsd.util.DateTime;
import com.location.hlsd.util.ThreadPool;

public class ServiceTimer extends Service implements BDLocationListener, HandleLocation {
	private EntityRelation mEntityRelation;
	private EntityLocations mEntityLocations;
	private LocationClient mLocationClient;
	private ThreadPool mThreadPool;
	private RequestLocation mRequestLocation;	
				
	@Override
	public IBinder onBind(final Intent intent) {		
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {		
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.stop();
		
		mLocationClient = null;
		
		if (mThreadPool != null) {
			mThreadPool.close();
			mThreadPool = null;
		}
		
		super.onDestroy();
	}

	@Override
	public void onStart(final Intent intent, final int startId) {		
		super.onStart(intent, startId);
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.stop();			
				
		mLocationClient = null;
		
		if (checkStatus())		
			startLocation();
		else
			stopSelf();
	}

	@Override
	public void onReceiveLocation(final BDLocation location) {
		if (mLocationClient != null) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		
		if (location != null) {
			final int locType = location.getLocType();			
			if (locType == 61 || locType == 65 || locType == 66 || locType == 161) {
				if (mEntityLocations == null)
					mEntityLocations = new EntityLocations();
						
				final long clientTime = DateTime.getNow();
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				final float radius = location.getRadius();
				final float direction = location.getDirection();
								
				mEntityLocations.setClientTime(clientTime);
				mEntityLocations.setLatitude(latitude);
				mEntityLocations.setLongitude(longitude);
				mEntityLocations.setRadius(radius);
				mEntityLocations.setDirection(direction);
				mEntityLocations.setMap("bmap");
				
				if (mThreadPool == null)
					mThreadPool = new ThreadPool();
											
				if (mRequestLocation == null)
					mRequestLocation = new RequestLocation(mThreadPool, this);
								
				mRequestLocation.request(mEntityRelation, mEntityLocations);
				return;
			}
		}
		
		stopSelf();
	}

	@Override
	public void onLocation(final boolean success) {
		stopSelf();
	}
		
	private void getRelation() {
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();
	}
			
	private void startLocation() {		
		if (mEntityRelation != null)
			mLocationClient = EntityLocation.initLocation(this, this);		
	}		
				
	private boolean checkStatus() {
		getRelation();
		
		if (mEntityRelation != null) {
			if (getAirplaneMode()) {						
				if (mEntityRelation.getStatus())
					setAirplaneMode(false);
			} else {				
				final NetworkInfo networkInfo = getNetwork();
				if (networkInfo != null) {					
					final String typeName = networkInfo.getTypeName();					
					if (typeName != null) {
						if (typeName.equalsIgnoreCase("WIFI")) {
							if (networkInfo.isAvailable() && networkInfo.isConnected())
								return true;
						} else {
							if (typeName.equalsIgnoreCase("MOBILE")) {
								if (mEntityRelation.getStatus()) {
									if (networkInfo.isConnected())										
										return true;
									 else		
										setMobileData(true);									
								}								
							}
						}						
					}					
				} else {
					if (mEntityRelation.getStatus())
						setMobileData(true);					
				}
			}
		} 
		
		return false;
	}
	
	private boolean getAirplaneMode() {
		final int airplaneMode = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
		if (airplaneMode == 1)
			return true;
		else
			return false;
	}
	
	private void setAirplaneMode(final boolean enable) {
		Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
		final Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enable);
		sendBroadcast(intent);
	}
	
	private NetworkInfo getNetwork() {		
		final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			return connectivityManager.getActiveNetworkInfo();			
		} else
			return null;	
	}
	
	private void setMobileData(final boolean enable) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);    
	    Class<?> cmClass = null;
	    Field field = null;     
	    Object object = null;
	    Class<?> iClass = null;     
	    Method setMobileDataEnabled = null;
	    
	    try {
	    	String name = connectivityManager.getClass().getName();
	    	cmClass = Class.forName(name);
	    	field = cmClass.getDeclaredField("mService");    
	    	field.setAccessible(true);
	    	object = field.get(connectivityManager);
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
