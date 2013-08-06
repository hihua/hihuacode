package com.apps.game.market.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterSearchApp;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.app.RequestAppSearch;

public class ActivitySearch extends ActivityBase {
	private LinearLayout mLayoutColumn;
	private ListView mListView;	
	private AdapterSearchApp mAdapter;
	
	@Override
	protected void onAppCreate() {
		mKeyword = getIntent().getStringExtra("keyword");		
		setContentView(R.layout.search);		
		layout();
	}
	
	@Override
	protected void onAppEntry() {
		setSearch(mKeyword);
		search(mKeyword);
	}

	@Override
	protected void onAppClose() {
		if (mFinish)
			finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setFinish(true);
	}

	@Override
	protected void onAppResume() {
		
	}

	@Override
	protected void onAppClick(View v) {
		final Object obj = v.getTag();		
		if (v instanceof FrameLayout) {
			final Intent intent = mGlobalData.getLastIntent();
			if (intent != null && obj != null && obj instanceof Integer) {
				final Integer index = (Integer) obj;
				mGlobalData.setSelectColumn(index);				
				onBackPressed();				
			}
		}				
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}
	
	private void layout() {
		mLayoutColumn = (LinearLayout) findViewById(R.id.search_layout_column);		
		mListView = (ListView) findViewById(R.id.search_listview);
		layoutColumns(mLayoutColumn, null, null);
	}

	@Override
	protected void search(String keyword) {
		final RequestApp requestApp = new RequestAppSearch(keyword);
		mAdapter = new AdapterSearchApp(this, mListView, requestApp, this);
		mListView.setAdapter(mAdapter);
	}
}
