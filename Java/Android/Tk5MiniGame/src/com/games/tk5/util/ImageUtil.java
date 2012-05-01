package com.games.tk5.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class ImageUtil {
	
	public static Bitmap getImage(Context context, int res, int[] filterColor, Rect rect) throws Exception {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		bitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
		if (filterColor == null)
			return bitmap;
		else
			return filterColor(bitmap, filterColor);		
	}
	
	public static Bitmap getImage(Context context, int res, int[] filterColor, int left, int top) throws Exception {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		int x = left;
		if (left > 0) {
			if (left >= bitmap.getWidth())
				throw new Exception();
			else
				x = left;
		}
		
		int y = top;
		if (top > 0) {
			if (top >= bitmap.getHeight())
				throw new Exception();
			else
				y = top;
		}
				
		bitmap = Bitmap.createBitmap(bitmap, x, y, bitmap.getWidth() - x, bitmap.getHeight() - y);
		if (filterColor == null)
			return bitmap;
		else
			return filterColor(bitmap, filterColor);		
	}
	
	public static Bitmap getImage(Context context, int res, int[] filterColor) throws Exception {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());		
		bitmap = Bitmap.createBitmap(bitmap, 0, rect.top, rect.right - rect.left, rect.bottom - rect.top);
		if (filterColor == null)
			return bitmap;
		else
			return filterColor(bitmap, filterColor);		
	}
	
	public static Bitmap getImage(Context context, int res) throws Exception {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());				
		return Bitmap.createBitmap(bitmap, 0, rect.top, rect.right - rect.left, rect.bottom - rect.top);
	}
	
	public static Bitmap getImage(Context context, int res, Rect rect) throws Exception {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);						
		return Bitmap.createBitmap(bitmap, 0, rect.top, rect.right - rect.left, rect.bottom - rect.top);
	}
	
	public static Bitmap setAlpha(Bitmap bitmap, int alpha) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);			
		paint.setAlpha(alpha);
		
		Bitmap panel = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(panel);
		canvas.drawBitmap(bitmap, 0f, 0f, paint);
		return panel;
	}
	
	public static Bitmap filterColor(Bitmap bitmap, int[] filterColors) throws Exception {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] argbs = new int[width * height];
		bitmap.getPixels(argbs, 0, width, 0, 0, width, height);
				
		for (int i = 0;i < argbs.length;i++) {
			for (int filterColor : filterColors) {
				if (argbs[i] == filterColor)
					argbs[i] = Color.TRANSPARENT; 
			}
		}
		
		return Bitmap.createBitmap(argbs, width, height, Bitmap.Config.ARGB_8888);	
	}
}
