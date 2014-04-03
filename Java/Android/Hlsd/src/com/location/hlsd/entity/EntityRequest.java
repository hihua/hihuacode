package com.location.hlsd.entity;

import android.content.Context;

public class EntityRequest {
	private String url;
	private boolean string = true;
	private String charset = "UTF-8";
	private Context context;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public boolean getString() {
		return string;
	}

	public void setString(final boolean string) {
		this.string = string;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(final String charset) {
		this.charset = charset;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(final Context context) {
		this.context = context;
	}
}
