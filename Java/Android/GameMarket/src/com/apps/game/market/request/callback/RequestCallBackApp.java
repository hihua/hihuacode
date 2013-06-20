package com.apps.game.market.request.callback;

import java.util.List;

import com.apps.game.market.entity.app.EntityApp;

public interface RequestCallBackApp {
	public void onCallBackApp(List<EntityApp> list, boolean finish);
}
