package com.apps.game.market.view;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.util.ApkManager;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopWindowMyAppRun implements OnClickListener {	
	private final Context mContext;
	private final LayoutInflater mInflater;
	private PopupWindow mPop;
	private final EntityAppInfo mEntityAppInfo;
	private LinearLayout mRoot;

	public PopWindowMyAppRun(Context context, EntityAppInfo entityAppInfo) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mEntityAppInfo = entityAppInfo;
		setView();
	}
	
	private void setView() {
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_myapp_run, null, false);
		final TextView textView = (TextView) mRoot.findViewById(R.id.popwindow_myapp_run_name);
		final FrameLayout confirm = (FrameLayout) mRoot.findViewById(R.id.popwindow_myapp_run_confirm);
		final FrameLayout cancel = (FrameLayout) mRoot.findViewById(R.id.popwindow_myapp_run_cancel);
		
		final String name = mEntityAppInfo.getAppName() + mContext.getString(R.string.popwindow_myapp_run_ma);
		textView.setText(name);
		
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
	public void onClick(View view) {		
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
				
		final int id = view.getId();
		switch (id) {
			case R.id.popwindow_myapp_run_confirm: {
				final String packageName = mEntityAppInfo.getPackageName();
				if (packageName != null)
					ApkManager.runApp(mContext, packageName);
			}
			break;		
		}		
	}
}
