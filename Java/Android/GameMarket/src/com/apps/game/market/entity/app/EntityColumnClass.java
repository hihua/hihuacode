package com.apps.game.market.entity.app;

import com.apps.game.market.request.app.RequestApp;

public class EntityColumnClass {
	private String name;
	private RequestApp request;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RequestApp getRequest() {
		return request;
	}

	public void setRequest(RequestApp request) {
		this.request = request;
	}
}
