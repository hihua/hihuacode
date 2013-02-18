package com.apps.game.market.task;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.apps.game.market.entity.EntityImage;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.request.RequestImage;
import com.apps.game.market.util.HttpClass;
import com.apps.game.market.util.ImageCache;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.ImageView;

public class TaskImage implements Runnable, Callback {
	private boolean mQuit = false;
	private boolean mLock = false;
	private boolean mCache = true;
	private final Map<ImageView, String> mTasks = new HashMap<ImageView, String>();
	private final HttpClass mHttpClass = new HttpClass();
	private final ImageCache mImageCache = ImageCache.getInstance();
	private final Handler mHandler = new Handler(this);
	private final Thread mThread = new Thread(this);
	private final Object mSync = new Object();
	
	public TaskImage() {
				
	}
	
	public void cache(boolean cache) {
		mCache = cache;
	}
	
	public void setUrl(String url, ImageView imageView) {	
		if (!mTasks.containsKey(imageView)) {
			final RequestImage requestImage = new RequestImage(url, imageView, mCache);
			requestImage.request();				
		}
		
		mTasks.put(imageView, url);			
	}
	
	public void start() {
		mThread.start();
	}
	
	public void stop() {
		mQuit = true;
		synchronized (mSync) {
			mSync.notifyAll();			
		}
	}
	
	public void lock(boolean lock) {
		mLock = lock;
		if (!mLock) {
			synchronized (mSync) {
				mSync.notifyAll();			
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (mSync) {
				try {
					mSync.wait();
				} catch (InterruptedException e) {			
					
				}				
			}			
			
			if (mQuit)
				break;
			
			if (mLock)
				continue;
					
			for (Entry<ImageView, String> entry : mTasks.entrySet()) {
				final ImageView imageView = entry.getKey();
				final String url = entry.getValue();
								
				Drawable drawable = mImageCache.get(url);
				if (drawable == null) {
					final EntityRequest req = new EntityRequest(); 
					req.setHeader(null);
					req.setString(false);
					req.setUrl(url);					
					EntityResponse resp = mHttpClass.request(req);
					if (resp != null) {
						final InputStream stream = resp.getStream();
						drawable = Drawable.createFromStream(stream, "");
						resp.close();
						if (drawable != null) {
							if (mCache)
								mImageCache.set(url, drawable);
							
							final EntityImage entityImage = new EntityImage();
							entityImage.setUrl(url);
							entityImage.setImageView(imageView);
							entityImage.setDrawable(drawable);
											
							Message msg = mHandler.obtainMessage();
							msg.what = 0;
							msg.obj = entityImage;				
							mHandler.sendMessage(msg);
						}			
					}
				}									
				
				if (mLock || mQuit)
					break;
			}
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		EntityImage entityImage = (EntityImage) msg.obj;
		ImageView imageView = entityImage.getImageView();
		Drawable drawable = entityImage.getDrawable();
		imageView.setImageDrawable(drawable);
		return true;
	}
}
