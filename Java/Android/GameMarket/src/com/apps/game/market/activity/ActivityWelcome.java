package com.apps.game.market.activity;

import com.apps.game.market.App;
import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.RequestColumn;
import com.apps.game.market.request.RequestTag;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.request.callback.RequestCallBackColumn;
import com.apps.game.market.request.callback.RequestCallBackTag;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class ActivityWelcome extends Activity implements RequestCallBackColumn, RequestCallBackTag, RequestCallBackAd {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);	
	}
			
	@Override
	protected void onResume() {		
		super.onResume();
		init();
	}

	@Override
	protected void onStop() {		
		super.onStop();
		finish();
	}

	@Override
	protected void onDestroy() {		
		super.onDestroy();				
	}
	
	@Override
	public void onCallBackAd(boolean success) {
		final Intent intent = new Intent(this, ActivityIndex.class); 
        startActivity(intent);
	}
	
	@Override
	public void onCallBackTag(boolean success) {
		final GlobalData globalData = GlobalData.globalData;
		final EntityColumn entityColumn = globalData.getColumn(0);
		if (entityColumn != null) {
			final RequestAd requestAd = new RequestAd(this);
			requestAd.request(entityColumn.getId());
		}
	}

	@Override
	public void onCallBackColumn(boolean success) {
		if (success) {
			final RequestTag requestTag = new RequestTag(this);
			requestTag.request();
		} else {
			final RequestCallBackColumn callback = this;
			final ActivityWelcome activity = this;
			final Dialog dialog = new AlertDialog.Builder(this).setTitle(R.string.welcome_error).setMessage(R.string.welcome_no_network).setPositiveButton(R.string.welcome_retry, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final RequestColumn requestColumn = new RequestColumn(callback);
					requestColumn.request();
				}
			}).setNegativeButton(R.string.welcome_quit, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.finish();					
				}
			}).create();
			
			dialog.show();
		}
	}
			
	private boolean init() {
		final App app = (App) getApplication();
		final GlobalData globalData = app.globalData;
		if (!globalData.init(this))
			return false;
				
		final GlobalObject globalObject = app.globalObject;
		globalObject.setContext(this);
		
		final RequestColumn requestColumn = new RequestColumn(this);
		requestColumn.request();
		
		return true;
	}		
}
