package com.apps.game.market.views;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityDetail;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.view.ScrollViewAd;
import com.apps.game.market.view.callback.ScrollViewAdCallBack;
import com.apps.game.market.viewholder.ViewHolderSingleApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class ViewColumnSingle extends ViewColumn implements RequestCallBackAd, ScrollViewAdCallBack {
	private ScrollViewAd mScrollViewAd;
	private ListView mListView;
	private TextView mTextViewAdName;
	private TextView mTextViewAdPosition;
	private RequestAd mRequestAd;
	private ViewGroup mLayoutRoot;	
	private List<EntityAd> mAds;
	private SingleAppListAdapter mAdapter;
	
	public ViewColumnSingle(Context context, ViewGroup parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
		super(context, parent, layoutColumn, entityColumn);
		mRequestAd = new RequestAd(this);
	}
		
	@Override
	protected void onInit() {
		if (!mInit)	{
			if (mEntityColumn != null) {
				long id = mEntityColumn.getId();			
				mRequestAd.request(id);
			}						
		}
	}

	@Override
	protected void onRefresh() {
		if (mAdapter != null)
			mAdapter.refresh();
	}

	@Override
	protected View setView() {
		mLayoutRoot = (LinearLayout) mInflater.inflate(R.layout.index_scroll, null);
		mListView = (ListView) mLayoutRoot.findViewById(R.id.index_listview);		
		return mLayoutRoot;
	}

	@Override
	public void onCallBackAd(boolean success) {
		LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.aditem, null);
		RelativeLayout layoutAd = (RelativeLayout) layout.findViewById(R.id.aditem_layout);		
		LinearLayout layoutColumn = (LinearLayout) layout.findViewById(R.id.aditem_layout_column);
		
		if (success && mEntityColumn != null) {
			long id = mEntityColumn.getId();
			mAds = mGlobalData.getAds(id);
			boolean header = false;
			
			if (mAds != null) {
				mTextViewAdName = (TextView) layout.findViewById(R.id.aditem_textview_name);
				mTextViewAdPosition = (TextView) layout.findViewById(R.id.aditem_textview_position);
				
				mScrollViewAd = (ScrollViewAd) layout.findViewById(R.id.aditem_view);
				mScrollViewAd.setTop(mParent, mListView);
				mScrollViewAd.setCallBack(this);
								
				setAds(mScrollViewAd, mAds);				
				startScroll();
				onFinishScroll(0);
				header = true;
			} else
				layout.removeView(layoutAd);
						
			boolean classes = setSubColumn(layoutColumn, 0);
			if (!classes)
				layout.removeView(layoutColumn);
			
			if (header || classes)
				mListView.addHeaderView(layout);
		}
		
		if (mEntityColumn != null) {
			mAdapter = new SingleAppListAdapter(mContext, mListView, mEntityColumn.getRequest());
			mListView.setAdapter(mAdapter);	
		}
	}

	@Override
	public void startScroll() {
		if (mScrollViewAd != null) {			
			mScrollViewAd.startTimer(4000, 3000);
		}
	}

	@Override
	public void stopScroll() {
		if (mScrollViewAd != null)
			mScrollViewAd.cancelTimer();
	}

	@Override
	public void onFinishScroll(int screen) {
		if (mAds != null && mTextViewAdName != null && mTextViewAdPosition != null) {
			StringBuilder position = new StringBuilder(); 
			for (int i = 0;i < mAds.size();i++) {
				if (i == screen) {
					EntityAd ad = mAds.get(i);
					mTextViewAdName.setText(ad.getName());           	
					position.append("●");
				} else
					position.append("○");
			}
		
			mTextViewAdPosition.setText(position.toString());			
		}
	}

	@Override
	protected void onStop() {
		if (mAdapter != null)
			mAdapter.stop();
	}

	@Override
	public void onSubColumn(final ViewGroup parent, final int position, final String url) {		
		setSubColumn(parent, 0);
	}

	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (mAdapter != null)
			mAdapter.setAppStatus(entityApp);
	}
}

class SingleAppListAdapter extends BaseAdapter implements OnClickListener, OnScrollListener, RequestCallBackApp {
	private final GlobalObject mGlobalObject = GlobalObject.globalObject;
	private final RequestApp mRequestApp;
	private final ListView mListView;
	private final long mPageSize = 50;
	private boolean mRequest = true;	
	private final LayoutInflater mInflater;
	private final List<EntityApp> mList = new Vector<EntityApp>();
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	SingleAppListAdapter(Context context, ListView listView, RequestApp requestApp) {
		mContext = context;
		mListView = listView;
		mInflater = LayoutInflater.from(context);
		mListView.setOnScrollListener(this);
		mRequestApp = requestApp;
		requestApp.setCallBackApp(this);
		requestApp.request(0, mPageSize);
		mTasks.start();
	}
				
	@Override
	public int getCount() {
		int size = mList.size();	
		return size;
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
		ViewHolderSingleApp holder = null;		
		if (convertView == null) {
			holder = new ViewHolderSingleApp();
			convertView = mInflater.inflate(R.layout.single_app, parent, false);										
			holder.setIcon((ImageView) convertView.findViewById(R.id.single_app_icon));	
			holder.setLayout((LinearLayout) convertView.findViewById(R.id.single_app_layout));
			holder.setName((TextView) convertView.findViewById(R.id.single_app_name));
			holder.setSize((TextView) convertView.findViewById(R.id.single_app_size));			
			holder.setPrice((TextView) convertView.findViewById(R.id.single_app_price));
			holder.setDcount((TextView) convertView.findViewById(R.id.single_app_dcount));			
			holder.setPcount((TextView) convertView.findViewById(R.id.single_app_pcount));
			holder.setDownload((ImageView) convertView.findViewById(R.id.single_app_download));
			convertView.setTag(holder);						
		} else
			holder = (ViewHolderSingleApp) convertView.getTag();
						
		EntityApp entityApp = mList.get(position);						
		String url = entityApp.getIcon() + "?a1=" + position;
		mTasks.setUrl(url, holder.getIcon());
		
		Drawable drawable = mImageCache.get(url);
		if (drawable != null)
			holder.getIcon().setImageDrawable(drawable);
		else							
			holder.getIcon().setImageResource(R.drawable.ic_launcher);
		
		String name = entityApp.getName();
		if (name.length() > 10)
			name = name.substring(0, 10);
			
		holder.getName().setText(name);
				
		long size = entityApp.getSize();
		double d = (double)size / 1024d / 1024d;		
		holder.getSize().setText(mFormat.format(d) + "M");
		
		String price = entityApp.getPrice();
		holder.getPrice().setText(price);
		
		long dcount = entityApp.getDcount();		
		holder.getDcount().setText(String.valueOf(dcount));
		
		long pcount = entityApp.getPcount();		
		holder.getPcount().setText(String.valueOf(pcount));
				
		ImageView download = holder.getDownload();
		download.setTag(entityApp);
		download.setOnClickListener(this);
		
		ImageView icon = holder.getIcon();
		icon.setTag(entityApp);
		icon.setOnClickListener(this);
		
		LinearLayout layout = holder.getLayout();
		layout.setTag(entityApp);
		layout.setOnClickListener(this);
		
		return convertView;
	}

	@Override
	public void onCallBackApp(List<EntityApp> list, boolean finish) {
		if (list != null) {
			mList.addAll(list);
			notifyDataSetChanged();			
			mRequest = false;			
		} else {
			if (!finish)
				mRequest = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int count = mListView.getHeaderViewsCount();
		if (firstVisibleItem + visibleItemCount == totalItemCount && !mRequest) {
			//long p = (totalItemCount - count) / mPageSize;			
			mRequestApp.request(totalItemCount - count, mPageSize);	
			mRequest = true;		
		}		
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.single_app_download) {
			final EntityApp entityApp = (EntityApp) v.getTag();
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
		
		if (v.getId() == R.id.single_app_icon || v.getId() == R.id.single_app_layout) {
			final EntityApp entityApp = (EntityApp) v.getTag();
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(mContext, ActivityDetail.class); 
	        mContext.startActivity(intent);
		}
	}
	
	public void refresh() {
		notifyDataSetChanged();
		mTasks.lock(false);
	}
	
	public void stop() {
		mTasks.stop();
	}
	
	public void setAppStatus(EntityApp entityApp) {
		notifyDataSetChanged();
	}
}