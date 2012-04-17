package com.buildings;

public class BuildingMarket extends Building {
	private Long leftCapacity;
	private Long capacity;
	
	public Long getLeftCapacity() {
		return leftCapacity;
	}
	
	public Long getCapacity() {
		return capacity;
	}
	
	public void setLeftCapacity(Long leftCapacity) {
		this.leftCapacity = leftCapacity;
	}
	
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}	
}
