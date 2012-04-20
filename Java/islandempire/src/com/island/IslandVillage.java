package com.island;

import java.util.Date;

public class IslandVillage extends IslandBuilding {
	private Long totalTime;
	private Date finishTime;

	public Long getTotalTime() {
		return totalTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
}
