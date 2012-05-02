package com.request;

import com.worldmap.WorldMap;

public class RequestWorldMaps extends RequestBase {	
	private final String URL = "/world_maps?";
	private final StringBuilder m_URL = new StringBuilder();
	
	public WorldMap request(String host, String cookie, String authorization, Long x, Long y, Long width, Long height) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("x=");
		m_URL.append(x);
		m_URL.append("&y=");
		m_URL.append(y);
		m_URL.append("&width=");
		m_URL.append(width);
		m_URL.append("&height=");
		m_URL.append(height);
		
		String response = super.request(m_URL.toString(), null, cookie, authorization);
		if (response == null)
			return null;
		else
			return WorldMap.parse(response);
	}
	
	public String request(String host, String cookie, String authorization, Long x, Long y, Long width) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("x=");
		m_URL.append(x);
		m_URL.append("&y=");
		m_URL.append(y);
		m_URL.append("&width=");
		m_URL.append(width);
		m_URL.append("&height=");
		m_URL.append(width);
		
		String response = super.request(m_URL.toString(), null, cookie, authorization);
		if (response == null)
			return null;
		else
			return response;
	}
}
