package com.games.tk5.welcome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.games.tk5.Sprite;
import com.games.tk5.ViewBase;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

public class WelcomeCloud extends Sprite {
	private final long m_Delay = 5000;	
	private final float m_Move = 2f;
	private boolean m_Start = false;
	private long m_LastTime = 0;
	
	public WelcomeCloud(ViewBase viewBase) {
		super(viewBase);
		setTop(25f);		
	}
	
	public boolean init(Context context, int res, int alpha) {
		try {
			m_Bitmap = ImageUtil.getImage(context, res);
			m_Bitmap = ImageUtil.setAlpha(m_Bitmap, alpha);
			setWidth(m_Bitmap.getWidth());
			setHeight(m_Bitmap.getHeight());			
			return true;
		} catch (Exception e) {			
			Logs.LogsError(e);
			return false;
		}		
	}
	
	private float getMove() {
		return m_Move;
	}
	
	public boolean getStart() {
		return m_Start;
	}
	
	public void setStart(boolean start) {
		m_Start = start;
	}
	
	private void setLastTime(long timeMillis) {
		m_LastTime = timeMillis;
	}
	
	private long getLastTime() {
		return m_LastTime;
	}
				
	private boolean checkTimeout() {
		long now = System.currentTimeMillis();
		if (now - getLastTime() > m_Delay)
			return true;
		else
			return false;
	}
	
	@Override
	public void draw(Canvas canvas) {		
		if (getStart()) {
			RectF rectF = getRectF();
			if (rectF.right < 0f) {
				setLastTime(System.currentTimeMillis());
				setLeft(getScreenWidth());								
				return;
			} else {
				if (getLastTime() == 0)
					setLeft(getLeft() - getMove());
			}
			
			if (getLastTime() > 0 && checkTimeout()) {				
				setLastTime(0);
				return;
			}																	
		}
		
		canvas.drawBitmap(getBitmap(), getLeft(), getTop(), getPaint());
	}		
}
