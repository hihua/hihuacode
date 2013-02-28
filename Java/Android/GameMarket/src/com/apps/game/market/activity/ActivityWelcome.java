package com.apps.game.market.activity;

import java.util.Timer;
import java.util.TimerTask;

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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class ActivityWelcome extends Activity implements RequestCallBackColumn, RequestCallBackTag, RequestCallBackAd {
	private TextView mTextView;
	private Timer mTimer;	
	private int mCurrentId = R.drawable.spinner_01;
	private Handler mHandle = new Handler() {
		@Override  
        public void handleMessage(Message msg) {
			mCurrentId++;
			if (mCurrentId > R.drawable.spinner_40)
				mCurrentId = R.drawable.spinner_01;
			
			Drawable drawable = getResources().getDrawable(mCurrentId);
			mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		mTextView = (TextView) findViewById(R.id.welcome_textView);
				
		if (mTimer == null) {
			mTimer = new Timer();		
			TimerTask task = new TimerTask() {		
				@Override
				public void run() {
					mHandle.sendEmptyMessage(1);
				}
			};
			
			mTimer.schedule(task, 1, 20);
		}
		
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
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}		
	}
	
	@Override
	public void onCallBackAd(boolean success) {
		Intent intent = new Intent(this, ActivityIndex.class); 
        startActivity(intent);
	}
	
	@Override
	public void onCallBackTag(boolean success) {
		GlobalData globalData = GlobalData.globalData;
		EntityColumn entityColumn = globalData.getColumn(0);
		if (entityColumn != null) {
			RequestAd requestAd = new RequestAd(this);
			requestAd.request(entityColumn.getId());
		}
	}

	@Override
	public void onCallBackColumn(boolean success) {
		if (success) {
			RequestTag requestTag = new RequestTag(this);
			requestTag.request();
		}
	}
	
	private boolean init() {
		GlobalData globalData = new GlobalData();
		if (!globalData.init(this))
			return false;
		
		GlobalObject globalObject = new GlobalObject(this);		
		RequestColumn requestColumn = new RequestColumn(this);
		requestColumn.request();
		return true;
	}

	
}
