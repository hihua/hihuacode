package com.queue;

import java.util.Date;

public class BattleQueue extends Queue {
	private Long toX;
	private Long toLevel;
	private Date arriveTime;
	private Long toY;
	private Long fromLevel;
	private Long id;
	private Long fromX;
	private String fromTownName;
	private Long fromY;

	public Long getToX() {
		return toX;
	}

	public Long getToLevel() {
		return toLevel;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public Long getToY() {
		return toY;
	}

	public Long getFromLevel() {
		return fromLevel;
	}

	public Long getId() {
		return id;
	}

	public Long getFromX() {
		return fromX;
	}

	public String getFromTownName() {
		return fromTownName;
	}

	public Long getFromY() {
		return fromY;
	}

	public void setToX(Long toX) {
		this.toX = toX;
	}

	public void setToLevel(Long toLevel) {
		this.toLevel = toLevel;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public void setToY(Long toY) {
		this.toY = toY;
	}

	public void setFromLevel(Long fromLevel) {
		this.fromLevel = fromLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFromX(Long fromX) {
		this.fromX = fromX;
	}

	public void setFromTownName(String fromTownName) {
		this.fromTownName = fromTownName;
	}

	public void setFromY(Long fromY) {
		this.fromY = fromY;
	}
}
