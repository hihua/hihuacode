package com.buildings;

public class BuildingConsume {
	private Long duration;
	private Long wood;
	private String description;
	private Long stone;
	
	public Long getDuration() {
		return duration;
	}
	
	public Long getWood() {
		return wood;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Long getStone() {
		return stone;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public void setWood(Long wood) {
		this.wood = wood;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStone(Long stone) {
		this.stone = stone;
	}	
}
