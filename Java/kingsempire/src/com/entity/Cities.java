package com.entity;

import java.util.List;

public class Cities {
	private List<CityInfo> cityInfos;
	private ItemInfo itemInfo;
	private String message;
	
	public List<CityInfo> getCityInfos() {
		return cityInfos;
	}
	
	public ItemInfo getItemInfos() {
		return itemInfo;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setCityInfos(List<CityInfo> cityInfos) {
		this.cityInfos = cityInfos;
	}
	
	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}		
}
