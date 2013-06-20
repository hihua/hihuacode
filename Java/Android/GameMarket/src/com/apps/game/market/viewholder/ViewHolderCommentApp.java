package com.apps.game.market.viewholder;

import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolderCommentApp {
	private TextView nick;
	private RatingBar rating;
	private TextView score;
	private TextView content;
	private TextView date;

	public TextView getNick() {
		return nick;
	}

	public void setNick(TextView nick) {
		this.nick = nick;
	}

	public RatingBar getRating() {
		return rating;
	}

	public void setRating(RatingBar rating) {
		this.rating = rating;
	}

	public TextView getScore() {
		return score;
	}

	public void setScore(TextView score) {
		this.score = score;
	}

	public TextView getContent() {
		return content;
	}

	public void setContent(TextView content) {
		this.content = content;
	}

	public TextView getDate() {
		return date;
	}

	public void setDate(TextView date) {
		this.date = date;
	}
}
