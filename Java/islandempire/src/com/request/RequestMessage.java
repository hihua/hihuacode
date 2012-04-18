package com.request;

public class RequestMessage extends RequestParent {
	private final String URL = "/messages.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, String to, Long page) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?to=");
		m_URL.append(to);
		m_URL.append("&page=");
		m_URL.append(page);
		
		return super.request(m_URL.toString(), clientv, cookie, null);
	}
	
	public String request(String host, String clientv, String cookie, String username, String to, Long page) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?to=");
		m_URL.append(to);
		m_URL.append("&page=");
		m_URL.append(page);
		
		return super.request(m_URL.toString(), clientv, cookie, username, null);
	}
	
	public String request(String host, String clientv, String cookie, String to, String from, String subject, String body) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("to=");
        m_Body.append(to);
        m_Body.append("&from=");
        m_Body.append(from);
        m_Body.append("&subject=");
		m_Body.append(subject);
		m_Body.append("&body=");
		m_Body.append(body);
		
		return super.request(m_URL.toString(), clientv, cookie, m_Body.toString());
	}
}
