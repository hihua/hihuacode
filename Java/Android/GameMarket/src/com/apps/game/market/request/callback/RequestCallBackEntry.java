package com.apps.game.market.request.callback;

import com.apps.game.market.entity.EntityEntry;

public interface RequestCallBackEntry {
	public void onCallBackEntry(boolean success, EntityEntry entityEntry);
}
