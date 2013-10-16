package com.location.mylbs.entity;

public class EntityLocations {
	private long serverTime;
	private long clientTime;
	private double latitude;
	private double longitude;
	private float radius;
	private float direction;

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(final long serverTime) {
		this.serverTime = serverTime;
	}

	public long getClientTime() {
		return clientTime;
	}

	public void setClientTime(final long clientTime) {
		this.clientTime = clientTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(final float radius) {
		this.radius = radius;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(final float direction) {
		this.direction = direction;
	}
}
