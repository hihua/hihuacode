package com.apps.game.market.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.apps.game.market.entity.EntityDownload;

import android.content.Context;
import android.os.Environment;

public class FileManager {
	private final String app = "gamemarket";
	private String rootPath;
	private String appsPath;
	private String cachePath;
	private String upgradePath;

	public FileManager(Context context) {
		init(context);
	}

	private void init(Context context) {
		File root = null;
		final boolean exist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (exist)
			root = Environment.getExternalStorageDirectory();
		else
			context.getFilesDir();

		rootPath = root.getPath() + "/" + app;
		appsPath = rootPath + "/app";
		cachePath = rootPath + "/cache";
		upgradePath = rootPath + "/upgrade";

		File path = new File(appsPath);
		if (!path.exists())
			path.mkdirs();

		path = new File(cachePath);
		if (!path.exists())
			path.mkdirs();
		
		path = new File(upgradePath);
		if (!path.exists())
			path.mkdirs();
		
		appsPath += "/";
		cachePath += "/";
		upgradePath += "/";
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
	
	public String getUpgradePath() {
		return upgradePath;
	}
	
	public long appExists(String packageName) {
		final String dir = getAppsPath();
		final File file = new File(dir, packageName + ".apk");
		if (file.exists() && file.isFile())
			return file.length();			
		else
			return -1;
	}
	
	public String getFilename(String url) {
		final int p = url.lastIndexOf("/");
		if (p == -1 || p == url.length() - 1)
			return null;
		
		return url.substring(p);
	}
	
	public EntityDownload getDownloadStream(String dir, String filename, long size) {
		final EntityDownload entityDownload = new EntityDownload();
		final String path = dir + filename;		
		final File file = new File(path);
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
					//entityDownload.setPosition(file.length());
					//entityDownload.setAppend(true);
					
					if (!file.delete())
						return null;
					
					entityDownload.setPosition(0);
					entityDownload.setAppend(false);
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
		
		final BufferedOutputStream bufferStream = new BufferedOutputStream(outStream);
		entityDownload.setOutStream(outStream);
		entityDownload.setBufferStream(bufferStream);
		return entityDownload;
	}
}
