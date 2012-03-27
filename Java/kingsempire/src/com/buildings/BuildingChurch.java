package com.buildings;

import com.world.City;

public class BuildingChurch extends BuildingBase {

	@Override
	protected boolean onUpgrade(City city) {
		BuildingCastle buildingCastle = city.getBuildingCastle();
		if (buildingCastle == null || buildingCastle.getLevel() == null)
			return false;
		
		if (buildingCastle.getLevel() < 20)
			return false;
		else
			return true;
	}
}
