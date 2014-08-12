package com.location.hlsd.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpClass {
	
	private static void closeStream(final InputStream stream) {
		if (stream == null)
			return;
		
		try {				
			stream.close();
		} catch (final IOException e) {
			
		}
	}
		
	public static boolean request(final String webUrl) {		
		byte[] buffer = new byte[2048];
		boolean success = false;
		
		int times = 3;
		while (times-- > 0) {
			URL url = null;
			
			try {
				 url = new URL(webUrl);
			} catch (MalformedURLException e) {			
				return false;
			} 
			
			HttpURLConnection connection = null;
			
			try {
				connection = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {				
				return false;
			}					
						
			try {
				connection.setRequestMethod("GET");
			} catch (ProtocolException e) {
				return false;
			}
			
			connection.setDoInput(true);
			connection.setUseCaches(false);
			
			try {
				connection.connect();
			} catch (IOException e) {
				continue;
			}
			
			try {
				connection.getResponseCode();				
			} catch (IOException e) {
				connection.disconnect();
				continue;
			}
									
			InputStream in = null;
			
			try {
				in = connection.getInputStream();			
			} catch (IOException e) {
				connection.disconnect();
				continue;
			}
			
			if (in != null) {
				try {
					while (true) {
						int length = in.read(buffer);
						if (length > 0)
							continue;
						else
							break;
					}
					
					success = true;
				} catch (IOException e) {
					
				} finally {
					closeStream(in);
					connection.disconnect();
				}
				
				if (success)
					break;
			} else {
				success = true;
				connection.disconnect();
				break;
			}
		}
		
		return success;
	}	
}
