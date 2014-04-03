package com.location.hls.viewholder;

import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewHolderSelectItem {
	private RelativeLayout relativeLayout;
	private TextView textView;
	private RadioButton radioButton;

	public RelativeLayout getRelativeLayout() {
		return relativeLayout;
	}

	public void setRelativeLayout(final RelativeLayout relativeLayout) {
		this.relativeLayout = relativeLayout;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(final TextView textView) {
		this.textView = textView;
	}

	public RadioButton getRadioButton() {
		return radioButton;
	}

	public void setRadioButton(final RadioButton radioButton) {
		this.radioButton = radioButton;
	}
}
