package com.buildings;

import com.world.City;

public class BuildingSoldiers extends BuildingBase {
	
	@Override
	protected boolean onUpgrade(City city) {
		if (getBuildingType() == null)
			return false;
		
		if (getBuildingType() == 12) {
			BuildingCastle buildingCastle = city.getBuildingCastle();
			if (buildingCastle == null || buildingCastle.getLevel() == null)
				return false;
			
			if (buildingCastle.getLevel() < 10)
				return false;
			else {
				if (getLevel() == null)
					return false;
								
				BuildingSoldiers infantry = city.getBuildingInfantry();
				if (infantry == null || infantry.getLevel() == null)
					return true;
				
				if (getLevel() <= infantry.getLevel())
					return true;
				else
					return false;
			}
		} else {
			if (getLevel() == null)
				return false;
							
			BuildingSoldiers cavalry = city.getBuildingCavalry();
			if (cavalry == null || cavalry.getLevel() == null || cavalry.getLevel() == 0)
				return true;
			
			if (getLevel() < cavalry.getLevel())
				return true;
			else
				return false;
		}			
	}
}
