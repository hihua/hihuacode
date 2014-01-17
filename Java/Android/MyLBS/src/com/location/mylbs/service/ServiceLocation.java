package com.location.mylbs.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.location.mylbs.entity.EntityLocation;
import com.location.mylbs.entity.EntityLocations;
import com.location.mylbs.entity.EntityRelation;
import com.location.mylbs.request.RequestLocation;
import com.location.mylbs.util.DateTime;
import com.location.mylbs.util.ThreadPool;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;

public class ServiceLocation extends Service implements BDLocationListener {
	private EntityRelation mEntityRelation;
	private EntityLocations mEntityLocations;
	private LocationClient mLocationClient;
	private ThreadPool mThreadPool;
	private RequestLocation mRequestLocation;	
	private BinderLocation mBinderLocation;
	private int mStatus = -1;
	private final Object Lock = new Object();
				
	@Override
	public IBinder onBind(final Intent intent) {
		if (mBinderLocation == null)
			mBinderLocation = new BinderLocation();
		
		return mBinderLocation;
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
		if (checkStatus())
			startLocation();
		
		super.onStart(intent, startId);
	}

	@Override
	public void onReceiveLocation(final BDLocation location) {		
		if (location != null) {
			final int loctype = location.getLocType();
			if (loctype == 61 || loctype == 65 || loctype == 66 || loctype == 161) {
				if (mEntityLocations == null)
					mEntityLocations = new EntityLocations();
						
				final long clientTime = DateTime.getNow();
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				final float radius = location.getRadius();
				final float direction = location.getDerect();
				
				mEntityLocations.setClientTime(clientTime);
				mEntityLocations.setLatitude(latitude);
				mEntityLocations.setLongitude(longitude);
				mEntityLocations.setRadius(radius);
				mEntityLocations.setDirection(direction);
				
				synchronized (Lock) {
					if (mThreadPool == null)
						mThreadPool = new ThreadPool();
				}
							
				if (mRequestLocation == null)
					mRequestLocation = new RequestLocation(this, mThreadPool);
				
				mRequestLocation.request(mEntityRelation, mEntityLocations);				
			}						
		}
		
		if (mLocationClient != null) {
			mLocationClient.stop();
			mLocationClient = null;
		}
	}

	@Override
	public void onReceivePoi(final BDLocation location) {
		
	}
	
	public int getStatus() {
		if (mStatus == -1) {
			if (mEntityRelation == null)
				mEntityRelation = EntityRelation.getRelation();
			
			if (mEntityRelation != null) {
				final boolean status = mEntityRelation.getStatus();
				if (status)
					mStatus = 1;
				else
					mStatus = 0;
			}
		}	
		
		return mStatus;		
	}
	
	public boolean setStatus(final boolean status) {
		if (status)
			mStatus = 1;
		else
			mStatus = 0;
				
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();
		
		if (mEntityRelation != null) {		
			mEntityRelation.setStatus(status);
			return EntityRelation.writeRelation(mEntityRelation);
		} else
			return false;
	}
			
	private void startLocation() {
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();			
		
		if (mEntityRelation != null) {						
			if (mLocationClient == null)
				mLocationClient = EntityLocation.initLocation(this, 0, this);						
			
			EntityLocation.requestLocation(mLocationClient);
		}
	}		
				
	private boolean checkStatus() {		
		if (getAirplaneMode()) {						
			if (getStatus() == 1)
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
	
	private boolean getNetworkAvailable() {
		final ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm != null) {
			final NetworkInfo network = cm.getActiveNetworkInfo();
			if (network != null)
				return network.isAvailable();
		}
		
		return false;	
	}
	
	private void setMobileData(final boolean enable) {
		final ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);    
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
	
	public class BinderLocation extends Binder {
		
		public ServiceLocation getService() {
			return ServiceLocation.this;
		}						
	}
}
