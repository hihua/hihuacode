package com.games.tk5.welcome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.games.tk5.Sprite;
import com.games.tk5.ViewBase;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;
import com.games.tk5.util.Numeric;

public class WelcomeFlower extends Sprite {
	private final PointF m_Move = new PointF(0, 0);	
	private int m_Delay = Numeric.rndNumber(2, 15);
	private final RectF m_Clip = new RectF(40f, 220f, 775f, 350f);
	
	public WelcomeFlower(ViewBase viewBase) {
		super(viewBase);		
	}
	
	public boolean init(Context context, int res) {
		try {
			setAlpha(Numeric.rndNumber(50, 255));
			m_Bitmap = ImageUtil.getImage(context, res);			
			setWidth(m_Bitmap.getWidth());
			setHeight(m_Bitmap.getHeight());
			setLeftTop(Numeric.rndNumber(m_Clip.left, 680f), m_Clip.top);
			initMove();			
			return true;
		} catch (Exception e) {			
			Logs.LogsError(e);
			return false;
		}		
	}
		
	private void initMove() {
		float n = Numeric.rndNumber(0.5f, 2.0f);
		Logs.LogsInfo(n);
		m_Move.x = n;
		m_Move.y = n;
	}
	
	private void setMove() {
		PointF point = getMove();		
		setLeft(getLeft() + point.x);
		setTop(getTop() + point.y);
	}
	
	private PointF getMove() {
		return m_Move;
	}
	
	private int getDelay() {
		return m_Delay;
	}
	
	private void setDelay(int delay) {
		m_Delay = delay;
	}
	
	public boolean disappear() {
		if (getLeft() > m_Clip.right || getTop() > m_Clip.bottom)
			return true;
		else
			return false;
	}

	@Override
	public void draw(Canvas canvas) {
		int delay = getDelay();
		if (delay > 0) {
			delay--;
			setDelay(delay);
			return;
		}
		
		canvas.save();
		canvas.clipRect(m_Clip);
		canvas.drawBitmap(getBitmap(), getLeft(), getTop(), getPaint());
		setMove();		
		canvas.restore();
	}	
}
