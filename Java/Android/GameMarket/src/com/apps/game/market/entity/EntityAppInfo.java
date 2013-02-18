package com.apps.game.market.entity;

import android.graphics.drawable.Drawable;

public class EntityAppInfo {
	private String appName;
	private Drawable appIcon;
	private String packageName;
	private String versionName;
	private Integer versionCode;
	private Long size;

	public String getAppName() {
		return appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getVersionName() {
		return versionName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public Long getSize() {
		return size;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public void setSize(Long size) {
		this.size = size;
	}
}
