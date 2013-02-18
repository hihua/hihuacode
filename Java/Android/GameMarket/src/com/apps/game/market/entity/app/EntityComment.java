package com.apps.game.market.entity.app;

public class EntityComment {
	private long id;
	private String author;
	private long floor;
	private String date;
	private String comment;
	private float rating;

	public long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public long getFloor() {
		return floor;
	}

	public String getDate() {
		return date;
	}

	public String getComment() {
		return comment;
	}

	public float getRating() {
		return rating;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setFloor(long floor) {
		this.floor = floor;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
}
