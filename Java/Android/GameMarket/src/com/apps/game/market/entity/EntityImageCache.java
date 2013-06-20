package com.apps.game.market.entity;

import android.graphics.Bitmap;

public class EntityImageCache {
	private String url;
	private Bitmap bitmap;

	public String getUrl() {
		return url;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
