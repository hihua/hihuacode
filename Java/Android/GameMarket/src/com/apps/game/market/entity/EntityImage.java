package com.apps.game.market.entity;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class EntityImage {
	private String url;
	private ImageView imageView;
	private Drawable drawable;

	public String getUrl() {
		return url;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
}
