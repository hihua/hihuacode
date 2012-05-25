package com.games.tk5.timer;

import com.games.tk5.R;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class GamesTimer {
	private Paint m_Paint = new Paint();
	private Bitmap m_Image_Timer;
	private Bitmap m_Image_TimerIn;
	private Bitmap m_Image_TimerOut;
	private CallBackTimer m_CallBack;
	private long m_LastTime = 0;
	private long m_TotalTime = 0;
	private boolean m_Show = false;
	private boolean m_Status = false;
	private PointF m_Point_Timer = new PointF();
	private RectF m_Rect_TimerIn = new RectF();	
	private RectF m_Rect_TimerOut = new RectF();
	private Rect m_Rect_TimerSrc = new Rect();
	private RectF m_Rect_TimerDest = new RectF();
		
	public GamesTimer(CallBackTimer callback) {
		m_CallBack = callback;
		m_Paint.setAntiAlias(true);
	}
	
	public boolean init(Context context) {
		try {
			m_Image_Timer = ImageUtil.getImage(context, R.drawable.timer);
			m_Image_Timer = m_Image_Timer.copy(Config.ARGB_8888, true);
			m_Image_TimerIn = ImageUtil.getImage(context, R.drawable.timer_in);
			m_Image_TimerOut = ImageUtil.getImage(context, R.drawable.timer_out, new Rect(2, 2, 22, 366));
			setName(context);
			return true;
		} catch (Exception e) {			
			Logs.LogsError(e);
			return false;
		}
	}
	
	private void setName(Context context) {		
		Rect rect = new Rect(0, 0, m_Image_Timer.getWidth(), m_Image_Timer.getHeight());		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(22);
		paint.setTextAlign(Align.CENTER);
		Canvas canvas = new Canvas(m_Image_Timer);
		canvas.drawText(context.getString(R.string.timer_name), rect.centerX(), rect.bottom - 2, paint);
	}
	
	public void position(float left, float top) {
		m_Show = true;
		m_Point_Timer.set(left, top);
		left = left + (m_Image_Timer.getWidth() - m_Image_TimerOut.getWidth()) / 2;
		top = top + m_Image_Timer.getHeight() + 2;
		m_Rect_TimerOut.set(left, top, left + m_Image_TimerOut.getWidth(), top + m_Image_TimerOut.getHeight());
		left += 2f;
		top += 2f;
		m_Rect_TimerIn.set(left, top, left + m_Image_TimerIn.getWidth(), top + m_Image_TimerIn.getHeight());
		m_Rect_TimerSrc.set(0, 0, m_Image_TimerIn.getWidth(), m_Image_TimerIn.getHeight());
		m_Rect_TimerDest.set(left, top, left + m_Image_TimerIn.getWidth(), top + m_Image_TimerIn.getHeight());
	}
	
	public void start(long millisecond) {		
		m_Status = true;
		m_LastTime = System.currentTimeMillis();
		m_TotalTime = millisecond;
	}
	
	public void restart(long millisecond) {		
		m_Status = true;
		m_LastTime = System.currentTimeMillis();
		m_TotalTime = millisecond;
		m_Rect_TimerSrc.set(0, 0, m_Image_TimerIn.getWidth(), m_Image_TimerIn.getHeight());
		m_Rect_TimerDest.set(m_Rect_TimerIn.left, m_Rect_TimerIn.top, m_Rect_TimerIn.left + m_Image_TimerIn.getWidth(), m_Rect_TimerIn.top + m_Image_TimerIn.getHeight());
	}
		
	public void stop() {
		m_Status = false;
	}
	
	public void draw(Canvas canvas) {
		if (!m_Show)
			return;
		
		canvas.drawBitmap(m_Image_Timer, m_Point_Timer.x, m_Point_Timer.y, m_Paint);	
		canvas.drawBitmap(m_Image_TimerOut, m_Rect_TimerOut.left, m_Rect_TimerOut.top, m_Paint);
		if (m_Status) {
			long time = System.currentTimeMillis() - m_LastTime;			
			if (time > 0) {
				if (time >= m_TotalTime) {
					m_CallBack.onTimeOut();
					m_Status = false;
				} else {
					float p = (float)time / (float)m_TotalTime * m_Image_TimerIn.getHeight();
					m_Rect_TimerSrc.top = (int)p;
					m_Rect_TimerDest.top = m_Rect_TimerIn.top + p;
				}
			}			
		}				
		
		canvas.drawBitmap(m_Image_TimerIn, m_Rect_TimerSrc, m_Rect_TimerDest, m_Paint);
	}
}
