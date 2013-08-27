package com.apps.game.market;

import android.app.Application;
import android.util.Log;

import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;

public class App extends Application {
	public GlobalObject globalObject = null;
	public GlobalData globalData = null;

	@Override
	public void onCreate() {		
		super.onCreate();
		close();
		init();
	}
		
	@Override
	public void onTerminate() {
		Log.d("Application", "close");
		super.onTerminate();
		//close();
	}

	private void init() {
		globalObject = new GlobalObject();
		globalData = new GlobalData();
	}
	
	private void close() {
		if (globalObject != null)
			globalObject.close();
		
		if (globalData != null)
			globalData.close();
	}
}
