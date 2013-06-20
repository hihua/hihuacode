package com.apps.game.market.entity.app;

import java.util.List;

import com.apps.game.market.request.app.RequestApp;

public class EntityColumn {
	private long id;
	private String name;
	private boolean single;
	private List<EntityColumnClass> columnClass;
	private String desc;
	private RequestApp request;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean getSingle() {
		return single;
	}

	public List<EntityColumnClass> getColumnClass() {
		return columnClass;
	}

	public String getDesc() {
		return desc;
	}

	public RequestApp getRequest() {
		return request;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public void setColumnClass(List<EntityColumnClass> columnClass) {
		this.columnClass = columnClass;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setRequest(RequestApp request) {
		this.request = request;
	}
}
