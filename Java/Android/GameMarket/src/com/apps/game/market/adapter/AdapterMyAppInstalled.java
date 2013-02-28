package com.apps.game.market.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityAppInfo;
import com.apps.game.market.view.PopWindowMyAppRun;
import com.apps.game.market.viewholder.ViewHolderMyAppInstalled;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class AdapterMyAppInstalled extends BaseAdapter implements OnClickListener, OnScrollListener {
	private final List<EntityAppInfo> mList;
	private final ListView mListView;
	private final LayoutInflater mInflater;
	private final Context mContext;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private final String mSize;
	private final String mVersion;
	
	public AdapterMyAppInstalled(Context context, ListView listView, List<EntityAppInfo> list) {
		mContext = context;
		mListView = listView;
		mInflater = LayoutInflater.from(context);
		mList = list;
		mListView.setOnScrollListener(this);
		mSize = mContext.getString(R.string.myapp_size);
		mVersion = mContext.getString(R.string.myapp_version);
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
		ViewHolderMyAppInstalled holder = null;
		if (convertView == null) {
			holder = new ViewHolderMyAppInstalled();
			convertView = mInflater.inflate(R.layout.myapp_installed, parent, false);
			holder.setLayout1((LinearLayout) convertView.findViewById(R.id.myapp_layout1));
			holder.setLayout2((LinearLayout) convertView.findViewById(R.id.myapp_layout2));			
			holder.setIcon1((ImageView) convertView.findViewById(R.id.myapp_icon1));
			holder.setIcon2((ImageView) convertView.findViewById(R.id.myapp_icon2));
			holder.setName1((TextView) convertView.findViewById(R.id.myapp_name1));
			holder.setName2((TextView) convertView.findViewById(R.id.myapp_name2));
			holder.setSize1((TextView) convertView.findViewById(R.id.myapp_size1));
			holder.setSize2((TextView) convertView.findViewById(R.id.myapp_size2));
			holder.setVersion1((TextView) convertView.findViewById(R.id.myapp_version1));
			holder.setVersion2((TextView) convertView.findViewById(R.id.myapp_version2));
			convertView.setTag(holder);
		} else			
			holder = (ViewHolderMyAppInstalled) convertView.getTag();
		
		EntityAppInfo entityAppInfo1 = mList.get(position);
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
		double d = (double)size / 1024d / 1024d;
		holder.getSize1().setText(mSize + " " + mFormat.format(d) + "M");
		holder.getVersion1().setText(mVersion + " " + entityAppInfo1.getVersionName());
		
		LinearLayout layout1 = holder.getLayout1();
		layout1.setTag(entityAppInfo1);
		layout1.setOnClickListener(this);
		
		if (entityAppInfo2 != null) {
			drawable = entityAppInfo2.getAppIcon();
			if (drawable == null)
				holder.getIcon2().setImageResource(R.drawable.ic_launcher);
			else
				holder.getIcon2().setImageDrawable(drawable);
			
			name = entityAppInfo2.getAppName();
			holder.getName2().setText(name);
			
			size = entityAppInfo2.getSize();
			d = (double)size / 1024d / 1024d;
			holder.getSize2().setText(mSize + " " + mFormat.format(d) + "M");
			holder.getVersion2().setText(mVersion + " " + entityAppInfo2.getVersionName());
									
			LinearLayout layout2 = holder.getLayout2();
			layout2.setTag(entityAppInfo2);
			layout2.setOnClickListener(this);							
						
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
	public void onClick(View view) {
		final int id = view.getId();
		final Object object = view.getTag();
		if (id == R.id.myapp_layout1 || id == R.id.myapp_layout2 && object != null) {
			final EntityAppInfo entityAppInfo = (EntityAppInfo) object;
			final PopWindowMyAppRun pop = new PopWindowMyAppRun(mContext, entityAppInfo);
			pop.show();			
		}
	}	
	
	public void refresh() {
		notifyDataSetChanged();		
	}
}
