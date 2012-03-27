package com.entity;

import com.world.City;

public class CityInfo {
	private City city;
	private String packet;
	private String updateTime;
	
	public City getCity() {
		return city;
	}
	
	public String getPacket() {
		return packet;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void setPacket(String packet) {
		this.packet = packet;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}	
}
