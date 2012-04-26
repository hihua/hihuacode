package com.request;

public class RequestBuildings extends RequestBase {
	private final String URL = "/buildings/";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String cookie, String authorization, Long id) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append(id);
		
		String response = super.request(m_URL.toString(), "_method=put", cookie, authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
