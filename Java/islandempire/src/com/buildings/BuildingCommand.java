package com.buildings;

public class BuildingCommand extends Building {
	private Long accelerateRate;
	private Long spyLine;
	private Long availableLine;

	public Long getAccelerateRate() {
		return accelerateRate;
	}

	public Long getSpyLine() {
		return spyLine;
	}

	public Long getAvailableLine() {
		return availableLine;
	}

	public void setAccelerateRate(Long accelerateRate) {
		this.accelerateRate = accelerateRate;
	}

	public void setSpyLine(Long spyLine) {
		this.spyLine = spyLine;
	}

	public void setAvailableLine(Long availableLine) {
		this.availableLine = availableLine;
	}
}
