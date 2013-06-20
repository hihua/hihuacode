package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderSingleApp;

public class AdapterSingleApp extends AdapterBase implements RequestCallBackApp {	
	private final RequestApp mRequestApp;	
	private final long mPageSize = 50;
	private boolean mRequest = true;	
	private final List<EntityApp> mList = new Vector<EntityApp>();
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();	
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	public AdapterSingleApp(Context context, ListView listView, RequestApp requestApp, OnClickListener onClick) {
		super(context, listView, onClick);
		mListView.setOnScrollListener(this);
		mRequestApp = requestApp;
		requestApp.setCallBackApp(this);
		requestApp.request(0, mPageSize);
		mTasks.start();
	}
				
	@Override
	public int getCount() {
		final int size = mList.size();	
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
			holder.setAction((LinearLayout) convertView.findViewById(R.id.single_app_action));
			holder.setText((TextView) convertView.findViewById(R.id.single_app_text));
			holder.setDownload((ImageView) convertView.findViewById(R.id.single_app_download));
			convertView.setTag(holder);						
		} else
			holder = (ViewHolderSingleApp) convertView.getTag();
										
		final EntityApp entityApp = mList.get(position);		
		ImageView imageView = holder.getIcon();
		imageView.setTag(entityApp);
		imageView.setOnClickListener(mOnClick);
		
		String text = entityApp.getIcon();
		mTasks.setUrl(text, imageView);
		
		Bitmap bitmap = mImageCache.get(text);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else							
			imageView.setImageResource(R.drawable.ic_launcher);
		
		TextView textView = holder.getName();
		text = entityApp.getName();		
		textView.setText(text);
		textView.setTag(entityApp);
		textView.setOnClickListener(mOnClick);
				
		long size = entityApp.getSize();
		double d = (double)size / 1024d / 1024d;		
		holder.getSize().setText(mFormat.format(d) + "M");
		
		text = entityApp.getPrice();
		holder.getPrice().setText(text);
		
		long dcount = entityApp.getDcount();		
		holder.getDcount().setText(String.valueOf(dcount));
		
		long pcount = entityApp.getPcount();		
		holder.getPcount().setText(String.valueOf(pcount));
		
		LinearLayout layout = holder.getAction();
		imageView = holder.getDownload();		
		int color = mContext.getResources().getColor(R.color.single_app_background1);
		
		if (position % 2 == 0)
			imageView.setImageResource(R.drawable.single_app_download1);
		else {
			color = mContext.getResources().getColor(R.color.single_app_background2);
			imageView.setImageResource(R.drawable.single_app_download2);
		}
		
		convertView.setBackgroundColor(color);
		
		textView = holder.getText();
		switch (entityApp.getStatus()) {
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
		
		layout.setTag(entityApp);
		layout.setOnClickListener(mOnClick);
		
		imageView.setTag(entityApp);
		imageView.setOnClickListener(mOnClick);
				
		layout = holder.getLayout();
		layout.setTag(entityApp);
		layout.setOnClickListener(mOnClick);
		
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
		final int count = mListView.getHeaderViewsCount();
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
