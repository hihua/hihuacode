package com.hero;

import java.util.List;

public class Enhance {
	private List<Long> luckGems;
	private Double rate;
	private Long safeGems;
	private List<Long> luckValue;
	private Long profit;
	private NeedResources needResources;
	private FreeSafeResources freeSafeResources;

	public List<Long> getLuckGems() {
		return luckGems;
	}

	public Double getRate() {
		return rate;
	}

	public Long getSafeGems() {
		return safeGems;
	}

	public List<Long> getLuckValue() {
		return luckValue;
	}

	public Long getProfit() {
		return profit;
	}

	public NeedResources getNeedResources() {
		return needResources;
	}

	public FreeSafeResources getFreeSafeResources() {
		return freeSafeResources;
	}

	public void setLuckGems(List<Long> luckGems) {
		this.luckGems = luckGems;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public void setSafeGems(Long safeGems) {
		this.safeGems = safeGems;
	}

	public void setLuckValue(List<Long> luckValue) {
		this.luckValue = luckValue;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public void setNeedResources(NeedResources needResources) {
		this.needResources = needResources;
	}

	public void setFreeSafeResources(FreeSafeResources freeSafeResources) {
		this.freeSafeResources = freeSafeResources;
	}
}
