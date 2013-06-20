package com.apps.game.market.views;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.apps.game.market.adapter.AdapterMyAppBrowse;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;

public class ViewColumnMyAppBrowse extends ViewColumnMyApp {

	public ViewColumnMyAppBrowse(Context context, ViewPager parent,	ViewGroup layoutColumn, EntityColumn entityColumn, OnClickListener onClick, List<EntityApp> list) {
		super(context, parent, layoutColumn, entityColumn, onClick, null);
		mAdapter = new AdapterMyAppBrowse(context, mListView, onClick, list);
		mListView.setAdapter(mAdapter);
	}
}
