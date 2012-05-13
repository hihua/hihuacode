package com.request;

public class RequestRanks extends RequestParent {
	private final String URL = "/ranks.json?do=%s&user_id=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, String rank, Long userId) {
		String url = String.format(URL, rank, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		return requestUrl(m_URL.toString(), clientv, cookie, null);
	}
	
	public String request(String host, String clientv, String cookie, String rank, String username, Long userId) {
		String url = String.format(URL, rank, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		return requestUrl(m_URL.toString(), clientv, cookie, username, null);
	}
}
