package com.apps.game.market.views;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterSingleApp;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.view.ScrollViewAd;
import com.apps.game.market.view.callback.ScrollViewAdCallBack;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewColumnSingle extends ViewColumn implements RequestCallBackAd, ScrollViewAdCallBack {
	private ScrollViewAd mScrollViewAd;
	private ListView mListView;
	private TextView mTextViewAdName;
	private TextView mTextViewAdPosition;
	private RequestAd mRequestAd;
	private ViewGroup mLayoutRoot;	
	private List<EntityAd> mAds;
	private AdapterSingleApp mAdapter;
	
	public ViewColumnSingle(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
		super(context, parent, layoutColumn, entityColumn);
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
		LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.aditem, null);
		RelativeLayout layoutAd = (RelativeLayout) layout.findViewById(R.id.aditem_layout);		
		LinearLayout layoutColumn = (LinearLayout) layout.findViewById(R.id.aditem_layout_column);
		
		if (success && mEntityColumn != null) {
			long id = mEntityColumn.getId();
			mAds = mGlobalData.getAds(id);
			boolean header = false;
			
			if (mAds != null) {
				mTextViewAdName = (TextView) layout.findViewById(R.id.aditem_textview_name);
				mTextViewAdPosition = (TextView) layout.findViewById(R.id.aditem_textview_position);
				
				mScrollViewAd = (ScrollViewAd) layout.findViewById(R.id.aditem_view);
				mScrollViewAd.setTop(mParent, mListView);
				mScrollViewAd.setCallBack(this);
								
				setAds(mScrollViewAd, mAds);				
				startScroll();
				onFinishScroll(0);
				header = true;
			} else
				layout.removeView(layoutAd);
						
			boolean classes = setSubColumn(layoutColumn, 0);
			if (!classes)
				layout.removeView(layoutColumn);
			
			if (header || classes)
				mListView.addHeaderView(layout);
		}
		
		if (mEntityColumn != null) {
			mAdapter = new AdapterSingleApp(mContext, mListView, mEntityColumn.getRequest());
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
		if (mAds != null && mTextViewAdName != null && mTextViewAdPosition != null) {
			StringBuilder position = new StringBuilder(); 
			for (int i = 0;i < mAds.size();i++) {
				if (i == screen) {
					EntityAd ad = mAds.get(i);
					mTextViewAdName.setText(ad.getName());           	
					position.append("●");
				} else
					position.append("○");
			}
		
			mTextViewAdPosition.setText(position.toString());			
		}
	}

	@Override
	protected void onStop() {
		if (mAdapter != null)
			mAdapter.stop();
	}

	@Override
	public void onSubColumn(final ViewGroup parent, final int position, final String url) {		
		setSubColumn(parent, 0);
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}
}
