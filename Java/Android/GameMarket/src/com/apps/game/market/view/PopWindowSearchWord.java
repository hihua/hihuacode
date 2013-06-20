package com.apps.game.market.view;

import java.util.List;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntitySearchWord;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
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
			final Editable editable = mView.getText();
			final EntitySearchWord searchWord = list.get(0);
			if (editable != null && searchWord.getHotWords() != null) {
				final String s = editable.toString();
				final String hotWords = searchWord.getHotWords();
				if (hotWords.equals(s))
					compare = true;
			}
		}
					
		if (!compare && list != null && list.size() > 0) {
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
			
			final int left = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_padding_left);
			final int right = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_padding_right);
			final int top = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_padding_top);
			final int bottom = mContext.getResources().getDimensionPixelSize(R.dimen.popwindow_searchword_padding_bottom);
			
			mRoot.removeAllViews();
			boolean flag = true;
			for (EntitySearchWord searchWord : list) {
				final String hostWords = searchWord.getHotWords();
				if (hostWords == null || hostWords.length() == 0)
					continue;
				
				final long resultNumber = searchWord.getResultNumber();				
				String text = hostWords + "(" + resultNumber + ")";
				int color = mContext.getResources().getColor(R.color.search_text);
				
				final LinearLayout linearLayout = new LinearLayout(mContext);
				final LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);			
				linearLayout.setLayoutParams(linearParams);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				
				final FrameLayout frameLayout = new FrameLayout(mContext);
				frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				frameLayout.setPadding(left, top, right, bottom);
				
				final FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				frameParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
								
				final TextView textView = new TextView(mContext);			
				textView.setLayoutParams(frameParams);		
				textView.setText(text);
				textView.setTextColor(color);
				textView.setSingleLine(true);
				textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
				
				frameLayout.setTag(searchWord);
				frameLayout.setClickable(true);
				frameLayout.setOnClickListener(this);			
				frameLayout.addView(textView);
				
				if (!flag) {
					color = mContext.getResources().getColor(R.color.search_line);						
					final LinearLayout line = new LinearLayout(mContext);
					line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 1));
					line.setBackgroundColor(color);			
					line.setOrientation(LinearLayout.VERTICAL);
					linearLayout.addView(line);					
				}
				
				flag = false;								
				linearLayout.addView(frameLayout);			
				mRoot.addView(linearLayout);				
			}
			
			final int width = mView.getWidth();			
			mPop = new PopupWindow(mRoot, width, LayoutParams.WRAP_CONTENT, true);
			mPop.setOutsideTouchable(false);
			mPop.showAsDropDown(mView, 0, 0);			
		} else {
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
		}
	}
		
	@Override
	public void onClick(View v) {		
		final Object obj = v.getTag();
		if (v instanceof FrameLayout && obj != null && obj instanceof EntitySearchWord) {						
			final EntitySearchWord searchWord = (EntitySearchWord) obj;			
			mView.setText(searchWord.getHotWords());
			
			if (mPop != null && mPop.isShowing()) {
				mPop.dismiss();
				mPop = null;
			}
		}
	}
}
