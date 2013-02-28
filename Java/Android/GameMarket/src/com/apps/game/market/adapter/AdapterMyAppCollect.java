package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderMyAppCollect;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class AdapterMyAppCollect extends BaseAdapter implements OnClickListener, OnScrollListener {
	private final GlobalObject mGlobalObject = GlobalObject.globalObject;
	private final GlobalData mGlobalData = GlobalData.globalData;
	private final ListView mListView;
	private final LayoutInflater mInflater;
	private final List<EntityApp> mList;
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private final OnClickListener mOnClick;
		
	public AdapterMyAppCollect(Context context, ListView listView, List<EntityApp> list, OnClickListener onClick) {
		mContext = context;
		mListView = listView;
		mList = list;
		mOnClick = onClick;
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
		ViewHolderMyAppCollect holder = null;
		if (convertView == null) {
			holder = new ViewHolderMyAppCollect();
			convertView = mInflater.inflate(R.layout.myapp_collect, parent, false);	
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_collect_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_collect_layout2));
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_collect_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_collect_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_collect_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_collect_name2));
			holder.setPrice1((TextView) convertView.findViewById(R.id.myapp_collect_price1));
			holder.setPrice2((TextView) convertView.findViewById(R.id.myapp_collect_price2));
			holder.setDel1((FrameLayout) convertView.findViewById(R.id.myapp_collect_del1));
			holder.setDel2((FrameLayout) convertView.findViewById(R.id.myapp_collect_del2));
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_collect_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_collect_size2));
			holder.setAction1((TextView) convertView.findViewById(R.id.myapp_collect_action1));
			holder.setAction2((TextView) convertView.findViewById(R.id.myapp_collect_action2));
			convertView.setTag(holder);						
		} else
			holder = (ViewHolderMyAppCollect) convertView.getTag();		
								
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
		
		imageView.setTag(entityApp1);
		imageView.setOnClickListener(mOnClick);
		
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
		
		long size = entityApp1.getSize();
		double d = (double)size / 1024d / 1024d;
		holder.getSize1().setText(mFormat.format(d) + "M");
		
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
				
		FrameLayout frameLayout = holder.getDel1();
		frameLayout.setTag(entityApp1);
		frameLayout.setOnClickListener(this);				
				
		if (entityApp2 != null) {
			imageView = holder.getIcon2();
			text = entityApp2.getIcon();
			mTasks.setUrl(text, imageView);
			
			bitmap = mImageCache.get(text);
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else							
				imageView.setImageResource(R.drawable.ic_launcher);
			
			imageView.setTag(entityApp2);
			imageView.setOnClickListener(mOnClick);
			
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
			
			size = entityApp2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getSize2().setText(mFormat.format(d) + "M");
			
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
					
			frameLayout = holder.getDel2();
			frameLayout.setTag(entityApp2);
			frameLayout.setOnClickListener(this);						
			
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
		int id = v.getId();
		Object obj = v.getTag();								
		if ((id == R.id.myapp_collect_del1 || id == R.id.myapp_collect_del2) && obj != null && obj instanceof EntityApp) {							
			final EntityApp entityApp = (EntityApp) obj;
			final AlertDialog.Builder builder = new Builder(mContext);
			final String name = entityApp.getName();
			final String msg = mContext.getString(R.string.dialog_myapp_collect_del);
			final String confirm = mContext.getString(R.string.dialog_confirm);
			final String cancel = mContext.getString(R.string.dialog_cancel);
			builder.setMessage(msg);
			builder.setTitle(name);
			builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();						
					mGlobalData.removeCollectApps(entityApp);					
					Toast.makeText(mContext, R.string.tip_myapp_collect_del_success, Toast.LENGTH_LONG).show();					
					notifyDataSetChanged();
				}
			});

			builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
							
			builder.create().show();
			return;
		}
	}
	
	public void setAppStatus(EntityApp entityApp) {
		notifyDataSetChanged();
	}
}
