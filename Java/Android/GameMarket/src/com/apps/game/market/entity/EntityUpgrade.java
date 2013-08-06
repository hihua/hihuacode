package com.apps.game.market.entity;

public class EntityUpgrade {
	private int upgradeVersionCode;
	private String upgradeVersionName;
	private String upgradeUrl;
	private long upgradeFileSize;
	private int upgradeForce;
	private int entryTable;

	public int getUpgradeVersionCode() {
		return upgradeVersionCode;
	}

	public void setUpgradeVersionCode(int upgradeVersionCode) {
		this.upgradeVersionCode = upgradeVersionCode;
	}

	public String getUpgradeVersionName() {
		return upgradeVersionName;
	}

	public void setUpgradeVersionName(String upgradeVersionName) {
		this.upgradeVersionName = upgradeVersionName;
	}

	public String getUpgradeUrl() {
		return upgradeUrl;
	}

	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}

	public long getUpgradeFileSize() {
		return upgradeFileSize;
	}

	public void setUpgradeFileSize(long upgradeFileSize) {
		this.upgradeFileSize = upgradeFileSize;
	}

	public int getUpgradeForce() {
		return upgradeForce;
	}

	public void setUpgradeForce(int upgradeForce) {
		this.upgradeForce = upgradeForce;
	}

	public int getEntryTable() {
		return entryTable;
	}

	public void setEntryTable(int entryTable) {
		this.entryTable = entryTable;
	}
}
