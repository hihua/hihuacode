package com.events;

import java.util.Date;

public class EventArmy extends EventBase {
	private String fromCityName;
	private Long totalTime;
	private Date arriveTime;
	private Long mission;
	private Long id;
	private String toCityName;
	
	public String getFromCityName() {
		return fromCityName;
	}
	
	public Long getTotalTime() {
		return totalTime;
	}
	
	public Date getArriveTime() {
		return arriveTime;
	}
	
	public Long getMission() {
		return mission;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getToCityName() {
		return toCityName;
	}
	
	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}
	
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public void setMission(Long mission) {
		this.mission = mission;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}	
}
