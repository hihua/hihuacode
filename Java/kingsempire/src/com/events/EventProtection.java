package com.events;

import java.util.Date;

public class EventProtection extends EventBase {
	private Date finishTime;

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
}
