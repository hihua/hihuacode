package com.apps.game.market.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityColumn;

public class ViewColumnMyApp extends ViewColumn {
	private LinearLayout mLayoutRoot;

	public ViewColumnMyApp(Context context, ViewGroup parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
		super(context, parent, layoutColumn, entityColumn);
	}

	@Override
	protected void onInit() {
		
	}

	@Override
	protected void onRefresh() {
		
	}

	@Override
	protected View setView() {		
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.index_scroll, null);
		return mLayoutRoot;
	}

	@Override
	protected void onStop() {
		
	}
}
