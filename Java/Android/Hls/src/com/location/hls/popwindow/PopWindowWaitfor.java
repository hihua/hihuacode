package com.location.hls.popwindow;

import com.location.hls.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

public class PopWindowWaitfor implements Runnable {
	private final LayoutInflater mInflater;
	private LinearLayout mRoot;
	private ProgressBar mProgressBar;
	private final Handler mHandler;
	private PopupWindow mPop;
	private int mProgress = 0;
	
	public PopWindowWaitfor(Context context) {		
		mInflater = LayoutInflater.from(context);
		mHandler = new Handler();
		setView();		
	}
	
	private void setView() {
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_waitfor, null, false);
		mProgressBar = (ProgressBar) mRoot.findViewById(R.id.waitfor_progressbar);
		
		mPop = new PopupWindow(mRoot, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);		
		mPop.setBackgroundDrawable(new ColorDrawable(0x00000000));		
		mPop.update();		
	}
			
	public void start() {
		mProgress = 0;		
		mHandler.postDelayed(this, 200);
		mPop.showAtLocation(mRoot, Gravity.CENTER, 0, 0);
	}
	
	public void close() {		
		mHandler.removeCallbacks(this);
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
	}

	@Override
	public void run() {
		mProgress = (mProgress + 20) % 100;
		mProgressBar.setProgress(mProgress);
	}
}
