package com.apps.game.market.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.apps.game.market.entity.EntityDownload;

import android.content.Context;
import android.os.Environment;

public class FileManager {
	private final String app = "gamemarket";
	private String rootPath;
	private String appsPath;
	private String cachePath;

	public FileManager(Context context) {
		init(context);
	}

	private void init(Context context) {
		File root = null;
		boolean exist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (exist)
			root = Environment.getExternalStorageDirectory();
		else
			context.getFilesDir();

		rootPath = root.getPath() + "/" + app;
		appsPath = rootPath + "/app";
		cachePath = rootPath + "/cache";

		File path = new File(appsPath);
		if (!path.exists())
			path.mkdirs();

		path = new File(cachePath);
		if (!path.exists())
			path.mkdirs();
		
		appsPath += "/";
		cachePath += "/";
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getAppsPath() {
		return appsPath;
	}

	public String getCachePath() {
		return cachePath;
	}
	
	public String getFilename(String url) {
		int p = url.lastIndexOf("/");
		if (p == -1 || p == url.length() - 1)
			return null;
		
		return url.substring(p);
	}
	
	public EntityDownload getDownloadStream(String filename, long size) {
		EntityDownload entityDownload = new EntityDownload();
		String path = getAppsPath() + filename;		
		File file = new File(path);
		entityDownload.setFile(file);
		
		if (file.exists()) {
			if (file.length() == size) {
				entityDownload.setPosition(size);
				entityDownload.setAppend(false);
				entityDownload.setFinish(true);
				return entityDownload;
			} else {
				if (file.length() > size) {
					if (!file.delete())
						return null;
						
					entityDownload.setPosition(0);
					entityDownload.setAppend(false);
				} else {
					entityDownload.setPosition(file.length());
					entityDownload.setAppend(true);
				}
			}
		}
		
		entityDownload.setFinish(false);
		FileOutputStream outStream = null;
		
		try {
			outStream = new FileOutputStream(file, entityDownload.getAppend());
		} catch (FileNotFoundException e) {
			return null;
		}
		
		BufferedOutputStream bufferStream = new BufferedOutputStream(outStream);
		entityDownload.setOutStream(outStream);
		entityDownload.setBufferStream(bufferStream);
		return entityDownload;
	}
}
