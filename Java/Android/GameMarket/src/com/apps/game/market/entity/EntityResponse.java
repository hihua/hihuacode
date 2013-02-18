package com.apps.game.market.entity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class EntityResponse {
	private HttpURLConnection connection;
	private Map<String, List<String>> headers;
	private boolean string;
	private InputStream stream;
	private String body;

	public HttpURLConnection getConnection() {
		return connection;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public boolean isString() {
		return string;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getBody() {
		return body;
	}

	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
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

	public void close() {
		if (stream != null) {				
			try {
				stream.close();
			} catch (IOException e) {
				Log.e(getClass().getName(), e.toString());
			}
		}
		
		if (connection != null)
			connection.disconnect();
	}
}
