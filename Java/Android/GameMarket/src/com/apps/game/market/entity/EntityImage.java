package com.apps.game.market.entity;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class EntityImage {
	private String url;
	private ImageView imageView;
	private Bitmap bitmap;

	public String getUrl() {
		return url;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
