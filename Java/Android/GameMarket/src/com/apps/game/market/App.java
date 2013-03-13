package com.apps.game.market;

import android.app.Application;

import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;

public class App extends Application {
	public GlobalObject globalObject = null;
	public GlobalData globalData = null;

	@Override
	public void onCreate() {		
		super.onCreate();
		init();
	}
		
	public void init() {
		globalObject = new GlobalObject();
		globalData = new GlobalData();
	}	
}
