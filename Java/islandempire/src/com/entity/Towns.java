package com.entity;

import java.util.List;

public class Towns {
	private List<TownInfo> townInfos;
	private String message;
	private String ranks;
	private String heroes;

	public List<TownInfo> getTownInfos() {
		return townInfos;
	}

	public String getMessage() {
		return message;
	}

	public String getRanks() {
		return ranks;
	}

	public String getHeroes() {
		return heroes;
	}

	public void setTownInfos(List<TownInfo> townInfos) {
		this.townInfos = townInfos;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRanks(String ranks) {
		this.ranks = ranks;
	}

	public void setHeroes(String heroes) {
		this.heroes = heroes;
	}
}
