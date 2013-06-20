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
import com.apps.game.market.view.PopWindowQuit;
import com.apps.game.market.views.ViewColumn;

public class ActivityIndex extends ActivityBase implements OnPageChangeListener, OnClickListener {
	private ViewPager mPager;	
	private ViewColumn mCurrent;	
	private LinearLayout mLayoutColumn;
	private LinearLayout mLayoutMyAppColumn;
	private boolean mInit = false;
	private int mTotal;
	
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
		int selectColumn = mGlobalData.getSelectColumn();
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
		final PopWindowQuit pop = new PopWindowQuit(this, this);
		pop.show();
	}

	private void init() {
		if (!mInit) {
			mGlobalObject.setContext(this);
			mGlobalObject.init();
			mInit = true;
		}			
	}
	
	private void layout() {		
		mLayoutColumn = (LinearLayout) findViewById(R.id.index_column);
		mLayoutMyAppColumn = (LinearLayout) findViewById(R.id.index_myapp_column);
		mPager = (ViewPager) findViewById(R.id.index_viewpager);
		mTotal = layoutColumns(mLayoutColumn, mLayoutMyAppColumn, mPager);
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
			final Object obj = v.getTag();
			if (obj != null && obj instanceof Integer) {
				boolean found = false;
				final Integer index = (Integer) obj;
				for (ViewColumn viewColumn : mViewColumns) {
					final ViewGroup viewGroup = viewColumn.getViewColumn();
					if (viewGroup.equals(v) && mCurrent != null && !viewColumn.equals(mCurrent)) {
						found = true;
						break;
					}
				}
				
				if (found)
					mPager.setCurrentItem(index, true);
				else {
					if (index == mTotal)
						mPager.setCurrentItem(index, true);
				}
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
		
		if (position > mTotal - 1) {
			mLayoutColumn.setVisibility(View.GONE);
			mLayoutMyAppColumn.setVisibility(View.VISIBLE);
		} else {
			mLayoutColumn.setVisibility(View.VISIBLE);
			mLayoutMyAppColumn.setVisibility(View.GONE);
		}
				
		mCurrent = viewColumn;
	}
		
	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mCurrent != null)
			mCurrent.onAppStatus(entityApp);		
	}
}
