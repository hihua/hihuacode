package com.queue;

import java.util.Date;

public class TransportQueue extends Queue {
	private Long fleetTownId;
	private Long fleetIndex;
	private Date finishTime;
	private Long queueId;

	public Long getFleetTownId() {
		return fleetTownId;
	}

	public Long getFleetIndex() {
		return fleetIndex;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Long getQueueId() {
		return queueId;
	}

	public void setFleetTownId(Long fleetTownId) {
		this.fleetTownId = fleetTownId;
	}

	public void setFleetIndex(Long fleetIndex) {
		this.fleetIndex = fleetIndex;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setQueueId(Long queueId) {
		this.queueId = queueId;
	}
}
