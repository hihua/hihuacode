package com.apps.game.market.activity;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntitySearchWord;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestSearchWord;
import com.apps.game.market.request.callback.RequestCallBackSearchWord;
import com.apps.game.market.view.PopWindowSearchWord;
import com.apps.game.market.view.PopWindowTagMore;
import com.apps.game.market.views.ViewColumn;
import com.apps.game.market.views.ViewColumnDouble;
import com.apps.game.market.views.ViewColumnMyApp;
import com.apps.game.market.views.ViewColumnSingle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;

public abstract class ActivityBase extends Activity implements OnClickListener, RequestCallBackSearchWord {
	protected GlobalObject mGlobalObject = GlobalObject.globalObject;
	protected GlobalData mGlobalData = GlobalData.globalData;
	protected List<EntityTag> mTags;
	protected List<EntityColumn> mColumns;	
	protected List<ViewColumn> mViewColumns = new Vector<ViewColumn>();
	protected EntityTag mEntityTag;
	protected LinearLayout mLinearBottom;
	private PopWindowSearchWord mSearchWord;
	private EditText mSearch;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
		onAppCreate();
		layoutTags();
		layoutSearch();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		onAppClose();		
	}

	@Override
	protected void onResume() {		
		super.onResume();
		mGlobalObject.setActivity(this);
		onAppResume();
	}
	
	protected void layoutTags() {
		View v = findViewById(R.id.bottom_layout);
		if (v != null) {
			mLinearBottom = (LinearLayout) v;		
			mTags = mGlobalData.getTags();
			if (mTags != null) {
				int index = 0;
				for (EntityTag entityTag : mTags) {
					String name = entityTag.getName();
					TextView textView = new TextView(this);
					textView.setTag(entityTag);
					textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
					textView.setClickable(true);					
					textView.setOnClickListener(this);
					textView.setGravity(Gravity.CENTER);
					textView.setSingleLine(true);
					textView.setText(name);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f);
					
					int resId = 0;			
					switch (index) {
						case 0:
							resId = R.drawable.tag1;
							break;
							
						case 1:
							resId = R.drawable.tag2;
							break;
							
						case 2:
							resId = R.drawable.tag3;
							break;
							
						case 3:
							resId = R.drawable.tag4;
							break;
					}
					
					Drawable drawable = getResources().getDrawable(resId);					
					textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);										
					mLinearBottom.addView(textView);
					index++;
					
					if (index == 4)
						break;
				}
				
				TextView textView = new TextView(this);	
				textView.setId(R.id.tag_more_id);
				textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
				textView.setClickable(true);
				textView.setOnClickListener(this);
				textView.setGravity(Gravity.CENTER);
				textView.setText(R.string.tag_more);
				textView.setSingleLine(true);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f);
				Drawable drawable = getResources().getDrawable(R.drawable.tag_more);				
				textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
				mLinearBottom.addView(textView);
			}
		}		
	}
	
	protected void layoutColumns(ViewGroup layoutColumn , ViewPager parent) {			
		int color = getResources().getColor(R.color.column_button);	
		mColumns = mGlobalData.getColumns();
		int index = 0;
		if (mColumns != null) {			
			for (EntityColumn entityColumn : mColumns) {			
				String name = entityColumn.getName();
				boolean single = entityColumn.getSingle();
				FrameLayout frameLayout = new FrameLayout(this);
				frameLayout.setTag(index);
				frameLayout.setClickable(true);
				frameLayout.setOnClickListener(this);
				LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
				int marginLeft = getResources().getDimensionPixelSize(R.dimen.column_margins_left);
				int marginTop = getResources().getDimensionPixelSize(R.dimen.column_margins_top);
				int marginRight = getResources().getDimensionPixelSize(R.dimen.column_margins_right);
				int marginBottom = getResources().getDimensionPixelSize(R.dimen.column_margins_bottom);
				linearLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
				frameLayout.setLayoutParams(linearLayoutParams);				
				TextView textView = new TextView(this);
				FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
				frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;				
				textView.setLayoutParams(frameLayoutParams);				
				textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
				textView.setText(name);
				textView.setTextColor(color);
				textView.setSingleLine(true);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f);				
				frameLayout.addView(textView);
				layoutColumn.addView(frameLayout);				
				if (parent != null) {
					ViewColumn viewColumn = null;					
					if (single)
						viewColumn = new ViewColumnSingle(this, parent, frameLayout, entityColumn);
					else
						viewColumn = new ViewColumnDouble(this, parent, frameLayout, entityColumn);
					
					mViewColumns.add(viewColumn);
				}
				
				index++;
			}
		}
		
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setTag(index);
		frameLayout.setId(R.id.column_myapp_id);
		frameLayout.setClickable(true);
		frameLayout.setOnClickListener(this);
		LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		int marginLeft = getResources().getDimensionPixelSize(R.dimen.column_margins_left);
		int marginTop = getResources().getDimensionPixelSize(R.dimen.column_margins_top);
		int marginRight = getResources().getDimensionPixelSize(R.dimen.column_margins_right);
		int marginBottom = getResources().getDimensionPixelSize(R.dimen.column_margins_bottom);
		linearLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		frameLayout.setLayoutParams(linearLayoutParams);
		TextView textView = new TextView(this);		
		FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		textView.setLayoutParams(frameLayoutParams);		
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		textView.setText(R.string.column_myapp);
		textView.setTextColor(color);
		textView.setSingleLine(true);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f);
		frameLayout.addView(textView);
		layoutColumn.addView(frameLayout);
		if (parent != null) {
			ViewColumn viewColumn = new ViewColumnMyApp(this, parent, frameLayout, null); 
			mViewColumns.add(viewColumn);
		}
	}
	
	private void layoutSearch() {
		View view = findViewById(R.id.header_edittext_search);
		if (view != null) {			
			final EditText search = (EditText) view;			
			final RequestCallBackSearchWord mCallBack = this;
			mSearch = search;
			search.addTextChangedListener(new TextWatcher() {				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
					
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (count > 0) {
						RequestSearchWord request = new RequestSearchWord(mCallBack);
						request.request(s.toString());
					}										
				}						
				
				@Override
				public void afterTextChanged(Editable s) {
					
				}
			});
		}
	}
		
	@Override
	public void onClick(View v) {
		Object object = v.getTag();
		if (mTags != null && object != null) {
			if (object instanceof EntityTag) {
				EntityTag tag = mGlobalData.getSelectTag();
				if (tag != null && tag.equals(object))
					return;
				
				for (EntityTag entityTag : mTags) {					
					if (object.equals(entityTag)) {						
						mGlobalData.setSelectTag(entityTag);
						entryTag(entityTag);						
						return;
					}
				}
			}			
		}
		
		if (v.getId() == R.id.tag_more_id) {
			if (mTags != null && mTags.size() > 4) {
				PopWindowTagMore pop = new PopWindowTagMore(this, mEntityTag);
				pop.show(v);
			}
			
			return;
		}
				
		onAppClick(v);
	}
	
	@Override
	public void onCallBackSearchWord(List<EntitySearchWord> searchWords) {
		if (mSearchWord == null)
			mSearchWord = new PopWindowSearchWord(this, mSearch);
		
		mSearchWord.setList(searchWords);
	}

	protected void entryTag(EntityTag entityTag) {
		Intent intent = new Intent(this, ActivityTag.class); 
        startActivity(intent);
	}
		
	protected abstract void onAppCreate();
	protected abstract void onAppClose();
	protected abstract void onAppResume();
	protected abstract void onAppClick(View v);
	public abstract void onAppStatus(EntityApp entityApp);
}
