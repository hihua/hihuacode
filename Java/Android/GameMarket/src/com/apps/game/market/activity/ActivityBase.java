package com.apps.game.market.activity;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.App;
import com.apps.game.market.R;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.EntitySearchWord;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityTag;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestSearchWord;
import com.apps.game.market.request.callback.RequestCallBackInfo;
import com.apps.game.market.request.callback.RequestCallBackSearchWord;
import com.apps.game.market.service.ServiceManager;
import com.apps.game.market.task.TaskCaches;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.view.PopWindowMyAppRun;
import com.apps.game.market.view.PopWindowSearchWord;
import com.apps.game.market.view.PopWindowTagMore;
import com.apps.game.market.view.callback.CallBackCacheFinish;
import com.apps.game.market.views.ViewColumn;
import com.apps.game.market.views.ViewColumnDouble;
import com.apps.game.market.views.ViewColumnMyAppBrowse;
import com.apps.game.market.views.ViewColumnMyAppCollect;
import com.apps.game.market.views.ViewColumnMyAppDownloaded;
import com.apps.game.market.views.ViewColumnMyAppInstalled;
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

public abstract class ActivityBase extends Activity implements OnClickListener, RequestCallBackSearchWord, RequestCallBackInfo, CallBackCacheFinish {
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
	protected boolean mFinish = false;
			
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
	protected void onPause() {
		ServiceManager.startUpgrade(this, 3600000L, 7200000L);
		super.onPause();
	}

	@Override
	protected void onStop() {		
		super.onStop();
		onAppClose();		
	}

	@Override
	protected void onResume() {
		ServiceManager.stopUpgrade(this);						
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
					LinearLayout linearLayout = new LinearLayout(this);
					linearLayout.setTag(entityTag);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1.0f));					
					linearLayout.setOnClickListener(this);
					linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
					final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams.bottomMargin = 7;
					final TextView textView = new TextView(this);
					textView.setTag(entityTag);
					textView.setLayoutParams(layoutParams);						
					textView.setSingleLine(true);
					textView.setText(name);					
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f);					
					boolean selected = false;
					
					int resId = 0;			
					switch (index) {
						case 0: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag1_select;
								selected = true;
							} else
								resId = R.drawable.tag1;
						}
						break;
							
						case 1: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag2_select;
								selected = true;
							} else
								resId = R.drawable.tag2;
						}
						break;
							
						case 2: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag3_select;
								selected = true;
							} else
								resId = R.drawable.tag3;
						}
						break;
							
						case 3: {
							if (mEntityTag != null && entityTag.equals(mEntityTag)) {
								resId = R.drawable.tag4_select;
								selected = true;
							} else
								resId = R.drawable.tag4;
						}
						break;
					}
					
					if (selected) {
						textView.setTextColor(getResources().getColor(R.color.tag_text_select));
						linearLayout.setBackgroundResource(R.drawable.tag_select);
						linearLayout.setClickable(false);
					} else {
						textView.setTextColor(getResources().getColor(R.color.tag_text));
						linearLayout.setBackgroundResource(R.drawable.tag);
						linearLayout.setClickable(true);
					}
					
					final Drawable drawable = getResources().getDrawable(resId);					
					textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
					textView.setCompoundDrawablePadding(2);
					linearLayout.addView(textView);					
					mLinearBottom.addView(linearLayout);
					
					linearLayout = new LinearLayout(this);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					linearLayout.setLayoutParams(new LinearLayout.LayoutParams(1, LayoutParams.FILL_PARENT));
					linearLayout.setBackgroundResource(R.color.tag_line1);
					mLinearBottom.addView(linearLayout);
					
					linearLayout = new LinearLayout(this);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					linearLayout.setLayoutParams(new LinearLayout.LayoutParams(1, LayoutParams.FILL_PARENT));
					linearLayout.setBackgroundResource(R.color.tag_line2);
					mLinearBottom.addView(linearLayout);					
					index++;
					
					if (index == 4)
						break;
				}
				
				final LinearLayout linearLayout = new LinearLayout(this);
				linearLayout.setBackgroundResource(R.drawable.tag);
				linearLayout.setId(R.id.tag_more_id);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1.0f));
				linearLayout.setClickable(true);
				linearLayout.setOnClickListener(this);
				linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
				final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams.bottomMargin = 7;				
				final TextView textView = new TextView(this);				
				textView.setLayoutParams(layoutParams);
				textView.setId(R.id.tag_more_text_id);
				textView.setText(R.string.tag_more);
				textView.setSingleLine(true);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f);
				textView.setTextColor(getResources().getColor(R.color.tag_text));
				final Drawable drawable = getResources().getDrawable(R.drawable.tagmore);				
				textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
				textView.setCompoundDrawablePadding(8);
				linearLayout.addView(textView);					
				mLinearBottom.addView(linearLayout);
			}
		}		
	}
	
	protected int layoutColumns(final ViewGroup layoutColumn, final ViewGroup layoutMyAppColumn, final ViewPager parent) {			
		int color = getResources().getColor(R.color.column_button);	
		mColumns = mGlobalData.getColumns();
		int index = 0;
		if (mColumns != null) {			
			for (EntityColumn entityColumn : mColumns) {			
				final String name = entityColumn.getName();
				boolean single = entityColumn.getSingle();
				final ViewGroup viewGroup = addColumn(0, name, layoutColumn, index++, color);
				final LinearLayout linearLayout = new LinearLayout(this);
				linearLayout.setLayoutParams(new LinearLayout.LayoutParams(1, LayoutParams.FILL_PARENT));
				linearLayout.setBackgroundResource(R.color.column_line);
				layoutColumn.addView(linearLayout);
								
				if (parent != null) {
					ViewColumn viewColumn = null;					
					if (single)
						viewColumn = new ViewColumnSingle(this, parent, viewGroup, entityColumn, this, this);
					else
						viewColumn = new ViewColumnDouble(this, parent, viewGroup, entityColumn, this, this);
					
					mViewColumns.add(viewColumn);
				}				
			}
		}
		
		final int total = index;
		
		String name = getString(R.string.column_myapp);
		ViewGroup viewGroup = addColumn(R.id.column_myapp_id, name, layoutColumn, index, color);
		
		if (layoutMyAppColumn == null)
			return total;
				
		name = getString(R.string.app_installed);
		viewGroup = addColumn(R.id.column_myapp_installed_id, name, layoutMyAppColumn, index++, color);
		if (parent != null) {			
			final ViewColumn viewColumn = new ViewColumnMyAppInstalled(this, parent, viewGroup, null, this, mGlobalData.getLocalApps()); 
			mViewColumns.add(viewColumn);
		}
		
		name = getString(R.string.app_downloaded);
		viewGroup = addColumn(R.id.column_myapp_downloaded_id, name, layoutMyAppColumn, index++, color);
		if (parent != null) {
			final ViewColumn viewColumn = new ViewColumnMyAppDownloaded(this, parent, viewGroup, null, this, mGlobalData.getDownloadApps()); 
			mViewColumns.add(viewColumn);
		}
		
		name = getString(R.string.app_collect);
		viewGroup = addColumn(R.id.column_myapp_collect_id, name, layoutMyAppColumn, index++, color);
		if (parent != null) {
			final ViewColumn viewColumn = new ViewColumnMyAppCollect(this, parent, viewGroup, null, this, mGlobalData.getCollectApps()); 
			mViewColumns.add(viewColumn);
		}
		
		name = getString(R.string.app_browse);
		viewGroup = addColumn(R.id.column_myapp_browse_id, name, layoutMyAppColumn, index++, color);
		if (parent != null) {
			final ViewColumn viewColumn = new ViewColumnMyAppBrowse(this, parent, viewGroup, null, this, mGlobalData.getBrowseApps()); 
			mViewColumns.add(viewColumn);
		}
		
		return total;
	}
	
	private ViewGroup addColumn(final int id, final String name, final ViewGroup layoutColumn, final int index, final int color) {
		final FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setTag(index);		
		if (id != 0)
			frameLayout.setId(id);
		
		frameLayout.setClickable(true);
		frameLayout.setOnClickListener(this);
		final LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		frameLayout.setLayoutParams(linearLayoutParams);
		final TextView textView = new TextView(this);		
		final FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		frameLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		textView.setLayoutParams(frameLayoutParams);		
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		textView.setText(name);
		textView.setTextColor(color);
		textView.setSingleLine(true);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
		frameLayout.addView(textView);
		layoutColumn.addView(frameLayout);
		return frameLayout;
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
						final RequestSearchWord request = new RequestSearchWord(mCallBack);
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
				
		switch (id) {
			case R.id.header_imageview_search: {
				Editable editable = mSearch.getText();
				if (editable.length() > 0) {
					String keyword = editable.toString();
					search(keyword);
				}
			}
			break;
		
			case R.id.tag_more_id: {
				if (mTags != null && mTags.size() > 4) {
					PopWindowTagMore pop = new PopWindowTagMore(this, (LinearLayout) v, mEntityTag, this);
					pop.show();
				}
			}
			break;
		
			case R.id.single_app_icon:
			case R.id.single_app_layout:
			case R.id.app_layout1:
			case R.id.app_layout2: {
				if (obj != null && obj instanceof EntityApp) {						
					final EntityApp entityApp = (EntityApp) obj;
					entryDetail(entityApp);
				}
			}
			break;
			
			case R.id.single_app_action:
			case R.id.single_app_download: 
			case R.id.app_action1: 
			case R.id.app_action2: 
			case R.id.app_download1: 
			case R.id.app_download2:
			case R.id.detail_app_action:
			case R.id.detail_app_download:
			case R.id.myapp_downloaded_action1:
			case R.id.myapp_downloaded_action2:
			case R.id.myapp_collect_action1: 
			case R.id.myapp_collect_action2:
			case R.id.myapp_collect_download1:
			case R.id.myapp_collect_download2:
			case R.id.myapp_browse_action1:
			case R.id.myapp_browse_action2: 
			case R.id.myapp_browse_download1:
			case R.id.myapp_browse_download2:{
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
				}
			}
			break;
			
			case R.id.myapp_installed_action1:
			case R.id.myapp_installed_action2: {
				if (obj != null && obj instanceof EntityAppInfo) {
					final EntityAppInfo entityAppInfo = (EntityAppInfo) obj;
					final PopWindowMyAppRun pop = new PopWindowMyAppRun(this, entityAppInfo);
					pop.show();	
				}				
			}
			break;
			
			case R.id.popwindow_quit_confirm: {
				close();
				super.onBackPressed();				
			}
			break;
		}	
				
		onAppClick(v);
	}
	
	@Override
	public void onCallBackInfo(EntityApp entityApp) {
		if (entityApp != null)
			entryDetail(entityApp);
	}

	public void close() {		
		mGlobalObject.close();
		mGlobalData.close();
		if (mViewColumns != null) {
			for (ViewColumn viewColumn : mViewColumns)
				viewColumn.stop();
		}
		
		final FileManager fileManager = mGlobalObject.getFileManager();
		final String cachePath = fileManager.getCachePath();		
		final TaskCaches taskCaches = new TaskCaches(this);
		taskCaches.execute(cachePath);
	}
			
	@Override
	public void onCallBackSearchWord(final List<EntitySearchWord> searchWords) {
		if (mSearchWord == null)
			mSearchWord = new PopWindowSearchWord(this, mSearch);
		
		mSearchWord.setList(searchWords);
	}
	
	@Override
	public void onCacheFinish() {
		finish();
	}
	
	public void setFinish(boolean finish) {
		mFinish = finish;
	}
	
	protected void entryTag(final EntityTag entityTag) {
		setFinish(true);
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
