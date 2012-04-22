package com.games.tk5;

import com.games.tk5.util.AudioPlayer;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;

public class WelcomeView extends ViewBase {
	private Bitmap[] m_Image_Background = new Bitmap[2];
	private Bitmap[] m_Image_Flower = new Bitmap[4];
	private final Paint m_Paint = new Paint();
	private boolean m_Exit = false;
	private final Paint m_Entry_Paint = new Paint();
	private final PointF m_Point_Up = new PointF();
	private final PointF m_Point_Down = new PointF();
		
	protected WelcomeView(Context context, ViewCallBack callback) {
		super(context, callback);
		m_Paint.setAntiAlias(true);
		m_Entry_Paint.setAntiAlias(true);
		m_Entry_Paint.setColor(Color.BLACK);
	}
	
	private boolean initImages() {
		try {
			m_Image_Background[0] = ImageUtil.getImage(getContext(), R.drawable.welcome_out);
			m_Image_Background[1] = ImageUtil.getImage(getContext(), R.drawable.welcome_in);
			
			int[] filterColor = { Color.BLACK };
			m_Image_Flower[0] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_1, filterColor);
			m_Image_Flower[0] = setImagesAlpha(m_Image_Flower[0], 50);
			m_Image_Flower[1] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_2, filterColor);
			m_Image_Flower[1] = setImagesAlpha(m_Image_Flower[1], 20);
			m_Image_Flower[2] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_3, filterColor);
			m_Image_Flower[2] = setImagesAlpha(m_Image_Flower[2], 20);
			m_Image_Flower[3] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_4, filterColor);
			m_Image_Flower[3] = setImagesAlpha(m_Image_Flower[3], 20);
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}
	}
	
	private Bitmap setImagesAlpha(Bitmap bitmap, int alpha) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);			
		paint.setAlpha(alpha);
		
		Bitmap panel = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(panel);
		canvas.drawBitmap(bitmap, 0f, 0f, paint);
		return panel;
	}

	@Override
	protected boolean onInit() {		
		return initImages();
	}

	@Override
	protected void onPrepare() {
		setGameStatus(GameStatus.Runing);
		setViewStatus(ViewStatus.Entry);
		m_Point_Up.set(((float)getScreenWidth() - 1f) / 2f, 0f);
		m_Point_Down.set(((float)getScreenWidth() - 1f) / 2f, (float)getScreenHeight() - 1f);
		AudioPlayer.musicStart(getContext(), R.raw.index_background);
	}

	@Override
	protected void onExit() {
		if (!m_Exit) {
			getHandler().post(new Runnable() {			
				@Override
				public void run() {
					getViewCallBack().changeIndex(false);
				}
			});						
		}
	}

	@Override
	protected boolean onBack() {
		m_Exit = true;
		setDestroy();
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEntryView(Canvas canvas, int deay) {
		drawWelcome(canvas);
		Path pathUp = setTranslation(m_Point_Up, 0.11f, true);
		Path pathDown = setTranslation(m_Point_Down, 0.11f, false);
		if (pathUp == null || pathDown == null) {			
			setViewStatus(ViewStatus.Started);
		} else {
			canvas.drawPath(pathUp, m_Entry_Paint);
			canvas.drawPath(pathDown, m_Entry_Paint);
			pathUp.close();
			pathDown.close();			
		}
	}

	@Override
	protected void onSelectView(Canvas canvas, int deay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onStartingView(Canvas canvas, int deay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDrawView(Canvas canvas) {
		drawWelcome(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN)
			setViewStatus(ViewStatus.Leave);			
				
		return super.onTouchEvent(event);
	}

	@Override
	protected void onEndingView(Canvas canvas, int deay) {
		
	}

	@Override
	protected boolean onLeaveView(Canvas canvas, int deay) {
		return true;
	}
	
	private void drawWelcome(Canvas canvas) {
		canvas.drawBitmap(m_Image_Background[0], 0f, 0f, m_Paint);	
		canvas.drawBitmap(m_Image_Background[1], 0f, 208f, m_Paint);
	}
}
