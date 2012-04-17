package com.buildings;

import java.util.List;

public class BuildingResource extends Building {
	private List<BuildingLine> buildingLine;

	public List<BuildingLine> getBuildingLine() {
		return buildingLine;
	}

	public void setLine(List<BuildingLine> buildingLine) {
		this.buildingLine = buildingLine;
	}	
}
