package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.entity.EntityRequest;
import com.entity.EntityResponse;

public class HttpClass {
	private static HttpClass mHttpClass;	
	private HttpClient mHttpClient;
	private HttpParams mParams;
	private final int mConnectTimeout = 30000;
	private final int mReadTimeout = 30000;
		
	public synchronized static HttpClass getInstance() {
		if (mHttpClass == null)
			mHttpClass = new HttpClass();
		
		return mHttpClass;
	}
	
	private HttpClass() {
		mParams = new BasicHttpParams();
        mParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, mConnectTimeout);
        mParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, mReadTimeout);
        HttpProtocolParams.setVersion(mParams, HttpVersion.HTTP_1_1);        
        mHttpClient = new DefaultHttpClient(mParams);
	}
		
	public void logError(String e, EntityRequest req) {
		Logs log = Logs.getInstance();
		log.writeLogs(e.toString());
		if (req != null) {
			log.writeLogs(req.getUrl());
			if (req.getBody() != null)
				log.writeLogs(req.getBody());
		}    	 	
	}
	
	public void logError(String e, EntityRequest req, String body) {
		Logs log = Logs.getInstance();
		log.writeLogs(e.toString());
    	
		if (req != null) {
			log.writeLogs(req.getUrl());
			if (req.getBody() != null)
				log.writeLogs(req.getBody());
		}
		
    	if (body == null)
    		log.writeLogs("null");
    	else
    		log.writeLogs(body);
	}
	
	private void abort(HttpGet httpGet, HttpPost httpPost) {
		if (httpGet != null)
			httpGet.abort();
		
		if (httpPost != null)
			httpPost.abort();
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
			
	private void closeStream(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				logError(e.toString(), null);
			}
		}
	}
	
	public EntityResponse request(EntityRequest req) {
		int times = 3;
		while (times-- > 0) {
			HttpGet httpGet = null;
			HttpPost httpPost = null;
			
			if (req.getBody() == null)
				httpGet = new HttpGet(req.getUrl());
			else
				httpPost = new HttpPost(req.getUrl());
			
			Map<String, String> header = req.getHeader();
			if (header != null) {
				for (Entry<String, String> entry : header.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();					
					if (req.getBody() == null)
						httpGet.addHeader(key, value);
					else
						httpPost.addHeader(key, value);
				}
			}
			
			if (req.getBody() != null) {
				try {
					StringEntity string = new StringEntity(req.getBody(), req.getCharset());
					string.setContentType("application/x-www-form-urlencoded");
					httpPost.setEntity(string);					
				} catch (UnsupportedEncodingException e) {
					logError(e.toString(), req);
					continue;
				}
			}		
			
			HttpResponse response = null;
						
			try {
				if (req.getBody() == null)
					response = mHttpClient.execute(httpGet);
				else
					response = mHttpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				abort(httpGet, httpPost);
				logError(e.toString(), req);				
				continue;
			} catch (IOException e) {
				abort(httpGet, httpPost);
				logError(e.toString(), req);				
				continue;
			}
			
			StatusLine status = response.getStatusLine();
			if (status == null) {
				abort(httpGet, httpPost);
				logError("status null", req);				
				continue;
			}
								
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				abort(httpGet, httpPost);
				logError("entity null", req);				
				continue;
			}
			
			int code = status.getStatusCode();
			EntityResponse resp = null;
			if (code == HttpStatus.SC_OK) {
				resp = new EntityResponse();
				resp.setCode(code);
				resp.setString(req.getString());
			}
									
			if (req.getString()) {
				String body = null;
				
				try {
					body = getBody(entity, req.getCharset());
				} catch (Exception e) {
					abort(httpGet, httpPost);
					logError(e.toString(), req);					
					continue;
				}		
				
				if (code != HttpStatus.SC_OK) {
					abort(httpGet, httpPost);
					logError("code: " + code, req, body);					
					continue;
				}
								
				resp.setBody(body);				
			} else {
				if (code != HttpStatus.SC_OK) {
					String body = null;
					
					try {
						body = getBody(entity, req.getCharset());
					} catch (Exception e) {
						abort(httpGet, httpPost);
						logError(e.toString(), req);						
					}
					
					abort(httpGet, httpPost);
					logError("code: " + code, req, body);					
					continue;
				}
				
				InputStream stream = null;
				
				try {
					stream = entity.getContent();					
				} catch (IllegalStateException e) {
					closeStream(stream);
					abort(httpGet, httpPost);
					logError(e.toString(), req);					
					continue;
				} catch (IOException e) {
					closeStream(stream);
					abort(httpGet, httpPost);
					logError(e.toString(), req);
					continue;
				}
				
				resp.setHttpGet(httpGet);
				resp.setHttpPost(httpPost);
				resp.setEntity(entity);
				resp.setStream(stream);
			}
			
			return resp;
		}
		
		return null;
	}
	
	private String getBody(HttpEntity entity, String charset) throws Exception {
    	InputStream inputStream = getInputStream(entity);		
		InputStreamReader streamReader = new InputStreamReader(inputStream, charset);				
		BufferedReader bufferReader = new BufferedReader(streamReader);
		StringBuilder sb = new StringBuilder();
		String s = "";
				
		while ((s = bufferReader.readLine()) != null) {				
			sb.append(s);
			sb.append("\n");
		}
		
		closeStream(inputStream);
		closeStream(streamReader);
		closeReader(bufferReader);						    			
		return sb.toString();		
    }
	
	private InputStream getInputStream(HttpEntity entity) throws Exception {    			
		Header header = entity.getContentEncoding();
		InputStream inputStream = entity.getContent();;
				
		if (header != null && inputStream != null) {
			String value = header.getValue();
			if (value != null && value.equals("gzip")) {				
				InputStream gzip = new GZIPInputStream(inputStream);
				return gzip;
			}
		}
		    	
		return inputStream;
    }
}
