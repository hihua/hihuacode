package com.games.tk5;

import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

public abstract class GamesBase extends ViewBase implements Callback {
	private Bitmap m_Index_Image;
	private String m_Index_Name;
	private String[] m_Index_Desc;
	private boolean m_Select = false;
	private final Paint m_Index_Paint = new Paint();
	private final Paint m_Index_Line = new Paint();
	private final Paint m_Index_Font = new Paint();
	private final Paint m_Entry_Paint = new Paint();
	private final Paint m_Leave_Paint = new Paint();
	private int m_Select_Frame = 0;
	private int m_Select_Alpha = 255;
	private boolean m_Select_Next = true;
	private final RectF m_Rect = new RectF();
	private final PointF m_Point_Left = new PointF();
	private final PointF m_Point_Right = new PointF();
	private final Handler m_Handler = new Handler(this);
		
	@Override
	public boolean handleMessage(Message msg) {
		getViewCallBack().changeIndex(true);
		return false;
	}

	protected GamesBase(Context context, ViewCallBack callback) {
		super(context, callback);		
		m_Index_Paint.setAntiAlias(true);
		m_Index_Line.setAntiAlias(false);
		m_Index_Line.setStyle(Style.STROKE);
		m_Index_Font.setAntiAlias(true);
		m_Index_Font.setTextSize(17);
		m_Entry_Paint.setAntiAlias(true);
		m_Entry_Paint.setColor(Color.BLACK);
		m_Entry_Paint.setStyle(Style.FILL);
		m_Leave_Paint.setAntiAlias(true);
		m_Leave_Paint.setColor(Color.BLACK);
		m_Leave_Paint.setStyle(Style.FILL);
	}
	
	public boolean initIndex(RectF rect) {
		m_Rect.set(rect);
		return onInitIndex();
	}
	
	public void drawIndex(Canvas canvas, Bitmap imageSelect) {
		int color = Color.rgb(115, 165, 16);
		int border = Color.rgb(107, 115, 66);
		m_Index_Font.setColor(Color.BLACK);
		if (getSelect()) {
			m_Select_Alpha = 40;			
			if (3 <= m_Select_Frame && m_Select_Frame <= 4) {
				color = Color.rgb(123, 173, 16);
				border = Color.rgb(107, 115, 57);
				m_Select_Alpha = 100;
				m_Index_Font.setColor(Color.rgb(24, 33, 24));
			} else if (5 <= m_Select_Frame && m_Select_Frame <= 6) {
				color = Color.rgb(132, 181, 16);
				border = Color.rgb(107, 123, 49);
				m_Select_Alpha = 160;
				m_Index_Font.setColor(Color.rgb(24, 41, 24));
			} else if (7 <= m_Select_Frame && m_Select_Frame <= 8) {
				color = Color.rgb(156, 206, 16);
				border = Color.rgb(148, 148, 33);
				m_Select_Alpha = 210;
				m_Index_Font.setColor(Color.rgb(24, 57, 8));
			} else if (9 <= m_Select_Frame && m_Select_Frame <= 17) {
				color = Color.rgb(213, 255, 30);
				border = Color.rgb(104, 173, 24);
				m_Select_Alpha = 255;
				m_Index_Font.setColor(Color.rgb(24, 82, 0));
			}
			
			if (m_Select_Next) {
				m_Select_Frame++;
				if (m_Select_Frame == 17)
					m_Select_Next = false;
			} else {
				m_Select_Frame--;
				if (m_Select_Frame == 0)
					m_Select_Next = true;
			}
						
			//Logs.LogsInfo("%d,color=%d,%d,%d", m_Select_Frame, Color.red(color), Color.green(color), Color.blue(color));
		}	
										
		RectF rect = new RectF(m_Rect.left, m_Rect.top, m_Rect.left + m_Index_Image.getWidth() + 5f, m_Rect.top + m_Index_Image.getHeight() + 30f);		
		m_Index_Paint.setColor(Color.WHITE);		
		canvas.drawRect(rect, m_Index_Paint);		
		rect.left = rect.left + 1.5f;
		rect.right = rect.right - 1.5f;		
		rect.bottom = rect.top + 20f;				
		if (getSelect()) {			
			m_Index_Line.setAlpha(m_Select_Alpha);
			canvas.drawBitmap(imageSelect, rect.left - 0.5f, rect.top, m_Index_Line);			
			m_Index_Line.setAlpha(255);			
		}
		
		m_Index_Line.setColor(border);		
		canvas.drawRect(rect, m_Index_Line);				
		m_Index_Font.setTextAlign(Align.CENTER);
		canvas.drawText(m_Index_Name, rect.centerX(), rect.bottom - 4f, m_Index_Font);		
		rect.top = rect.bottom + 5f;
		rect.left = rect.left + 1.5f;
		rect.right = rect.right - 1.5f;
		canvas.drawBitmap(m_Index_Image, rect.left, rect.top, m_Index_Paint);		
		rect.left = rect.left - 1f;
		rect.right = rect.right + 1f;
		rect.bottom = rect.top + m_Index_Image.getHeight();
		rect.top = rect.top - 1f;		
		m_Index_Line.setColor(Color.rgb(66, 82, 8));
		canvas.drawRect(rect, m_Index_Line);
		rect.left = rect.left - 1f;
		rect.right = rect.right + 1f;
		rect.bottom = rect.bottom + 1f;
		rect.top = rect.top - 1f;		
		m_Index_Line.setColor(color);
		canvas.drawRect(rect, m_Index_Line);		
		rect.left = rect.left - 1f;
		rect.right = rect.right + 1f;
		rect.bottom = rect.bottom + 1f;
		rect.top = rect.top - 1f;
		m_Index_Line.setColor(Color.rgb(57, 74, 8));
		canvas.drawRect(rect, m_Index_Line);
		
		if (getSelect())
			drawDesc(canvas);		
	}
	
	protected double getFontHeight(Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		return Math.ceil(fontMetrics.descent - fontMetrics.ascent);
	}
	
	private void drawDesc(Canvas canvas) {
		m_Index_Font.setColor(Color.BLACK);
		m_Index_Font.setTextAlign(Align.LEFT);		
		double height = getFontHeight(m_Index_Font);
		float left = 30f;
		float top = 352f;
		
		for (String desc : m_Index_Desc) {
			canvas.drawText(desc, left, top, m_Index_Font);
			top += height + 2;
		}		
	}
	
	protected void setIndexText(int resName, int resDesc) {
		m_Index_Name = getResources().getString(resName);
		String indexDesc = getResources().getString(resDesc);
		if (indexDesc.indexOf("\n") == -1)
			m_Index_Desc = new String[] { indexDesc };
		else
			m_Index_Desc = indexDesc.split("\\n");		
	}
	
	protected boolean setIndexImage(int res) {
		try {
			m_Index_Image = ImageUtil.getImage(getContext(), res);
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}
	}
	
	public boolean getSelect() {
		return m_Select;
	}
	
	public void setSelect(boolean select) {		
		m_Select = select;
		if (!select)
			m_Index_Paint.setAlpha(255);
	}
	
	public boolean onKeyDown(float x, float y) {		
		if (m_Rect.contains(x, y))
			return true;
		else
			return false;		
	}

	@Override
	protected boolean onInit() {		
		return onInitGames();
	}

	@Override
	protected void onPrepare() {
		setGameStatus(GameStatus.Runing);
		setViewStatus(ViewStatus.Entry);
		startMediaPlay(R.raw.game_background);
		m_Point_Left.set((float)(getScreenWidth() - 2), 0f);
		m_Point_Right.set((float)(getScreenWidth() - 1), 1f);
		setFrame(0);		
	}
		
	@Override
	protected void onExit() {
		m_Handler.post(new Runnable() {			
			@Override
			public void run() {
				getViewCallBack().changeIndex(true);
			}
		});
	}
	
	@Override
	protected boolean onBack() {
		setViewStatus(ViewStatus.Leave);
		return true;
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
		onEntryGames(canvas);
		Path path = setTranslation(m_Point_Left, m_Point_Right, 0.3f, false);
		if (path == null) {
			m_Point_Left.set(0f, (float)(getScreenHeight() - 2));
			m_Point_Right.set(1f, (float)(getScreenHeight() - 1));
			setViewStatus(ViewStatus.Started);
		} else {
			path.close();
			canvas.drawPath(path, m_Entry_Paint);			
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
		onDrawGames(canvas);
	}

	@Override
	protected void onEndingView(Canvas canvas, int deay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean onLeaveView(Canvas canvas, int deay) {
		Path path = setTranslation(m_Point_Left, m_Point_Right, 0.25f, true);
		if (path == null) {			
			setViewStatus(ViewStatus.Entry);
			return true;
		} else {
			path.close();
			canvas.drawPath(path, m_Entry_Paint);
			return false;
		}		
	}

	protected abstract boolean onInitIndex();
	protected abstract boolean onInitGames();
	protected abstract void onEntryGames(Canvas canvas);
	protected abstract void onDrawGames(Canvas canvas);
}
