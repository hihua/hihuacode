package com.buildings;

public class BuildingTower {
	private Long defense;
	private Long level;
	private Long id;
	private Long type;
	private String status;
	private Long attack;
	
	public Long getDefense() {
		return defense;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getType() {
		return type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Long getAttack() {
		return attack;
	}
	
	public void setDefense(Long defense) {
		this.defense = defense;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setAttack(Long attack) {
		this.attack = attack;
	}	
}
