package com.skills;

import com.resources.ResourcesBase;

public class SkillBase {
	private Long duration;
	private Long maxLevel;
	private Long level;
	private Long id;
	private Long leaderShip;
	private Long skillType;
	private String description;
	private SkillConsume consume;
	
	public Long getDuration() {
		return duration;
	}
	
	public Long getMaxLevel() {
		return maxLevel;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getLeaderShip() {
		return leaderShip;
	}
	
	public Long getSkillType() {
		return skillType;
	}
	
	public String getDescription() {
		return description;
	}
	
	public SkillConsume getConsume() {
		return consume;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public void setMaxLevel(Long maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setLeaderShip(Long leaderShip) {
		this.leaderShip = leaderShip;
	}
	
	public void setSkillType(Long skillType) {
		this.skillType = skillType;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setConsume(SkillConsume consume) {
		this.consume = consume;
	}
	
	public boolean checkUpgrade(Long gold) {
		if (getDuration() != null && getDuration() == 0 && getId() != null && getLevel() != null && getMaxLevel() != null && getLevel() < getMaxLevel()) {
			SkillConsume consume = getConsume();
			if (consume != null && consume.getGold() != null && consume.getGold() <= gold)
				return true;				
		}
		
		return false;
	}
	
	public void decreaseResources(ResourcesBase gold) {		
		if (gold == null || gold.getCount() == null)
			return;
		
		SkillConsume consume = getConsume();
		if (consume != null && consume.getGold() != null && consume.getGold() <= gold.getCount())
			gold.setCount(gold.getCount() - consume.getGold());
	}
}
