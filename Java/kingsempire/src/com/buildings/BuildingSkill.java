package com.buildings;

import com.world.City;

public class BuildingSkill extends BuildingBase {
	
	@Override
	protected boolean onUpgrade(City city) {		
		BuildingCastle buildingCastle = city.getBuildingCastle();
		if (buildingCastle == null || buildingCastle.getLevel() == null)
			return false;
		
		if (buildingCastle.getLevel() < 10)
			return false;
		else
			return true;
	}
}
