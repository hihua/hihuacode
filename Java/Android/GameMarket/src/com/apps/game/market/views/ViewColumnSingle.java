package com.apps.game.market.views;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterSingleApp;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.RequestInfo;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.request.callback.RequestCallBackInfo;
import com.apps.game.market.view.ScrollViewAd;
import com.apps.game.market.view.callback.CallBackScrollViewAd;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ViewColumnSingle extends ViewColumn implements RequestCallBackAd, CallBackScrollViewAd {
	private ScrollViewAd mScrollViewAd;
	private ListView mListView;	
	private final RequestAd mRequestAd;
	private ViewGroup mLayoutRoot;	
	private List<EntityAd> mAds;
	private AdapterSingleApp mAdapter;
	private final OnClickListener mOnClick;
	
	public ViewColumnSingle(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn, OnClickListener onClick, RequestCallBackInfo callBack) {
		super(context, parent, layoutColumn, entityColumn, callBack);
		mOnClick = onClick;
		mRequestAd = new RequestAd(this);
	}
		
	@Override
	protected void onInit() {
		if (!mInit)	{
			if (mEntityColumn != null) {
				long id = mEntityColumn.getId();			
				mRequestAd.request(id);
			}						
		}
	}

	@Override
	protected void onRefresh() {
		if (mAdapter != null)
			mAdapter.refresh();
	}

	@Override
	protected View setView() {
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.index_scroll, null);
		mListView = (ListView) mLayoutRoot.findViewById(R.id.index_listview);		
		return mLayoutRoot;
	}

	@Override
	public void onCallBackAd(boolean success) {
		final LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.aditem, null);
		final RelativeLayout layoutAd = (RelativeLayout) layout.findViewById(R.id.aditem_layout);
		final LinearLayout layoutCircle = (LinearLayout) layoutAd.findViewById(R.id.aditem_circle);
		final RelativeLayout layoutColumn = (RelativeLayout) layout.findViewById(R.id.aditem_column);
		boolean header = false;
		
		if (success && mEntityColumn != null) {
			final long id = mEntityColumn.getId();
			mAds = mGlobalData.getAds(id);
						
			if (mAds != null) {				
				mScrollViewAd = (ScrollViewAd) layout.findViewById(R.id.aditem_view);				
				mScrollViewAd.setTop(mParent, mListView);
				mScrollViewAd.setCallBack(this);
				
				setAds(mScrollViewAd, mAds);
				setAdText(layoutCircle, mAds);
				startScroll();
				onFinishScroll(0);				
				header = true;
			} else
				layout.removeView(layoutAd);						
		} else
			layout.removeView(layoutAd);
		
		final boolean classes = setSubColumn(layoutColumn, 0);
		if (classes)
			header = true;
		else
			layout.removeView(layoutColumn);
		
		if (header)
			mListView.addHeaderView(layout);
				
		if (mEntityColumn != null) {
			mAdapter = new AdapterSingleApp(mContext, mListView, mEntityColumn.getRequest(), mOnClick);
			mListView.setAdapter(mAdapter);	
		}
	}

	@Override
	public void startScroll() {
		if (mScrollViewAd != null) {			
			mScrollViewAd.startTimer(4000, 3000);
		}
	}

	@Override
	public void stopScroll() {
		if (mScrollViewAd != null)
			mScrollViewAd.cancelTimer();
	}

	@Override
	public void onFinishScroll(int screen) {
		if (mAds != null)
			setAdText(screen, mAds);
	}
	
	@Override
	public void onClick(int screen) {
		final EntityAd entityAd = mAds.get(screen);
		if (entityAd.getGameId() > -1) {
			final RequestInfo request = new RequestInfo(mCallBack, entityAd.getGameId());
			request.request();
		}
	}

	@Override
	protected void onStop() {
		if (mAdapter != null)
			mAdapter.stop();
	}

	@Override
	public void onSubColumn(final RequestApp request) {		
		if (mAdapter != null) {
			mAdapter.stop();
			mAdapter = null;
		}
		
		mAdapter = new AdapterSingleApp(mContext, mListView, request, mOnClick);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}	
}
