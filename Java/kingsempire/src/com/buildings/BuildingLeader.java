package com.buildings;

import com.world.City;

public class BuildingLeader extends BuildingBase {
	
	@Override
	protected boolean onUpgrade(City city) {		
		return true;
	}
}
