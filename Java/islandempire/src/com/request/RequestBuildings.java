package com.request;

public class RequestBuildings extends RequestParent {
	private final String URL = "/building_events";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long id) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("building_id=");
		m_Body.append(id);
		
		String response = super.request(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}		
}
