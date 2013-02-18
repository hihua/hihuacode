package com.apps.game.market.views;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityDetail;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.view.ScrollViewAd;
import com.apps.game.market.view.callback.ScrollViewAdCallBack;
import com.apps.game.market.viewholder.ViewHolderDoubleApp;

public class ViewColumnDouble extends ViewColumn implements RequestCallBackAd, ScrollViewAdCallBack {
	private ScrollViewAd mScrollViewAd;
	private ListView mListView;
	private TextView mTextViewAdName;
	private TextView mTextViewAdPosition;
	private RequestAd mRequestAd;
	private LinearLayout mLayoutRoot;	
	private List<EntityAd> mAds;
	private DoubleAppListAdapter mAdapter;
	
	public ViewColumnDouble(Context context, ViewGroup parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
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
		mListView.setDivider(null);
		mListView.setDividerHeight(0);
		return mLayoutRoot;
	}

	@Override
	public void onCallBackAd(boolean success) {
		LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.aditem, null);
		RelativeLayout layoutAd = (RelativeLayout) layout.findViewById(R.id.aditem_layout);		
		RelativeLayout layoutColumn = (RelativeLayout) layout.findViewById(R.id.aditem_layout_column);
		
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
						
			boolean classes = setClasses(layoutColumn, 0);
			if (!classes)
				layout.removeView(layoutColumn);
				
			if (header || classes)
				mListView.addHeaderView(layout);
		}
		
		if (mEntityColumn != null) {
			mAdapter = new DoubleAppListAdapter(mContext, mListView, mEntityColumn.getRequest());
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
}

class DoubleAppListAdapter extends BaseAdapter implements OnClickListener, OnScrollListener, RequestCallBackApp {
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
		
	DoubleAppListAdapter(Context context, ListView listView, RequestApp requestApp) {
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
		
		if (position == 0) {
			View view = convertView.findViewById(R.id.app_layout_top1);
			view.setVisibility(View.GONE);
			view = convertView.findViewById(R.id.app_layout_top2);
			view.setVisibility(View.GONE);
		} else {
			View view = convertView.findViewById(R.id.app_layout_top1);
			view.setVisibility(View.VISIBLE);
			view = convertView.findViewById(R.id.app_layout_top2);
			view.setVisibility(View.VISIBLE);
		}
						
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
			holder.getIcon1().setImageResource(R.drawable.ic_menu_refresh);
		
		String name = entityApp1.getName();
		if (name.length() > 6)
			name = name.substring(0, 6);
		
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
						
		switch (entityApp1.getStatus()) {
			case NOINSTALL:
				holder.getAction1().setText(R.string.app_download);
				break;
			
			case INSTALL:
				holder.getAction1().setText(R.string.app_run);
				break;
				
			case DOWNLOADING:
				holder.getAction1().setText(R.string.app_downloading);
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
				holder.getIcon2().setImageResource(R.drawable.ic_menu_refresh);
											
			name = entityApp2.getName();
			if (name.length() > 6)
				name = name.substring(0, 6);
			
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
			
			switch (entityApp2.getStatus()) {
				case NOINSTALL:
					holder.getAction2().setText(R.string.app_download);
					break;
				
				case INSTALL:
					holder.getAction2().setText(R.string.app_run);
					break;
					
				case DOWNLOADING:
					holder.getAction2().setText(R.string.app_downloading);
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
			//long p = (totalItemCount - count) * 2 / mPageSize;			
			mRequestApp.request((totalItemCount - count) * 2, mPageSize);			
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
	
	public void refresh() {		
		mTasks.lock(false);
	}
	
	public void stop() {
		mTasks.stop();
	}

	@Override
	public void onClick(View v) {
		Object obj = v.getTag();
		if (v instanceof LinearLayout && obj != null) {
			if (obj instanceof EntityApp) {				
				final EntityApp entityApp = (EntityApp) v.getTag();
				GlobalData globalData = GlobalData.globalData;
				globalData.setSelectApp(entityApp);
				Intent intent = new Intent(mContext, ActivityDetail.class); 
				mContext.startActivity(intent);
			}			
		}
	}
}