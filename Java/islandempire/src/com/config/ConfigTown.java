package com.config;

import java.util.HashMap;
import java.util.List;

public class ConfigTown {
	private Long townId;
	private Boolean autoUpgrade = false;
	private List<Long> upgradePriority = null;
	private Boolean autoAttack = false;
	private Long attackLevelMin = 0L;
	private Long attackLevelMax = 0L;
	private Boolean autoRecruit = false;
	private HashMap<String, Double> sells = null;
	private HashMap<String, Double> buys = null;

	public Long getTownId() {
		return townId;
	}

	public Boolean getAutoUpgrade() {
		return autoUpgrade;
	}

	public List<Long> getUpgradePriority() {
		return upgradePriority;
	}

	public Boolean getAutoAttack() {
		return autoAttack;
	}

	public Long getAttackLevelMin() {
		return attackLevelMin;
	}

	public Long getAttackLevelMax() {
		return attackLevelMax;
	}

	public Boolean getAutoRecruit() {
		return autoRecruit;
	}

	public HashMap<String, Double> getSells() {
		return sells;
	}

	public HashMap<String, Double> getBuys() {
		return buys;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}

	public void setUpgradePriority(List<Long> upgradePriority) {
		this.upgradePriority = upgradePriority;
	}

	public void setAutoAttack(Boolean autoAttack) {
		this.autoAttack = autoAttack;
	}

	public void setAttackLevelMin(Long attackLevelMin) {
		this.attackLevelMin = attackLevelMin;
	}

	public void setAttackLevelMax(Long attackLevelMax) {
		this.attackLevelMax = attackLevelMax;
	}

	public void setAutoRecruit(Boolean autoRecruit) {
		this.autoRecruit = autoRecruit;
	}

	public void setSells(HashMap<String, Double> sells) {
		this.sells = sells;
	}

	public void setBuys(HashMap<String, Double> buys) {
		this.buys = buys;
	}
}
