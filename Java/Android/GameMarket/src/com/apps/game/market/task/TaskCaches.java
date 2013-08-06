package com.apps.game.market.task;

import java.io.File;

import com.apps.game.market.view.callback.CallBackCacheFinish;

import android.os.AsyncTask;

public class TaskCaches extends AsyncTask<String, Integer, CallBackCacheFinish> {
	private final long mMax = 1024 * 1024 * 10;
	private final CallBackCacheFinish mCallBack;
	
	public TaskCaches(CallBackCacheFinish callBack) {
		mCallBack = callBack;
	}

	@Override
	protected CallBackCacheFinish doInBackground(String... params) {
		final String cachePath = params[0];
		final File file = new File(cachePath);
		final File[] files = file.listFiles();
		if (files != null) {
			long total = 0;
			for (File f : files) {
				total += f.length();
				if (total > mMax)
					break;
			}
					
			if (total > mMax) {
				long len = 0;
				for (File f : files) {
					len = f.length();
					if (f.delete()) {
						total -= len;
						if (total < mMax)
							break;
					}					
				}
			}						
		}
		
		return mCallBack;
	}

	@Override
	protected void onPostExecute(CallBackCacheFinish result) {
		result.onCacheFinish();
	}	
}
