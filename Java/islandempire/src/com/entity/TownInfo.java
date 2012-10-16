package com.entity;

import com.towns.Town;

public class TownInfo {
	private Long townId;
	private Town town;
	private String packet;
	private String updateTime;
	private String heroEquipment;

	public Long getTownId() {
		return townId;
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

	public String getHeroEquipment() {
		return heroEquipment;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
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

	public void setHeroEquipment(String heroEquipment) {
		this.heroEquipment = heroEquipment;
	}
}
