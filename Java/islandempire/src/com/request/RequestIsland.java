package com.request;

import java.util.List;

import com.island.Island;
import com.island.IslandBuilding;

public class RequestIsland extends RequestParent {
	private final String URL = "/islands/%d.json?user_id=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public List<IslandBuilding> request(String host, String clientv, String cookie, Long islandNumber, Long userId) {
		String url = String.format(URL, islandNumber, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Island.parse(response);
	}
		
	public String request(String host, String clientv, String cookie, String x, String y, Long userId) {
		try {
			Long islandNumber = Long.parseLong(x + y);
			String url = String.format(URL, islandNumber, userId);
			m_URL.setLength(0);
			m_URL.append(host);
			m_URL.append(url);
			
			return requestUrl(m_URL.toString(), clientv, cookie, null);
		} catch (Exception e) {
			return null;
		}				
	}
}
