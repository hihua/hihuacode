package com.island;

public class IslandTown extends IslandBuilding {
	private Long cityAttackLevel;
	private String ownerName;
	private Long ownerId;

	public Long getCityAttackLevel() {
		return cityAttackLevel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setCityAttackLevel(Long cityAttackLevel) {
		this.cityAttackLevel = cityAttackLevel;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
}
