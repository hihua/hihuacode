package com.apps.game.market.util;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.entity.EntityImageCache;

import android.graphics.drawable.Drawable;

public class ImageCache {
	private static ImageCache mImageCache;
	private final int mTotal = 15;
	private final List<EntityImageCache> mList = new Vector<EntityImageCache>();
	
	public synchronized static ImageCache getInstance() {				
		if (mImageCache == null)
			mImageCache = new ImageCache();
		
		return mImageCache;
	}
	
	private ImageCache() {

	}
			
	public void set(String url, Drawable drawable) {
		synchronized (mList) {
			for (EntityImageCache imageCache : mList) {
				if (imageCache.getUrl().equals(url)) {
					mList.remove(imageCache);
					break;
				}
			}
			
			if (mList.size() >= mTotal)				
				mList.remove(0);				
							
			EntityImageCache imageCache = new EntityImageCache();
			imageCache.setUrl(url);
			imageCache.setDrawable(drawable);
			mList.add(imageCache);			
		}		
	}
	
	public Drawable get(String url) {
		synchronized (mList) {
			for (EntityImageCache imageCache : mList) {
				if (imageCache.getUrl().equals(url)) {
					return imageCache.getDrawable();				
				}
			}
			
			return null;
		}		
	}		
}