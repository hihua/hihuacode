package com.request;

import java.util.List;

import com.world.WorldMap;

public class RequestWorldMaps extends RequestBase {	
	private final String URL = "/world_maps?";
	private final StringBuilder m_URL = new StringBuilder();
	
	public List<WorldMap> request(String host, String authorization, Long x, Long y, Long width, Long height) {
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
		
		String response = super.request(m_URL.toString(), null, authorization);
		if (response == null)
			return null;
		
		List<WorldMap> worldMaps = WorldMap.parse(response);
		return worldMaps;
	}
	
	public String request(String host, String authorization, Long x, Long y, Long width) {
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
		
		String response = super.request(m_URL.toString(), null, authorization);
		if (response == null)
			return null;
		else
			return response;
	}
}
