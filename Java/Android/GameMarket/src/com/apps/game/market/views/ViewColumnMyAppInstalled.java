package com.apps.game.market.views;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.apps.game.market.adapter.AdapterMyAppInstalled;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityColumn;

public class ViewColumnMyAppInstalled extends ViewColumnMyApp {

	public ViewColumnMyAppInstalled(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn, OnClickListener onClick, List<EntityAppInfo> list) {
		super(context, parent, layoutColumn, entityColumn, onClick, null);
		mAdapter = new AdapterMyAppInstalled(context, mListView, onClick, list);
		mListView.setAdapter(mAdapter);
	}	
}
