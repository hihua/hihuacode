package com.apps.game.market.view;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityTag;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.global.GlobalData;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
	private final Context mContext;
	private final LayoutInflater mInflater;
	private PopupWindow mPop;
	private final EntityTag mEntityTag;	
	private final GlobalData mGlobalData = GlobalData.globalData;
	private final List<EntityTag> mTags;
	private final LinearLayout mLayout;
	private TextView mTextView;

	public PopWindowTagMore(Context context, LinearLayout layout, EntityTag entityTag) {
		mContext = context;
		mLayout = layout;
		mEntityTag = entityTag;
		mInflater = LayoutInflater.from(context);
		mTags = mGlobalData.getTags();
		setView();
	}

	private void setView() {
		final View view = mLayout.findViewById(R.id.tag_more_text_id);
		if (view != null)
			mTextView = (TextView) view;
				
		final int width = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_width);			
		final LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.popwindow_tag_more, null, false);
		final int size = mTags.size();
		final int left = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_item_padding_left);
		final int top = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_item_padding_top);
		final int right = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_item_padding_right);
		final int bottom = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_item_padding_bottom);		
		final StringBuilder s = new StringBuilder();
		
		for (int i = 4;i < size;i++) {
			final LinearLayout linearLayout = new LinearLayout(mContext);
			final LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);			
			linearLayout.setLayoutParams(linearParams);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
						
			final EntityTag entityTag = mTags.get(i);
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
			int color = mContext.getResources().getColor(R.color.tag_more_text);
			
			final FrameLayout frameLayout = new FrameLayout(mContext);
			frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			frameLayout.setPadding(left, top, right, bottom);
			
			final FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			frameParams.gravity = Gravity.CENTER;
			
			final TextView textView = new TextView(mContext);			
			textView.setLayoutParams(frameParams);		
			textView.setText(name);
			textView.setTextColor(color);
			textView.setSingleLine(true);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f);
			
			if (mEntityTag != null && entityTag.equals(mEntityTag))
				frameLayout.setBackgroundResource(R.drawable.tag_more_item_corner);				
						
			frameLayout.setTag(entityTag);
			frameLayout.setClickable(true);
			frameLayout.setOnClickListener(this);			
			frameLayout.addView(textView);
			
			if (i != 4) {
				color = mContext.getResources().getColor(R.color.tag_more_line);						
				final LinearLayout line = new LinearLayout(mContext);
				line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 1));
				line.setBackgroundColor(color);			
				line.setOrientation(LinearLayout.VERTICAL);
				linearLayout.addView(line);
			}		
			
			linearLayout.addView(frameLayout);			
			layout.addView(linearLayout);
		}
		
		mPop = new PopupWindow(layout, width, LayoutParams.WRAP_CONTENT, true);		
		layout.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mPop != null && mPop.isShowing()) {
					mPop.dismiss();
					mPop = null;
		
					if (mTextView != null) {
						final int color = mContext.getResources().getColor(R.color.tag_text);					
						mTextView.setTextColor(color);
						final Drawable drawable = mContext.getResources().getDrawable(R.drawable.tagmore);					
						mTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
					}					
				}
				
				return false;
			}
		});
	}
	
	public void show() {
		mPop.showAsDropDown(mLayout, 0, 10);
		
		if (mTextView != null) {
			final int color = mContext.getResources().getColor(R.color.tag_text_select);					
			mTextView.setTextColor(color);		
			final Drawable drawable = mContext.getResources().getDrawable(R.drawable.tagmore_select);					
			mTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
		}		
	}

	@Override
	public void onClick(View view) {
		final Object object = view.getTag();
		final EntityTag entityTag = (EntityTag) object;
		if (mEntityTag != null && entityTag.equals(mEntityTag))
			return;
		
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
			
			if (mTextView != null) {
				final int color = mContext.getResources().getColor(R.color.tag_text);
				mTextView.setTextColor(color);
				final Drawable drawable = mContext.getResources().getDrawable(R.drawable.tagmore);
				mTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
			}
		}
		
		mGlobalData.setSelectTag(entityTag);
		final Intent intent = new Intent(mContext, ActivityTag.class); 
		mContext.startActivity(intent);
	}
}
