package com.hero;

public class Enhance {
	private Long defense;
	private Long npcPrice;
	private Double rate;
	private Long intelligence;
	private Long attack;
	private NeedResources needResources;

	public Long getDefense() {
		return defense;
	}

	public Long getNpcPrice() {
		return npcPrice;
	}

	public Double getRate() {
		return rate;
	}

	public Long getIntelligence() {
		return intelligence;
	}

	public Long getAttack() {
		return attack;
	}

	public NeedResources getNeedResources() {
		return needResources;
	}

	public void setDefense(Long defense) {
		this.defense = defense;
	}

	public void setNpcPrice(Long npcPrice) {
		this.npcPrice = npcPrice;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public void setIntelligence(Long intelligence) {
		this.intelligence = intelligence;
	}

	public void setAttack(Long attack) {
		this.attack = attack;
	}

	public void setNeedResources(NeedResources needResources) {
		this.needResources = needResources;
	}
}
