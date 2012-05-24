package com.request;

import java.util.List;

import com.island.WorldMap;

public class RequestWorldMaps extends RequestParent {
	private final String URL = "/world_maps.json?island_number=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public List<WorldMap> request(String host, String clientv, String cookie, Long islandNumber) {
		String url = String.format(URL, islandNumber);
		m_URL.setLength(0);
		m_URL.append(host);		
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return WorldMap.parse(response);
	}
	
	public String request(String host, String clientv, String cookie, String x, String y) {
		Long islandNumber = Long.parseLong(x + y);
		String url = String.format(URL, islandNumber);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		return requestUrl(m_URL.toString(), clientv, cookie, null);		
	}
}
