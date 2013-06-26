package com.apps.game.market.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.apps.game.market.entity.EntityImageCache;
import com.apps.game.market.global.GlobalObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class ImageCache {
	private static ImageCache mImageCache;
	private final int mTotal = 30;
	private final List<EntityImageCache> mList = new Vector<EntityImageCache>();
	private final FileManager mFileManager;
	private final String mCachePath;
	
	public synchronized static ImageCache getInstance() {				
		if (mImageCache == null)
			mImageCache = new ImageCache();
		
		return mImageCache;
	}
	
	private ImageCache() {
		final GlobalObject globalObject = GlobalObject.globalObject;
		mFileManager = globalObject.getFileManager();
		mCachePath = mFileManager.getCachePath();
	}
			
	public void set(String url, Bitmap bitmap) {
		synchronized (mList) {
			for (EntityImageCache imageCache : mList) {
				if (imageCache.getUrl().equals(url)) {
					mList.remove(imageCache);
					break;
				}
			}
			
			if (mList.size() >= mTotal)				
				mList.remove(0);				
							
			final EntityImageCache imageCache = new EntityImageCache();
			imageCache.setUrl(url);
			imageCache.setBitmap(bitmap);
			mList.add(imageCache);
			setBitmap(url, bitmap);
		}		
	}
	
	public Bitmap get(String url) {
		synchronized (mList) {
			for (EntityImageCache imageCache : mList) {
				if (imageCache.getUrl().equals(url)) {
					return imageCache.getBitmap();
				}
			}
									
			return getBitmap(url);
		}		
	}		
	
	private void setBitmap(String url, Bitmap bitmap) {
		String filename = getFilename(url);
		if (filename == null)
			return;
		
		filename = mCachePath + filename;	
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(filename, false);
		} catch (FileNotFoundException e) {
			return;
		}
		
		bitmap.compress(CompressFormat.PNG, 100, out);
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	private Bitmap getBitmap(String url) {
		String filename = getFilename(url);
		if (filename == null)
			return null;
		
		filename = mCachePath + filename;
		final File file = new File(filename);
		if (!file.exists())
			return null;
		
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			return null;
		}
		
		final Bitmap bitmap = BitmapFactory.decodeStream(in);
		return bitmap;
	}
	
	private String getFilename(String url) {
		int p = url.lastIndexOf("/");
		if (p > -1 && p < url.length() - 1) {
			String filename = url.substring(p + 1);
			int a = filename.indexOf("?");
			int b = filename.indexOf("=");
			if (a != -1 && b != -1 && b < filename.length() - 1 && b > a)
				filename = filename.substring(b + 1);
			
			return filename;
		} else
			return null;
	}
}