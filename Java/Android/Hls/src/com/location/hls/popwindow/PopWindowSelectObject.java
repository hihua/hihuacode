package com.location.hls.popwindow;

import java.util.List;

import com.location.hls.R;
import com.location.hls.entity.EntityRelation;
import com.location.hls.entity.EntitySelect;
import com.location.hls.viewholder.ViewHolderSelectItem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopWindowSelectObject {
	private final Context mContext;
	private final LayoutInflater mInflater;
	private LinearLayout mRoot;
	private ListView mListView;
	private final EntityRelation mEntityRelation;
	private final List<EntitySelect> mEntitySelects;
	private final OnClickListener mOnClick;	
	private PopupWindow mPop;
	
	public PopWindowSelectObject(final Context context, final EntityRelation entityRelation, final List<EntitySelect> entitySelects, final OnClickListener onClick) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mEntityRelation = entityRelation;
		mEntitySelects = entitySelects;
		mOnClick = onClick;		
		setView();
	}
	
	private void setView() {
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_select_object, null, false);
		mListView = (ListView) mRoot.findViewById(R.id.select_object_lv);		
		mListView.setAdapter(new SelectItem(mContext, mEntityRelation, mEntitySelects, mOnClick));
		
		mPop = new PopupWindow(mRoot, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);		
		mPop.setBackgroundDrawable(new ColorDrawable(0x00000000));		
		mPop.update();
	}
	
	public void show() {		
		mPop.showAtLocation(mRoot, Gravity.CENTER, 0, 0);
	}
	
	public void close() {		
		if (mPop != null && mPop.isShowing()) {
			mPop.dismiss();
			mPop = null;
		}
	}
	
	class SelectItem extends BaseAdapter implements OnClickListener {
		private final LayoutInflater mInflater;
		private final EntityRelation mRelation;
		private final EntityRelation mEntityRelation;
		private final List<EntitySelect> mEntitySelects;
		private final OnClickListener mOnClick;
		
		SelectItem(final Context context, final EntityRelation entityRelation, final List<EntitySelect> entitySelects, final OnClickListener onClick) {
			mInflater = LayoutInflater.from(context);
			mEntityRelation = entityRelation;			
			mEntitySelects = entitySelects;
			mOnClick = onClick;
			mRelation = EntityRelation.getRelation();
		}

		@Override
		public int getCount() {			
			return mEntitySelects.size();
		}

		@Override
		public Object getItem(final int position) {
			return mEntitySelects.get(position);			
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			ViewHolderSelectItem holder = null;
			if (convertView == null) {
				holder = new ViewHolderSelectItem();
				convertView = mInflater.inflate(R.layout.popwindow_select_object_item, parent, false);
				holder.setRelativeLayout((RelativeLayout) convertView.findViewById(R.id.rl_item));
				holder.setTextView((TextView) convertView.findViewById(R.id.tv_name));
				holder.setRadioButton((RadioButton) convertView.findViewById(R.id.rb_selected));
				convertView.setTag(holder);
			} else
				holder = (ViewHolderSelectItem) convertView.getTag();
			
			final RelativeLayout relativeLayout = holder.getRelativeLayout();
			final TextView textView = holder.getTextView();
			final RadioButton radioButton = holder.getRadioButton();
			
			final EntitySelect entitySelect = mEntitySelects.get(position);
			final String name = entitySelect.getName();
			final String color = entitySelect.getColor();
			String to = null;
			
			if (mRelation != null)
				to = mRelation.getTo();
			
			textView.setText(name);
			textView.setTextColor(Color.parseColor(color));
			
			if (to != null && to.equals(name))
				radioButton.setChecked(true);
			else
				radioButton.setChecked(false);
			
			relativeLayout.setOnClickListener(mOnClick);
			relativeLayout.setTag(entitySelect);
			
			radioButton.setOnClickListener(this);
			radioButton.setTag(entitySelect);
			return convertView;
		}
		
		@Override
		public void onClick(final View view) {
			if (mEntityRelation != null && mRelation != null) {
				final RadioButton radioButton = (RadioButton) view;
				if (radioButton.isChecked()) {
					final Object object = view.getTag();
					final EntitySelect entitySelect = (EntitySelect) object;
					final String name = entitySelect.getName();
					mEntityRelation.setTo(name);
					mRelation.setTo(name);					
					EntityRelation.writeRelation(mEntityRelation);
					notifyDataSetChanged();
				}	
			}					
		}		
	}
}
