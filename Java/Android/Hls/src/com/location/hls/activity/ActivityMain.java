package com.location.hls.activity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.location.hls.R;
import com.location.hls.entity.EntityLocation;
import com.location.hls.entity.EntityLocations;
import com.location.hls.entity.EntityRelation;
import com.location.hls.entity.EntitySelect;
import com.location.hls.handle.HandleIcon;
import com.location.hls.handle.HandleRelation;
import com.location.hls.handle.HandleSelect;
import com.location.hls.handle.HandleSelects;
import com.location.hls.popwindow.PopWindowSelectObject;
import com.location.hls.popwindow.PopWindowWaitfor;
import com.location.hls.request.RequestIcon;
import com.location.hls.request.RequestRelation;
import com.location.hls.request.RequestSelect;
import com.location.hls.request.RequestSelects;
import com.location.hls.util.DateTime;
import com.location.hls.util.FileManager;
import com.location.hls.util.Images;
import com.location.hls.util.ThreadPool;

public class ActivityMain extends Activity implements BDLocationListener, OnGetRoutePlanResultListener, OnGetGeoCoderResultListener, OnMarkerClickListener, HandleRelation, HandleSelect, HandleIcon, HandleSelects, Runnable, OnClickListener {
	private LocationClient mLocationClient;
	private MapView mMapView;
	private BaiduMap mBaiduMap;	
	private RoutePlanSearch mRoutePlanSearch;
	private GeoCoder mGeoCoder;
	private LinearLayout mLayoutTime;
	private EntityRelation mEntityRelation;
	private RequestRelation mRequestRelation;
	private RequestSelect mRequestSelect;
	private RequestIcon mRequestIcon;
	private Handler mHandler;
	private PopWindowWaitfor mWaitfor;
	private PopWindowSelectObject mSelectObject;
	private Map<String, BitmapDescriptor> mBitmapDescriptor = new HashMap<String, BitmapDescriptor>();
	private final ThreadPool mThreadPool = new ThreadPool();
	private double mDefaultX = 23.125d;
	private double mDefaultY = 113.301d;
	private double mLatitude = mDefaultX;
	private double mLongitude = mDefaultY;
	private float mZoom = 12.7f;
	private boolean mStatus = false;
		
	@Override
	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);	
		mEntityRelation = EntityRelation.getRelation();
		if (mEntityRelation != null)
			mStatus = mEntityRelation.getStatus();
		
		initMap();
		setContentView(R.layout.activity_main);
		initControl();
		initMapView();
		init();
	}
			
	@Override
	protected void onResume() {			
		if (mMapView != null)
			mMapView.onResume();
		
		super.onResume();
	}

	@Override
	protected void onDestroy() {				
		if (mMapView != null)
			mMapView.onDestroy();
		
		if (mHandler != null)
			mHandler.removeCallbacks(this);
		
		if (mRoutePlanSearch != null)
			mRoutePlanSearch.destroy();
		
		if (mGeoCoder != null)
			mGeoCoder.destroy();
		
		if (mLocationClient != null)
			mLocationClient.stop();
					
		mThreadPool.close();
		super.onDestroy();
		exit();
	}

	@Override
	protected void onPause() {				
		if (mMapView != null)
			mMapView.onPause();
		
		super.onPause();
	}
	
	protected void exit() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
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
		
		item = menu.add(Menu.NONE, Menu.FIRST + i++, j++, R.string.menu_select_object);
		item.setIcon(android.R.drawable.ic_menu_my_calendar);
				
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
				
				final EntityRelation entityRelation = EntityRelation.getRelation();
				if (entityRelation != null) {
					entityRelation.setStatus(mStatus);
					EntityRelation.writeRelation(entityRelation);
				}
			}
			break;
			
			case Menu.FIRST + 3: {
				if (mLocationClient == null)
					mLocationClient = EntityLocation.initLocation(this, 0, this);
								
				final Toast toast = Toast.makeText(this, R.string.location_my_location, Toast.LENGTH_LONG);
				toast.show();
			}
			break;
			
			case Menu.FIRST + 4: {
				mWaitfor = new PopWindowWaitfor(this);
				mWaitfor.start();
				
				final RequestSelects request = new RequestSelects(this, mThreadPool, this);
				request.request();
			}
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void initMap() {
		SDKInitializer.initialize(getApplicationContext());  
	}
	
	private void initMapView() {
		mMapView = (MapView) findViewById(R.id.baidu_mapview);	
		
		mBaiduMap = mMapView.getMap();		
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setOnMarkerClickListener(this);
						
		mapViewRefresh(mDefaultX, mDefaultY, mZoom);		
	}
	
	private void init() {		
		mHandler = new Handler();
		if (mEntityRelation != null) {
			final String to = mEntityRelation.getTo();
			mRequestSelect = new RequestSelect(this, mThreadPool, this);
			mRequestSelect.request(to);
		}
	
		//request(true);
	}
	
	private void initControl() {
		mLayoutTime = (LinearLayout) findViewById(R.id.layout_time);		
	}
	
	private void request(final boolean auto) {
		if (mEntityRelation == null)
			mEntityRelation = EntityRelation.getRelation();
		
		if (mEntityRelation != null) {
			if (mRequestRelation == null)
				mRequestRelation = new RequestRelation(this, mThreadPool, this);
			
			mRequestRelation.request(mEntityRelation, auto);
		}	
	}
	
	private void mapViewRefresh(final double latitude, final double longitude, final float zoom) {
		final LatLng latLng = new LatLng(latitude, longitude);		
		final MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, zoom);		
		mBaiduMap.animateMapStatus(mapStatusUpdate);
	}
	
	private void mapViewRefresh(final double latitude, final double longitude, final float zoom, final int duration) {
		final LatLng latLng = new LatLng(latitude, longitude);		
		final MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, zoom);		
		mBaiduMap.animateMapStatus(mapStatusUpdate, duration);
	}
	
	private void addTime(final String sColor, final EntityLocations entityLocations, final int index) {
		if (mLayoutTime == null)
			return;

		final Resources res = getResources();
		TextView sTextView = null;
		TextView cTextView = null;
		int color = 0;

		if (sColor != null) {
			try {
				color = Color.parseColor(sColor);
			} catch (Exception e) {
				color = res.getColor(R.color.textview_clienttime);
			}
		} else
			color = res.getColor(R.color.textview_clienttime);

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
			final RelativeLayout relativeLayout = new RelativeLayout(this);
			relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			sTextView = new TextView(this);
			sTextView.setId(R.id.tv_time);
			sTextView.setLayoutParams(layoutParams);
			sTextView.setTextColor(color);
			sTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
			sTextView.setClickable(true);

			layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			cTextView = new TextView(this);
			cTextView.setLayoutParams(layoutParams);
			cTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
			cTextView.setClickable(true);

			relativeLayout.addView(sTextView);
			relativeLayout.addView(cTextView);
			mLayoutTime.addView(relativeLayout);
		}

		if (sTextView != null) {
			final long serverTime = entityLocations.getServerTime();
			final String time = DateTime.getTimeStamp(serverTime, "yyyy-MM-dd HH:mm:ss");
			sTextView.setTextColor(color);
			sTextView.setTag(entityLocations);
			sTextView.setText(time);
			sTextView.setOnClickListener(this);
		}

		if (cTextView != null) {
			final long clientTime = entityLocations.getClientTime();
			final String time = DateTime.getTimeStamp(clientTime, "yyyy-MM-dd HH:mm:ss");
			cTextView.setTextColor(color);
			cTextView.setTag(entityLocations);
			cTextView.setText(time);
			cTextView.setOnClickListener(this);
		}
	}

	@Override
	public void onRelation(final EntityLocation entityLocation, final boolean auto) {
		if (mWaitfor != null) {
			mWaitfor.close();
			mWaitfor = null;
		}
	
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

		if (mEntityRelation == null || entityLocation == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}								

		final String to = mEntityRelation.getTo();
		if (to == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}
				
		final String sColor = entityLocation.getColor();
		final List<EntityLocations> locations = entityLocation.getLocations();
		if (locations == null) {
			mapViewRefresh(mLatitude, mLongitude, mZoom);
			return;
		}
		
		int color = 0, alpha = 20;
		
		try {
			color = Color.parseColor(sColor);
		} catch (Exception e) {
			color = Color.argb(alpha, 255, 0, 0);
		}
		
		color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
		
		if (locations.size() > 0) {			
			mBaiduMap.clear();

			int i = 0;
			for (final EntityLocations entityLocations : locations) {		
				addTime(sColor, entityLocations, i);
				final int j = i + 1;
				final String name = to.toLowerCase(Locale.ENGLISH) + j;										
								
				final double latitude = entityLocations.getLatitude();
				final double longitude = entityLocations.getLongitude();
				final double accuracy = entityLocations.getRadius();
				
				if (i == 0) {
					mLatitude = latitude;
					mLongitude = longitude;
				}
				
				final MarkerOptions markerOptions = new MarkerOptions();				
				final CircleOptions circleOptions = new CircleOptions();				
				final LatLng latLng = new LatLng(latitude, longitude);
				markerOptions.position(latLng);
				circleOptions.center(latLng);
				circleOptions.radius((int) accuracy);			
				circleOptions.fillColor(color);
				circleOptions.stroke(new Stroke(1, color));
				
				if (mBitmapDescriptor.containsKey(name)) {
					final BitmapDescriptor bitmapDescriptor = mBitmapDescriptor.get(name);
					markerOptions.icon(bitmapDescriptor);
					mBaiduMap.addOverlay(markerOptions);
				} else {
					final BitmapDescriptor bitmapDescriptor = Images.getIcon(name);
					if (bitmapDescriptor != null) {
						markerOptions.icon(bitmapDescriptor);
						mBitmapDescriptor.put(name, bitmapDescriptor);
						mBaiduMap.addOverlay(markerOptions);
					}
				}
								
				mBaiduMap.addOverlay(circleOptions);
				i++;
			}
			
			mapViewRefresh(mLatitude, mLongitude, 17.0f, 800);
		}		
	}

	@Override
	public void onSelect(final EntitySelect entitySelect) {
		if (entitySelect == null)
			return;

		final String saveDir = FileManager.getIcons();
		final List<String> urls = new Vector<String>();

		final List<String> icons = entitySelect.getIcons();
		if (icons != null && saveDir != null) {
			for (String icon : icons) {
				final String filename = FileManager.urlFilename(icon);
				if (filename == null)
					continue;

				final File file = new File(saveDir, filename);
				if (!file.exists())
					urls.add(icon);				
			}		
		}

		if (urls.size() > 0) {
			mRequestIcon = new RequestIcon(this, mThreadPool, this);
			mRequestIcon.request(urls, saveDir);
		} else
			request(true);
	}

	@Override
	public void onIcon() {
		request(true);
	}
	
	@Override
	public void run() {
		request(true);
	}
	
	@Override
	public void onSelects(final List<EntitySelect> entitySelects) {
		if (mWaitfor != null) {
			mWaitfor.close();
			mWaitfor = null;
		}

		if (entitySelects != null) {
			mSelectObject = new PopWindowSelectObject(this, mEntityRelation, entitySelects, this);
			mSelectObject.show();
		}
	}

	@Override
	public void onClick(final View view) {
		final Object object = view.getTag();
		final int id = view.getId();
		switch (id) {
			case R.id.tv_time: {				
				if (object != null) {
					final EntityLocations entityLocations = (EntityLocations) object;
					final double latitude = entityLocations.getLatitude();
					final double longitude = entityLocations.getLongitude();
					mapViewRefresh(latitude, longitude, 17.0f, 800);
				}
			}
			break;
			
			case R.id.rl_item: {
				if (mSelectObject != null) {
					mSelectObject.close();
					mSelectObject = null;
				}

				if (mEntityRelation == null)
					mEntityRelation = EntityRelation.getRelation();

				if (mEntityRelation != null) {
					mWaitfor = new PopWindowWaitfor(this);
					mWaitfor.start();

					final EntitySelect entitySelect = (EntitySelect) object;
					final String name = entitySelect.getName();
					mEntityRelation.setTo(name);
					mRequestSelect = new RequestSelect(this, mThreadPool, this);
					mRequestSelect.request(name);
				}
			}
			break;
		}
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
				final double latitude = location.getLatitude();
				final double longitude = location.getLongitude();
				
				if ((mLatitude != mDefaultX || mLongitude != mDefaultY) && mRoutePlanSearch == null) {
					mRoutePlanSearch = RoutePlanSearch.newInstance();
					mRoutePlanSearch.setOnGetRoutePlanResultListener(this);
																									
					final PlanNode from = PlanNode.withLocation(new LatLng(latitude, longitude));
					final PlanNode to = PlanNode.withLocation(new LatLng(mLatitude, mLongitude));
					
					final WalkingRoutePlanOption walkingRoutePlanOption = new WalkingRoutePlanOption();
					walkingRoutePlanOption.from(from);
					walkingRoutePlanOption.to(to);
					
					if (!mRoutePlanSearch.walkingSearch(walkingRoutePlanOption))						
						mRoutePlanSearch = null;					
				}				
			}			
		}		
	}

	@Override
	public void onGetDrivingRouteResult(final DrivingRouteResult result) {
		
	}

	@Override
	public void onGetTransitRouteResult(final TransitRouteResult result) {
		
	}

	@Override
	public void onGetWalkingRouteResult(final WalkingRouteResult result) {
		mRoutePlanSearch.destroy();
		mRoutePlanSearch = null;
		
		if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
			final List<WalkingRouteLine> list = result.getRouteLines();
        	if (list != null && list.size() > 0) {
        		final WalkingRouteLine walkingRouteLine = list.get(0);        	        	
	        	final WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);            
	            overlay.setData(walkingRouteLine);  
	            overlay.addToMap();  
	            overlay.zoomToSpan();
        	}
        }
	}

	@Override
	public boolean onMarkerClick(final Marker marker) {
		if (marker != null) {
			final LatLng latLng = marker.getPosition();
			if (latLng != null && mGeoCoder == null) {
				final ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
				reverseGeoCodeOption.location(latLng);
				
				mGeoCoder = GeoCoder.newInstance();
				mGeoCoder.setOnGetGeoCodeResultListener(this);
				if (!mGeoCoder.reverseGeoCode(reverseGeoCodeOption))					
					mGeoCoder = null;				
			}			
		}
					
		return false;
	}

	@Override
	public void onGetGeoCodeResult(final GeoCodeResult result) {
		
	}

	@Override
	public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult result) {
		mGeoCoder.destroy();
		mGeoCoder = null;
				
		String address = getString(R.string.address_failed);
		if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR)
			address = result.getAddress();
		
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(R.string.address);
		alertDialog.setMessage(address);
		alertDialog.setPositiveButton(R.string.confirm, null);
		alertDialog.show();
	}		
}
