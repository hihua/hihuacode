package com.apps.game.market.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;

import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.util.ApkManager;

public class GlobalData {
	public static GlobalData globalData = null;
	private Map<String, EntityApp> remoteApps = new HashMap<String, EntityApp>();
	private List<EntityAppInfo> localApps = new Vector<EntityAppInfo>();
	private Map<Long, List<EntityAd>> ads = new HashMap<Long, List<EntityAd>>();
	private List<EntityColumn> columns = new Vector<EntityColumn>();
	private List<EntityTag> tags = new Vector<EntityTag>();
	private int viewId = 0;
	private EntityApp selectApp = null;
	private EntityTag selectTag = null;
	private int selectColumn = -1;
	private Intent lastIntent = null;
	
	public GlobalData() {		
		globalData = this;
	}
	
	public boolean init(Context context) {
		List<EntityAppInfo> list = ApkManager.getApps(context);
		if (list != null) {
			for (EntityAppInfo entityAppInfo : list)
				putLocalApp(entityAppInfo);			
		}
		
		return true;
	}
	
	public List<EntityAppInfo> getLocalApps() {
		return localApps;
	}
	
	public synchronized void putRemoteApp(EntityApp remoteApp) {
		String packageName = remoteApp.getPackageName();
		if (!remoteApps.containsKey(packageName))
			remoteApps.put(packageName, remoteApp);
	}
	
	public void putLocalApp(EntityAppInfo entityAppInfo) {
		String packageName = entityAppInfo.getPackageName();
		for (EntityAppInfo appInfo : localApps) {
			if (appInfo.getPackageName().equals(packageName))
				return;
		}
		
		localApps.add(entityAppInfo);
	}
	
	public void removeLocalApp(String packageName) {
		for (EntityAppInfo appInfo : localApps) {
			if (appInfo.getPackageName().equals(packageName)) {
				localApps.remove(appInfo);
				break;
			}				
		}
		
		if (remoteApps.containsKey(packageName)) {
			EntityApp remoteApp = remoteApps.get(packageName);
			remoteApp.setStatus(EnumAppStatus.NOINSTALL);
		}
	}
	
	public boolean appInstalled(String packageName) {
		for (EntityAppInfo appInfo : localApps) {
			if (appInfo.getPackageName().equals(packageName))
				return true;
		}
		
		return false;
	}
	
	public boolean existAds(long id) {
		return ads.containsKey(id);
	}
	
	public List<EntityAd> getAds(long id) {
		if (existAds(id))
			return ads.get(id);
		else
			return null;
	}
	
	public void putAds(long n, List<EntityAd> list) {
		ads.put(n, list);
	}
	
	public void addColumns(List<EntityColumn> list) {
		columns.addAll(list);
	}
			
	public void addTags(List<EntityTag> list) {
		tags.addAll(list);
	}	
		
	public EntityColumn getColumn(int index) {
		if (index >= columns.size())
			return null;
		else
			return columns.get(index);
	}
	
	public List<EntityColumn> getColumns() {
		return columns;
	}
	
	public List<EntityTag> getTags() {
		return tags;
	}
	
	public int getViewId() {
		viewId++;
		return viewId;
	}
	
	public EntityApp getSelectApp() {
		return selectApp;
	}
	
	public void setSelectApp(EntityApp selectApp) {
		this.selectApp = selectApp;
	}
	
	public EntityTag getSelectTag() {
		return selectTag;
	}
	
	public void setSelectTag(EntityTag selectTag) {
		this.selectTag = selectTag;
	}
	
	public int getSelectColumn() {
		return selectColumn;
	}
	
	public void setSelectColumn(int selectColumn) {
		this.selectColumn = selectColumn;
	}
	
	public Intent getLastIntent() {
		return lastIntent;
	}
	
	public void setLastIntent(Intent lastIntent) {
		this.lastIntent = lastIntent;
	}
}
