package com.request;

import com.towns.Town;

public class RequestTowns extends RequestParent {	
	private final String URL = "/towns/%d.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public Town request(String host, String clientv, String cookie, Long id) {
		String url = String.format(URL, id);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = super.request(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Town.parse(response);
	}
	
	public Town request(String host, String clientv, String cookie, String username, Long id) {
		String url = String.format(URL, id);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = super.request(m_URL.toString(), clientv, cookie, username, null);
		if (response == null)
			return null;
		else
			return Town.parse(response);
	}
}
