package com.location.hlsd.entity;

import java.io.IOException;
import java.io.InputStream;

import android.net.http.AndroidHttpClient;

public class EntityResponse {
	private AndroidHttpClient client;
	private boolean string;
	private InputStream stream;
	private String body;

	public AndroidHttpClient getClient() {
		return client;
	}

	public void setClient(final AndroidHttpClient client) {
		this.client = client;
	}

	public boolean isString() {
		return string;
	}

	public void setString(final boolean string) {
		this.string = string;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(final InputStream stream) {
		this.stream = stream;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void close() {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {

			}
		}
			
		if (client != null)
			client.close();
	}
}
