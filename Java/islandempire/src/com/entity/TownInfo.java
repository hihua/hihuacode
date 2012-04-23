package com.entity;

import com.towns.Town;

public class TownInfo {
	private Long id;
	private Town town;
	private String packet;
	private String updateTime;

	public Long getId() {
		return id;
	}
	
	public Town getTown() {
		return town;
	}

	public String getPacket() {
		return packet;
	}

	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	

	public void setTown(Town town) {
		this.town = town;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
