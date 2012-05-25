package com.games.tk5.square;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.games.tk5.GamesBase;
import com.games.tk5.R;
import com.games.tk5.ViewCallBack;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

public class SquareView extends GamesBase {
	private Bitmap m_Image_Color;
	private Bitmap m_Image_Background;	
	private Bitmap m_Image_Left;
	private Bitmap m_Image_Top;	
	private final Paint m_Paint = new Paint();
	
	public SquareView(Context context, ViewCallBack callback) {
		super(context, callback);
		m_Paint.setAntiAlias(true);
	}
	
	private boolean initImages() {
		try {
			m_Image_Color = ImageUtil.getImage(getContext(), R.drawable.square_color);
			m_Image_Background = ImageUtil.getImage(getContext(), R.drawable.square_background);
			m_Image_Left = ImageUtil.getImage(getContext(), R.drawable.square_left);
			m_Image_Top = ImageUtil.getImage(getContext(), R.drawable.square_top);			
			return true;
		} catch (Exception e) {
			Logs.LogsError(e);
			return false;
		}		
	}
	
	private void drawColor(Canvas canvas) {
		canvas.drawBitmap(m_Image_Color, null, getSreenRect(), m_Paint);
	}
	
	private void drawLeft(Canvas canvas) {
		float left = 16f;
		float top = (getScreenHeight() - m_Image_Left.getHeight()) / 2;
		canvas.drawBitmap(m_Image_Left, left, top, m_Paint);
	}

	private void drawTop(Canvas canvas) {
		float left = getScreenWidth() - m_Image_Top.getWidth();
		float top = 0;
		canvas.drawBitmap(m_Image_Top, left, top, m_Paint);
	}
	
	private void drawBackground(Canvas canvas) {
		float left = 16f + m_Image_Left.getWidth() + 12f;
		float top = m_Image_Top.getHeight() + (getScreenHeight() - m_Image_Top.getHeight() - m_Image_Background.getHeight()) / 2;
		canvas.drawBitmap(m_Image_Background, left, top, m_Paint);
	}
	
	@Override
	protected boolean onInitIndex() {
		setIndexText(R.string.index_square, R.string.square_desc);
		return setIndexImage(R.drawable.index_square);
	}
		
	@Override
	protected boolean onInitGames() {
		return initImages();
	}
	
	@Override
	protected void onEntryGames(Canvas canvas) {
		drawColor(canvas);
		drawLeft(canvas);
		drawTop(canvas);
		drawBackground(canvas);
	}
	
	@Override
	protected void onSelectGames(Canvas canvas) {
		
	}
	
	@Override
	protected void onDrawGames(Canvas canvas) {
		drawColor(canvas);
		drawLeft(canvas);
		drawTop(canvas);
		drawBackground(canvas);
	}

	@Override
	protected void onTouch(float x, float y) {
		
	}

	@Override
	protected void onEndingGames(Canvas canvas) {
		
	}		
}
