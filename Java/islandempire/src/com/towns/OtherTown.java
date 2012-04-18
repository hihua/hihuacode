package com.towns;

import java.util.List;

import com.queue.BattleQueue;

public class OtherTown {
	private String name;
	private Long resourceType;
	private List<BattleQueue> battleQueues;
	private Long level;
	private Long id;
	private Long islandX;
	private Long islandY;
	private Long islandZ;
	private Long islandNumber;
	private Boolean isCapital;

	public String getName() {
		return name;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public List<BattleQueue> getBattleQueues() {
		return battleQueues;
	}

	public Long getLevel() {
		return level;
	}

	public Long getId() {
		return id;
	}

	public Long getIslandX() {
		return islandX;
	}

	public Long getIslandY() {
		return islandY;
	}

	public Long getIslandZ() {
		return islandZ;
	}

	public Long getIslandNumber() {
		return islandNumber;
	}

	public Boolean getIsCapital() {
		return isCapital;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public void setBattleQueues(List<BattleQueue> battleQueues) {
		this.battleQueues = battleQueues;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIslandX(Long islandX) {
		this.islandX = islandX;
	}

	public void setIslandY(Long islandY) {
		this.islandY = islandY;
	}

	public void setIslandZ(Long islandZ) {
		this.islandZ = islandZ;
	}

	public void setIslandNumber(Long islandNumber) {
		this.islandNumber = islandNumber;
	}

	public void setIsCapital(Boolean isCapital) {
		this.isCapital = isCapital;
	}
}
