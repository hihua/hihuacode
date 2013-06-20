package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderMyAppBrowse;

public class AdapterMyAppBrowse extends AdapterBase {	
	private final List<EntityApp> mList;
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	public AdapterMyAppBrowse(Context context, ListView listView, OnClickListener onClick, List<EntityApp> list) {
		super(context, listView, onClick);
		mListView.setOnScrollListener(this);
		mList = list;		
		mTasks.start();
	}
				
	@Override
	public int getCount() {
		final int size = mList.size();	
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
		ViewHolderMyAppBrowse holder = null;
		if (convertView == null) {			
			holder = new ViewHolderMyAppBrowse();
			convertView = mInflater.inflate(R.layout.myapp_browse, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_browse_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_browse_layout2));			
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_browse_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_browse_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_browse_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_browse_name2));
			holder.setPrice1((TextView) convertView.findViewById(R.id.myapp_browse_price1));
			holder.setPrice2((TextView) convertView.findViewById(R.id.myapp_browse_price2));			
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_browse_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_browse_size2));
			holder.setDcount1((TextView) convertView.findViewById(R.id.myapp_browse_dcount1));
			holder.setDcount2((TextView) convertView.findViewById(R.id.myapp_browse_dcount2));
			holder.setPcount1((TextView) convertView.findViewById(R.id.myapp_browse_pcount1));
			holder.setPcount2((TextView) convertView.findViewById(R.id.myapp_browse_pcount2));
			holder.setRating1((RatingBar) convertView.findViewById(R.id.myapp_browse_rating1));
			holder.setRating2((RatingBar) convertView.findViewById(R.id.myapp_browse_rating2));
			holder.setScore1((TextView) convertView.findViewById(R.id.myapp_browse_score1));
			holder.setScore2((TextView) convertView.findViewById(R.id.myapp_browse_score2));			
			holder.setAction1((LinearLayout) convertView.findViewById(R.id.myapp_browse_action1));
			holder.setAction2((LinearLayout) convertView.findViewById(R.id.myapp_browse_action2));
			holder.setText1((TextView) convertView.findViewById(R.id.myapp_browse_text1));
			holder.setText2((TextView) convertView.findViewById(R.id.myapp_browse_text2));
			holder.setDownload1((ImageView) convertView.findViewById(R.id.myapp_browse_download1));
			holder.setDownload2((ImageView) convertView.findViewById(R.id.myapp_browse_download2));			
			convertView.setTag(holder);						
		} else			
			holder = (ViewHolderMyAppBrowse) convertView.getTag();
										
		final EntityApp entityApp1 = mList.get(position * 2);
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
		
		textView = holder.getPrice1();
		text = entityApp1.getPrice();
		textView.setText(text);
		
		long size = entityApp1.getSize();
		double d = (double) size / 1024d / 1024d;
		holder.getSize1().setText(mFormat.format(d) + "M");
		
		long dcount = entityApp1.getDcount();		
		long pcount = entityApp1.getPcount();
		holder.getDcount1().setText(String.valueOf(dcount));
		holder.getPcount1().setText(String.valueOf(pcount));
		holder.getRating1().setRating(entityApp1.getRating());
		holder.getScore1().setText(String.valueOf(entityApp1.getRating()));
						
		LinearLayout layout = holder.getAction1();
		imageView = holder.getDownload1();
		textView = holder.getText1();
								
		switch (entityApp1.getStatus()) {
			case NOINSTALL:				
				text = mContext.getString(R.string.app_download);
				layout.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);			
				break;
			
			case INSTALL:				
				text = mContext.getString(R.string.app_install);
				layout.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
				break;
				
			case INSTALLED:				
				text = mContext.getString(R.string.app_run);
				layout.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
				break;
				
			case WAITING:				
				text = mContext.getString(R.string.app_waiting);
				layout.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
				break;
				
			case DOWNLOADING:				
				text = mContext.getString(R.string.app_downloading);
				layout.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
				break;
		}							
		
		textView.setText(text);
		
		layout.setTag(entityApp1);
		layout.setOnClickListener(mOnClick);
		
		imageView.setTag(entityApp1);
		imageView.setOnClickListener(mOnClick);
								
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
			
			textView = holder.getPrice2();
			text = entityApp2.getPrice();
			textView.setText(text);			
			
			size = entityApp2.getSize();
			d = (double) size / 1024d / 1024d;
			holder.getSize2().setText(mFormat.format(d) + "M");
						
			dcount = entityApp2.getDcount();		
			pcount = entityApp2.getPcount();
			holder.getDcount2().setText(String.valueOf(dcount));
			holder.getPcount2().setText(String.valueOf(pcount));
			holder.getRating2().setRating(entityApp2.getRating());
			holder.getScore2().setText(String.valueOf(entityApp2.getRating()));
							
			layout = holder.getAction2();		
			imageView = holder.getDownload2();
			textView = holder.getText2();
									
			switch (entityApp2.getStatus()) {
				case NOINSTALL:				
					text = mContext.getString(R.string.app_download);
					layout.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);			
					break;
				
				case INSTALL:				
					text = mContext.getString(R.string.app_install);
					layout.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					break;
					
				case INSTALLED:				
					text = mContext.getString(R.string.app_run);
					layout.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					break;
					
				case WAITING:				
					text = mContext.getString(R.string.app_waiting);
					layout.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					break;
					
				case DOWNLOADING:				
					text = mContext.getString(R.string.app_downloading);
					layout.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					break;
			}							
			
			textView.setText(text);
			
			layout.setTag(entityApp2);
			layout.setOnClickListener(mOnClick);
			
			imageView.setTag(entityApp2);
			imageView.setOnClickListener(mOnClick);			
			
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
	
	@Override
	public void refresh() {
		notifyDataSetChanged();
		mTasks.lock(false);
	}
	
	@Override
	public void stop() {
		mTasks.stop();
	}
		
	@Override
	public void setAppStatus(EntityApp entityApp) {
		notifyDataSetChanged();
	}
}
