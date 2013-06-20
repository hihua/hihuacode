package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.viewholder.ViewHolderMyAppInstalled;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AdapterMyAppInstalled extends AdapterBase {
	private final List<EntityAppInfo> mList;	
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
		
	public AdapterMyAppInstalled(Context context, ListView listView, OnClickListener onClick, List<EntityAppInfo> list) {
		super(context, listView, onClick);
		mListView.setOnScrollListener(this);
		mList = list;		
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
		ViewHolderMyAppInstalled holder = null;
		if (convertView == null) {
			holder = new ViewHolderMyAppInstalled();
			convertView = mInflater.inflate(R.layout.myapp_installed, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_installed_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_installed_layout2));
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_installed_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_installed_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_installed_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_installed_name2));
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_installed_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_installed_size2));
			holder.setVersion1((TextView) convertView.findViewById(R.id.myapp_installed_version1));
			holder.setVersion2((TextView) convertView.findViewById(R.id.myapp_installed_version2));
			holder.setAction1((LinearLayout) convertView.findViewById(R.id.myapp_installed_action1));
			holder.setAction2((LinearLayout) convertView.findViewById(R.id.myapp_installed_action2));
			convertView.setTag(holder);
		} else			
			holder = (ViewHolderMyAppInstalled) convertView.getTag();
		
		final EntityAppInfo entityAppInfo1 = mList.get(position * 2);
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
		double d = (double) size / 1024d / 1024d;
		holder.getSize1().setText(mFormat.format(d) + "M");
		holder.getVersion1().setText(entityAppInfo1.getVersionName());
		
		LinearLayout layout = holder.getAction1();
		layout.setTag(entityAppInfo1);
		layout.setOnClickListener(mOnClick);			
		
		if (entityAppInfo2 != null) {
			drawable = entityAppInfo2.getAppIcon();
			if (drawable == null)
				holder.getIcon2().setImageResource(R.drawable.ic_launcher);
			else
				holder.getIcon2().setImageDrawable(drawable);
			
			name = entityAppInfo2.getAppName();
			holder.getName2().setText(name);
			
			size = entityAppInfo2.getSize();
			d = (double) size / 1024d / 1024d;
			holder.getSize2().setText(mFormat.format(d) + "M");
			holder.getVersion2().setText(entityAppInfo2.getVersionName());
									
			layout = holder.getAction2();
			layout.setTag(entityAppInfo2);
			layout.setOnClickListener(mOnClick);							
						
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
	public void refresh() {
		notifyDataSetChanged();		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void setAppStatus(EntityApp entityApp) {
		
	}
}
