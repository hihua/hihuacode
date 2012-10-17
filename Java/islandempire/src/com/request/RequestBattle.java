package com.request;

public class RequestBattle extends RequestParent {
	private final String URL = "/messages/get_battle_info";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, Long userId, String mailId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("user_id=");
		m_Body.append(userId);
		m_Body.append("&mail_id=");
		m_Body.append(mailId);
		
		return requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());		
	}
}
