package com.location.hls.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileManager {
	private static final String mHome = "hls";
	private static final String mIcons = "images";
	
	public static String getHome() {
		final File root = Environment.getExternalStorageDirectory();
		if (root == null)
			return null;
				
		return root.getPath() + "/" + mHome;		
	}
				
	public static String getIcons() {
		final String home = getHome();
		if (home == null)
			return null;
		
		final String icons = home + "/" + mIcons;
		final File file = new File(icons);
		if (!file.exists() && !file.mkdirs())
			return null;
		
		return icons;
	}
	
	public static String urlFilename(final String url) {
		if (url == null)
			return null;
		
		final int length = url.length();
		final int p = url.lastIndexOf("/");
		if (p > -1 && p < length - 1) {
			final String filename = url.substring(p + 1);
			return filename;
		}
		
		return null;
	}
	
	public static void closeStream(final OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (final IOException e) {
				
			}
		}
	}
	
	public static boolean saveFile(final InputStream in, final String saveDir, final String filename) {
		boolean success = false;
		if (in == null || saveDir == null || filename == null)
			return success;
		
		final File file = new File(saveDir, filename);
		OutputStream out = null;
		
		try {
			out = new FileOutputStream(file, false);
		} catch (final FileNotFoundException e) {
			return success;
		}
		
		final byte[] buffer = new byte[8192];
				
		try {
			while (true) {
				final int len = in.read(buffer);
				if (len > 0)
					out.write(buffer, 0, len);
				else {
					if (len == -1)
						break;
				}
			}
			
			success = true;
		} catch (final IOException e) {
			
		} finally {
			closeStream(out);
		}
		
		return success;
	}
}
