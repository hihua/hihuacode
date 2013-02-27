package com.apps.game.market.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterIndexPager;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.views.ViewColumn;
import com.apps.game.market.views.ViewColumnMyApp;

public class ActivityIndex extends ActivityBase implements OnPageChangeListener, OnClickListener {
	private ViewPager mPager;	
	private ViewColumn mCurrent;
	private LinearLayout mLinearHeader;
	private LinearLayout mLayoutColumn;
	private boolean mInit = false;
	
	@Override
	protected void onAppCreate() {
		setContentView(R.layout.index);
		init();
		layout();
	}
	
	@Override
	protected void onAppEntry() {
		
	}
	
	@Override
	protected void onAppClose() {
		if (mCurrent != null)
			mCurrent.stopScroll();
		
		mGlobalData.setLastIntent(getIntent());
	}	
	
	@Override
	protected void onAppResume() {
		int selectColumn  = mGlobalData.getSelectColumn();
		if (selectColumn != -1) {									
			mPager.setCurrentItem(selectColumn, false);			
			mCurrent = mViewColumns.get(selectColumn);
			mGlobalData.setSelectColumn(-1);
		}
		
		if (mCurrent != null) {
			mCurrent.init();
			mCurrent.setHighlight(true);
			mCurrent.startScroll();	
			mCurrent.refresh();
		}
		
		mGlobalData.setSelectTag(null);
	}		

	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		close();		
		finish();
	}

	private void init() {
		if (!mInit) {
			mGlobalObject.setContext(this);
			mGlobalObject.init();
			mInit = true;
		}			
	}
	
	private void layout() {
		mLinearHeader = (LinearLayout) findViewById(R.id.header_layout);
		mLayoutColumn = (LinearLayout) findViewById(R.id.index_layout_column);
		mPager = (ViewPager) findViewById(R.id.index_viewpager);
		layoutColumns(mLayoutColumn, mPager);
		mPager.setAdapter(new AdapterIndexPager(mViewColumns));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(this);
		
		if (mViewColumns.size() > 0) {
			mCurrent = mViewColumns.get(0);
			mCurrent.init();			
		}
	}
	
	@Override
	protected void onAppClick(View v) {
		if (v instanceof FrameLayout) {
			Object obj = v.getTag();
			if (obj != null && obj instanceof Integer) {
				boolean found = false;
				Integer index = (Integer) obj;
				for (ViewColumn viewColumn : mViewColumns) {
					ViewGroup viewGroup = viewColumn.getViewColumn();
					if (viewGroup.equals(v) && mCurrent != null && !viewColumn.equals(mCurrent)) {
						found = true;
						break;
					}
				}
				
				if (found)
					mPager.setCurrentItem(index, true);
			}			
		}
	}

	@Override
	public void onPageScrollStateChanged(int scrollState) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {		
		if (mCurrent != null) {
			mCurrent.stopScroll();
			mCurrent.setHighlight(false);			
		}
		
		final ViewColumn viewColumn = mViewColumns.get(position);
		viewColumn.init();
		viewColumn.startScroll();
		viewColumn.setHighlight(true);
		viewColumn.refresh();
		
		if (viewColumn instanceof ViewColumnMyApp) {
			showTop(true);
			showBottom(true);
		} else {
			showTop(false);
			showBottom(false);
		}
		
		mCurrent = viewColumn;
	}
	
	public void close() {
		mGlobalObject.close();
		if (mViewColumns != null) {
			for (ViewColumn viewColumn : mViewColumns)
				viewColumn.stop();
		}
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mCurrent != null)
			mCurrent.onAppStatus(entityApp);		
	}
	
	private void showTop(boolean hide) {
		if (hide) {
			if (mLinearHeader != null && mLinearHeader.getVisibility() == View.VISIBLE)
				mLinearHeader.setVisibility(View.GONE);
			
			if (mLayoutColumn != null && mLayoutColumn.getVisibility() == View.VISIBLE)
				mLayoutColumn.setVisibility(View.GONE);
		} else {
			if (mLinearHeader != null && mLinearHeader.getVisibility() == View.GONE)
				mLinearHeader.setVisibility(View.VISIBLE);
			
			if (mLayoutColumn != null && mLayoutColumn.getVisibility() == View.GONE)
				mLayoutColumn.setVisibility(View.VISIBLE);
		}
	}
	
	private void showBottom(boolean hide) {
		if (hide) {
			if (mLinearBottom != null && mLinearBottom.getVisibility() == View.VISIBLE)
				mLinearBottom.setVisibility(View.GONE);			
		} else {						
			if (mLinearBottom != null && mLinearBottom.getVisibility() == View.GONE)
				mLinearBottom.setVisibility(View.VISIBLE);
		}
	}
}
