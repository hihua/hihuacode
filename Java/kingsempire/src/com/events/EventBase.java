package com.events;

public class EventBase {
	private Long duration;
	private Boolean canCancel;
	private String desc;
	private Boolean emergency;
	
	public Long getDuration() {
		return duration;
	}
	
	public Boolean getCanCancel() {
		return canCancel;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public Boolean getEmergency() {
		return emergency;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public void setCanCancel(Boolean canCancel) {
		this.canCancel = canCancel;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setEmergency(Boolean emergency) {
		this.emergency = emergency;
	}	
}
