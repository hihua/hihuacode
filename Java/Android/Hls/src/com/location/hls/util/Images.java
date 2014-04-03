package com.location.hls.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Images {
	
	public static Drawable getIcon(final String name) {
		final String icons = FileManager.getIcons();
		if (icons == null)
			return null;
		
		final String ext = ".png";
		final String filename = name + ext;
		final File file = new File(icons, filename);
		if (!file.exists())
			return null;
		
		InputStream in = null;
		
		try {
			in = new FileInputStream(file);
		} catch (final FileNotFoundException e) {
			return null;
		}
		
		Bitmap bitmap = null;
		
		try {
			bitmap = BitmapFactory.decodeStream(in);				
		} catch (final Exception e) {
			
		}
		
		try {
			in.close();
		} catch (final IOException e) {
			
		}
		
		if (bitmap != null)
			return new BitmapDrawable(bitmap);
		else
			return null;
	}
}
