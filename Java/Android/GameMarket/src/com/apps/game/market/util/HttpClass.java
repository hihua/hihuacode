package com.apps.game.market.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;

public class HttpClass {
	private final int mConnectTimeout = 30000;
	private final int mReadTimeout = 30000;
	
	private void setHeader(HttpURLConnection connection, Map<String, String> header) {		
		for (Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();			
			connection.setRequestProperty(key, value);
		}
	}
	
	private void closeStream(InputStream stream) {
		if (stream == null)
			return;
		
		try {				
			stream.close();
		} catch (IOException e) {
			
		}
	}
	
	private void closeStream(OutputStream stream) {
		if (stream == null)
			return;
		
		try {				
			stream.close();
		} catch (IOException e) {
			
		}
	}
	
	private void closeReader(Reader reader) {
		if (reader == null)
			return;
		
		try {				
			reader.close();
		} catch (IOException e) {
			
		}
	}

	public EntityResponse request(EntityRequest req) {
		Map<String, String> header = req.getHeader();
		String webUrl = req.getUrl();
		String body = req.getBody();
		String charset = req.getCharset();
		
		int times = 3;
		while (times > 0) {
    		URL url = null;
    		
    		try {
    			 url = new URL(webUrl);
    		} catch (MalformedURLException e) {			
    			return null;
    		}
    		
    		HttpURLConnection connection;
    		
    		try {
    			connection = (HttpURLConnection)url.openConnection();
    		} catch (IOException e) {
    			times--;
    			continue;
    		}
    		
    		if (mConnectTimeout > 0)
    			connection.setConnectTimeout(mConnectTimeout);
    	
    		if (mReadTimeout > 0)
    			connection.setReadTimeout(mReadTimeout);
    		    		    		
    		if (header != null)
    			setHeader(connection, header);
    		
    		if (body == null)  {
    			try {
    				connection.setRequestMethod("GET");
    			} catch (ProtocolException e) {				
    				return null;
    			}
    						
    			connection.setDoOutput(true);
    			connection.setDoInput(true);
    			
    			try {
    				connection.connect();
    			} catch (IOException e) {				
    				times--;
        			continue;
    			}
    		} else {
    			try {
    				connection.setRequestMethod("POST");
    			} catch (ProtocolException e) {				
    				return null;
    			}
    			
    			connection.setDoOutput(true);
    			connection.setDoInput(true);
    						
    			try {
    				connection.connect();
    			} catch (IOException e) {				
    				times--;
        			continue;
    			}
    		
    			byte[] requestByte = null;
    			
    			try {
    				requestByte = body.getBytes(charset);
    			} catch (UnsupportedEncodingException e) {
    				connection.disconnect();
    				return null;
    			}
    			
    			OutputStream outputStream = null;
    			
    			try {
    				outputStream = connection.getOutputStream();
    			} catch (IOException e) {
    				connection.disconnect();
    				times--;
        			continue;
    			}
    			
    			try {
    				outputStream.write(requestByte);
    			} catch (IOException e) {
    				closeStream(outputStream);
    				connection.disconnect();
    				times--;
        			continue;
    			}
    			
    			closeStream(outputStream);						
    		}
    		
    		int responseCode = HttpURLConnection.HTTP_OK;
    		
    		try {
    			responseCode = connection.getResponseCode();    			
    		} catch (IOException e) {
    			connection.disconnect();
    			times--;
    			continue;
    		}
    		
    		if (responseCode != HttpURLConnection.HTTP_OK) {    			    			
    			connection.disconnect();
    			times--;
    			continue;		
			} else {
				EntityResponse resp = getBody(connection, req);				
				if (resp == null) {					
					times--;
					continue;
				} else
					return resp;
			}    		
    	}
    	
    	return null;
	}
	
	private EntityResponse getBody(HttpURLConnection connection, EntityRequest req) {
		String charset = req.getCharset();
		boolean string = req.getString();
				
		EntityResponse resp = new EntityResponse();
		resp.setHeaders(connection.getHeaderFields());
		
    	InputStream inputStream = getInputStream(connection);
    	if (!string) {
    		resp.setConnection(connection);
    		resp.setStream(inputStream);
    		return resp;
    	}
    					
		InputStreamReader streamReader = null;
		
		try {
			streamReader = new InputStreamReader(inputStream, charset);
		} catch (UnsupportedEncodingException e) {
			closeStream(inputStream);
			connection.disconnect();
			return null;
		}
		
		BufferedReader bufferReader = new BufferedReader(streamReader);
		StringBuilder sb = new StringBuilder();
		String s = "";
		
		try {
			while ((s = bufferReader.readLine()) != null) {				
				sb.append(s);
				sb.append("\n");
			}
			
			closeStream(inputStream);
			closeReader(streamReader);
			closeReader(bufferReader);
			connection.disconnect();
			return resp;
		} catch (IOException e) {
			closeStream(inputStream);
			closeReader(streamReader);
			closeReader(bufferReader);
			connection.disconnect();
			return null;
		}
    }
           
    private InputStream getInputStream(HttpURLConnection connection) {
    	InputStream inputStream = null;
    	
		try {
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			return null;
		}
		
		if (inputStream == null)
			return null;
		
		String contentEncoding = connection.getHeaderField("Content-Encoding");
		if (contentEncoding != null) {
			if (contentEncoding.equals("gzip")) {
				try {
					InputStream gzip = new GZIPInputStream(inputStream);
					return gzip;
				} catch (IOException e) {
					closeStream(inputStream);
					return null;
				}
			} else {
				closeStream(inputStream);
				return null;
			}
		}
    	
		return inputStream;
    }
}
