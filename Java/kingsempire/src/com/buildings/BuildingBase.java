package com.buildings;

import com.resources.ResourcesBase;
import com.world.City;

public abstract class BuildingBase {
	private Long duration;
	private Long maxLevel;
	private Long property;
	private Long level;
	private Long id;
	private BuildingConsume consume;	
	private String description;
	private Long buildingType;
				
	public Long getDuration() {
		return duration;
	}

	public Long getMaxLevel() {
		return maxLevel;
	}

	public Long getProperty() {
		return property;
	}

	public Long getLevel() {
		return level;
	}

	public Long getId() {
		return id;
	}

	public BuildingConsume getConsume() {
		return consume;
	}

	public String getDescription() {
		return description;
	}

	public Long getBuildingType() {
		return buildingType;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public void setMaxLevel(Long maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConsume(BuildingConsume consume) {
		this.consume = consume;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBuildingType(Long buildingType) {
		this.buildingType = buildingType;
	}

	public boolean checkUpgrade(City city) {
		if (getLevel() == null || getMaxLevel() == null || getDuration() == null)
			return false;
		
		if (getLevel() >= getMaxLevel() || getDuration() > 0)
			return false;
		
		BuildingConsume buildingConsume = getConsume();
		if (buildingConsume == null)
			return false;
		
		Long wood = buildingConsume.getWood();
		ResourcesBase resourcesWood = city.getResourcesWood();
		if (wood != null) {
			if (resourcesWood != null && resourcesWood.getCount() != null) {
				if (wood > resourcesWood.getCount())
					return false;
			} else
				return false;			
		}
		
		Long stone = buildingConsume.getStone();
		ResourcesBase resourcesStone = city.getResourcesStone();
		if (stone != null) {
			if (resourcesStone != null && resourcesStone.getCount() != null) {
				if (stone > resourcesStone.getCount())
					return false;
			} else
				return false;
		}
		
		if (wood == null && stone == null)
			return false;
		else		
			return onUpgrade(city);		
	}
	
	public void decreaseResources(City city) {
		BuildingConsume buildingConsume = getConsume();
		if (buildingConsume == null)
			return;
		
		Long wood = buildingConsume.getWood();
		ResourcesBase resourcesWood = city.getResourcesWood();
		if (wood != null && resourcesWood != null && resourcesWood.getCount() != null && wood <= resourcesWood.getCount())
			resourcesWood.setCount(resourcesWood.getCount() - wood);			
				
		Long stone = buildingConsume.getStone();
		ResourcesBase resourcesStone = city.getResourcesStone();
		if (stone != null && resourcesStone != null && resourcesStone.getCount() != null && stone <= resourcesStone.getCount())
			resourcesStone.setCount(resourcesStone.getCount() - stone);
	}
	
	protected abstract boolean onUpgrade(City city);
}
