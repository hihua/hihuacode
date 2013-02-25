package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityDetail;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackApp;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderSingleApp;

public class AdapterSingleApp extends BaseAdapter implements OnClickListener, OnScrollListener, RequestCallBackApp {
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
		
	public AdapterSingleApp(Context context, ListView listView, RequestApp requestApp) {
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
		String url = entityApp.getIcon();
		mTasks.setUrl(url, holder.getIcon());
		
		Bitmap bitmap = mImageCache.get(url);
		if (bitmap != null)
			holder.getIcon().setImageBitmap(bitmap);
		else							
			holder.getIcon().setImageResource(R.drawable.ic_launcher);
		
		String name = entityApp.getName();			
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
