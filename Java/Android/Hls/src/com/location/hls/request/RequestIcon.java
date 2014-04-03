package com.location.hls.request;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.os.Message;

import com.location.hls.entity.EntityRequest;
import com.location.hls.entity.EntityResponse;
import com.location.hls.handle.HandleIcon;
import com.location.hls.util.FileManager;
import com.location.hls.util.ThreadPool;

public final class RequestIcon extends RequestBase {
	private List<String> mUrls;
	private String mSaveDir;
	private HandleIcon mHandle;
		
	public RequestIcon(final Context context, final ThreadPool threadPool, final HandleIcon handle) {
		super(context, threadPool);
		mHandle = handle;
	}
			
	public void request(final List<String> urls, final String saveDir) {
		mUrls = urls;
		mSaveDir = saveDir;
		
		super.request();
	}

	@Override
	public boolean handleMessage(final Message msg) {
		mHandle.onIcon();
		return true;
	}

	@Override
	protected void onTask(final EntityRequest entityRequest) {
		for (final String url : mUrls) {
			entityRequest.setUrl(url);
			entityRequest.setString(false);
			
			final EntityResponse entityResponse = mHttpClass.request(entityRequest);
			if (entityResponse != null) {
				final InputStream in = entityResponse.getStream();
				final String filename = FileManager.urlFilename(url);
				FileManager.saveFile(in, mSaveDir, filename);				
				entityResponse.close();						
			}
		}			
		
		if (mHandler != null)
			mHandler.sendEmptyMessage(0);
	}

	@Override
	public void destroy() {
		mHandler = null;
	}
}
