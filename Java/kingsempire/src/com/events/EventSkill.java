package com.events;

public class EventSkill extends EventBase {
	private String finishDesc;
	private Boolean canSpeed;
	private Long type;
	
	public String getFinishDesc() {
		return finishDesc;
	}
	
	public Boolean getCanSpeed() {
		return canSpeed;
	}
	
	public Long getType() {
		return type;
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
}
