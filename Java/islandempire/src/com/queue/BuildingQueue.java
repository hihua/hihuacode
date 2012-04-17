package com.queue;

import java.util.Date;

public class BuildingQueue {
	private Long totalTime;
	private Date finishTime;
	private Long buildingId;
	private Long queueId;

	public Long getTotalTime() {
		return totalTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public Long getQueueId() {
		return queueId;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public void setQueueId(Long queueId) {
		this.queueId = queueId;
	}
}
