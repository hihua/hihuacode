package com.request;

public class RequestOthers extends RequestBase {
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String cookie, String authorization, String url, String body) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = super.request(m_URL.toString(), body, cookie, authorization);
		if (response == null)
			return false;
		else
			return true;
	}	
}
