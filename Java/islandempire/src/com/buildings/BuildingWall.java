package com.buildings;

import java.util.List;

public class BuildingWall extends Building {
	private Long defense;
	private List<BuildingTower>	buildingTower;
	
	public Long getDefense() {
		return defense;
	}
	
	public List<BuildingTower> getBuildingTower() {
		return buildingTower;
	}
	
	public void setDefense(Long defense) {
		this.defense = defense;
	}
	
	public void setBuildingTower(List<BuildingTower> buildingTower) {
		this.buildingTower = buildingTower;
	}	
}
