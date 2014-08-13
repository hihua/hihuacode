package com.location.hls.util;

import java.io.File;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;

public class Images {
			
	public static BitmapDescriptor getIcon(final String name) {
		final String icons = FileManager.getIcons();
		if (icons == null)
			return null;
		
		final String ext = ".png";
		final String filename = name + ext;
		final File file = new File(icons, filename);
		if (!file.exists())
			return null;
				
		final String filepath = file.getAbsolutePath();
		final BitmapDescriptor bitmap = BitmapDescriptorFactory.fromPath(filepath);		
		return bitmap;
	}
}
