package com.games.tk5.square;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.games.tk5.Sprite;
import com.games.tk5.ViewBase;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

public class SquareLeft extends Sprite {
	private Bitmap m_Bitmap_Dest;
	
	public SquareLeft(ViewBase viewBase) {
		super(viewBase);		
	}
	
	public boolean init(Context context, int res) {
		try {
			m_Bitmap = ImageUtil.getImage(context, res);			
			setWidth(m_Bitmap.getWidth());
			setHeight(m_Bitmap.getHeight());			
			return true;
		} catch (Exception e) {			
			Logs.LogsError(e);
			return false;
		}		
	}
	
	public void setValues(int[] values) {		
		m_Bitmap_Dest = getBitmap().copy(Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Canvas canvas = new Canvas(m_Bitmap_Dest);
	}
			
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_Bitmap_Dest, getLeft(), getTop(), getPaint());
	}	
}
