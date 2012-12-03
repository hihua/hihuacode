package com.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.util.Logs;

public class RequestBase {
	protected final Logs Log = Logs.getInstance();	
	private String m_ReqCharset = "UTF-8";
	private String m_RespCharset = "UTF-8";
	private int m_ConnectTimeout = 30000;
	private int m_ReadTimeout = 30000;
	private final int m_Times = 3;
	private Map<String, List<String>> m_Headers;
	
	protected RequestBase() {
		
	}
	
	private void closeStream(OutputStream stream) {
		if (stream != null) {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	private void closeStream(InputStream stream) {
		if (stream != null) {
			try {				
				stream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	private void closeStream(InputStreamReader stream) {
		if (stream != null) {
			try {				
				stream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	private void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {				
				reader.close();				
			} catch (IOException e) {
				
			}
		}
	}
	    
	protected void setReqCharset(String charset) {
    	m_ReqCharset = charset;
	}
	
	protected void setRespCharset(String charset) {
		m_RespCharset = charset;
	}		
	
	protected void setConnectTimeout(int timeout) {
		m_ConnectTimeout = timeout;
	}
	
	protected void setReadTimeout(int timeout) {
		m_ReadTimeout = timeout;
	}
	
	private void setHeader(HttpURLConnection connection, HashMap<String, String> header) {		
		for (Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			connection.setRequestProperty(key, value);
		}
	}
	
	protected String requestClient(String webUrl, HashMap<String, String> header, String body) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, m_ConnectTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, m_ReadTimeout);
		
		int times = m_Times;
    	while (times-- > 0) {
    		if (body == null) {
    			HttpGet httpGet = new HttpGet(webUrl);    			
    			if (header != null) {
    				for (Entry<String, String> entry : header.entrySet()) {
    					String key = entry.getKey();
    					String value = entry.getValue();
    					httpGet.addHeader(key, value);
    				}
    			}
    			    			    			
    			
    			HttpResponse response = null;
    			
    			try {
    				response = httpclient.execute(httpGet);    				
    	        } catch (ClientProtocolException e) {
    	        	Log.writeLogs(e.toString());	        	
    			} catch (IOException e) {
    				Log.writeLogs(e.toString());
    			}			

    			if (response == null)    				
    				continue;
    			    			
    			int code = 0;			
    			StatusLine status = response.getStatusLine();
    			if (status == null) {
    				httpGet.releaseConnection();
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    			
    			code = status.getStatusCode();
    			if (code != 200)
    				Log.writeLogs("ResponseCode: " + code);			
    			
    			HttpEntity entity = response.getEntity();
    			if (entity == null) {
    				httpGet.releaseConnection();
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    									
    			String content = null;
    			
    			try {
    				content = EntityUtils.toString(entity);
    			} catch (ParseException e) {
    				Log.writeLogs(e.toString());	   
    			} catch (IOException e) {
    				Log.writeLogs(e.toString());
    			}
    			
    			if (content == null) {				
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    			
    			if (code != 200) {
    				Log.writeLogs(content);				
    				continue;
    			} else
    				httpGet.releaseConnection();
    			
    			Header[] headers = response.getAllHeaders();
    			if (headers != null) {
    				m_Headers = new HashMap<String, List<String>>();
    				for (int i = 0;i < headers.length;i++) {
    					String name = headers[i].getName();
    					String value = headers[i].getValue();
    					
    					if (m_Headers.containsKey(name)) {
    						List<String> list = m_Headers.get(name);
    						list.add(value);
    					} else {
    						List<String> list = new Vector<String>();
    						list.add(value);
    						m_Headers.put(name, list);
    					}
    				}
    			}
    			
    			httpclient.getConnectionManager().shutdown();
    			return content;
    		} else {
    			HttpPost httpPost = new HttpPost(webUrl);
    			if (header != null) {
    				for (Entry<String, String> entry : header.entrySet()) {
    					String key = entry.getKey();
    					String value = entry.getValue();
    					httpPost.addHeader(key, value);
    				}
    			}
    			
    			StringEntity string = null;
    			
    			try {
    				string = new StringEntity(body, m_ReqCharset);
    				string.setContentType("application/x-www-form-urlencoded");
    				httpPost.setEntity(string);
    			} catch (UnsupportedEncodingException e) {
    				Log.writeLogs(e.toString());
    				httpPost.releaseConnection();
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    			
    			HttpResponse response = null;
    			
    			try {
    				response = httpclient.execute(httpPost);                         
    	        } catch (ClientProtocolException e) {
    	        	Log.writeLogs(e.toString());	        	
    			} catch (IOException e) {
    				Log.writeLogs(e.toString());
    			}
    			
    			if (response == null)    				
    				continue;
    			    			
    			int code = 0;			
    			StatusLine status = response.getStatusLine();
    			if (status == null) {
    				httpPost.releaseConnection();
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    			
    			code = status.getStatusCode();
    			if (code != 200)
    				Log.writeLogs("ResponseCode: " + code);			
    			
    			HttpEntity entity = response.getEntity();
    			if (entity == null) {		
    				httpPost.releaseConnection();
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    									
    			String content = null;
    			
    			try {
    				content = EntityUtils.toString(entity);
    			} catch (ParseException e) {
    				Log.writeLogs(e.toString());	   
    			} catch (IOException e) {
    				Log.writeLogs(e.toString());
    			}
    			
    			if (content == null) {				
    				httpclient.getConnectionManager().shutdown();
    				continue;
    			}
    			
    			if (code != 200) {
    				Log.writeLogs(content);				
    				continue;
    			} else
    				httpPost.releaseConnection();
    			
    			Header[] headers = response.getAllHeaders();
    			if (headers != null) {
    				m_Headers = new HashMap<String, List<String>>();
    				for (int i = 0;i < headers.length;i++) {
    					String name = headers[i].getName();
    					String value = headers[i].getValue();
    					
    					if (m_Headers.containsKey(name)) {
    						List<String> list = m_Headers.get(name);
    						list.add(value);
    					} else {
    						List<String> list = new Vector<String>();
    						list.add(value);
    						m_Headers.put(name, list);
    					}
    				}
    			}
    			
    			httpclient.getConnectionManager().shutdown();
    			return content;
    		}
    	}
    	
    	return null;
	}
	
	protected String request(String webUrl, HashMap<String, String> header, String body) {
		m_Headers = null;
    	int times = m_Times;
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
    		
    		if (m_ConnectTimeout > 0)
    			connection.setConnectTimeout(m_ConnectTimeout);
    	
    		if (m_ReadTimeout > 0)
    			connection.setReadTimeout(m_ReadTimeout);
    		    		    		
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
    				requestByte = body.getBytes(m_ReqCharset);
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
    		
    		int responseCode = 200;
    		
    		try {
    			responseCode = connection.getResponseCode();    			
    		} catch (IOException e) {
    			times--;
    			continue;
    		}
    		
    		if (responseCode != HttpURLConnection.HTTP_OK) {
    			StringBuilder sb = new StringBuilder();    			
    			sb.append(webUrl);
    			if (body != null) {
    				sb.append("\n");
        			sb.append(body);
    			}    			
    			
    			String response = getBody(connection);
    			if (response != null) {
    				sb.append("\n");
    				sb.append(response);
    			}
    			
    			connection.disconnect();
    			Log.writeLogs(sb.toString());
    			Log.writeLogs("ResponseCode: " + responseCode);
    			
				return null;			
			} else {
				String response = getBody(connection);
				connection.disconnect();
				if (response == null) {					
					times--;
					continue;
				} else {
					m_Headers = connection.getHeaderFields();
					return response;
				}
			}    		
    	}
    	
    	return null;
    }
	
	public Map<String, List<String>> getHeader() {
		return m_Headers;
	}
    
    private String getBody(HttpURLConnection connection) {
    	InputStream inputStream = getInputStream(connection);
		if (inputStream == null)						
			return null;
						
		InputStreamReader streamReader = null;
		
		try {
			streamReader = new InputStreamReader(inputStream, m_RespCharset);
		} catch (UnsupportedEncodingException e) {
			closeStream(inputStream);			
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
			closeStream(streamReader);
			closeReader(bufferReader);						    			
			return sb.toString();
		} catch (IOException e) {
			closeStream(inputStream);
			closeStream(streamReader);
			closeReader(bufferReader);
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
