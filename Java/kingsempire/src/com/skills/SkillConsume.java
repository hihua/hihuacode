package com.skills;

public class SkillConsume {
	private Long duration;
	private Long gold;
	private String description;
	private String message;
	
	public Long getDuration() {
		return duration;
	}
	
	public Long getGold() {
		return gold;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public void setGold(Long gold) {
		this.gold = gold;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
