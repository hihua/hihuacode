package com.apps.game.market.adapter;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityDetail;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.task.TaskDownload;
import com.apps.game.market.task.TaskImage;
import com.apps.game.market.util.FileManager;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderMyAppDownload;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AdapterMyAppDownload extends BaseAdapter implements OnClickListener, OnScrollListener {
	private final GlobalObject mGlobalObject = GlobalObject.globalObject;
	private final GlobalData mGlobalData = GlobalData.globalData;
	private final ListView mListView;
	private final LayoutInflater mInflater;
	private final List<EntityApp> mList;
	private final TaskImage mTasks = new TaskImage();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	public AdapterMyAppDownload(Context context, ListView listView, List<EntityApp> list) {
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
		ViewHolderMyAppDownload holder = null;
		if (convertView == null) {			
			holder = new ViewHolderMyAppDownload();
			convertView = mInflater.inflate(R.layout.myapp_download, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_download_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_download_layout2));			
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_download_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_download_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_download_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_download_name2));
			holder.setDel1((FrameLayout) convertView.findViewById(R.id.myapp_download_del1));
			holder.setDel2((FrameLayout) convertView.findViewById(R.id.myapp_download_del2));
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_download_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_download_size2));
			holder.setInstall1((TextView) convertView.findViewById(R.id.myapp_download_install1));
			holder.setInstall2((TextView) convertView.findViewById(R.id.myapp_download_install2));
			convertView.setTag(holder);						
		} else			
			holder = (ViewHolderMyAppDownload) convertView.getTag();		
								
		EntityApp entityApp1 = mList.get(position * 2);
		EntityApp entityApp2 = null;
		if (position * 2 + 1 < mList.size())
			entityApp2 = mList.get(position * 2 + 1);
				
		ImageView imageView = holder.getIcon1();
		String icon = entityApp1.getIcon();
		mTasks.setUrl(icon, imageView);
		
		Bitmap bitmap = mImageCache.get(icon);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else							
			imageView.setImageResource(R.drawable.ic_launcher);
		
		imageView.setTag(entityApp1);
		imageView.setOnClickListener(this);
		
		TextView textView = holder.getName1();
		String name = entityApp1.getName();		
		textView.setText(name);
		textView.setTag(entityApp1);
		textView.setOnClickListener(this);
		
		long size = entityApp1.getSize();
		double d = (double)size / 1024d / 1024d;
		holder.getSize1().setText(mFormat.format(d) + "M");
		
		textView = holder.getInstall1();
		if (entityApp1.getStatus() == EnumAppStatus.INSTALLED)
			textView.setText(R.string.myapp_reinstall);			
		else
			textView.setText(R.string.myapp_install);
				
		textView.setTag(entityApp1);
		textView.setOnClickListener(this);
		FrameLayout frameLayout = holder.getDel1();
		frameLayout.setTag(entityApp1);
		frameLayout.setOnClickListener(this);
		
		LinearLayout layout = holder.getLayout1();
		layout.setTag(entityApp1);
		layout.setOnClickListener(this);
		
		if (entityApp2 != null) {
			imageView = holder.getIcon2();
			icon = entityApp2.getIcon();
			mTasks.setUrl(icon, imageView);
			
			bitmap = mImageCache.get(icon);
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else							
				imageView.setImageResource(R.drawable.ic_launcher);
			
			imageView.setTag(entityApp2);
			imageView.setOnClickListener(this);
			
			textView = holder.getName2();
			name = entityApp2.getName();
			textView.setText(name);
			textView.setTag(entityApp2);
			textView.setOnClickListener(this);
									
			size = entityApp2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getSize2().setText(mFormat.format(d) + "M");
			textView = holder.getInstall2();
						
			if (entityApp2.getStatus() == EnumAppStatus.INSTALLED)
				textView.setText(R.string.myapp_reinstall);
			else
				textView.setText(R.string.myapp_install);
					
			textView.setTag(entityApp2);
			textView.setOnClickListener(this);
			frameLayout = holder.getDel2();
			frameLayout.setTag(entityApp2);
			frameLayout.setOnClickListener(this);
			
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
		int id = v.getId();
		Object obj = v.getTag();
		if ((id == R.id.myapp_download_icon1 || id == R.id.myapp_download_icon2) && obj != null && obj instanceof EntityApp) {
			final EntityApp entityApp = (EntityApp) obj;
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(mContext, ActivityDetail.class); 
	        mContext.startActivity(intent);
	        return;
		}
		
		if ((id == R.id.myapp_download_name1 || id == R.id.myapp_download_name2) && obj != null && obj instanceof EntityApp) {
			final EntityApp entityApp = (EntityApp) obj;
			GlobalData globalData = GlobalData.globalData;
			globalData.setSelectApp(entityApp);
			Intent intent = new Intent(mContext, ActivityDetail.class); 
	        mContext.startActivity(intent);
	        return;
		}
		
		if ((id == R.id.myapp_download_del1 || id == R.id.myapp_download_del2) && obj != null && obj instanceof EntityApp) {							
			final EntityApp entityApp = (EntityApp) obj;						
			final AlertDialog.Builder builder = new Builder(mContext);
			final String name = entityApp.getName();
			final String msg = mContext.getString(R.string.dialog_myapp_download_del);
			final String confirm = mContext.getString(R.string.dialog_confirm);
			final String cancel = mContext.getString(R.string.dialog_cancel);
			builder.setMessage(msg);
			builder.setTitle(name);
			builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();						
					mGlobalData.removeDownloadApps(entityApp);					
					final String packageName = entityApp.getPackageName();
					boolean success = false;
					if (packageName != null) {
						FileManager fileManager = mGlobalObject.getFileManager();
						File file = new File(fileManager.getAppsPath(), packageName + ".apk");
						if (file.exists() && file.isFile() && file.delete())
							success = true;					
					}
					
					if (success)
						Toast.makeText(mContext, R.string.tip_myapp_download_del_success, Toast.LENGTH_LONG).show();
					else
						Toast.makeText(mContext, R.string.tip_myapp_download_del_error, Toast.LENGTH_LONG).show();
					
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
		
		if ((id == R.id.myapp_download_install1 || id == R.id.myapp_download_install2) && obj != null && obj instanceof EntityApp) {							
			final EntityApp entityApp = (EntityApp) obj;
			final AlertDialog.Builder builder = new Builder(mContext);
			final String name = entityApp.getName();
			String msg = mContext.getString(R.string.dialog_myapp_download_install);
			final EnumAppStatus status = entityApp.getStatus();
			if (status == EnumAppStatus.INSTALLED)
				msg = mContext.getString(R.string.dialog_myapp_download_reinstall);
			
			final String confirm = mContext.getString(R.string.dialog_confirm);
			final String cancel = mContext.getString(R.string.dialog_cancel);
			builder.setMessage(msg);
			builder.setTitle(name);
			builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					final TaskDownload taskDownload = mGlobalObject.getTaskDownload();
					if (!taskDownload.installApp(mContext, entityApp))
						setAppStatus(entityApp);				
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
