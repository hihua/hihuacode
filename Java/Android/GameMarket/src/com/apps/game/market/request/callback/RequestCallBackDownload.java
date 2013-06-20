package com.apps.game.market.request.callback;

import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumDownloadStatus;
import com.apps.game.market.request.RequestDownload;

public interface RequestCallBackDownload {
	public void onCallBackDownload(RequestDownload requestDownload, EnumDownloadStatus status, EntityApp entityApp);
}
