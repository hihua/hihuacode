package com.games.tk5.welcome;

import java.util.List;
import java.util.Vector;

import com.games.tk5.R;
import com.games.tk5.ViewBase;
import com.games.tk5.ViewCallBack;
import com.games.tk5.util.AudioPlayer;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;
import com.games.tk5.util.Numeric;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;

public class WelcomeView extends ViewBase {
	private Bitmap[] m_Image_Background = new Bitmap[2];
	private Bitmap[] m_Image_Flower = new Bitmap[4];
	private WelcomeCloud[] m_Image_Cloud = new WelcomeCloud[2];
	private final Paint m_Paint = new Paint();
	private boolean m_Exit = false;
	private final Paint m_Entry_Paint = new Paint();
	private final PointF m_Point_Up = new PointF();
	private final PointF m_Point_Down = new PointF();
	private final List<WelcomeFlower> m_Flowers = new Vector<WelcomeFlower>();
		
	public WelcomeView(Context context, ViewCallBack callback) {
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
			m_Image_Flower[0] = ImageUtil.setAlpha(m_Image_Flower[0], 50);
			m_Image_Flower[1] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_2, filterColor);
			m_Image_Flower[1] = ImageUtil.setAlpha(m_Image_Flower[1], 20);
			m_Image_Flower[2] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_3, filterColor);
			m_Image_Flower[2] = ImageUtil.setAlpha(m_Image_Flower[2], 20);
			m_Image_Flower[3] = ImageUtil.getImage(getContext(), R.drawable.welcome_flower_4, filterColor);
			m_Image_Flower[3] = ImageUtil.setAlpha(m_Image_Flower[3], 20);
			m_Image_Cloud[0] = new WelcomeCloud(this);
			m_Image_Cloud[0].init(getContext(), R.drawable.welcome_cloud_1);
			m_Image_Cloud[1] = new WelcomeCloud(this);
			m_Image_Cloud[1].init(getContext(), R.drawable.welcome_cloud_2);
			addFlower(Numeric.rndNumber(5, 7));
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}
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
		drawCloud(canvas);
		Path pathUp = setTranslation(m_Point_Up, 0.11f, true);
		Path pathDown = setTranslation(m_Point_Down, 0.11f, false);
		if (pathUp == null || pathDown == null) {			
			setViewStatus(ViewStatus.Started);
			m_Image_Cloud[1].setStart(true);
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
		drawCloud(canvas);
		drawFlowers(canvas);
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
	
	private void drawCloud(Canvas canvas) {
		m_Image_Cloud[1].draw(canvas);
	}
	
	private void drawFlowers(Canvas canvas) {
		List<WelcomeFlower> disappears = new Vector<WelcomeFlower>();		
		for (WelcomeFlower flower : m_Flowers) {
			flower.draw(canvas);
			if (flower.disappear())
				disappears.add(flower);
		}
		
		while (disappears.size() > 0) {
			WelcomeFlower flower = disappears.remove(0);
			m_Flowers.remove(flower);
			addFlower();
		}
	}
	
	private void addFlower() {				
		int n = Numeric.rndNumber(1, 4);
		int res = 0;
		switch (n) {
			case 1:
				res = R.drawable.welcome_flower_1;
				break;
				
			case 2:
				res = R.drawable.welcome_flower_2;
				break;
				
			case 3:
				res = R.drawable.welcome_flower_3;
				break;
				
			case 4:
				res = R.drawable.welcome_flower_4;
				break;
				
			default:
				res = R.drawable.welcome_flower_1;
				break;
		}
		
		WelcomeFlower flower = new WelcomeFlower(this);
		flower.init(getContext(), res);			
		m_Flowers.add(flower);		
	}
	
	private void addFlower(int count) {
		for (int i = 0;i < count;i++) {
			int n = Numeric.rndNumber(1, 4);
			int res = 0;
			switch (n) {
				case 1:
					res = R.drawable.welcome_flower_1;
					break;
					
				case 2:
					res = R.drawable.welcome_flower_2;
					break;
					
				case 3:
					res = R.drawable.welcome_flower_3;
					break;
					
				case 4:
					res = R.drawable.welcome_flower_4;
					break;
					
				default:
					res = R.drawable.welcome_flower_1;
					break;
			}
			
			WelcomeFlower flower = new WelcomeFlower(this);
			flower.init(getContext(), res);			
			m_Flowers.add(flower);
		}		
	}
}
