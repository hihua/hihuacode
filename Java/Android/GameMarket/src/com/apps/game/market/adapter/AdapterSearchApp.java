package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderDoubleApp;

public class AdapterSearchApp extends BaseAdapter implements OnScrollListener, RequestCallBackApp {
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
		
	public AdapterSearchApp(Context context, ListView listView, RequestApp requestApp, OnClickListener onClick) {
		mContext = context;
		mListView = listView;
		mInflater = LayoutInflater.from(context);
		mListView.setOnScrollListener(this);
		mRequestApp = requestApp;		
		mOnClick = onClick;
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
			holder.setClass1((LinearLayout) convertView.findViewById(R.id.app_class1));
			holder.setClass2((LinearLayout) convertView.findViewById(R.id.app_class2));
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
				
		ImageView imageView = holder.getIcon1();
		String text = entityApp1.getIcon();
		mTasks.setUrl(text, imageView);
		
		Bitmap bitmap = mImageCache.get(text);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else							
			imageView.setImageResource(R.drawable.ic_launcher);
		
		TextView textView = holder.getName1();
		text = entityApp1.getName();		
		textView.setText(text);
		textView.setTag(entityApp1);
		textView.setOnClickListener(mOnClick);
		
		textView = holder.getPrice1();
		text = entityApp1.getPrice();
		textView.setText(text);
		textView.setTag(entityApp1);
		textView.setOnClickListener(mOnClick);
		
		text = entityApp1.getType();	
		long size = entityApp1.getSize();
		double d = (double)size / 1024d / 1024d;
		holder.getTypeSize1().setText(text + " " + mFormat.format(d) + "M");
		
		long dcount = entityApp1.getDcount();		
		long pcount = entityApp1.getPcount();
		holder.getDcount1().setText(String.valueOf(dcount));
		holder.getPcount1().setText(String.valueOf(pcount));
		holder.getRating1().setRating(entityApp1.getRating());
		
		textView = holder.getAction1();
		textView.setTag(entityApp1);
						
		switch (entityApp1.getStatus()) {
			case NOINSTALL:
				textView.setText(R.string.app_download);
				textView.setClickable(true);
				textView.setOnClickListener(mOnClick);				
				break;
			
			case INSTALL:
				textView.setText(R.string.app_install);
				textView.setClickable(true);
				textView.setOnClickListener(mOnClick);
				break;
				
			case INSTALLED:
				textView.setText(R.string.app_run);
				textView.setClickable(true);
				textView.setOnClickListener(mOnClick);
				break;
				
			case WAITING:
				textView.setText(R.string.app_waiting);
				textView.setClickable(true);
				textView.setOnClickListener(mOnClick);
				break;
				
			case DOWNLOADING:
				textView.setText(R.string.app_downloading);
				textView.setClickable(false);
				textView.setOnClickListener(mOnClick);
				break;
		}								
				
		LinearLayout layout = holder.getClass1();
		layout.setTag(entityApp1);
		layout.setOnClickListener(mOnClick);
		
		if (entityApp2 != null) {			
			imageView = holder.getIcon2();
			text = entityApp2.getIcon();
			mTasks.setUrl(text, imageView);
			
			bitmap = mImageCache.get(text);
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else							
				imageView.setImageResource(R.drawable.ic_launcher);
											
			textView = holder.getName2();
			text = entityApp2.getName();		
			textView.setText(text);
			textView.setTag(entityApp2);
			textView.setOnClickListener(mOnClick);
			
			textView = holder.getPrice2();
			text = entityApp2.getPrice();
			textView.setText(text);
			textView.setTag(entityApp2);
			textView.setOnClickListener(mOnClick);
			
			text = entityApp2.getType();	
			size = entityApp2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getTypeSize2().setText(text + " " + mFormat.format(d) + "M");
			
			dcount = entityApp2.getDcount();		
			pcount = entityApp2.getPcount();
			holder.getDcount2().setText(String.valueOf(dcount));
			holder.getPcount2().setText(String.valueOf(pcount));
			holder.getRating2().setRating(entityApp2.getRating());
			
			textView = holder.getAction2();
			textView.setTag(entityApp2);
			
			switch (entityApp2.getStatus()) {
				case NOINSTALL:
					textView.setText(R.string.app_download);
					textView.setClickable(true);
					textView.setOnClickListener(mOnClick);
					break;
				
				case INSTALL:
					textView.setText(R.string.app_install);
					textView.setClickable(true);
					textView.setOnClickListener(mOnClick);
					break;
					
				case INSTALLED:
					textView.setText(R.string.app_run);
					textView.setClickable(true);
					textView.setOnClickListener(mOnClick);
					break;
					
				case WAITING:
					textView.setText(R.string.app_waiting);
					textView.setClickable(true);
					textView.setOnClickListener(mOnClick);
					break;
					
				case DOWNLOADING:
					textView.setText(R.string.app_downloading);
					textView.setClickable(false);
					textView.setOnClickListener(mOnClick);
					break;
			}
						
			layout = holder.getClass2();
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
		notifyDataSetChanged();
		mTasks.lock(false);
	}
	
	public void setAppStatus(EntityApp entityApp) {
		notifyDataSetChanged();
	}
}
