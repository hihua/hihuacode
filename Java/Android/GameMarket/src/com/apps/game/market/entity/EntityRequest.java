package com.apps.game.market.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityRequest {
	private String url;
	private Map<String, String> header;
	private String body;
	private boolean string = true;
	private String charset = "UTF-8";
	
	public String getUrl() {
		return url;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}

	public boolean getString() {
		return string;
	}

	public String getCharset() {
		return charset;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setString(boolean string) {
		this.string = string;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void addHeader(String key, String value) {
		if (header == null)
			header = new HashMap<String, String>();

		header.put(key, value);
	}
}
