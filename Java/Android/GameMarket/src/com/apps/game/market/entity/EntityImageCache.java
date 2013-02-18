package com.apps.game.market.entity;

import android.graphics.drawable.Drawable;

public class EntityImageCache {
	private String url;
	private Drawable drawable;

	public String getUrl() {
		return url;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
}
