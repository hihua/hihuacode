package com.skills;

public class SkillSoldiers {
	private SkillBase InfantryAttack;
	private SkillBase InfantryDefense;
	private SkillBase CavalryAttack;
	private SkillBase CavalryDefense;
	
	public SkillBase getInfantryAttack() {
		return InfantryAttack;
	}
	
	public SkillBase getInfantryDefense() {
		return InfantryDefense;
	}
	
	public SkillBase getCavalryAttack() {
		return CavalryAttack;
	}
	
	public SkillBase getCavalryDefense() {
		return CavalryDefense;
	}
	
	public void setInfantryAttack(SkillBase infantryAttack) {
		InfantryAttack = infantryAttack;
	}
	
	public void setInfantryDefense(SkillBase infantryDefense) {
		InfantryDefense = infantryDefense;
	}
	
	public void setCavalryAttack(SkillBase cavalryAttack) {
		CavalryAttack = cavalryAttack;
	}
	
	public void setCavalryDefense(SkillBase cavalryDefense) {
		CavalryDefense = cavalryDefense;
	}	
}
