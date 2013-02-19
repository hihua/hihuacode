package com.apps.game.market.task;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.activity.ActivityBase;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.enums.EnumDownloadStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestDownload;
import com.apps.game.market.request.callback.RequestCallBackDownload;

public class TaskDownload implements RequestCallBackDownload {
	private GlobalObject mGlobalObject = GlobalObject.globalObject;
	private GlobalData mGlobalData = GlobalData.globalData;
	private final int Max = 2;
	private ActivityBase mActivityBase = null;
	private List<EntityApp> mList = new Vector<EntityApp>();
	private RequestDownload[] mRequestDownload = new RequestDownload[Max];
		
	public TaskDownload() {
		for (int i = 0;i < Max;i++)
			mRequestDownload[i] = new RequestDownload(this);
	}
		
	public void setActivity(ActivityBase activityBase) {
		mActivityBase = activityBase;
	}
		
	public void add(EntityApp entityApp) {
		entityApp.setStatus(EnumAppStatus.WAITING);
		for (RequestDownload requestDownload : mRequestDownload) {			
			boolean busy = requestDownload.getBusy();
			if (!busy) {
				requestDownload.request(entityApp);				
				return;
			}
		}
		
		mList.add(entityApp);
	}
	
	public void cancel(EntityApp entityApp) {
		if (mList.indexOf(entityApp) > -1)
			mList.remove(entityApp);
		else {
			for (RequestDownload requestDownload : mRequestDownload) {
				if (requestDownload.getEntityApp() != null && entityApp.equals(requestDownload.getEntityApp())) {
					requestDownload.cancel();
					return;
				}
			}
		}		
	}		

	@Override
	public void onCallBackDownload(RequestDownload requestDownload, EnumDownloadStatus status, EntityApp entityApp) {
		requestDownload.setEntityApp(null);
		switch (status) {
			case FINISH:
				entityApp.setStatus(EnumAppStatus.INSTALL);
				break;
				
			case CANCEL:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
				
			case FAILED:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
	
			default:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
		}
		
		if (mActivityBase != null)
			mActivityBase.onAppStatus(entityApp);
		
		if (mList.size() > 0) {
			entityApp = mList.remove(0);
			requestDownload.request(entityApp);
		} else
			requestDownload.setBusy(false);
	}
}
