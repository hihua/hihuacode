package com.apps.game.market.activity;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterTagApp;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.app.RequestAppTag;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ActivityTag extends ActivityBase {
	private LinearLayout mLayoutColumn;
	private ListView mListView;		
	private AdapterTagApp mAdapter;
		
	@Override
	protected void onAppCreate() {
		mEntityTag = mGlobalData.getSelectTag();
		setContentView(R.layout.tag);
		layout();
	}
	
	@Override
	protected void onAppEntry() {		
		final RequestApp requestApp = new RequestAppTag();
		mAdapter = new AdapterTagApp(this, mListView, requestApp, mEntityTag.getId(), this);
		mListView.setAdapter(mAdapter);	
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
	protected void onAppClick(final View v) {		
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
	
	private void layout() {
		mLayoutColumn = (LinearLayout) findViewById(R.id.tag_layout_column);		
		mListView = (ListView) findViewById(R.id.tag_listview);
		layoutColumns(mLayoutColumn, null, null);
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}
}
