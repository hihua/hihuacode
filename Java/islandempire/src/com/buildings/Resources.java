package com.buildings;

public class Resources {
	private Long maxVolume;
	private Long increasePerHour;
	private String resourceName;
	private Long resourceCount;

	public Long getMaxVolume() {
		return maxVolume;
	}

	public Long getIncreasePerHour() {
		return increasePerHour;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Long getResourceCount() {
		return resourceCount;
	}

	public void setMaxVolume(Long maxVolume) {
		this.maxVolume = maxVolume;
	}

	public void setIncreasePerHour(Long increasePerHour) {
		this.increasePerHour = increasePerHour;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceCount(Long resourceCount) {
		this.resourceCount = resourceCount;
	}
}
