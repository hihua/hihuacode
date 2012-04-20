package com.entity;

import com.towns.Town;

public class TownInfo {
	private Town town;
	private String packet;
	private String updateTime;

	public Town getTown() {
		return town;
	}

	public String getPacket() {
		return packet;
	}

	public String getUpdateTime() {
		return updateTime;
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
