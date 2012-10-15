package com.island;

public class IslandBuilding {
	private String name;
	private Long subType;
	private Long level;
	private Long id;
	private Long islandId;
	private String type;
	private String cityStatus;
	private Long islandZ;
	private String moveDestroyStatus;

	public String getName() {
		return name;
	}

	public Long getSubType() {
		return subType;
	}

	public Long getLevel() {
		return level;
	}

	public Long getId() {
		return id;
	}

	public Long getIslandId() {
		return islandId;
	}

	public String getType() {
		return type;
	}

	public String getCityStatus() {
		return cityStatus;
	}

	public Long getIslandZ() {
		return islandZ;
	}

	public String getMoveDestroyStatus() {
		return moveDestroyStatus;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubType(Long subType) {
		this.subType = subType;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIslandId(Long islandId) {
		this.islandId = islandId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCityStatus(String cityStatus) {
		this.cityStatus = cityStatus;
	}

	public void setIslandZ(Long islandZ) {
		this.islandZ = islandZ;
	}

	public void setMoveDestroyStatus(String moveDestroyStatus) {
		this.moveDestroyStatus = moveDestroyStatus;
	}
}
