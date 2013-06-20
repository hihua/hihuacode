package com.apps.game.market.viewholder;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolderSingleApp {
	private ImageView icon;
	private LinearLayout layout;
	private TextView name;
	private TextView size;
	private TextView price;
	private TextView dcount;
	private TextView pcount;
	private LinearLayout action;
	private TextView text;
	private ImageView download;

	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}

	public LinearLayout getLayout() {
		return layout;
	}

	public void setLayout(LinearLayout layout) {
		this.layout = layout;
	}

	public TextView getName() {
		return name;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public TextView getSize() {
		return size;
	}

	public void setSize(TextView size) {
		this.size = size;
	}

	public TextView getPrice() {
		return price;
	}

	public void setPrice(TextView price) {
		this.price = price;
	}

	public TextView getDcount() {
		return dcount;
	}

	public void setDcount(TextView dcount) {
		this.dcount = dcount;
	}

	public TextView getPcount() {
		return pcount;
	}

	public void setPcount(TextView pcount) {
		this.pcount = pcount;
	}

	public LinearLayout getAction() {
		return action;
	}

	public void setAction(LinearLayout action) {
		this.action = action;
	}

	public TextView getText() {
		return text;
	}

	public void setText(TextView text) {
		this.text = text;
	}

	public ImageView getDownload() {
		return download;
	}

	public void setDownload(ImageView download) {
		this.download = download;
	}
}
