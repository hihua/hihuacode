package com.queue;

import java.util.Date;
import java.util.List;

public class BuildingQueue {
	private Long totalTime;
	private Long nextLevel;
	private List<LinesEvent> linesEvent;
	private Date finishTime;
	private Long buildingId;
	private Long queueId;

	public Long getTotalTime() {
		return totalTime;
	}

	public Long getNextLevel() {
		return nextLevel;
	}

	public List<LinesEvent> getLinesEvent() {
		return linesEvent;
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

	public void setNextLevel(Long nextLevel) {
		this.nextLevel = nextLevel;
	}

	public void setLinesEvent(List<LinesEvent> linesEvent) {
		this.linesEvent = linesEvent;
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
