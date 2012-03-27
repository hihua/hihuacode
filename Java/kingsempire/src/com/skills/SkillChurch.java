package com.skills;

public class SkillChurch extends SkillBase {	
	private SkillBase churchGift;
	private SkillBase churchLucky;
	private SkillBase churchRecall;
	private SkillBase churchAngel;
	
	public SkillBase getChurchGift() {
		return churchGift;
	}
	
	public SkillBase getChurchLucky() {
		return churchLucky;
	}
	
	public SkillBase getChurchRecall() {
		return churchRecall;
	}
	
	public SkillBase getChurchAngel() {
		return churchAngel;
	}
	
	public void setChurchGift(SkillBase churchGift) {
		this.churchGift = churchGift;
	}
	
	public void setChurchLucky(SkillBase churchLucky) {
		this.churchLucky = churchLucky;
	}
	
	public void setChurchRecall(SkillBase churchRecall) {
		this.churchRecall = churchRecall;
	}
	
	public void setChurchAngel(SkillBase churchAngel) {
		this.churchAngel = churchAngel;
	}	
}
