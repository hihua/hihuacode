package com.request;

public class RequestHonor extends RequestParent {
	private final String URL = "/honor/get_medal_award";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public void request(String host, String clientv, String cookie, Long userId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_Body.setLength(0);
		m_Body.append("user_id=");
		m_Body.append(userId);
		
		requestUrl(URL.toString(), clientv, cookie, m_Body.toString());
	}
}
