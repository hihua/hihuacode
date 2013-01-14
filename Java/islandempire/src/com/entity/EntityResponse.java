package com.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class EntityResponse {
	private int code;
	private Map<String, List<String>> headers;
	private boolean string;
	private InputStream stream;
	private String body;
	private HttpGet httpGet;
	private HttpPost httpPost;
	private HttpEntity entity;

	public int getCode() {
		return code;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public boolean getString() {
		return string;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getBody() {
		return body;
	}

	public HttpGet getHttpGet() {
		return httpGet;
	}

	public HttpPost getHttpPost() {
		return httpPost;
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public void setString(boolean string) {
		this.string = string;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setHttpGet(HttpGet httpGet) {
		this.httpGet = httpGet;
	}

	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}

	private void abort(HttpGet httpGet, HttpPost httpPost) {
		if (httpGet != null)
			httpGet.abort();
		
		if (httpPost != null)
			httpPost.abort();
	}
	
	public void close() {
		if (stream == null)
			return;

		try {
			stream.close();
		} catch (IOException e) {
			
		}
				
		abort(httpGet, httpPost);	
	}
}
