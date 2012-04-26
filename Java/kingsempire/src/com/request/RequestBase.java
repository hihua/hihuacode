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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;

import com.util.Logs;

public class RequestBase {
	protected final Logs Log = Logs.getInstance();
	private final String[] UserAgent = { "User-Agent", "Kings%20Empire%20Zh%20Deluxe/2840 CFNetwork/548.0.4 Darwin/11.0.0" };
	private final String[] Udid = { "Udid", "b635e22da9ab4750876a7f3cab8f495de2298a14" };
	private final String[] Clientv = { "Clientv", "1.2.1" };
	private final String[] Appid = { "Appid", "500556141" };    
	private final String[] Locale = { "Locale", "zh" };
	private final String[] Accept = { "Accept", "*/*" };
	private final String[] AcceptEncoding = { "Accept-Encoding", "gzip" };    
	private final String Key = "3da541559918a808c2402bba5012f6c60b27661c";
	private final String Sig = "Sig";
	private final String Authorization = "Authorization";
	private final String Cookie = "Cookie";
	    
	private String m_MD5Charset = "UTF-8";
	private String m_ReqCharset = "UTF-8";
	private String m_RespCharset = "UTF-8";
	private int m_ConnectTimeout = 30000;
	private int m_ReadTimeout = 30000;
	private final int m_Times = 3;
    
    public RequestBase() {
    	
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
    
	protected void setMD5Charset(String charset) {
    	m_MD5Charset = charset;
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
	
	private String GetMD5(String webUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(Key);
        sb.append(Udid[1]);
        sb.append(webUrl);
        sb.append(Appid[1]);
        
        String s = sb.toString();

        try {
			byte[] org = s.getBytes(m_MD5Charset);
			if (org == null || org.length == 0)
				return null;
        			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] dest = md5.digest(org);
			if (dest == null || dest.length == 0)
				return null;
			
			sb.setLength(0);
			
			for (byte b : dest) {
				String tmp = Integer.toHexString(b & 0xFF);
				if (tmp.length() == 1)
					tmp = "0" + tmp;		
				
				sb.append(tmp);
			}
			
			s = sb.toString();
			s = s.toUpperCase();
			return s;
		} catch (UnsupportedEncodingException e) {			
			return null;
		} catch (NoSuchAlgorithmException e) {			
			return null;
		}
    }
	
	private void SetHeader(HttpURLConnection connection, String cookie, String authorization, String md5) {
		if (authorization != null)
			connection.setRequestProperty(Authorization, authorization);
		
		connection.setRequestProperty(AcceptEncoding[0], AcceptEncoding[1]);
		connection.setRequestProperty(Accept[0], Accept[1]);
		connection.setRequestProperty(Udid[0], Udid[1]);		
		connection.setRequestProperty(UserAgent[0], UserAgent[1]);
		connection.setRequestProperty(Clientv[0], Clientv[1]);
		connection.setRequestProperty(Appid[0], Appid[1]);
		connection.setRequestProperty(Locale[0], Locale[1]);		
		connection.setRequestProperty(Sig, md5);
		connection.setRequestProperty(Cookie, cookie);
	}
    
    protected String request(String webUrl, String body, String cookie, String authorization) {
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
    		
    		String content = webUrl;
    		if (body != null)
    			content = body;
    				
    		String md5 = GetMD5(content);
    		if (md5 == null) 
    			return null;
    		
    		SetHeader(connection, cookie, authorization, md5);
    		
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
    		
    		if (responseCode != 200) {
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
    			
				return null;			
			} else {
				String response = getBody(connection);
				connection.disconnect();
				if (response == null) {					
					times--;
					continue;
				} else
					return response;								
			}    		
    	}
    	
    	return null;
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
