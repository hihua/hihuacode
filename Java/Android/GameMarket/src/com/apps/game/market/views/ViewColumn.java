package com.apps.game.market.views;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityColumnClass;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestImage;
import com.apps.game.market.request.app.RequestApp;
import com.apps.game.market.request.callback.RequestCallBackInfo;
import com.apps.game.market.view.PopWindowColumnSelect;
import com.apps.game.market.view.ScrollViewAd;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ViewColumn extends ViewBase implements OnClickListener {
	protected GlobalObject mGlobalObject = GlobalObject.globalObject;
	protected GlobalData mGlobalData = GlobalData.globalData;
	protected ViewPager mParent;
	protected ViewGroup mLayoutColumn;
	protected EntityColumn mEntityColumn;
	protected RequestCallBackInfo mCallBack;
	private List<TextView> mAdText;
	private int mSelect = 0;
	protected boolean mInit = false;
	
	protected ViewColumn(Context context, ViewPager parent, ViewGroup layoutColumn, EntityColumn entityColumn, RequestCallBackInfo callBack) {
		super(context);
		mParent = parent;
		mLayoutColumn = layoutColumn;
		mEntityColumn = entityColumn;
		mCallBack = callBack;
	}
	
	public void init() {
		onInit();
		mInit = true;
	}
	
	public void refresh() {
		onRefresh();
	}
	
	public void startScroll() {
		
	}
	
	public void stopScroll() {
		
	}
	
	public void stop() {
		
	}
	
	public ViewGroup getViewColumn() {
		return mLayoutColumn;
	}
		
	public void setHighlight(boolean highlight) {
		if (highlight)
			mLayoutColumn.setBackgroundResource(R.drawable.column_corner);			
		else
			mLayoutColumn.setBackgroundResource(android.R.color.transparent);
	}
	
	protected boolean setSubColumn(final ViewGroup parent, int current) {
		final String desc = mEntityColumn.getDesc();
		if (desc == null)
			return false;
		
		List<EntityColumnClass> columnClasses = mEntityColumn.getColumnClass();
		if (columnClasses != null) {
			final EntityColumnClass entityColumnClass = columnClasses.get(mSelect);
			String name = entityColumnClass.getName();
			
			final int width = mContext.getResources().getDimensionPixelSize(R.dimen.column_select_width);
			
			final RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
			relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			relativeLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
			
			final LinearLayout layout = new LinearLayout(mContext);
			layout.setId(R.id.column_select_id);
			layout.setTag(columnClasses);
			layout.setLayoutParams(relativeLayout);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setClickable(true);
			layout.setOnClickListener(this);
			int color = mContext.getResources().getColor(R.color.column_select_background);
			layout.setBackgroundColor(color);
			layout.setGravity(Gravity.CENTER_VERTICAL);
			
			int left = mContext.getResources().getDimensionPixelSize(R.dimen.column_select_margin_left);
			int top = mContext.getResources().getDimensionPixelSize(R.dimen.column_select_margin_top);
			int right = mContext.getResources().getDimensionPixelSize(R.dimen.column_select_margin_right);
			int bottom = mContext.getResources().getDimensionPixelSize(R.dimen.column_select_margin_bottom);
			
			LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			linearLayout.setMargins(left, top, right, bottom);
			
			TextView textView = new TextView(mContext);
			textView.setId(R.id.column_select_text_id);
			textView.setLayoutParams(linearLayout);
			textView.setText(name);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f);
			color = mContext.getResources().getColor(R.color.column_ad_text);
			textView.setTextColor(color);
			layout.addView(textView);
									
			name = mContext.getString(R.string.column_select);
			
			left = mContext.getResources().getDimensionPixelSize(R.dimen.column_dropdown_margin_left);
			top = mContext.getResources().getDimensionPixelSize(R.dimen.column_dropdown_margin_top);
			right = mContext.getResources().getDimensionPixelSize(R.dimen.column_dropdown_margin_right);
			bottom = mContext.getResources().getDimensionPixelSize(R.dimen.column_dropdown_margin_bottom);
			
			linearLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			linearLayout.setMargins(left, top, right, bottom);
			
			textView = new TextView(mContext);
			textView.setLayoutParams(linearLayout);
			textView.setText(name);
			color = mContext.getResources().getColor(R.color.column_selected_background);
			textView.setTextColor(color);
			layout.addView(textView);
			parent.addView(layout);
		}
		
		final RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		relativeLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
		final TextView textView = new TextView(mContext);
		textView.setLayoutParams(relativeLayout);
		textView.setText(desc);
		final int color = mContext.getResources().getColor(R.color.column_ad_text);
		textView.setTextColor(color);
		parent.addView(textView);			
		return true;
	}		
		
	protected void setAds(ScrollViewAd scrollViewAd, List<EntityAd> ads) {
		for (EntityAd entityAd : ads) {
			final ImageView imageView = new ImageView(mContext);
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));				
			entityAd.setImageView(imageView);			
			final RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
			requestImage.request();
			scrollViewAd.putView(imageView, false);
		}
		
		if (ads.size() == 1) {
			for (int i = 0;i < 2;i++) {
				final EntityAd entityAd = ads.get(0);
				final ImageView imageView = new ImageView(mContext);		
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));
				entityAd.setImageView(imageView);			
				final RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
				requestImage.request();
				scrollViewAd.putView(imageView, true);					
			}				
		}
		
		if (ads.size() == 2) {
			for (int i = 0;i < 2;i++) {
				final EntityAd entityAd = ads.get(i);
				final ImageView imageView = new ImageView(mContext);		
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));
				entityAd.setImageView(imageView);			
				final RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
				requestImage.request();
				scrollViewAd.putView(imageView, true);
			}				
		}
	}
	
	protected void setAdText(final ViewGroup parent, final List<EntityAd> ads) {
		final int count = ads.size();
		mAdText = new Vector<TextView>();
		
		final String text = mContext.getString(R.string.ad_text);
		for (int i = 0;i < count;i++) {
			final TextView textView = new TextView(mContext);
			int color = mContext.getResources().getColor(R.color.ad_scroll);
			if (i == 0)
				color = mContext.getResources().getColor(R.color.ad_scroll_select);
			
			textView.setTextColor(color);
			textView.setText(text);
			mAdText.add(textView);
			parent.addView(textView);
		}
	}
	
	protected void setAdText(final int screen, final List<EntityAd> ads) {
		final int count = ads.size();
		for (int i = 0;i < count;i++) {						
			int color = mContext.getResources().getColor(R.color.ad_scroll);
			if (i == screen)
				color = mContext.getResources().getColor(R.color.ad_scroll_select);
			
			final TextView textView = mAdText.get(i);
			textView.setTextColor(color);			
		}
	}
			
	public void onAppStatus(EntityApp entityApp) {
		
	}
			
	public void onSubColumn(final RequestApp request) {
		
	}
	
	public void setSubColumn(final View parent, final int select, final List<EntityColumnClass> list) {
		final EntityColumnClass entityColumnClass = list.get(select);
		final String name = entityColumnClass.getName();
		final RequestApp request = entityColumnClass.getRequest();
		
		final View view = parent.findViewById(R.id.column_select_text_id);
		if (view != null) {
			TextView textView = (TextView) view;
			textView.setText(name);
		}
		
		mSelect = select;
		onSubColumn(request);
	}	
	
	@Override
	public void onClick(View v) {
		final Object obj = v.getTag();
		if (obj != null) {
			final List<EntityColumnClass> list = (List<EntityColumnClass>) obj;
			final PopWindowColumnSelect pop = new PopWindowColumnSelect(mContext, this, list, mSelect, v);
			pop.setList();
		}
	}

	protected abstract void onInit();
	protected abstract void onRefresh();
	protected abstract void onStop();	
}
