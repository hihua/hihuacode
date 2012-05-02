package com.worldmap;

import java.util.Date;

public class Tile {
	private Long mapType;
	private String name;
	private Long x;
	private Long y;
	private Long occupy;
	private Long level;
	private Date recoveryTime;
	private Date protectionFinishAt;
	private Long id;
	private Long majorCity;
	private Boolean inProtection;
	private String ownerCityName;
	private String ownerUserName;
	
	public Long getMapType() {
		return mapType;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getX() {
		return x;
	}
	
	public Long getY() {
		return y;
	}
	
	public Long getOccupy() {
		return occupy;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public Date getRecoveryTime() {
		return recoveryTime;
	}
	
	public Date getProtectionFinishAt() {
		return protectionFinishAt;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getMajorCity() {
		return majorCity;
	}
	
	public Boolean getInProtection() {
		return inProtection;
	}
	
	public String getOwnerCityName() {
		return ownerCityName;
	}
	
	public String getOwnerUserName() {
		return ownerUserName;
	}
	
	public void setMapType(Long mapType) {
		this.mapType = mapType;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setX(Long x) {
		this.x = x;
	}
	
	public void setY(Long y) {
		this.y = y;
	}
	
	public void setOccupy(Long occupy) {
		this.occupy = occupy;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	
	public void setProtectionFinishAt(Date protectionFinishAt) {
		this.protectionFinishAt = protectionFinishAt;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setMajorCity(Long majorCity) {
		this.majorCity = majorCity;
	}
	
	public void setInProtection(Boolean inProtection) {
		this.inProtection = inProtection;
	}
	
	public void setOwnerCityName(String ownerCityName) {
		this.ownerCityName = ownerCityName;
	}
	
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
}
