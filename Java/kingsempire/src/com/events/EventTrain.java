package com.events;

import java.util.Date;

public class EventTrain extends EventBase {
	private String finishDesc;
	private Boolean canSpeed;
	private Long type;
	private Long id;
	private Long count;
	private Date finishTime;
	
	public String getFinishDesc() {
		return finishDesc;
	}
	
	public Boolean getCanSpeed() {
		return canSpeed;
	}
	
	public Long getType() {
		return type;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getCount() {
		return count;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	
	public void setFinishDesc(String finishDesc) {
		this.finishDesc = finishDesc;
	}
	
	public void setCanSpeed(Boolean canSpeed) {
		this.canSpeed = canSpeed;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}	
}
