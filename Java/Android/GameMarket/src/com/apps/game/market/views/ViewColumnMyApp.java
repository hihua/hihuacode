package com.apps.game.market.views;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityDetail;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderDoubleApp;
import com.apps.game.market.viewholder.ViewHolderMyApp;

public class ViewColumnMyApp extends ViewColumn implements OnClickListener {
	private LinearLayout mLayoutRoot;
	private LinearLayout mLayoutBanner;
	private FrameLayout mLayoutInstalled;
	private FrameLayout mLayoutDownload;
	private FrameLayout mLayoutCollect;
	private FrameLayout mLayoutBrowse;	
	private ListView mListView;
	private MyAppListAdapter mAdapter;
	private DoubleMyAppListAdapter mAdapterDouble;
	private FrameLayout mCurrent;

	public ViewColumnMyApp(Context context, ViewGroup parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
		super(context, parent, layoutColumn, entityColumn);
	}

	@Override
	protected void onInit() {
		if (!mInit)	{
			List<EntityAppInfo> list = mGlobalData.getLocalApps();
			mAdapter = new MyAppListAdapter(mContext, mListView, list);	
			mListView.setAdapter(mAdapter);
		}
	}

	@Override
	protected void onRefresh() {
		if (mAdapterDouble != null)
			mAdapterDouble.refresh();
		else {
			if (mAdapter != null)
				mAdapter.refresh();
		}			
	}

	@Override
	protected View setView() {		
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.myapp, null);
		mListView = (ListView) mLayoutRoot.findViewById(R.id.myapp_listview);
		mLayoutBanner = (LinearLayout) mLayoutRoot.findViewById(R.id.myapp_banner);
		mLayoutInstalled = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_installed);
		mLayoutDownload = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_download);
		mLayoutCollect = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_collect);
		mLayoutBrowse = (FrameLayout) mLayoutRoot.findViewById(R.id.myapp_browse);
		mLayoutInstalled.setOnClickListener(this);
		mLayoutDownload.setOnClickListener(this);
		mLayoutCollect.setOnClickListener(this);
		mLayoutBrowse.setOnClickListener(this);		
		mLayoutInstalled.setBackgroundResource(R.drawable.myapp_column_corner);
		mCurrent = mLayoutInstalled;
		return mLayoutRoot;
	}

	@Override
	protected void onStop() {
		if (mAdapterDouble != null)
			mAdapterDouble.stop();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.myapp_installed: {
				if (!mCurrent.equals(mLayoutInstalled)) {
					if (mAdapterDouble != null) {
						mAdapterDouble.stop();
						mAdapterDouble = null;
					}
										
					if (mAdapter == null) {
						List<EntityAppInfo> list = mGlobalData.getLocalApps();
						mAdapter = new MyAppListAdapter(mContext, mListView, list);
					}
					
					mListView.setAdapter(mAdapter);	
					setBackground(mLayoutInstalled);
					showBanner(false);
				}
			}
			break;
			
			case R.id.myapp_download: {
				if (!mCurrent.equals(mLayoutDownload)) {
					if (mAdapterDouble != null) {
						mAdapterDouble.stop();
						mAdapterDouble = null;
					}
					
					List<EntityApp> list = mGlobalData.getDownloadApps();
					mAdapterDouble = new DoubleMyAppListAdapter(mContext, mListView, list);					
					mListView.setAdapter(mAdapterDouble);	
					setBackground(mLayoutDownload);
					showBanner(true);
				}
			}
			break;
			
			case R.id.myapp_collect: {
				if (!mCurrent.equals(mLayoutCollect)) {
					if (mAdapterDouble != null) {
						mAdapterDouble.stop();
						mAdapterDouble = null;
					}
					
					List<EntityApp> list = mGlobalData.getCollectApps();
					mAdapterDouble = new DoubleMyAppListAdapter(mContext, mListView, list);					
					mListView.setAdapter(mAdapterDouble);	
					setBackground(mLayoutCollect);
					showBanner(true);
				}
			}
			break;
			
			case R.id.myapp_browse: {
				if (!mCurrent.equals(mLayoutBrowse)) {
					if (mAdapterDouble != null) {
						mAdapterDouble.stop();
						mAdapterDouble = null;
					}
					
					List<EntityApp> list = mGlobalData.getBrowseApps();
					mAdapterDouble = new DoubleMyAppListAdapter(mContext, mListView, list);					
					mListView.setAdapter(mAdapterDouble);	
					setBackground(mLayoutBrowse);
					showBanner(true);
				}
			}
			break;
		}
	}
	
	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapterDouble != null)
			mAdapterDouble.setAppStatus(entityApp);
	}
	
	private void setBackground(FrameLayout layout) {
		mCurrent.setBackgroundResource(android.R.color.transparent);	
		layout.setBackgroundResource(R.drawable.myapp_column_corner);
		mCurrent = layout;
	}
	
	private void showBanner(boolean hide) {
		if (hide) {
			if (mLayoutBanner != null && mLayoutBanner.getVisibility() == View.VISIBLE)
				mLayoutBanner.setVisibility(View.GONE);									
		} else {
			if (mLayoutBanner != null && mLayoutBanner.getVisibility() == View.GONE)
				mLayoutBanner.setVisibility(View.VISIBLE);
		}
	}
}

class MyAppListAdapter extends BaseAdapter implements OnClickListener, OnScrollListener {
	private final List<EntityAppInfo> mList;
	private final ListView mListView;
	private final LayoutInflater mInflater;
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private final String mSize;
	private final String mVersion;
	
	MyAppListAdapter(Context context, ListView listView, List<EntityAppInfo> list) {
		mContext = context;
		mListView = listView;
		mInflater = LayoutInflater.from(context);
		mList = list;
		mListView.setOnScrollListener(this);
		mSize = mContext.getString(R.string.myapp_size);
		mVersion = mContext.getString(R.string.myapp_version);
	}
	
	@Override
	public int getCount() {
		int size = mList.size();	
		if (size % 2 == 0)
			return size / 2;
		else
			return size / 2 + 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int id) {		
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderMyApp holder = null;
		if (convertView == null) {
			holder = new ViewHolderMyApp();
			convertView = mInflater.inflate(R.layout.myapp_item, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_layout2));			
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_name2));
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_size2));
			holder.setVersion1((TextView) convertView.findViewById(R.id.myapp_version1));
			holder.setVersion2((TextView) convertView.findViewById(R.id.myapp_version2));
			convertView.setTag(holder);
		} else			
			holder = (ViewHolderMyApp) convertView.getTag();
		
		EntityAppInfo entityAppInfo1 = mList.get(position);
		EntityAppInfo entityAppInfo2 = null;		
		if (position * 2 + 1 < mList.size())
			entityAppInfo2 = mList.get(position * 2 + 1);
		
		Drawable drawable = entityAppInfo1.getAppIcon();
		if (drawable == null)
			holder.getIcon1().setImageResource(R.drawable.ic_launcher);
		else
			holder.getIcon1().setImageDrawable(drawable);
		
		String name = entityAppInfo1.getAppName();		
		holder.getName1().setText(name);
		
		long size = entityAppInfo1.getSize();
		double d = (double)size / 1024d / 1024d;
		holder.getSize1().setText(mSize + " " + mFormat.format(d) + "M");
		holder.getVersion1().setText(mVersion + " " + entityAppInfo1.getVersionName());
		
		LinearLayout layout1 = holder.getLayout1();
		layout1.setTag(entityAppInfo1);
		layout1.setOnClickListener(this);
		
		if (entityAppInfo2 != null) {
			drawable = entityAppInfo2.getAppIcon();
			if (drawable == null)
				holder.getIcon2().setImageResource(R.drawable.ic_launcher);
			else
				holder.getIcon2().setImageDrawable(drawable);
			
			name = entityAppInfo2.getAppName();
			holder.getName2().setText(name);
			
			size = entityAppInfo2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getSize2().setText(mSize + " " + mFormat.format(d) + "M");
			holder.getVersion2().setText(mVersion + " " + entityAppInfo2.getVersionName());
									
			LinearLayout layout2 = holder.getLayout2();
			layout2.setTag(entityAppInfo2);
			layout2.setOnClickListener(this);							
						
			if (holder.getLayout2().getVisibility() == View.INVISIBLE)
				holder.getLayout2().setVisibility(View.VISIBLE);
		} else
			holder.getLayout2().setVisibility(View.INVISIBLE);
		
		return convertView;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		Object object = view.getTag();
		if (id == R.id.myapp_version1 || id == R.id.myapp_version2 && object != null) {
			EntityAppInfo entityAppInfo = (EntityAppInfo) object;
			String packageName = entityAppInfo.getPackageName();
			
		}
	}	
	
	public void refresh() {
		notifyDataSetChanged();		
	}
}

class DoubleMyAppListAdapter extends BaseAdapter implements OnClickListener, OnScrollListener {
	private final GlobalObject mGlobalObject = GlobalObject.globalObject;
	private final ListView mListView;
	private final LayoutInflater mInflater;
	private final List<EntityApp> mList;
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	DoubleMyAppListAdapter(Context context, ListView listView, List<EntityApp> list) {
		mContext = context;
		mListView = listView;
		mList = list;
		mInflater = LayoutInflater.from(context);
		mListView.setOnScrollListener(this);		
		mTasks.start();
	}
				
	@Override
	public int getCount() {
		int size = mList.size();	
		if (size % 2 == 0)
			return size / 2;
		else
			return size / 2 + 1;
	}

	@Override
	public Object getItem(int position) {		
		return position;
	}

	@Override
	public long getItemId(int id) {		
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		ViewHolderDoubleApp holder = null;
		if (convertView == null) {			
			holder = new ViewHolderDoubleApp();
			convertView = mInflater.inflate(R.layout.double_app, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.app_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.app_layout2));			
			holder.setIcon1((ImageView) convertView.findViewById(R.id.app_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.app_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.app_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.app_name2));
			holder.setPrice1((TextView) convertView.findViewById(R.id.app_price1));
			holder.setPrice2((TextView) convertView.findViewById(R.id.app_price2));
			holder.setTypeSize1((TextView) convertView.findViewById(R.id.app_type_size1));
			holder.setTypeSize2((TextView) convertView.findViewById(R.id.app_type_size2));
			holder.setDcount1((TextView) convertView.findViewById(R.id.app_dcount1));
			holder.setDcount2((TextView) convertView.findViewById(R.id.app_dcount2));
			holder.setPcount1((TextView) convertView.findViewById(R.id.app_pcount1));
			holder.setPcount2((TextView) convertView.findViewById(R.id.app_pcount2));
			holder.setRating1((RatingBar) convertView.findViewById(R.id.app_ratingbar1));
			holder.setRating2((RatingBar) convertView.findViewById(R.id.app_ratingbar2));
			holder.setAction1((TextView) convertView.findViewById(R.id.app_action1));
			holder.setAction2((TextView) convertView.findViewById(R.id.app_action2));
			convertView.setTag(holder);						
		} else			
			holder = (ViewHolderDoubleApp) convertView.getTag();		
								
		EntityApp entityApp1 = mList.get(position * 2);
		EntityApp entityApp2 = null;
		if (position * 2 + 1 < mList.size())
			entityApp2 = mList.get(position * 2 + 1);
				
		String icon = entityApp1.getIcon() + "?a1=" + position;
		mTasks.setUrl(icon, holder.getIcon1());
		
		Drawable drawable = mImageCache.get(icon);
		if (drawable != null)
			holder.getIcon1().setImageDrawable(drawable);
		else							
			holder.getIcon1().setImageResource(R.drawable.ic_launcher);
		
		String name = entityApp1.getName();		
		holder.getName1().setText(name);
		
		String price = entityApp1.getPrice();
		holder.getPrice1().setText(price);
		
		String type = entityApp1.getType();	
		long size = entityApp1.getSize();
		double d = (double)size / 1024d / 1024d;
		holder.getTypeSize1().setText(type + " " + mFormat.format(d) + "M");
		
		long dcount = entityApp1.getDcount();		
		long pcount = entityApp1.getPcount();
		holder.getDcount1().setText(String.valueOf(dcount));
		holder.getPcount1().setText(String.valueOf(pcount));
		holder.getRating1().setRating(entityApp1.getRating());
		TextView action = holder.getAction1();
		action.setTag(entityApp1);
						
		switch (entityApp1.getStatus()) {
			case NOINSTALL:
				action.setText(R.string.app_download);
				action.setClickable(true);
				action.setOnClickListener(this);				
				break;
			
			case INSTALL:
				action.setText(R.string.app_install);
				action.setClickable(true);
				action.setOnClickListener(this);
				break;
				
			case INSTALLED:
				action.setText(R.string.app_run);
				action.setClickable(true);
				action.setOnClickListener(this);
				break;
				
			case WAITING:
				action.setText(R.string.app_waiting);
				action.setClickable(true);
				action.setOnClickListener(this);
				break;
				
			case DOWNLOADING:
				action.setText(R.string.app_downloading);
				action.setClickable(false);
				action.setOnClickListener(this);
				break;
		}
				
		LinearLayout layout = holder.getLayout1();
		layout.setTag(entityApp1);
		layout.setOnClickListener(this);
		
		if (entityApp2 != null) {
			icon = entityApp2.getIcon() + "?a2=" + position;			
			mTasks.setUrl(icon, holder.getIcon2());
						
			drawable = mImageCache.get(icon);
			if (drawable != null)
				holder.getIcon2().setImageDrawable(drawable);
			else								
				holder.getIcon2().setImageResource(R.drawable.ic_launcher);
											
			name = entityApp2.getName();		
			holder.getName2().setText(name);
			
			price = entityApp2.getPrice();
			holder.getPrice2().setText(price);
			
			type = entityApp2.getType();	
			size = entityApp2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getTypeSize2().setText(type + " " + mFormat.format(d) + "M");
			
			dcount = entityApp2.getDcount();		
			pcount = entityApp2.getPcount();
			holder.getDcount2().setText(String.valueOf(dcount));
			holder.getPcount2().setText(String.valueOf(pcount));
			holder.getRating2().setRating(entityApp2.getRating());
			action = holder.getAction2();
			action.setTag(entityApp2);
			
			switch (entityApp2.getStatus()) {
				case NOINSTALL:
					action.setText(R.string.app_download);
					action.setClickable(true);
					action.setOnClickListener(this);
					break;
				
				case INSTALL:
					action.setText(R.string.app_install);
					action.setClickable(true);
					action.setOnClickListener(this);
					break;
					
				case INSTALLED:
					action.setText(R.string.app_run);
					action.setClickable(true);
					action.setOnClickListener(this);
					break;
					
				case WAITING:
					action.setText(R.string.app_waiting);
					action.setClickable(true);
					action.setOnClickListener(this);
					break;
					
				case DOWNLOADING:
					action.setText(R.string.app_downloading);
					action.setClickable(false);
					action.setOnClickListener(this);
					break;
			}
			
			layout = holder.getLayout2();
			layout.setTag(entityApp2);
			layout.setOnClickListener(this);
			
			if (holder.getLayout2().getVisibility() == View.INVISIBLE)
				holder.getLayout2().setVisibility(View.VISIBLE);
		} else
			holder.getLayout2().setVisibility(View.INVISIBLE);
				
		return convertView;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
			mTasks.lock(true);
			break;
		
		case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
			mTasks.lock(false);
			break;
		
		case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			mTasks.lock(true);
			break;
		
		default:
			break;
		}
	}
	
	public void refresh() {
		notifyDataSetChanged();
		mTasks.lock(false);
	}
	
	public void stop() {
		mTasks.stop();
	}

	@Override
	public void onClick(View v) {
		Object obj = v.getTag();
		if (v instanceof LinearLayout && obj != null && obj instanceof EntityApp) {							
			final EntityApp entityApp = (EntityApp) obj;
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(mContext, ActivityDetail.class); 
			mContext.startActivity(intent);
			return;
		}
		
		if (v.getId() == R.id.app_action1 || v.getId() == R.id.app_action2) {
			if (v instanceof TextView && obj != null && obj instanceof EntityApp) {
				final EntityApp entityApp = (EntityApp) obj;
				TaskDownload taskDownload = mGlobalObject.getTaskDownload();
				EnumAppStatus status = entityApp.getStatus();
				switch (status) {
					case NOINSTALL:
						taskDownload.downloadApp(mContext, entityApp);
						break;
						
					case INSTALL:
						if (!taskDownload.installApp(mContext, entityApp))
							setAppStatus(entityApp);
						
						break;
						
					case INSTALLED:
						taskDownload.runApp(mContext, entityApp);					
						break;
				
					case WAITING:					
						taskDownload.downloadCancel(mContext, entityApp);
						break;
						
					case DOWNLOADING:
						taskDownload.downloadCancel(mContext, entityApp);		
						break;
				}
				return;
			}
		}		
	}
	
	public void setAppStatus(EntityApp entityApp) {
		notifyDataSetChanged();
	}
}