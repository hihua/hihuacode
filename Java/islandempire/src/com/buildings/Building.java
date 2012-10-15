package com.buildings;

public class Building {
	private Long maxLevel;
	private Long durability;
	private Long level;
	private Long id;
	private Long buildingType;
	private String status;
	private Long anchorIndex;

	public Long getMaxLevel() {
		return maxLevel;
	}

	public Long getDurability() {
		return durability;
	}

	public Long getLevel() {
		return level;
	}

	public Long getId() {
		return id;
	}

	public Long getBuildingType() {
		return buildingType;
	}

	public String getStatus() {
		return status;
	}

	public Long getAnchorIndex() {
		return anchorIndex;
	}

	public void setMaxLevel(Long maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setDurability(Long durability) {
		this.durability = durability;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBuildingType(Long buildingType) {
		this.buildingType = buildingType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAnchorIndex(Long anchorIndex) {
		this.anchorIndex = anchorIndex;
	}
}
