package com.apps.game.market.adapter;

import java.util.List;

import com.apps.game.market.views.ViewColumn;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class AdapterIndexPager extends PagerAdapter {
	private final List<ViewColumn> mViewColumns;
	
	public AdapterIndexPager(List<ViewColumn> viewColumns) {
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
    	final ViewColumn viewColumn = mViewColumns.get(position);    	
    	final View view = viewColumn.getView();
        viewGroup.addView(view);
        return view;
    }
}
