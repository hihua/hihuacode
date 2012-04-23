package com.config;

import java.util.HashMap;
import java.util.List;

public class ConfigTown {
	private Long id;
	private Boolean autoUpgrade = false;
	private List<Long> upgradePriority = null;
	private Boolean autoAttack = false;
	private Long attackTotal = 0L;
	private Long attackCount = 0L;
	private Long attackLevelMin = 0L;
	private Long attackLevelMax = 0L;
	private Boolean autoRecruit = false;
	private HashMap<String, Double> marketRate = null;

	public Long getId() {
		return id;
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

	public Long getAttackTotal() {
		return attackTotal;
	}

	public Long getAttackCount() {
		return attackCount;
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

	public HashMap<String, Double> getMarketRate() {
		return marketRate;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setAttackTotal(Long attackTotal) {
		this.attackTotal = attackTotal;
	}

	public void setAttackCount(Long attackCount) {
		this.attackCount = attackCount;
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

	public void setMarketRate(HashMap<String, Double> marketRate) {
		this.marketRate = marketRate;
	}
}
