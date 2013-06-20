package com.apps.game.market.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewBase {	
	protected Context mContext;
	protected LayoutInflater mInflater;
	private View mView;
	
	protected ViewBase(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mView = setView();
	}
	
	public View getView() {
		return mView;
	}
		
	protected abstract View setView();
}
