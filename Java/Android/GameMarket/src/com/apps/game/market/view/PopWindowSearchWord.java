package com.apps.game.market.view;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntitySearchWord;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopWindowSearchWord implements OnClickListener {	
	private final Context mContext;
	private final LayoutInflater mInflater;
	private PopupWindow mPop;
	private LinearLayout mRoot;
	private final EditText mView;

	public PopWindowSearchWord(Context context, EditText view) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mView = view;
		setView();
	}
	
	private void setView() {		
		mRoot = (LinearLayout) mInflater.inflate(R.layout.popwindow_searchword, null);
		mRoot.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {				
				if (mPop != null && mPop.isShowing()) {
					mPop.dismiss();
					mPop = null;
				}
				
				return false;
			}
		});
	}
	
	public void setList(List<EntitySearchWord> list) {
		boolean compare = false;
		if (list != null && list.size() == 1) {
			Editable editable = mView.getText();
			EntitySearchWord searchWord = list.get(0);
			if (editable != null && searchWord.getHotWords() != null) {
				String s = editable.toString();
				String hotWords = searchWord.getHotWords();
				if (hotWords.equals(s))
					compare = true;
			}
		}
					
		if (!compare && list != null && list.size() > 0) {
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
			
			int marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_margins_left);
			int marginTop = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_tag_more_item_margins_top);
			int marginButtom = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_margins_bottom);
			
			mRoot.removeAllViews();
			for (EntitySearchWord searchWord : list) {
				String hostWords = searchWord.getHotWords();
				if (hostWords == null || hostWords.length() == 0)
					continue;
				
				long resultNumber = searchWord.getResultNumber();
				String text = hostWords + "(" + resultNumber + ")";
				LinearLayout layout = new LinearLayout(mContext);
				layout.setOrientation(LinearLayout.VERTICAL);
				TextView textView = new TextView(mContext);				
				LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				linearParams.leftMargin = marginLeft;
				linearParams.topMargin = marginTop;
				linearParams.bottomMargin = marginButtom;
				layout.setLayoutParams(linearParams);
				textView.setText(text);
				textView.setTextColor(Color.BLACK);
				textView.setSingleLine(true);
				layout.setTag(searchWord);				
				layout.setClickable(true);
				layout.setOnClickListener(this);
				layout.addView(textView);
				mRoot.addView(layout);
			}
			
			int width = mView.getWidth();			
			mPop = new PopupWindow(mRoot, width, LayoutParams.WRAP_CONTENT, true);
			mPop.setOutsideTouchable(false);
			mPop.showAsDropDown(mView, 0, 1);			
		} else {
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
		}
	}
		
	@Override
	public void onClick(View view) {		
		Object obj = view.getTag();
		if (view instanceof LinearLayout && obj != null && obj instanceof EntitySearchWord) {						
			EntitySearchWord searchWord = (EntitySearchWord) obj;			
			mView.setText(searchWord.getHotWords());
			
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
		}
	}
}
