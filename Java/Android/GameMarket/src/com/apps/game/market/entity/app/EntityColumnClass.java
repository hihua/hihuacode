package com.apps.game.market.entity.app;

import java.util.Map;

public class EntityColumnClass {
	private String name;
	private Map<String, String> urls;

	public String getName() {
		return name;
	}

	public Map<String, String> getUrls() {
		return urls;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}
}
