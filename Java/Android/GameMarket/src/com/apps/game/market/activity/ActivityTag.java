package com.apps.game.market.activity;

import com.apps.game.market.R;
import com.apps.game.market.adapter.AdapterTagApp;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.app.RequestAppTag;
import com.apps.game.market.task.TaskDownload;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityTag extends ActivityBase {
	private LinearLayout mLayoutColumn;
	private ListView mListView;
	private TextView mTagName;	
	private boolean mFinish = true;	
	private AdapterTagApp mAdapter;
		
	@Override
	protected void onAppCreate() {
		setContentView(R.layout.tag);
		layout();
	}

	@Override
	protected void onAppClose() {
		if (mFinish)
			finish();				
	}
		
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mFinish = true;
	}

	@Override
	protected void onAppResume() {		
		mEntityTag = mGlobalData.getSelectTag();
		if (mEntityTag.getName() != null)
			mTagName.setText(mEntityTag.getName());
			
		RequestApp requestTag = new RequestAppTag();
		mAdapter = new AdapterTagApp(this, mListView, requestTag, mEntityTag.getId(), this);
		mListView.setAdapter(mAdapter);		
	}

	@Override
	protected void onAppClick(View v) {
		Object obj = v.getTag();
		if (v instanceof LinearLayout && obj != null && obj instanceof EntityApp) {
			mFinish = false;
			final EntityApp entityApp = (EntityApp) v.getTag();
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(this, ActivityDetail.class); 
	        startActivity(intent);
	        return;			
		}
		
		if (v instanceof FrameLayout) {
			Intent intent = mGlobalData.getLastIntent();
			if (intent != null && obj != null && obj instanceof Integer) {
				Integer index = (Integer) obj;
				mGlobalData.setSelectColumn(index);				
				onBackPressed();
				return;
			}
		}
		
		if (v.getId() == R.id.app_action1 || v.getId() == R.id.app_action2) {
			if (v instanceof TextView && obj != null && obj instanceof EntityApp) {
				final EntityApp entityApp = (EntityApp) obj;
				TaskDownload taskDownload = mGlobalObject.getTaskDownload();
				EnumAppStatus status = entityApp.getStatus();
				switch (status) {
					case NOINSTALL:
						taskDownload.downloadApp(this, entityApp);
						break;
						
					case INSTALL:
						if (!taskDownload.installApp(this, entityApp))
							onAppStatus(entityApp);
						
						break;
						
					case INSTALLED:
						taskDownload.runApp(this, entityApp);					
						break;
				
					case WAITING:					
						taskDownload.downloadCancel(this, entityApp);
						break;
						
					case DOWNLOADING:
						taskDownload.downloadCancel(this, entityApp);		
						break;
				}
				return;
			}
		}
	}
	
	private void layout() {
		mLayoutColumn = (LinearLayout) findViewById(R.id.tag_layout_column);
		mTagName = (TextView) findViewById(R.id.tag_name);
		mListView = (ListView) findViewById(R.id.tag_listview);
		layoutColumns(mLayoutColumn, null);
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}	
}
