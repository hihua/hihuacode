package com.location.hlsd.broadcast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

public class BroadcastTimer extends BroadcastReceiver implements BDLocationListener, HandleLocation {	
	private EntityRelation mEntityRelation;
	private EntityLocations mEntityLocations;
	private LocationClient mLocationClient;
	private ThreadPool mThreadPool;
	private RequestLocation mRequestLocation;
	
	@Override
	public void onReceive(final Context context, final Intent intent) {	
		Log.e("hls", "onReceive");
		if (checkStatus(context))
			startLocation(context);		
	}
	
	public boolean getStatus() {
		final EntityRelation relation = EntityRelation.getRelation();
		if (relation != null) {
			final boolean status = relation.getStatus();
			return status;
		}
		
		return false;	
	}
	
	private void startLocation(final Context context) {
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();			
		
		if (mEntityRelation != null)
			mLocationClient = EntityLocation.initLocation(context, this);		
	}
	
	private boolean checkStatus(final Context context) {
		if (getAirplaneMode(context)) {						
			if (getStatus())
				setAirplaneMode(context, false);
			
			return false;
		} else {
			if (getNetworkAvailable(context))
				return true;
			else {
				setMobileData(context, true);
				return false;
			}
		}	
	}
	
	private boolean getAirplaneMode(final Context context) {
		final int airplaneMode = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
		if (airplaneMode == 1)
			return true;
		else
			return false;
	}
	
	private void setAirplaneMode(final Context context, final boolean enable) {
		Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
		final Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enable);
		context.sendBroadcast(intent);
	}
	
	private boolean getNetworkAvailable(final Context context) {
		final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			final NetworkInfo network = cm.getActiveNetworkInfo();
			if (network != null)
				return network.isAvailable();
		}
		
		return false;	
	}
	
	private void setMobileData(final Context context, final boolean enable) {
		final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
	    Class<?> cmClass = null;
	    Field field = null;     
	    Object object = null;
	    Class<?> iClass = null;     
	    Method setMobileDataEnabled = null;
	    
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

	@Override
	public void onLocation(final boolean success) {
		
	}

	@Override
	public void onReceiveLocation(final BDLocation location) {
		Log.e("hls", "onReceiveLocation");
		if (location != null) {
			final int locType = location.getLocType();
			Log.e("hls", "onReceiveLocation " + locType);
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
				
				Log.e("hls", "request");
				mRequestLocation.request(mEntityRelation, mEntityLocations);				
			}
		}
		
		if (mLocationClient != null) {
			mLocationClient.stop();
			mLocationClient = null;
		}
	}	
}
