package com.apps.game.market.activity;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.apps.game.market.R;
import com.apps.game.market.views.ViewColumn;

public class ActivityIndex extends ActivityBase implements OnPageChangeListener, OnClickListener {
	private ViewPager mPager;	
	private ViewColumn mCurrent;

	@Override
	protected void onAppCreate() {
		setContentView(R.layout.index);
		init();
		layout();
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
		mGlobalObject.setContext(this);
		mGlobalObject.init();		
	}
	
	private void layout() {
		LinearLayout column = (LinearLayout) findViewById(R.id.index_layout_column);
		mPager = (ViewPager) findViewById(R.id.index_viewpager);
		layoutColumns(column, mPager);
		mPager.setAdapter(new IndexPagerAdapter(mViewColumns));
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
		mCurrent = viewColumn;
	}
	
	public void close() {
		mGlobalObject.close();
		if (mViewColumns != null) {
			for (ViewColumn viewColumn : mViewColumns)
				viewColumn.stop();
		}
	}
}

class IndexPagerAdapter extends PagerAdapter {
	private List<ViewColumn> mViewColumns;
			
	IndexPagerAdapter(List<ViewColumn> viewColumns) {
		mViewColumns = viewColumns;
	}

	@Override
	public int getCount() {		
		return mViewColumns.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	@Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object) {
		viewGroup.removeView((View) object);
    }
    
    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {    	    	
    	ViewColumn viewColumn = mViewColumns.get(position);    	
    	View view = viewColumn.getView();
        viewGroup.addView(view);
        return view;
    }
}
