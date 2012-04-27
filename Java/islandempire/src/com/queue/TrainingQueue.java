package com.queue;

import java.util.Date;

public class TrainingQueue {
	private Long totalTime;
	private Long count;
	private Date finishTime;
	private Long queueId;

	public Long getTotalTime() {
		return totalTime;
	}

	public Long getCount() {
		return count;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Long getQueueId() {
		return queueId;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setQueueId(Long queueId) {
		this.queueId = queueId;
	}
}
