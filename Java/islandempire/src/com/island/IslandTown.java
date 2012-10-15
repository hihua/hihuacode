package com.island;

public class IslandTown extends IslandBuilding {
	private Long honorPointCanPlunder;
	private Long cityAttackLevel;
	private String ownerName;
	private Long ownerId;
	private Long ownerHonorLevel;
	private Long ownerHonorPoint;
	private Long ownerScore;
	private Long cityWallStatus;

	public Long getHonorPointCanPlunder() {
		return honorPointCanPlunder;
	}

	public Long getCityAttackLevel() {
		return cityAttackLevel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Long getOwnerHonorLevel() {
		return ownerHonorLevel;
	}

	public Long getOwnerHonorPoint() {
		return ownerHonorPoint;
	}

	public Long getOwnerScore() {
		return ownerScore;
	}

	public Long getCityWallStatus() {
		return cityWallStatus;
	}

	public void setHonorPointCanPlunder(Long honorPointCanPlunder) {
		this.honorPointCanPlunder = honorPointCanPlunder;
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

	public void setOwnerHonorLevel(Long ownerHonorLevel) {
		this.ownerHonorLevel = ownerHonorLevel;
	}

	public void setOwnerHonorPoint(Long ownerHonorPoint) {
		this.ownerHonorPoint = ownerHonorPoint;
	}

	public void setOwnerScore(Long ownerScore) {
		this.ownerScore = ownerScore;
	}

	public void setCityWallStatus(Long cityWallStatus) {
		this.cityWallStatus = cityWallStatus;
	}
}
