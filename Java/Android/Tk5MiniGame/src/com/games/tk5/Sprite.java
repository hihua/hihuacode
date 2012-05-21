package com.games.tk5;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class Sprite {
	private final PointF m_Position = new PointF(0, 0);
	private final RectF m_RectF = new RectF();
	private int m_Width = 0;
	private int m_Height = 0;
	private int m_Alpha = 255;
	protected Bitmap m_Bitmap = null;
	private final Paint m_Paint = new Paint();	
	private final ViewBase m_ViewBase;
	
	public Sprite(ViewBase viewBase) {
		m_ViewBase = viewBase;
		m_Paint.setAntiAlias(true);		
	}
	
	public float getLeft() {
		return m_Position.x;
	}
	
	public float getTop() {
		return m_Position.y;
	}
	
	public void setLeft(float left) {
		m_Position.x = left;
	}
	
	public void setTop(float top) {
		m_Position.y = top;
	}
	
	public void setLeftTop(float left, float top) {
		m_Position.set(left, top);
	}
	
	public int getWidth() {
		return m_Width;
	}
	
	public int getHeight() {
		return m_Height;
	}
	
	protected void setWidth(int width) {
		m_Width = width;
	}
	
	protected void setHeight(int height) {
		m_Height = height;
	}
	
	public int getAlpha() {
		return m_Alpha;
	}
	
	public void setAlpha(int alpha) {		
		m_Alpha = alpha;
	}
	
	public Bitmap getBitmap() {
		return m_Bitmap;
	}
	
	protected Paint getPaint() {
		return m_Paint;
	}
	
	public RectF getRectF() {
		m_RectF.set(getLeft(), getTop(), getLeft() + (float)getWidth() - 1f, getTop() + (float)getHeight() - 1f);
		return m_RectF;
	}
	
	public int getFPS() {
		return m_ViewBase.getFPS();
	}
	
	public int getScreenWidth() {
		return m_ViewBase.getScreenWidth();		
	}
	
	public int getScreenHeight() {
		return m_ViewBase.getScreenHeight();
	}
	
	public Rect getSreenRect() {
		return m_ViewBase.getSreenRect();
	}
	
	public abstract void draw(Canvas canvas);
}
