package com.apps.game.market.views;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityAd;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.entity.app.EntityColumnClass;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestImage;
import com.apps.game.market.view.ScrollViewAd;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ViewColumn extends ViewBase {
	protected GlobalObject mGlobalObject = GlobalObject.globalObject;
	protected GlobalData mGlobalData = GlobalData.globalData;
	protected ViewGroup mParent;
	protected ViewGroup mLayoutColumn;
	protected EntityColumn mEntityColumn;
	protected boolean mInit = false;
	
	protected ViewColumn(Context context, ViewGroup parent, ViewGroup layoutColumn, EntityColumn entityColumn) {
		super(context);
		mParent = parent;
		mLayoutColumn = layoutColumn;
		mEntityColumn = entityColumn;
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
	
	protected boolean setClasses(ViewGroup parent, int current) {
		boolean classes = false;
		boolean single = mEntityColumn.getSingle();
		List<EntityColumnClass> columnClasses = mEntityColumn.getColumnClass();
		if (columnClasses != null) {
			classes = true;			
			int lastId = 0, position = 0;
			boolean first = true;
			LinearLayout selected = null;
			for (EntityColumnClass columnClass : columnClasses) {				
				String name = columnClass.getName();
				TextView textView = new TextView(mContext);				
				RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				relativeLayout.alignWithParent = true;
				relativeLayout.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
				if (first) {					
					int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_left_margins_left);
					int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_left_margins_top);
					int marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_left_margins_right);
					int marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_left_margins_bottom);
					relativeLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);					
				} else {					
					int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_right_margins_left);
					int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_right_margins_top);
					int marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_right_margins_right);
					int marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_right_margins_bottom);
					relativeLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);
					relativeLayout.addRule(RelativeLayout.RIGHT_OF, lastId);
				}
				
				textView.setLayoutParams(relativeLayout);
				textView.setText(name);
				textView.setTextColor(Color.WHITE);
				textView.setGravity(Gravity.BOTTOM);
				lastId = mGlobalData.getViewId();
				textView.setId(lastId);
				parent.addView(textView);
				first = false;
								
				Map<String, String> map = columnClass.getUrls();
				if (map != null) {
					for (Entry<String, String> entry : map.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						LinearLayout layout = new LinearLayout(mContext);
						int width = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_width);
						int height = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_height);
						relativeLayout = new RelativeLayout.LayoutParams(width, height);
						relativeLayout.alignWithParent = true;
						relativeLayout.addRule(RelativeLayout.RIGHT_OF, lastId);
						relativeLayout.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
						int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_margins_left);
						int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_margins_top);
						int marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_margins_right);
						int marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_tag_margins_bottom);
						relativeLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);
						layout.setLayoutParams(relativeLayout);
						layout.setOrientation(LinearLayout.VERTICAL);
						layout.setGravity(Gravity.BOTTOM);
						lastId = mGlobalData.getViewId();
						layout.setId(lastId);
												
						textView = new TextView(mContext);
						LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						linearLayout.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
						textView.setLayoutParams(linearLayout);
						textView.setText(key);
						textView.setTextColor(Color.WHITE);
						textView.setGravity(Gravity.BOTTOM);
						layout.addView(textView);
												
						if (position == current) {						
							selected = layout;
							layout.setBackgroundResource(R.drawable.column_selected);
						} else {
							parent.addView(layout);
							layout.setBackgroundResource(R.drawable.column_no_select);
						}
						
						position++;
					}
				}
			}
			
			if (!single) {
				LinearLayout top = new LinearLayout(mContext);				
				RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);			
				relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);							
				top.setLayoutParams(relativeLayout);
				top.setOrientation(LinearLayout.HORIZONTAL);
				
				LinearLayout layout = new LinearLayout(mContext);
				int height = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_height);
				LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height, 1.0f);
				int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_left_margins_left);
				int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_left_margins_top);
				int marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_left_margins_right);
				int marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_left_margins_bottom);
				linearLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);
				layout.setLayoutParams(linearLayout);
				layout.setBackgroundResource(R.color.double_app_top);
				top.addView(layout);
				
				layout = new LinearLayout(mContext);
				height = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_height);
				linearLayout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height, 1.0f);				
				marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_right_margins_left);
				marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_right_margins_top);
				marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_right_margins_right);
				marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_double_right_margins_bottom);
				linearLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);
				layout.setLayoutParams(linearLayout);
				layout.setBackgroundResource(R.color.double_app_top);
				top.addView(layout);
				parent.addView(top);				
			} else {
				LinearLayout layout = new LinearLayout(mContext);
				int height = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_single_height);
				RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, height);			
				relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				layout.setLayoutParams(relativeLayout);
				layout.setOrientation(LinearLayout.VERTICAL);			
				layout.setBackgroundResource(R.color.single_app_line);
				parent.addView(layout);
			}						
			
			if (selected != null)
				parent.addView(selected);
		} else {
			String desc = mEntityColumn.getDesc();
			if (desc != null) {
				classes = true;
				TextView textView = new TextView(mContext);
				textView.setId(mGlobalData.getViewId());
				RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				relativeLayout.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
				int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_desc_margins_left);
				int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_desc_margins_top);
				int marginRight = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_desc_margins_right);
				int marginBottom = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_desc_margins_bottom);
				relativeLayout.setMargins(marginLeft, marginTop, marginRight, marginBottom);
				textView.setLayoutParams(relativeLayout);
				textView.setText(desc);
				textView.setTextColor(Color.WHITE);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				parent.addView(textView);
				
				LinearLayout layout = new LinearLayout(mContext);
				int height = mContext.getResources().getDimensionPixelSize(R.dimen.subcolumn_single_height);
				relativeLayout = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, height);			
				relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				layout.setLayoutParams(relativeLayout);
				layout.setOrientation(LinearLayout.VERTICAL);			
				layout.setBackgroundResource(R.color.single_app_line);
				parent.addView(layout);
			}	
		}
		
		return classes;
	}
	
	protected void setAds(ScrollViewAd scrollViewAd, List<EntityAd> ads) {
		for (EntityAd entityAd : ads) {
			ImageView imageView = new ImageView(mContext);
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));				
			entityAd.setImageView(imageView);			
			RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
			requestImage.request();
			scrollViewAd.putView(imageView, false);
		}
		
		if (ads.size() == 1) {
			for (int i = 0;i < 2;i++) {
				EntityAd entityAd = ads.get(0);
				ImageView imageView = new ImageView(mContext);		
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));
				entityAd.setImageView(imageView);			
				RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
				requestImage.request();
				scrollViewAd.putView(imageView, true);					
			}				
		}
		
		if (ads.size() == 2) {
			for (int i = 0;i < 2;i++) {
				EntityAd entityAd = ads.get(i);
				ImageView imageView = new ImageView(mContext);		
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL));
				entityAd.setImageView(imageView);			
				RequestImage requestImage = new RequestImage(entityAd.getUrl(), imageView, false);
				requestImage.request();
				scrollViewAd.putView(imageView, true);
			}				
		}
	}
	
	protected abstract void onInit();
	protected abstract void onRefresh();
	protected abstract void onStop();
}
