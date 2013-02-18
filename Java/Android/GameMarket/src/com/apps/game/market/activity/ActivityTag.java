package com.apps.game.market.activity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.app.RequestAppTag;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderDoubleApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class ActivityTag extends ActivityBase {
	private LinearLayout mLayoutColumn;
	private ListView mListView;
	private TextView mTagName;	
	private boolean mFinish = true;
	private boolean mAdapter = false;
		
	@Override
	protected void onAppCreate() {
		setContentView(R.layout.tag);
		layout();
	}

	@Override
	protected void onAppClose() {
		if (mFinish)
			finish();				
	}
		
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mFinish = true;
	}

	@Override
	protected void onAppResume() {
		if (!mAdapter) {
			mEntityTag = mGlobalData.getSelectTag();
			if (mEntityTag.getName() != null)
				mTagName.setText(mEntityTag.getName());
				
			RequestApp requestTag = new RequestAppTag();		
			mListView.setAdapter(new TagAppListAdapter(this, mListView, requestTag, mEntityTag.getId(), this));
			mAdapter = true;
		}		
	}

	@Override
	protected void onAppClick(View v) {
		Object obj = v.getTag();
		if (v instanceof LinearLayout && obj != null && obj instanceof EntityApp) {
			mFinish = false;
			final EntityApp entityApp = (EntityApp) v.getTag();
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(this, ActivityDetail.class); 
	        startActivity(intent);
	        return;			
		}
		
		if (v instanceof FrameLayout) {
			Intent intent = mGlobalData.getLastIntent();
			if (intent != null && obj != null && obj instanceof Integer) {
				Integer index = (Integer) obj;
				mGlobalData.setSelectColumn(index);				
				onBackPressed();			
			}
		}
	}
	
	private void layout() {
		mLayoutColumn = (LinearLayout) findViewById(R.id.tag_layout_column);
		mTagName = (TextView) findViewById(R.id.tag_name);
		mListView = (ListView) findViewById(R.id.tag_listview);
		layoutColumns(mLayoutColumn, null);
	}	
}

class TagAppListAdapter extends BaseAdapter implements OnScrollListener, RequestCallBackApp {
	private final RequestApp mRequestApp;
	private final ListView mListView;
	private final long mPageSize = 20;
	private boolean mRequest = true;	
	private final LayoutInflater mInflater;
	private final List<EntityApp> mList = new Vector<EntityApp>();
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private long mTagId = 0;
	private OnClickListener mOnClick = null;
		
	TagAppListAdapter(Context context, ListView listView, RequestApp requestApp, long tagId, OnClickListener onClick) {
		mContext = context;
		mListView = listView;
		mInflater = LayoutInflater.from(context);
		mListView.setOnScrollListener(this);
		mRequestApp = requestApp;
		mTagId = tagId;
		mOnClick = onClick;
		requestApp.setCallBackApp(this);
		requestApp.request(tagId, 0, mPageSize);
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
		layout.setOnClickListener(mOnClick);
		
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
			layout.setOnClickListener(mOnClick);
			
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
			mRequestApp.request(mTagId, (totalItemCount - count) * 2, mPageSize);			
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
}