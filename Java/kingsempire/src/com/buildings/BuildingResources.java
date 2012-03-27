package com.buildings;

import com.world.City;

public class BuildingResources extends BuildingBase {
	private Long resources;
	
	public Long getResources() {
		return resources;
	}

	public void setResources(Long resources) {
		this.resources = resources;
	}
	
	@Override
	protected boolean onUpgrade(City city) {
		return true;
		
//		ResourcesBase resourcesWood = city.getResourcesWood();
//		ResourcesBase resourcesStone = city.getResourcesStone();
//		ResourcesBase resourcesIron = city.getResourcesIron();
//		ResourcesBase resourcesFood = city.getResourcesFood();
//				
//		if (getResources() == null)
//			return false;
//				
//		if (getResources() == 0) {
//			if (resourcesWood == null)
//				return false;
//			
//			Long count = resourcesWood.getCount();
//			if (count == null)
//				return false;
//		
//			if (resourcesStone != null && resourcesStone.getCount() != null && count >= resourcesStone.getCount())
//				return false;
//				
//			if (resourcesIron != null && resourcesIron.getCount() != null && count >= resourcesIron.getCount())
//				return false;
//			
//			if (resourcesFood != null && resourcesFood.getCount() != null && count >= resourcesFood.getCount())
//				return false;
//			
//			return true;
//		}
//		
//		if (getResources() == 1) {
//			if (resourcesStone == null)
//				return false;
//			
//			Long count = resourcesStone.getCount();
//			if (count == null)
//				return false;
//		
//			if (resourcesWood != null && resourcesWood.getCount() != null && count >= resourcesWood.getCount())
//				return false;
//				
//			if (resourcesIron != null && resourcesIron.getCount() != null && count >= resourcesIron.getCount())
//				return false;
//			
//			if (resourcesFood != null && resourcesFood.getCount() != null && count >= resourcesFood.getCount())
//				return false;
//			
//			return true;
//		}
//		
//		if (getResources() == 2) {
//			if (resourcesIron == null)
//				return false;
//			
//			Long count = resourcesIron.getCount();
//			if (count == null)
//				return false;
//		
//			if (resourcesWood != null && resourcesWood.getCount() != null && count >= resourcesWood.getCount())
//				return false;
//			
//			if (resourcesStone != null && resourcesStone.getCount() != null && count >= resourcesStone.getCount())
//				return false;			
//						
//			if (resourcesFood != null && resourcesFood.getCount() != null && count >= resourcesFood.getCount())
//				return false;
//			
//			return true;
//		}
//		
//		if (getResources() == 3) {
//			if (resourcesFood == null)
//				return false;
//			
//			Long count = resourcesFood.getCount();
//			if (count == null)
//				return false;
//		
//			if (resourcesWood != null && resourcesWood.getCount() != null && count >= resourcesWood.getCount())
//				return false;
//			
//			if (resourcesStone != null && resourcesStone.getCount() != null && count >= resourcesStone.getCount())
//				return false;
//				
//			if (resourcesIron != null && resourcesIron.getCount() != null && count >= resourcesIron.getCount())
//				return false;
//									
//			return true;
//		}
//			
//		return false;
	}
}
