package com.apps.game.market.activity;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.App;
import com.apps.game.market.R;
import com.apps.game.market.entity.EntitySearchWord;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestSearchWord;
import com.apps.game.market.request.callback.RequestCallBackSearchWord;
import com.apps.game.market.task.TaskDownload;
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
import android.text.Selection;
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
	protected GlobalObject mGlobalObject;
	protected GlobalData mGlobalData;
	protected List<EntityTag> mTags;
	protected List<EntityColumn> mColumns;	
	protected List<ViewColumn> mViewColumns = new Vector<ViewColumn>();
	protected EntityTag mEntityTag;
	protected LinearLayout mLinearBottom;
	private PopWindowSearchWord mSearchWord;
	private EditText mSearch;
	protected String mKeyword = "";
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		App app = (App) getApplication();
		mGlobalObject = app.globalObject;
		mGlobalData = app.globalData;		
		onAppCreate();
		layoutTags();
		layoutSearch();
		onAppEntry();
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
					final String name = entityTag.getName();
					final TextView textView = new TextView(this);
					textView.setTag(entityTag);
					textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));										
					textView.setOnClickListener(this);
					textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
					textView.setSingleLine(true);
					textView.setText(name);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
					boolean selected = false;
					
					int resId = 0;			
					switch (index) {
						case 0: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag1_selected;
								selected = true;
							} else
								resId = R.drawable.tag1;
						}
						break;
							
						case 1: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag2_selected;
								selected = true;
							} else
								resId = R.drawable.tag2;
						}
						break;
							
						case 2: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag3_selected;
								selected = true;
							} else
								resId = R.drawable.tag3;
						}
						break;
							
						case 3: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag4_selected;
								selected = true;
							} else
								resId = R.drawable.tag4;
						}
						break;
					}
					
					if (selected) {
						textView.setTextColor(getResources().getColor(R.color.tag_select));
						textView.setClickable(false);
					} else {
						textView.setTextColor(getResources().getColor(R.color.tag_no_select));
						textView.setClickable(true);
					}
					
					Drawable drawable = getResources().getDrawable(resId);					
					textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
					textView.setCompoundDrawablePadding(4);
					mLinearBottom.addView(textView);
					index++;
					
					if (index == 4)
						break;
				}
				
				final TextView textView = new TextView(this);	
				textView.setId(R.id.tag_more_id);
				textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
				textView.setClickable(true);
				textView.setOnClickListener(this);
				textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
				textView.setText(R.string.tag_more);
				textView.setSingleLine(true);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
				Drawable drawable = getResources().getDrawable(R.drawable.tag_more);				
				textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
				textView.setCompoundDrawablePadding(4);
				mLinearBottom.addView(textView);
			}
		}		
	}
	
	protected void layoutColumns(final ViewGroup layoutColumn, final ViewPager parent) {			
		int color = getResources().getColor(R.color.column_button);	
		mColumns = mGlobalData.getColumns();
		int index = 0;
		if (mColumns != null) {			
			for (EntityColumn entityColumn : mColumns) {			
				final String name = entityColumn.getName();
				boolean single = entityColumn.getSingle();
				final FrameLayout frameLayout = new FrameLayout(this);
				frameLayout.setTag(index);
				frameLayout.setClickable(true);
				frameLayout.setOnClickListener(this);
				final LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
				int marginLeft = getResources().getDimensionPixelSize(R.dimen.column_margins_left);
				int marginTop = getResources().getDimensionPixelSize(R.dimen.column_margins_top);
				int marginRight = getResources().getDimensionPixelSize(R.dimen.column_margins_right);
				int marginBottom = getResources().getDimensionPixelSize(R.dimen.column_margins_bottom);
				linearLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
				frameLayout.setLayoutParams(linearLayoutParams);				
				final TextView textView = new TextView(this);
				final FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
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
						viewColumn = new ViewColumnSingle(this, parent, frameLayout, entityColumn, this);
					else
						viewColumn = new ViewColumnDouble(this, parent, frameLayout, entityColumn, this);
					
					mViewColumns.add(viewColumn);
				}
				
				index++;
			}
		}
		
		final FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setTag(index);
		frameLayout.setId(R.id.column_myapp_id);
		frameLayout.setClickable(true);
		frameLayout.setOnClickListener(this);
		final LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		int marginLeft = getResources().getDimensionPixelSize(R.dimen.column_margins_left);
		int marginTop = getResources().getDimensionPixelSize(R.dimen.column_margins_top);
		int marginRight = getResources().getDimensionPixelSize(R.dimen.column_margins_right);
		int marginBottom = getResources().getDimensionPixelSize(R.dimen.column_margins_bottom);
		linearLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		frameLayout.setLayoutParams(linearLayoutParams);
		final TextView textView = new TextView(this);		
		final FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
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
			final ViewColumn viewColumn = new ViewColumnMyApp(this, parent, frameLayout, null, this); 
			mViewColumns.add(viewColumn);
		}
	}
	
	private void layoutSearch() {
		View view = findViewById(R.id.header_imageview_search);
		if (view != null)
			view.setOnClickListener(this);
				
		view = findViewById(R.id.header_edittext_search);
		if (view != null) {			
			final EditText search = (EditText) view;
			if (mKeyword.length() > 0)
				search.setText(mKeyword);
				
			final RequestCallBackSearchWord mCallBack = this;
			mSearch = search;
			search.addTextChangedListener(new TextWatcher() {				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
					
				}
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					final String keyword = s.toString();					
					if (count > 0 && !keyword.equals(mKeyword)) {
						RequestSearchWord request = new RequestSearchWord(mCallBack);
						request.request(s.toString());
						mKeyword = keyword;
					}										
				}						
				
				@Override
				public void afterTextChanged(Editable s) {
					Selection.setSelection(s, s.length());
				}
			});
		}
	}
		
	@Override
	public void onClick(View v) {
		final int id = v.getId();
		final Object obj = v.getTag();
		if (mTags != null && obj != null) {
			if (obj instanceof EntityTag) {								
				for (EntityTag entityTag : mTags) {					
					if (obj.equals(entityTag)) {						
						mGlobalData.setSelectTag(entityTag);
						entryTag(entityTag);						
						return;
					}
				}
			}			
		}
		
		if (id == R.id.tag_more_id) {
			if (mTags != null && mTags.size() > 4) {
				PopWindowTagMore pop = new PopWindowTagMore(this, (TextView) v, mEntityTag);
				pop.show();
			}
			
			return;
		}
		
		if (id == R.id.header_imageview_search) {
			Editable editable = mSearch.getText();
			if (editable.length() > 0) {
				String keyword = editable.toString();
				search(keyword);
			}				
		}
		
		if (id == R.id.single_app_icon || id == R.id.single_app_layout) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.app_name1 || id == R.id.app_name2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.app_price1 || id == R.id.app_price2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.app_icon1 || id == R.id.app_icon2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.app_class1 || id == R.id.app_class2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.myapp_download_name1 || id == R.id.myapp_download_name2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.myapp_download_icon1 || id == R.id.myapp_download_icon2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.myapp_collect_name1 || id == R.id.myapp_collect_name2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
		
		if (id == R.id.myapp_collect_icon1 || id == R.id.myapp_collect_icon2) {
			if (obj != null && obj instanceof EntityApp) {							
				final EntityApp entityApp = (EntityApp) obj;
				entryDetail(entityApp);
				return;
			}
		}
				
		if (id == R.id.single_app_download || id == R.id.app_action1 || id == R.id.app_action2 || id == R.id.myapp_collect_action1 || id == R.id.myapp_collect_action2) {
			if (obj != null && obj instanceof EntityApp) {
				final EntityApp entityApp = (EntityApp) obj;
				final TaskDownload taskDownload = mGlobalObject.getTaskDownload();
				final EnumAppStatus status = entityApp.getStatus();
				switch (status) {
					case NOINSTALL:
						taskDownload.downloadApp(this, entityApp);
						break;
						
					case INSTALL:
						if (!taskDownload.installApp(this, entityApp))
							onAppStatus(entityApp);
						
						break;
						
					case INSTALLED:
						taskDownload.runApp(this, entityApp);					
						break;
				
					case WAITING:					
						taskDownload.downloadCancel(this, entityApp);
						break;
						
					case DOWNLOADING:
						taskDownload.downloadCancel(this, entityApp);		
						break;
				}
				return;
			}
		}
				
		onAppClick(v);
	}
	
	@Override
	public void onCallBackSearchWord(final List<EntitySearchWord> searchWords) {
		if (mSearchWord == null)
			mSearchWord = new PopWindowSearchWord(this, mSearch);
		
		mSearchWord.setList(searchWords);
	}

	protected void entryTag(final EntityTag entityTag) {
		final Intent intent = new Intent(this, ActivityTag.class); 
        startActivity(intent);
	}
	
	protected void search(final String keyword) {
		mSearch.setText("");
		mKeyword = "";
		final Intent intent = new Intent();
		intent.putExtra("keyword", keyword);
		intent.setClass(this, ActivitySearch.class);
		startActivity(intent);
	}
	
	protected void setSearch(final String keyword) {
		if (mSearch != null)
			mSearch.setText(keyword);
	}
	
	private void entryDetail(final EntityApp entityApp) {		
		mGlobalData.setSelectApp(entityApp);
		final Intent intent = new Intent(this, ActivityDetail.class); 
		startActivity(intent);
	}
		
	protected abstract void onAppCreate();
	protected abstract void onAppEntry();
	protected abstract void onAppClose();
	protected abstract void onAppResume();
	protected abstract void onAppClick(View v);
	public abstract void onAppStatus(EntityApp entityApp);
}
