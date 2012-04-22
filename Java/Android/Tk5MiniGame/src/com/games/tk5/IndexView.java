package com.games.tk5;

import java.util.List;
import java.util.Vector;

import com.games.tk5.square.SquareView;
import com.games.tk5.util.AudioPlayer;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class IndexView extends ViewBase {
	private boolean m_LastView = false;
	private Bitmap m_Image_Background;
	private Bitmap m_Image_Banner;
	private Bitmap m_Image_Desc;
	private Bitmap[] m_Image_Top;
	private Bitmap[] m_Image_Bottom;
	private Bitmap m_Image_Select;
	private final Paint m_Index_Paint = new Paint();
	private final Paint m_Index_Banner = new Paint();
	private final Paint m_Entry_Paint = new Paint();
	private int m_Banner_TopFrame = 0;
	private int m_Banner_BottomFrame = 0;
	private int m_Banner_Update = 0;
	private final List<GamesBase> m_Games = new Vector<GamesBase>();
	private final float m_SpaceWidth = 2f;
	private final float m_SpaceHeight = 9f;
	private final float m_Index_Width = 126f;
	private float m_LeftRigth = 0;
	private int m_Cell_Total = 6;
	private GamesBase m_SelectGame = null;
	private final PointF m_Point_Left = new PointF();
	private final PointF m_Point_Right = new PointF();
		
	public IndexView(Context context, ViewCallBack callback) {
		super(context, callback);		
		m_Index_Paint.setAntiAlias(true);		
		m_Index_Banner.setAntiAlias(true);
		m_Index_Banner.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));  
		m_Index_Banner.setColor(Color.BLACK);
		m_Index_Banner.setTextSize(24);  
		m_Entry_Paint.setAntiAlias(true);
		m_Entry_Paint.setColor(Color.BLACK);		
	}
	
	public void setLastView(boolean lastView) {
		m_LastView = lastView;
	}
	
	private boolean initImages() {
		try {
			m_Image_Background = ImageUtil.getImage(getContext(), R.drawable.index_background);
			m_Image_Banner = ImageUtil.getImage(getContext(), R.drawable.index_banner, new int[] { Color.WHITE }, 90, 0);			
			m_Image_Banner = m_Image_Banner.copy(Config.ARGB_8888, true);
			setBanner(m_Image_Banner);
			if (!initImageDesc())
				return false;
			
			if (!initImageSelect())
				return false;
				
			return initTopBottom();
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}		
	}

	private boolean initTopBottom() {
		try {
			int[] filterColor = { Color.GREEN };
			Bitmap bitmap = ImageUtil.getImage(getContext(), R.drawable.index_top, filterColor);
			m_Image_Top = new Bitmap[9];
			int top = 0;
			int height = bitmap.getHeight() / m_Image_Top.length;
			for (int i = 0;i < m_Image_Top.length;i++) {
				m_Image_Top[i] = Bitmap.createBitmap(bitmap, 0, top, bitmap.getWidth(), height);
				top += height;
			}
			
			bitmap = ImageUtil.getImage(getContext(), R.drawable.index_bottom, filterColor);
			m_Image_Bottom = new Bitmap[9];
			top = 0;
			height = bitmap.getHeight() / m_Image_Bottom.length;
			for (int i = 0;i < m_Image_Bottom.length;i++) {
				m_Image_Bottom[i] = Bitmap.createBitmap(bitmap, 0, top, bitmap.getWidth(), height);
				top += height;
			}
			
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}		
	}
		
	private boolean initImageSelect() {
		try {
			int[] colorsLeft = new int[] { Color.rgb(199, 255, 92), Color.rgb(247, 255, 214), Color.rgb(247, 255, 231) };
			int[] colorsRight = new int[] { Color.rgb(247, 255, 231), Color.rgb(247, 255, 214), Color.rgb(199, 255, 92) };			
			float[] arraysLeft = new float[] { 0f, 0.3f, 1f};
																				
			RectF rect = new RectF(0, 0, 123, 21);
			float[] arrayRight = new float[] { 0f, 0.7f, 1f};
			m_Image_Select = Bitmap.createBitmap(124, 22, Config.ARGB_8888);
			LinearGradient gradientLeft = new LinearGradient(rect.left, rect.top, rect.centerX(), rect.top, colorsLeft, arraysLeft, Shader.TileMode.MIRROR);
			LinearGradient gradientRight = new LinearGradient(rect.centerX(), rect.top, rect.right, rect.top, colorsRight, arrayRight, Shader.TileMode.MIRROR);
			
			Paint paint = new Paint();			
			paint.setShader(gradientLeft);
			Canvas canvas = new Canvas(m_Image_Select);
			canvas.drawRect(rect.left, rect.top, rect.centerX(), rect.bottom, paint);
			paint.setShader(gradientRight);
			canvas.drawRect(rect.centerX(), rect.top, rect.right, rect.bottom, paint);						
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}
	}
	
	private boolean initImageDesc() {
		try {		
			Bitmap descLeft = ImageUtil.getImage(getContext(), R.drawable.index_desc_left);
			Bitmap descRight = ImageUtil.getImage(getContext(), R.drawable.index_desc_right);							
			m_Image_Desc = Bitmap.createBitmap(570 + descRight.getWidth(), descLeft.getHeight(), Config.ARGB_8888);
			
			int alpha = 120;			
			Paint paint = new Paint();
			paint.setAntiAlias(true);			
			paint.setAlpha(alpha);
			
			RectF dest = new RectF();
			dest.left = 0;
			dest.top = 0;
			dest.right = 570f;
			dest.bottom = dest.top + descLeft.getHeight();
			
			Canvas canvas = new Canvas(m_Image_Desc);			
			canvas.drawBitmap(descLeft, null, dest, paint);
			
			Rect src = new Rect();
			src.left = 0;
			src.top = 0;
			src.bottom = descRight.getHeight();			
			for (int i = 0;i < descRight.getWidth();i+=4) {
				src.right = src.left + 4;
				src.bottom = descRight.getHeight();
				dest.left = dest.right;
				dest.right = dest.left + src.width();
				canvas.drawBitmap(descRight, src, dest, paint);			
				paint.setAlpha(alpha);
				if (alpha > 0)
					alpha -= 8;
				else
					alpha = 0;								
			}
			
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}		
	}
	
	private void setBanner(Bitmap imageBanner) {
		Canvas canvas = new Canvas(imageBanner);
		canvas.drawText(getResources().getString(R.string.mini_game), 28f, 34f, m_Index_Banner);
	}

	@Override
	protected boolean onBack() {
		setDestroy();
		return false;
	}

	@Override
	protected boolean onInit() {
		if (m_LastView) {
			AudioPlayer.musicStart(getContext(), R.raw.index_background);
			return true;
		}
		
		m_LeftRigth = ((float)(getScreenWidth()) - m_Cell_Total * m_Index_Width - ((float)m_Cell_Total - 1) * m_SpaceWidth) / 2;
		RectF rect = new RectF();
		float indexLeft = m_LeftRigth;
		float indexTop = 72f;
				
		for (int i = 0; i < 12;i++) {
			if (i % m_Cell_Total == 0) {				
				indexLeft = m_LeftRigth;				
				if (i > 0)
					indexTop = rect.bottom + m_SpaceHeight;
			} else
				indexLeft = rect.right + m_SpaceWidth + 1;
									
			rect.left = indexLeft;
			rect.right = rect.left + m_Index_Width;
			rect.top = indexTop;
			rect.bottom = rect.top + 120f; 
			
			SquareView square = new SquareView(getContext(), getViewCallBack());
			square.initIndex(rect);						
			m_Games.add(square);						
		}
		
		return initImages();
	}
	
	@Override
	protected void onPrepare() {
		setGameStatus(GameStatus.Runing);
		if (m_LastView) {
			setViewStatus(ViewStatus.Started);
		} else {
			setViewStatus(ViewStatus.Entry);
			m_Point_Left.set((float)(getScreenWidth() - 2), 0f);
			m_Point_Right.set((float)(getScreenWidth() - 1), 1f);
		}		
	}

	@Override
	protected void onExit() {
		
	}

	@Override
	protected void onPause() {
		
	}

	@Override
	protected void onDestroy() {
		
	}

	@Override
	protected void onEntryView(Canvas canvas, int frame) {
		onDrawView(canvas);
		Path path = setTranslation(m_Point_Left, m_Point_Right, 0.3f, false);
		if (path == null) {
			m_Point_Left.set(0f, (float)(getScreenHeight() - 2));
			m_Point_Right.set(1f, (float)(getScreenHeight() - 1));
			setViewStatus(ViewStatus.Started);
		} else {
			canvas.drawPath(path, m_Entry_Paint);
			path.close();			
		}
	}

	@Override
	protected void onSelectView(Canvas canvas, int frame) {
		
	}

	@Override
	protected void onStartingView(Canvas canvas, int frame) {
		
	}

	@Override
	protected void onDrawView(Canvas canvas) {		
		canvas.drawBitmap(m_Image_Background, 0f, 0f, m_Index_Paint);
		updateTopBottom(canvas);	
		canvas.drawBitmap(m_Image_Banner, 0f, 0f, m_Index_Paint);
		canvas.drawBitmap(m_Image_Desc, 0, 335f, m_Index_Paint);
		
		for (GamesBase square : m_Games)
			square.drawIndex(canvas, m_Image_Select);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			//Logs.LogsInfo("%f, %f", event.getX(), event.getY());
			for (GamesBase square : m_Games) {
				if (square.onKeyDown(event.getX(), event.getY())) {					
					if (m_SelectGame != null) {
						if (m_SelectGame == square) {
							getViewCallBack().changeGames(square);
						} else
							m_SelectGame.setSelect(false);									
					}
					
					square.setSelect(true);
					m_SelectGame = square;
					break;
				}				
			}
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	protected void onEndingView(Canvas canvas, int frame) {
		
	}

	@Override
	protected boolean onLeaveView(Canvas canvas, int frame) {
		return true;		
	}
	
	private void updateTopBottom(Canvas canvas) {
		m_Banner_Update++;
		if (m_Banner_Update == 10) {
			m_Banner_Update = 0;			
			m_Banner_TopFrame++;			
			m_Banner_BottomFrame++;			
		}
		
		m_Banner_TopFrame = m_Banner_TopFrame % m_Image_Top.length;
		canvas.drawBitmap(m_Image_Top[m_Banner_TopFrame], 0f, 0f, m_Index_Paint);
		
		m_Banner_BottomFrame = m_Banner_BottomFrame % m_Image_Bottom.length;
		int height = getHeight() - m_Image_Bottom[m_Banner_BottomFrame].getHeight();
		canvas.drawBitmap(m_Image_Bottom[m_Banner_BottomFrame], 0f, height, m_Index_Paint);		
	}
}
