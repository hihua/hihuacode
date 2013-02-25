package com.apps.game.market.request;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.widget.ImageView;

import com.apps.game.market.entity.EntityImage;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.util.ImageCache;

public class RequestImage extends RequestBase {
	private String mUrl = "";
	private ImageView mImageView;
	private boolean mCache = true;
		
	public RequestImage(String url, ImageView imageView, boolean cache) {
		mUrl = url;		
		mImageView = imageView;
		mCache = cache;
	}		
	
	@Override
	protected void onTask(EntityRequest req) {
		ImageCache imageCache = ImageCache.getInstance();		
		req.setHeader(null);
		req.setString(false);
		req.setUrl(mUrl);
		EntityResponse resp = mHttpClass.request(req);
		if (resp != null) {
			InputStream stream = resp.getStream();
			Bitmap bitmap = BitmapFactory.decodeStream(stream);			
			resp.close();
			if (bitmap != null) {
				if (mCache)
					imageCache.set(mUrl, bitmap);
				
				EntityImage entityImage = new EntityImage();
				entityImage.setUrl(mUrl);
				entityImage.setImageView(mImageView);
				entityImage.setBitmap(bitmap);
								
				Message msg = mHandler.obtainMessage();
				msg.what = -1;
				msg.obj = entityImage;				
				mHandler.sendMessage(msg);
				return;
			}			
		}
		
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_menu_gallery);		
		EntityImage entityImage = new EntityImage();
		entityImage.setUrl(mUrl);
		entityImage.setImageView(mImageView);
		entityImage.setBitmap(bitmap);
						
		Message msg = mHandler.obtainMessage();
		msg.what = -1;
		msg.obj = entityImage;				
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onMessage(Message msg) {
		EntityImage entityImage = (EntityImage) msg.obj;
		ImageView imageView = entityImage.getImageView();
		Bitmap bitmap = entityImage.getBitmap();
		if (imageView != null)
			imageView.setImageBitmap(bitmap);
		
		
	}
}
