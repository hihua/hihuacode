package com.entity;

import java.util.List;

import com.world.Item;

public class ItemInfo {
	private List<Item> items;
	private String packet;
	private String updateTime;
	
	public List<Item> getItems() {
		return items;
	}
	
	public String getPacket() {
		return packet;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void setPacket(String packet) {
		this.packet = packet;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}	
}
