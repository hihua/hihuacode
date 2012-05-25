package com.games.tk5.square;

import android.content.Context;
import android.graphics.Canvas;

import com.games.tk5.Sprite;
import com.games.tk5.ViewBase;
import com.games.tk5.util.ImageUtil;
import com.games.tk5.util.Logs;

public class SquareScore extends Sprite {

	public SquareScore(ViewBase viewBase) {
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

	@Override
	public void draw(Canvas canvas) {
		
	}	
}
