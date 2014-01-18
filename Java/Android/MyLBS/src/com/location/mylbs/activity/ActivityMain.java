package com.location.mylbs.activity;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MKOfflineMapListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKRoutePlan;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.location.mylbs.R;
import com.location.mylbs.entity.EntityLocation;
import com.location.mylbs.entity.EntityLocations;
import com.location.mylbs.entity.EntityRelation;
import com.location.mylbs.handle.HandleRelation;
import com.location.mylbs.request.RequestRelation;
import com.location.mylbs.service.ServiceLocation;
import com.location.mylbs.service.ServiceLocation.BinderLocation;
import com.location.mylbs.util.DateTime;
import com.location.mylbs.util.ThreadPool;
import com.location.mylbs.util.TimerManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ActivityMain extends Activity implements BDLocationListener, MKOfflineMapListener, MKGeneralListener, MKMapViewListener, MKSearchListener, ServiceConnection, HandleRelation, Runnable, OnClickListener {
	private LocationClient mLocationClient;	
	private BMapManager mBMapManager;
	private MapView mMapView;
	private MKOfflineMap mMKOfflineMap;
	private MKSearch mMKSearch;
	private List<MyLocationOverlay> mMyLocationOverlays;
	private MyLocationOverlay mMyLocationOverlay;
	private RouteOverlay mRouteOverlay;
	private EntityRelation mEntityRelation;
	private RequestRelation mRequestRelation;
	private ThreadPool mThreadPool;
	private Handler mHandler;
	private LinearLayout mLayoutTime;
	private double mDefaultX = 23.125d;
	private double mDefaultY = 113.301d;
	private double mLatitude = mDefaultX;
	private double mLongitude = mDefaultY;
	private float mZoom = 12.7f;
	private boolean mStatus = false;
	private final Object Lock = new Object();
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEntityRelation = EntityRelation.getRelation();
		if (mEntityRelation != null)
			mStatus = mEntityRelation.getStatus();
		
		if (initMap()) {
			setContentView(R.layout.activity_main);
			initControl();
			initMapView();			
			initService();
		} else {
			final String content = getString(R.string.baidu_init_error);
			final Toast toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		int i = 1;
		int j = 1;
				
		MenuItem item = menu.add(Menu.NONE, Menu.FIRST + i++, j++, R.string.menu_refresh);
		item.setIcon(android.R.drawable.ic_menu_rotate);
		
		if (mStatus) {
			item = menu.add(Menu.NONE, Menu.FIRST + i++, j++, R.string.menu_network_close);
			item.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		} else {
			item = menu.add(Menu.NONE, Menu.FIRST + i++, j++, R.string.menu_network_start);
			item.setIcon(android.R.drawable.ic_menu_add);
		}
				
		item = menu.add(Menu.NONE, Menu.FIRST + i++, j++, R.string.menu_route);
		item.setIcon(android.R.drawable.ic_menu_directions);
				
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		final MenuItem item = menu.getItem(1);		
		if (mStatus) {
			item.setTitle(R.string.menu_network_close);
			item.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		} else {
			item.setTitle(R.string.menu_network_start);
			item.setIcon(android.R.drawable.ic_menu_add);
		}
					
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int id = item.getItemId();
		switch (id) {
			case Menu.FIRST + 1: {
				if (mHandler != null)
					mHandler.removeCallbacks(this);
					
				if (mMapView != null && mMyLocationOverlay != null && mRouteOverlay != null) {
					final List<Overlay> list = mMapView.getOverlays();
					if (list != null) {
						list.remove(mMyLocationOverlay);
						list.remove(mRouteOverlay);
						mMapView.refresh();
					}					
				}
									
				request(false);
				final Toast toast = Toast.makeText(this, R.string.request_relation_refresh, Toast.LENGTH_LONG);
				toast.show();
			}
			break;
			
			case Menu.FIRST + 2: {
				if (mStatus)
					mStatus = false;
				else
					mStatus = true;
				
				boolean found = false;
				final String packages = getPackageName();
				final String name = packages + ".service.ServiceLocation";
				final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		        final List<RunningServiceInfo> serviceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
		                
		        if (serviceInfos != null) {
		        	for (final RunningServiceInfo serviceInfo : serviceInfos) {                
		                final ComponentName serviceName = serviceInfo.service;
		                if (serviceName.getClassName().equals(name)) {
		                	found = true;
		                	break;
		                }
		            } 
		        }
		        
		        if (mEntityRelation == null)
	        		mEntityRelation = new EntityRelation();
		        
		        if (mEntityRelation != null)
	        		mEntityRelation.setStatus(mStatus);
		        		        
		        if (found) {
		        	final Intent intent = new Intent(this, ServiceLocation.class);		                	
	            	bindService(intent, this, BIND_AUTO_CREATE);
		        } else {
	        	  	if (EntityRelation.writeRelation(mEntityRelation)) {
		        		final Toast toast = Toast.makeText(this, R.string.status_prepare_success, Toast.LENGTH_LONG);
						toast.show();
		        	} else {
		        		final Toast toast = Toast.makeText(this, R.string.status_prepare_fail, Toast.LENGTH_LONG);
						toast.show();
		        	}	        		        	
		        }
			}
			break;
			
			case Menu.FIRST + 3: {
				if (mLocationClient == null)
					mLocationClient = EntityLocation.initLocation(this, 0, this);
				
				EntityLocation.requestLocation(mLocationClient);
				final Toast toast = Toast.makeText(this, R.string.location_my_location, Toast.LENGTH_LONG);
				toast.show();
			}
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		if (mRequestRelation != null) {
			mRequestRelation.destroy();
			mRequestRelation = null;
		}
		
		if (mThreadPool != null) {
			mThreadPool.close();
			mThreadPool = null;
		}
		
		if (mMKOfflineMap != null)
			mMKOfflineMap.destroy();
		
		if (mMapView != null)
			mMapView.destroy();

		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		
		if (mHandler != null)
			mHandler.removeCallbacks(this);

		super.onDestroy();		
	}

	@Override
	protected void onStop() {
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.stop();

		super.onStop();
	}

	@Override
	protected void onPause() {				
		if (mMapView != null)
			mMapView.onPause();

		if (mBMapManager != null)
			mBMapManager.stop();

		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mMapView != null)
			mMapView.onResume();			
				
		if (mBMapManager != null)
			mBMapManager.start();

		super.onResume();
	}
	
	private void init() {		
		mEntityRelation = EntityRelation.getRelation();		
		mHandler = new Handler();
		request(true);
	}
	
	private boolean initMap() {
		final String appkey = getString(R.string.baidu_appkey);
		mBMapManager = new BMapManager(this);
		return mBMapManager.init(appkey, this);
	}

	private void initMapView() {		
		mMapView = (MapView) findViewById(R.id.baidu_mapview);
		mMapView.setBuiltInZoomControls(true);
		mMapView.regMapViewListener(mBMapManager, this);
		
		final MapController mapController = mMapView.getController();
		mMKOfflineMap = new MKOfflineMap();
		mMKOfflineMap.init(mapController, this);
		
		mMyLocationOverlays = new Vector<MyLocationOverlay>();
		mapViewRefresh(mLatitude, mLongitude, mZoom);
	}
	
	private void initControl() {
		mLayoutTime = (LinearLayout) findViewById(R.id.layout_time);		
	}

	private void initService() {
		final String packages = getPackageName();
		final String name = packages + ".service.ServiceLocation";
		final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final List<RunningServiceInfo> serviceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
                
        if (serviceInfos != null) {
        	for (final RunningServiceInfo serviceInfo : serviceInfos) {                
                final ComponentName serviceName = serviceInfo.service;
                if (serviceName.getClassName().equals(name)) {                	
                	//bindService(new Intent(this, ServiceLocation.class), this, BIND_AUTO_CREATE);
                    return;
                }
            } 
        }
                
        TimerManager.stopAlarmManager(this, ServiceLocation.class);
        
        final long triggerAtMillis = SystemClock.elapsedRealtime() + 1 * 60 * 1000;
        final long intervalMillis = 5 * 60 * 1000;        
        TimerManager.startAlarmManager(this, ServiceLocation.class, true, AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis);
	}
	
	private void addTime(final String sColor, final EntityLocations entityLocations, final int index) {
		if (mLayoutTime == null)
			return;
						
		TextView sTextView = null;
		TextView cTextView = null;
		
		int count = mLayoutTime.getChildCount();
		if (index < count) {
			View view = mLayoutTime.getChildAt(index);
			final RelativeLayout relativeLayout = (RelativeLayout) view;
			count = relativeLayout.getChildCount();
			if (count > 0) {
				view = relativeLayout.getChildAt(0);
				sTextView = (TextView) view;
			}
			
			if (count > 1) {
				view = relativeLayout.getChildAt(1);
				cTextView = (TextView) view;
			}
		} else {
			final Resources res = getResources();				
			final RelativeLayout relativeLayout = new RelativeLayout(this);
			relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			int color = 0;
			
			if (sColor != null) {
				try {
					color = Color.parseColor(sColor);
				} catch (Exception e) {
					color = res.getColor(R.color.textview_servertime);
				}
			} else
				color = res.getColor(R.color.textview_servertime);									
			
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);			
			sTextView = new TextView(this);
			sTextView.setLayoutParams(layoutParams);
			sTextView.setTextColor(color);
			sTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
			sTextView.setClickable(true);
						
			if (sColor != null) {
				try {
					color = Color.parseColor(sColor);
				} catch (Exception e) {
					color = res.getColor(R.color.textview_clienttime);
				}
			} else
				color = res.getColor(R.color.textview_clienttime);
									
			layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);			
			cTextView = new TextView(this);
			cTextView.setLayoutParams(layoutParams);
			cTextView.setTextColor(color);
			cTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
			cTextView.setClickable(true);
			
			relativeLayout.addView(sTextView);
			relativeLayout.addView(cTextView);
			mLayoutTime.addView(relativeLayout);
		}			
		
		if (sTextView != null) {
			final long serverTime = entityLocations.getServerTime();
			final String time = DateTime.getTimeStamp(serverTime, "yyyy-MM-dd HH:mm:ss");
			sTextView.setTag(entityLocations);
			sTextView.setText(time);
			sTextView.setOnClickListener(this);			
		}
		
		if (cTextView != null) {
			final long clientTime = entityLocations.getClientTime();
			final String time = DateTime.getTimeStamp(clientTime, "yyyy-MM-dd HH:mm:ss");
			cTextView.setTag(entityLocations);
			cTextView.setText(time);
			cTextView.setOnClickListener(this);
		}		
	}
	
	private void mapViewRefresh(final double latitude, final double longitude, final float zoom) {
		if (mMapView == null)
			return;
				
		mMapView.refresh();
		final MapController	mapController = mMapView.getController();
		final GeoPoint geopoint = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
		mapController.setZoom(zoom);
		mapController.animateTo(geopoint);		
	}
	
	private void mapViewRefresh(final int latitude, final int longitude, final float zoom) {
		if (mMapView == null)
			return;
				
		mMapView.refresh();
		final MapController	mapController = mMapView.getController();
		final GeoPoint geopoint = new GeoPoint(latitude, longitude);
		mapController.setZoom(zoom);
		mapController.animateTo(geopoint);		
	}
	
	private void request(final boolean auto) {
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();
		
		if (mEntityRelation != null) {
			synchronized (Lock) {
				if (mThreadPool == null)
					mThreadPool = new ThreadPool();
			}
			
			if (mRequestRelation == null)
				mRequestRelation = new RequestRelation(this, mThreadPool, this);
			
			mRequestRelation.request(mEntityRelation, auto);			
		}		
	}
	
	@Override
	public void onGetNetworkState(final int state) {
		final String content = getString(R.string.baidu_networkstate_error, state);
		final Toast toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
		toast.show();		
	}

	@Override
	public void onGetPermissionState(final int state) {
		if (state == 0)
			return;
		
		final String content = getString(R.string.baidu_permission_error, state);
		final Toast toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
		toast.show();			
	}
	
	@Override
	public void onClickMapPoi(final MapPoi mapPoi) {
		
	}

	@Override
	public void onGetCurrentMap(final Bitmap bitmap) {
		
	}

	@Override
	public void onMapAnimationFinish() {
		
	}

	@Override
	public void onMapLoadFinish() {		
		init();
	}

	@Override
	public void onMapMoveFinish() {
		
	}	

	@Override
	public void onReceiveLocation(final BDLocation location) {
		if (mLocationClient != null)
			mLocationClient.stop();
		
		String content = null;
		
		if (location != null) {
			final int loctype = location.getLocType();
			if (loctype == 61 || loctype == 65 || loctype == 66 || loctype == 161) {
				if (mLatitude != mDefaultX || mLongitude != mDefaultY) {
					if (mMapView == null || mEntityRelation == null)
						return;
					
					if (mMKSearch == null) {
						mMKSearch = new MKSearch();
						if (!mMKSearch.init(mBMapManager, this))
							return;
					}
					
					final String defPackage = getPackageName();
					final String from = mEntityRelation.getFrom();														
					final double latitude = location.getLatitude();
					final double longitude = location.getLongitude();
					final float accuracy = location.getRadius();
					final String name = from.toLowerCase(Locale.ENGLISH) + 1;
					
					LocationData locationData = null;
					
					if (mMyLocationOverlay == null) {
						mMyLocationOverlay = new MyLocationOverlay(mMapView);
						locationData = new LocationData();						
					} else
						locationData = mMyLocationOverlay.getMyLocation();
					
					locationData.latitude = latitude;
					locationData.longitude = longitude;
					locationData.accuracy = accuracy;
					
					mMyLocationOverlay.setData(locationData);
					
					final List<Overlay> list = mMapView.getOverlays();
					if (list.indexOf(mMyLocationOverlay) == -1)
						list.add(mMyLocationOverlay);					
								
					final Resources res = getResources();
					final int id = res.getIdentifier(name, "drawable", defPackage);
					if (id > 0) {
						final Drawable drawable = res.getDrawable(id);
						mMyLocationOverlay.setMarker(drawable);				
					}
					
					mMapView.refresh();
					
					final MKPlanNode start = new MKPlanNode();
					start.pt = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
					final MKPlanNode end = new MKPlanNode();
					end.pt = new GeoPoint((int) (mLatitude * 1E6), (int) (mLongitude * 1E6));
					mMKSearch.walkingSearch(null, start, null, end);
				}
			} else
				content = getString(R.string.location_my_error, loctype);
		} else
			content = getString(R.string.location_my_fail);
		
		if (content != null) {			
			final Toast toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
			toast.show();
		}		
	}

	@Override
	public void onReceivePoi(final BDLocation location) {

	}
	
	@Override
	public void onGetOfflineMapState(final int type, final int state) {
		switch (type) {  
        case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
            break;  
            
        case MKOfflineMap.TYPE_NEW_OFFLINE:            
            break;  
            
        case MKOfflineMap.TYPE_VER_UPDATE:              
            break;  
        } 
	}

	@Override
	public void onRelation(final EntityLocation entityLocation, final boolean auto) {
		if (!auto) {
			int id = R.string.request_relation_success;			
			if (entityLocation == null)
				id = R.string.request_relation_fail;
						
			final Toast toast = Toast.makeText(this, id, Toast.LENGTH_LONG);
			toast.show();
		}
		
		if (mHandler == null)
			mHandler = new Handler();
		
		final int delayed = 2 * 60 * 1000;
		mHandler.postDelayed(this, delayed);
		
		if (mMapView == null)
			initMapView();
						
		if (mEntityRelation == null || entityLocation == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}
										
		final String to = mEntityRelation.getTo();
		if (to == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}
		
		if (mMyLocationOverlays == null)
			mMyLocationOverlays = new Vector<MyLocationOverlay>();
				
		final int size = mMyLocationOverlays.size();			
		final String defPackage = getPackageName();
		final String color = entityLocation.getColor();
		final List<EntityLocations> locations = entityLocation.getLocations();
		if (locations == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}
						
		int i = 0;
		for (final EntityLocations entityLocations : locations) {
			addTime(color, entityLocations, i);
			final int j = i + 1;
			final String name = to.toLowerCase(Locale.ENGLISH) + j;
			
			MyLocationOverlay myLocationOverlay = null;
			LocationData locationData = null;
						
			if (i < size) {
				myLocationOverlay = mMyLocationOverlays.get(i);
				locationData = myLocationOverlay.getMyLocation();
			} else {
				myLocationOverlay = new MyLocationOverlay(mMapView);
				locationData = new LocationData();				
				mMyLocationOverlays.add(myLocationOverlay);
			}
			
			if (i == 0) {
				mLatitude = entityLocations.getLatitude();
				mLongitude = entityLocations.getLongitude();
			}
						
			locationData.latitude = entityLocations.getLatitude();
			locationData.longitude = entityLocations.getLongitude();
			locationData.accuracy = entityLocations.getRadius();
			locationData.direction = entityLocations.getDirection();
			
			myLocationOverlay.setData(locationData);
			
			final List<Overlay> list = mMapView.getOverlays();
			if (list != null && list.indexOf(myLocationOverlay) == -1)
				list.add(myLocationOverlay);					
						
			final Resources res = getResources();
			final int id = res.getIdentifier(name, "drawable", defPackage);
			if (id > 0) {
				final Drawable drawable = res.getDrawable(id);
				myLocationOverlay.setMarker(drawable);				
			}
						
			i++;			
		}
				
		mapViewRefresh(mLatitude, mLongitude, 17.0f);
	}

	@Override
	public void run() {
		request(true);
	}

	@Override
	public void onServiceConnected(final ComponentName name, final IBinder binder) {
		if (binder != null) {
			final BinderLocation binderLocation = (BinderLocation) binder;
			final ServiceLocation serviceLocation = binderLocation.getService();						
			if (serviceLocation.setStatus(mStatus)) {
        		final Toast toast = Toast.makeText(this, R.string.status_prepare_success, Toast.LENGTH_LONG);
				toast.show();
        	} else {
        		final Toast toast = Toast.makeText(this, R.string.status_prepare_fail, Toast.LENGTH_LONG);
				toast.show();
        	}
						
			unbindService(this);
		}		
	}

	@Override
	public void onServiceDisconnected(final ComponentName name) {
		
	}

	@Override
	public void onClick(final View view) {
		final Object object = view.getTag();
		if (object != null) {
			final EntityLocations entityLocations = (EntityLocations) object;
			final double latitude = entityLocations.getLatitude();
			final double longitude = entityLocations.getLongitude();
			mapViewRefresh(latitude, longitude, 17.0f);
		}
	}

	@Override
	public void onGetAddrResult(final MKAddrInfo result, final int iError) {
		
	}

	@Override
	public void onGetBusDetailResult(final MKBusLineResult result, final int iError) {
		
	}

	@Override
	public void onGetDrivingRouteResult(final MKDrivingRouteResult result, final int iError) {
		
	}

	@Override
	public void onGetPoiDetailSearchResult(final int type, final int iError) {
		
	}

	@Override
	public void onGetPoiResult(final MKPoiResult result, final int type, final int iError) {
		
	}

	@Override
	public void onGetShareUrlResult(final MKShareUrlResult result, final int type, final int iError) {
		
	}

	@Override
	public void onGetSuggestionResult(final MKSuggestionResult result, final int iError) {
		
	}

	@Override
	public void onGetTransitRouteResult(final MKTransitRouteResult result, final int iError) {
		
	}

	@Override
	public void onGetWalkingRouteResult(final MKWalkingRouteResult result, final int iError) {
		if (result == null || mMapView == null)
			return;
				
		if (mRouteOverlay == null)
			mRouteOverlay = new RouteOverlay(this, mMapView);
		
		final MKRoutePlan routePlan = result.getPlan(0);
		final MKRoute route = routePlan.getRoute(0);
		mRouteOverlay.setData(route);		
		final List<Overlay> list = mMapView.getOverlays();
		if (list != null && list.indexOf(mRouteOverlay) == -1)
			list.add(mRouteOverlay);
		
		final GeoPoint start = route.getStart();
		final int latitude = start.getLatitudeE6();
		final int longitude = start.getLongitudeE6();
		
		mapViewRefresh(latitude, longitude, 15.0f);		
	}	
}
