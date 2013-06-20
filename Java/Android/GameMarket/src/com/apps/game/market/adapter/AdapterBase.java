package com.apps.game.market.adapter;

import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class AdapterBase extends BaseAdapter implements OnScrollListener {
	protected final GlobalObject mGlobalObject = GlobalObject.globalObject;
	protected final GlobalData mGlobalData = GlobalData.globalData;
	protected final Context mContext;
	protected final LayoutInflater mInflater;
	protected final ListView mListView;
	protected final OnClickListener mOnClick;
	
	public AdapterBase(Context context, ListView listView, OnClickListener onClick) {
		mContext = context;
		mListView = listView;
		mOnClick = onClick;
		mInflater = LayoutInflater.from(context);		
	}
	
	public abstract void refresh();
	public abstract void stop();
	public abstract void setAppStatus(EntityApp entityApp);
}
