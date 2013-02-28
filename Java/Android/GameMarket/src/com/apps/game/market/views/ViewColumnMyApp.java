package com.apps.game.market.views;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterMyAppBrowse;
import com.apps.game.market.adapter.AdapterMyAppCollect;
import com.apps.game.market.adapter.AdapterMyAppDownload;
import com.apps.game.market.adapter.AdapterMyAppInstalled;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;

public class ViewColumnMyApp extends ViewColumn implements OnClickListener {
	private LinearLayout mLayoutRoot;
	private ImageView mImageHome;
	private LinearLayout mLayoutBanner;
	private FrameLayout mLayoutInstalled;
	private FrameLayout mLayoutDownload;
	private FrameLayout mLayoutCollect;
	private FrameLayout mLayoutBrowse;	
	private ListView mListView;
	private AdapterMyAppInstalled mAdapterInstalled;
	private AdapterMyAppDownload mAdapterDownload;
	private AdapterMyAppCollect mAdapterCollect;
	private AdapterMyAppBrowse mAdapterBrowse;
	private FrameLayout mCurrent;
	private final OnClickListener mOnClick;

	public ViewColumnMyApp(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn, OnClickListener onClick) {		
		super(context, parent, layoutColumn, entityColumn);
		mOnClick = onClick;
	}

	@Override
	protected void onInit() {
		if (!mInit)	{
			List<EntityAppInfo> list = mGlobalData.getLocalApps();
			mAdapterInstalled = new AdapterMyAppInstalled(mContext, mListView, list);	
			mListView.setAdapter(mAdapterInstalled);
		}
	}

	@Override
	protected void onRefresh() {
		if (mAdapterInstalled != null)
			mAdapterInstalled.refresh();
		
		if (mAdapterDownload != null)
			mAdapterDownload.refresh();
		
		if (mAdapterCollect != null)
			mAdapterCollect.refresh();
		
		if (mAdapterBrowse != null)
			mAdapterBrowse.refresh();
	}

	@Override
	protected View setView() {		
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.myapp, null);
		mImageHome = (ImageView) mLayoutRoot.findViewById(R.id.myapp_home);
		mListView = (ListView) mLayoutRoot.findViewById(R.id.myapp_listview);
		mLayoutBanner = (LinearLayout) mLayoutRoot.findViewById(R.id.myapp_banner);
		mLayoutInstalled = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_installed);
		mLayoutDownload = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_download);
		mLayoutCollect = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_collect);
		mLayoutBrowse = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_browse);
		mLayoutInstalled.setOnClickListener(this);
		mLayoutDownload.setOnClickListener(this);
		mLayoutCollect.setOnClickListener(this);
		mLayoutBrowse.setOnClickListener(this);
		mImageHome.setOnClickListener(this);
		mLayoutInstalled.setBackgroundResource(R.drawable.myapp_column_corner);
		mCurrent = mLayoutInstalled;
		return mLayoutRoot;
	}

	@Override
	protected void onStop() {		
		adapterStop();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.myapp_home: {
				mParent.setCurrentItem(0, true);
			}
			break;
		
			case R.id.myapp_installed: {
				if (!mCurrent.equals(mLayoutInstalled)) {
					adapterStop();										
					if (mAdapterInstalled == null) {
						List<EntityAppInfo> list = mGlobalData.getLocalApps();
						mAdapterInstalled = new AdapterMyAppInstalled(mContext, mListView, list);
					}
					
					mListView.setAdapter(mAdapterInstalled);	
					setBackground(mLayoutInstalled);
					showBanner(false);
				}
			}
			break;
			
			case R.id.myapp_download: {
				if (!mCurrent.equals(mLayoutDownload)) {
					adapterStop();					
					List<EntityApp> list = mGlobalData.getDownloadApps();
					mAdapterDownload = new AdapterMyAppDownload(mContext, mListView, list, mOnClick);					
					mListView.setAdapter(mAdapterDownload);	
					setBackground(mLayoutDownload);
					showBanner(true);
				}
			}
			break;
			
			case R.id.myapp_collect: {
				if (!mCurrent.equals(mLayoutCollect)) {
					adapterStop();					
					List<EntityApp> list = mGlobalData.getCollectApps();
					mAdapterCollect = new AdapterMyAppCollect(mContext, mListView, list, mOnClick);					
					mListView.setAdapter(mAdapterCollect);	
					setBackground(mLayoutCollect);
					showBanner(true);
				}
			}
			break;
			
			case R.id.myapp_browse: {
				if (!mCurrent.equals(mLayoutBrowse)) {
					adapterStop();
					List<EntityApp> list = mGlobalData.getBrowseApps();
					mAdapterBrowse = new AdapterMyAppBrowse(mContext, mListView, list, mOnClick);					
					mListView.setAdapter(mAdapterBrowse);	
					setBackground(mLayoutBrowse);
					showBanner(true);
				}
			}
			break;
		}
	}
	
	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapterDownload != null)
			mAdapterDownload.setAppStatus(entityApp);
				
		if (mAdapterCollect != null)
			mAdapterCollect.setAppStatus(entityApp);
				
		if (mAdapterBrowse != null)
			mAdapterBrowse.setAppStatus(entityApp);		
	}
	
	private void setBackground(FrameLayout layout) {
		mCurrent.setBackgroundResource(android.R.color.transparent);	
		layout.setBackgroundResource(R.drawable.myapp_column_corner);
		mCurrent = layout;
	}
	
	private void showBanner(boolean hide) {
		if (hide) {
			if (mLayoutBanner != null && mLayoutBanner.getVisibility() == View.VISIBLE)
				mLayoutBanner.setVisibility(View.GONE);									
		} else {
			if (mLayoutBanner != null && mLayoutBanner.getVisibility() == View.GONE)
				mLayoutBanner.setVisibility(View.VISIBLE);
		}
	}
	
	private void adapterStop() {
		if (mAdapterDownload != null) {
			mAdapterDownload.stop();
			mAdapterDownload = null;
		}
		
		if (mAdapterCollect != null) {
			mAdapterCollect.stop();
			mAdapterCollect = null;
		}
		
		if (mAdapterBrowse != null) {
			mAdapterBrowse.stop();
			mAdapterBrowse = null;
		}
	}
}
