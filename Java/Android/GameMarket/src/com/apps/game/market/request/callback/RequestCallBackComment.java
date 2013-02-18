package com.apps.game.market.request.callback;

import java.util.List;

import com.apps.game.market.entity.app.EntityComment;

public interface RequestCallBackComment {
	public void onCallBackComment(List<EntityComment> list, boolean finish);
}
