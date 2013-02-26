package com.apps.game.market.view;

import java.util.Timer;
import java.util.TimerTask;

import com.apps.game.market.view.callback.ScrollViewAdCallBack;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

public class ScrollViewAd extends ViewGroup {	
	private Scroller mScroller;	
	private VelocityTracker mVelocityTracker;
	private int mScreen = 0;
	private float mLastX = 0.0f;	
	private float mTouchX = 0.0f;	
	private ViewGroup mParent;
	private ListView mListView;	
	private Timer mTimer;
	private int mFake = 0;
	private ScrollViewAdCallBack mCallBack;
	private final Handler mHandle = new Handler() {
		@Override  
        public void handleMessage(Message msg) {
			toScroll(true, true);
			if (mCallBack != null) {
				int total = getChildCount() - mFake;
				switch (total) {
					case 1:
						mCallBack.onFinishScroll(0);
						break;
						
					case 2:
						int screen = mScreen % total;
						mCallBack.onFinishScroll(screen);
						break;
					
					default:
						mCallBack.onFinishScroll(mScreen);
						break;
				}					
			}
        }
	};
	
	public ScrollViewAd(Context context) {
		super(context);
		init();
	}
	
	public ScrollViewAd(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScrollViewAd(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		mScroller = new Scroller(getContext());		
	}
	
	public void putView(View view, boolean fake) {
		addView(view);
		if (fake)
			mFake++;
	}
	
	public void startTimer(long delay, long period) {
		if (mTimer != null)
			return;
		
		mTimer = new Timer();		
		TimerTask task = new TimerTask() {		
			@Override
			public void run() {			            
				mHandle.sendEmptyMessage(1);
			}
		};
		
		mTimer.schedule(task, delay, period);
	}
	
	public void cancelTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}		
	}
	
	public void setTop(ViewGroup parent, ListView listView) {
		mParent = parent;
		mListView = listView;
	}
	
	public void setCallBack(ScrollViewAdCallBack callback) {
		mCallBack = callback;
	}
	
	@Override 
    public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
    }
			
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);
		
		final int count = getChildCount();		
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.measure(width, height);							
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!changed)
			return;
		
		final int width = getWidth();
		final int height = getHeight();		
		int startLeft = -width;
				
		final int count = getChildCount();				
		if (count == 0)
			return;
											
		int i = mScreen - 1;
		if (i < 0)
			i = count - 1;
		
		int j = mScreen + 1;
		if (j > count - 1)
			j = 0;
		
		View view = getChildAt(i);
		view.layout(startLeft, 0, startLeft + width, height);
		startLeft += width;
		
		view = getChildAt(mScreen);
		view.layout(startLeft, 0, startLeft + width, height);
		startLeft += width;
		
		view = getChildAt(j);
		view.layout(startLeft, 0, startLeft + width, height);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {		
		if (mListView != null)
			mListView.requestDisallowInterceptTouchEvent(true);
		
		if (mParent != null)
			mParent.requestDisallowInterceptTouchEvent(true);			
								
		if (event.getAction() == MotionEvent.ACTION_CANCEL)
			event.setAction(MotionEvent.ACTION_UP);
		
		return super.dispatchTouchEvent(event);		
	}
		
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {		
		if (mListView != null)
			mListView.requestDisallowInterceptTouchEvent(true);
		
		if (mParent != null)
			mParent.requestDisallowInterceptTouchEvent(true);				
		
		if (event.getAction() == MotionEvent.ACTION_CANCEL)
			event.setAction(MotionEvent.ACTION_UP);
			
		return super.onInterceptTouchEvent(event);		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		if (mParent != null)
			mParent.requestDisallowInterceptTouchEvent(true);
		
		if (mListView != null)
			mListView.requestDisallowInterceptTouchEvent(true);
				
		final float x = event.getX();		
		final int action = event.getAction();
								
		if (mVelocityTracker == null)			
			mVelocityTracker = VelocityTracker.obtain();
				
		mVelocityTracker.addMovement(event);
				
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				if (!mScroller.isFinished())
					mScroller.abortAnimation();
				
				mTouchX = x;				
				mLastX = x;				
				cancelTimer();
			}	
			break;
			
			case MotionEvent.ACTION_MOVE: {				
				int detaX = (int) (mLastX - x);				
				scrollBy(detaX, 0);				
				mLastX = x;				
			}
			break;
			
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP: {				
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);				
				float velocityX = velocityTracker.getXVelocity();
												
				if (velocityX > 600.0f)
					toScroll(false, true);
				else {
					if (velocityX < -600.0f)
						toScroll(true, true);
					else {
						if (mLastX > mTouchX)
							toScroll(false, false);
						
						if (mLastX < mTouchX)
							toScroll(true, false);
					}
				}				

				if (mVelocityTracker != null) {
					mVelocityTracker.clear();
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				
				if (mListView != null)
					mListView.requestDisallowInterceptTouchEvent(false);
				
				if (mParent != null)
					mParent.requestDisallowInterceptTouchEvent(false);
				
				if (mCallBack != null) {
					int total = getChildCount() - mFake;
					switch (total) {
						case 1:
							mCallBack.onFinishScroll(0);
							break;
							
						case 2:
							int screen = mScreen % total;
							mCallBack.onFinishScroll(screen);
							break;
						
						default:
							mCallBack.onFinishScroll(mScreen);
							break;
					}					
				}
				
				startTimer(4000, 3000);
			}
			break;
		}
				
		return true;
	}
		
	private void toScroll(boolean right, boolean change) {		
		final int x = getScrollX();
		final int width = getWidth();		
		final int count = getChildCount();
		final View current = getChildAt(mScreen);
		final int vleft = current.getLeft();
		final int vright = current.getRight();		
		final int half = (vleft + vright) / 2;
		
		if (count == 0)
			return;			
		
		if (!change) {
			if (right) {			
				if (x > half)
					change = true;				
			} else {
				if (x < vleft - width / 2)
					change = true;
			}
		}
				
		int tx = -(x - vleft);				
		if (change) {
			if (right) {				
				mScreen++;				
				if (mScreen > count - 1)
					mScreen = 0;
				
				tx = vright - x;
				setLayout(true, count, width);
			} else {
				mScreen--;
				if (mScreen < 0)
					mScreen = count - 1;
				
				tx = vleft - width - x;
				setLayout(false, count, -width);				
			}					
		}
						
		mScroller.startScroll(x, 0, tx, 0, 500);		
		invalidate();
	}
	
	private void setLayout(boolean next, int count, int width) {		
		int p = 0;
		if (next)
			p = mScreen + 1;			
		else
			p = mScreen - 1;
		
		if (p < 0)
			p = count - 1;
		
		if (p > count - 1)
			p = 0;
		
		final View current = getChildAt(mScreen);
		final View view = getChildAt(p);		
		final int top = current.getTop();
		final int bottom = current.getBottom();		
		if (width > 0) {			
			final int right = current.getRight();
			view.layout(right, top, right + width, bottom);
		} else {
			final int left = current.getLeft();
			view.layout(left + width, top, left, bottom);
		}
	}
}
