package com.apps.game.market.entity.app;

import android.widget.ImageView;

public class EntityAd {
	private String name;
	private String url;
	private ImageView imageView;
		
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
}
