package com.apps.game.market.view;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityTag;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopWindowTagMore implements OnClickListener {
	private Context mContext;
	private LayoutInflater mInflater;
	private PopupWindow mPop;
	private EntityTag mEntityTag;
	private GlobalObject mGlobalObject = GlobalObject.globalObject;
	private GlobalData mGlobalData = GlobalData.globalData;
	private List<EntityTag> mTags;

	public PopWindowTagMore(Context context, EntityTag entityTag) {
		mContext = context;
		mEntityTag = entityTag;
		mInflater = LayoutInflater.from(context);
		mTags = mGlobalData.getTags();
		setView();
	}

	private void setView() {
		int width = mContext.getResources().getDimensionPixelSize(R.dimen.tag_more_width);			
		LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.tag_more, null, false);
		int size = mTags.size();
		int left = mContext.getResources().getDimensionPixelSize(R.dimen.tag_more_item_margins_left);
		int top = mContext.getResources().getDimensionPixelSize(R.dimen.tag_more_item_margins_top);
		int right = mContext.getResources().getDimensionPixelSize(R.dimen.tag_more_item_margins_right);
		int bottom = mContext.getResources().getDimensionPixelSize(R.dimen.tag_more_item_margins_bottom);		
		StringBuilder s = new StringBuilder();
		
		for (int i = 4;i < size;i++) {
			FrameLayout frameLayout = new FrameLayout(mContext);
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			linearParams.setMargins(left, top, right, bottom);
			frameLayout.setLayoutParams(linearParams);
						
			EntityTag entityTag = mTags.get(i);
			String name = entityTag.getName();
			int length = name.length();
			
			String[] array = new String[length];
			for (int j = 0;j < length;j++)
				array[j] = name.substring(j, j + 1);
			
			s.setLength(0);
			for (int j = 0;j < length;j++) {
				s.append(" ");
				s.append(array[j]);
			}
			
			s.deleteCharAt(0);
			name = s.toString();			
			int color = mContext.getResources().getColor(R.color.tag_more_no_select);
			
			TextView textView = new TextView(mContext);
			FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			frameParams.gravity = Gravity.CENTER;
			textView.setText(name);
			textView.setLayoutParams(frameParams);			
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f);
			
			if (mEntityTag != null && entityTag.equals(mEntityTag)) {
				frameLayout.setBackgroundResource(R.drawable.tag_more_item_corner);
				color = mContext.getResources().getColor(R.color.tag_more_select);
			}
			
			frameLayout.setTag(entityTag);
			frameLayout.setClickable(true);
			frameLayout.setOnClickListener(this);
			textView.setTextColor(color);
			frameLayout.addView(textView);
			layout.addView(frameLayout);
		}
		
		mPop = new PopupWindow(layout, width, LayoutParams.WRAP_CONTENT, true);		
		layout.setOnTouchListener(new OnTouchListener() {
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
	
	public void show(View view) {
		mPop.showAsDropDown(view, 0, 10);
	}

	@Override
	public void onClick(View view) {
		Object object = view.getTag();
		EntityTag entityTag = (EntityTag) object;
		if (mEntityTag != null && entityTag.equals(mEntityTag))
			return;
		
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
		
		mGlobalData.setSelectTag(entityTag);
		Intent intent = new Intent(mContext, ActivityTag.class); 
		mContext.startActivity(intent);
	}
}
