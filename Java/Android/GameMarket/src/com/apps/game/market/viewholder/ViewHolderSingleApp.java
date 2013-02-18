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
	private ImageView download;

	public ImageView getIcon() {
		return icon;
	}

	public LinearLayout getLayout() {
		return layout;
	}

	public TextView getName() {
		return name;
	}

	public TextView getSize() {
		return size;
	}

	public TextView getPrice() {
		return price;
	}

	public TextView getDcount() {
		return dcount;
	}

	public TextView getPcount() {
		return pcount;
	}

	public ImageView getDownload() {
		return download;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}

	public void setLayout(LinearLayout layout) {
		this.layout = layout;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public void setSize(TextView size) {
		this.size = size;
	}

	public void setPrice(TextView price) {
		this.price = price;
	}

	public void setDcount(TextView dcount) {
		this.dcount = dcount;
	}

	public void setPcount(TextView pcount) {
		this.pcount = pcount;
	}

	public void setDownload(ImageView download) {
		this.download = download;
	}
}
