package com.apps.game.market.view;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityColumnClass;
import com.apps.game.market.views.ViewColumn;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class PopWindowColumnSelect implements OnClickListener {
	private final Context mContext;
	private final LayoutInflater mInflater;
	private PopupWindow mPop;	
	private LinearLayout mRoot;	
	private final ViewColumn mViewColumn;
	private final List<EntityColumnClass> mList;
	private final int mSelect;
	private final View mParent;
	
	public PopWindowColumnSelect(Context context, ViewColumn viewColumn, List<EntityColumnClass> list, int select, View parent) {
		mContext = context;
		mInflater = LayoutInflater.from(context);		
		mViewColumn = viewColumn;
		mList = list;
		mSelect = select;
		mParent = parent;
		setView();
	}
	
	private void setView() {
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_column_select, null);
		mRoot.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {				
				if (mPop != null && mPop.isShowing()) {
					mPop.dismiss();
					mPop = null;
				}
				
				return false;
			}
		});
	}
	
	public void setList() {
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
		
		int index = 0;		
		for (EntityColumnClass entityColumnClass : mList) {
			final String name = entityColumnClass.getName();
			
			final LinearLayout layout = new LinearLayout(mContext);			
			layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			layout.setOrientation(LinearLayout.VERTICAL);			
			int color = mContext.getResources().getColor(R.color.column_select_background);
			if (index == mSelect)
				color = mContext.getResources().getColor(R.color.column_selected_background);
			else {
				layout.setClickable(true);
				layout.setOnClickListener(this);
				layout.setTag(index);
			}
			
			layout.setBackgroundColor(color);
			layout.setGravity(Gravity.CENTER_VERTICAL);
			
			final int left = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_column_select_margin_left);
			final int top = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_column_select_margin_top);
			final int right = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_column_select_margin_right);
			final int bottom = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_column_select_margin_bottom);
			
			final LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			linearLayout.setMargins(left, top, right, bottom);
			
			final TextView textView = new TextView(mContext);
			textView.setLayoutParams(linearLayout);
			textView.setText(name);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f);
			color = mContext.getResources().getColor(R.color.column_ad_text);
			textView.setTextColor(color);
			layout.addView(textView);
			mRoot.addView(layout);			
			index++;
		}
		
		final int width = mParent.getWidth();			
		mPop = new PopupWindow(mRoot, width, LayoutParams.WRAP_CONTENT, true);
		mPop.setOutsideTouchable(false);
		mPop.showAsDropDown(mParent, 0, 0);
	}

	@Override
	public void onClick(View v) {
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
		
		final Object obj = v.getTag();
		if (obj != null && obj instanceof Integer) {
			final Integer select = (Integer) obj;
			mViewColumn.setSubColumn(mParent, select, mList);						
		}
	}
}
