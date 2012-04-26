package com.request;

public class RequestMessages extends RequestBase {
	private final String URL = "/messages?";
	private final StringBuilder m_URL = new StringBuilder();
	
	public String request(String host, String cookie, String authorization, String to, Long page) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("to=");
		m_URL.append(to);
		m_URL.append("&diviceWidth=560");
		m_URL.append("&page=");
		m_URL.append(page);
				
		String response = super.request(m_URL.toString(), null, cookie, authorization);
		return response;		
	}
}
