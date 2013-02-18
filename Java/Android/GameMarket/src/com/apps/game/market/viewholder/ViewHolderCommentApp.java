package com.apps.game.market.viewholder;

import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolderCommentApp {
	private TextView nick;
	private TextView floor;
	private RatingBar rating;
	private TextView content;
	private TextView date;

	public TextView getNick() {
		return nick;
	}

	public TextView getFloor() {
		return floor;
	}

	public RatingBar getRating() {
		return rating;
	}

	public TextView getContent() {
		return content;
	}

	public TextView getDate() {
		return date;
	}

	public void setNick(TextView nick) {
		this.nick = nick;
	}

	public void setFloor(TextView floor) {
		this.floor = floor;
	}

	public void setRating(RatingBar rating) {
		this.rating = rating;
	}

	public void setContent(TextView content) {
		this.content = content;
	}

	public void setDate(TextView date) {
		this.date = date;
	}
}
