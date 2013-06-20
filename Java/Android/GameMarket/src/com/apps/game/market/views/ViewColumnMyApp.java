package com.apps.game.market.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterBase;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.request.callback.RequestCallBackInfo;

public abstract class ViewColumnMyApp extends ViewColumn {
	protected ListView mListView;
	protected LinearLayout mLayoutRoot;
	protected AdapterBase mAdapter;	
	
	public ViewColumnMyApp(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn, OnClickListener onClick, RequestCallBackInfo callBack) {		
		super(context, parent, layoutColumn, entityColumn, callBack);		
	}

	@Override
	protected void onInit() {
		
	}

	@Override
	protected void onRefresh() {
		if (mAdapter != null)
			mAdapter.refresh();
	}

	@Override
	protected View setView() {		
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.myapp_scroll, null);
		mListView = (ListView) mLayoutRoot.findViewById(R.id.myapp_listview);		
		return mLayoutRoot;
	}

	@Override
	protected void onStop() {		
		if (mAdapter != null)
			mAdapter.stop();
	}
		
	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}	
}
