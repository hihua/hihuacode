package com.request;

public class RequestReceive extends RequestParent {
	private final String URL = "/buildings/receive";	
	private final StringBuilder m_Body = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, Long buildingId) {		
		m_Body.setLength(0);
		m_Body.append("building_id=");
		m_Body.append(buildingId);
		
		return requestUrl(URL, clientv, cookie, m_Body.toString());
	}
}
