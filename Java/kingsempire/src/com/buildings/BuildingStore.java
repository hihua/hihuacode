package com.buildings;

import com.resources.ResourcesBase;
import com.world.City;

public class BuildingStore extends BuildingBase {
	
	@Override
	protected boolean onUpgrade(City city) {
		ResourcesBase resourcesWood = city.getResourcesWood();
		if (resourcesWood != null && resourcesWood.getCount() != null && resourcesWood.getCapacity() != null) {
			long count = resourcesWood.getCount();
			long capacity = resourcesWood.getCapacity();
			
			if ((double)count / (double)capacity >= 0.95)
				return true;
		}
		
		ResourcesBase resourcesIron = city.getResourcesIron();
		if (resourcesIron != null && resourcesIron.getCount() != null && resourcesIron.getCapacity() != null) {
			long count = resourcesIron.getCount();
			long capacity = resourcesIron.getCapacity();
			
			if ((double)count / (double)capacity >= 0.95)
				return true;
		}
		
		ResourcesBase resourcesStone = city.getResourcesStone();
		if (resourcesStone != null && resourcesStone.getCount() != null && resourcesStone.getCapacity() != null) {
			long count = resourcesStone.getCount();
			long capacity = resourcesStone.getCapacity();
			
			if ((double)count / (double)capacity >= 0.95)
				return true;
		}
		
		ResourcesBase resourcesFood = city.getResourcesFood();
		if (resourcesFood != null && resourcesFood.getCount() != null && resourcesFood.getCapacity() != null) {
			long count = resourcesFood.getCount();
			long capacity = resourcesFood.getCapacity();
			
			if ((double)count / (double)capacity >= 0.95)
				return true;
		}
		
		return false;
	}
}
