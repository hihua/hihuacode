package com.apps.game.market.request.callback;

import com.apps.game.market.entity.EntityUpgrade;

public interface RequestCallBackVersion {
	public void onCallBackVersion(boolean success, EntityUpgrade entityUpgrade);
}
