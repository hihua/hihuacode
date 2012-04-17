package com.buildings;

public class BuildingPort extends Building {
	private Long availableFleet;
	private Long fleetQuota;
	
	public Long getAvailableFleet() {
		return availableFleet;
	}
	
	public Long getFleetQuota() {
		return fleetQuota;
	}
	
	public void setAvailableFleet(Long availableFleet) {
		this.availableFleet = availableFleet;
	}
	
	public void setFleetQuota(Long fleetQuota) {
		this.fleetQuota = fleetQuota;
	}	
}
