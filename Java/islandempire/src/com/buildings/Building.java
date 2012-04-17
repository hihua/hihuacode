package com.buildings;

public class Building {
	private Long level;
	private Long id;
	private Long buildingType;
	private String status;
	private Long anchorIndex;
	
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
