package com.location.mylbs.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import com.location.mylbs.entity.EntityRequest;
import com.location.mylbs.entity.EntityResponse;

public class HttpClass {
	
	private void closeStream(final InputStream stream) {
		if (stream == null)
			return;
		
		try {				
			stream.close();
		} catch (final IOException e) {
			
		}
	}
	
	private void closeStream(final OutputStream stream) {
		if (stream == null)
			return;
		
		try {				
			stream.close();
		} catch (final IOException e) {
			
		}
	}	
		
	public EntityResponse request(final EntityRequest entityRequest) {
		final String url = entityRequest.getUrl();		
		final boolean string = entityRequest.getString();
		final String charset = entityRequest.getCharset();
		final Context context = entityRequest.getContext();
		
		int times = 3;
		while (times-- > 0) {
			final AndroidHttpClient client = AndroidHttpClient.newInstance("", context);			
			final HttpGet get = new HttpGet(url);
			HttpResponse response = null;
									
			try {
				response = client.execute(get);				
			} catch (final ClientProtocolException e) {
				client.close();				
    			continue;
			} catch (final IOException e) {
				client.close();				
    			continue;
			}
			
			final StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				client.close();				
    			continue;
			}
			
			final HttpEntity entity = response.getEntity();  
            InputStream in = null;
            
			try {
				in = entity.getContent();
			} catch (final IllegalStateException e) {
				client.close();				
    			continue;
			} catch (final IOException e) {
				client.close();				
    			continue;
			}
			
			if (in != null) {
				final EntityResponse entityResponse = new EntityResponse();
				entityResponse.setString(string);
				
				if (string) {
					final ByteArrayOutputStream outs = new ByteArrayOutputStream();
	            	final byte[] buffer = new byte[2048];
	            	int count = 0;
	                int length = -1;
	                
	                try {
						while ((length = in.read(buffer)) != -1) {
							outs.write(buffer, 0, length);
							count += length;
						}
						
						if (count > 0) {
		                	final String content = new String(outs.toByteArray(), charset);
		                	entityResponse.setBody(content);                    	
		                } else                    	
		                	entityResponse.setBody("");
						
						closeStream(outs);
						closeStream(in);
						client.close();					
					} catch (final IOException e) {
						closeStream(outs);
						closeStream(in);
						client.close();					
		    			continue;
					}
				} else {
					entityResponse.setStream(in);
	            	entityResponse.setClient(client);
				}
				
				return entityResponse;
			}			
		}
		
		return null;
	}
}
