package com.apps.game.market.view;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityBase;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopWindowQuit implements OnClickListener {
	private final Context mContext;
	private final LayoutInflater mInflater;
	private PopupWindow mPop;
	private LinearLayout mRoot;
	private final ActivityBase mActivity;
	
	public PopWindowQuit(Context context, ActivityBase activity) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mActivity = activity;
		setView();
	}
	
	private void setView() {
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_quit, null, false);		
		final FrameLayout confirm = (FrameLayout) mRoot.findViewById(R.id.popwindow_quit_confirm);
		final FrameLayout cancel = (FrameLayout) mRoot.findViewById(R.id.popwindow_quit_cancel);
		
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		mPop = new PopupWindow(mRoot, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);		
		mPop.setOutsideTouchable(false);			
		mPop.update();
	}
	
	public void show() {
		mPop.showAtLocation(mRoot, Gravity.CENTER, 0, 0);
	}

	@Override
	public void onClick(View v) {
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
		
		mActivity.onClick(v);		
	}
}
