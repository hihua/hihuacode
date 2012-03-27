package com.request;

public class RequestRecruit extends RequestBase {
	private final String URL = "/recruit_events";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, String authorization, Long cityId, String soldierName, Long count) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("city_id=");
		m_Body.append(cityId);
		m_Body.append("&soldier_name=");
		m_Body.append(soldierName);
		m_Body.append("&count=");
		m_Body.append(count);
				
		String response = super.request(m_URL.toString(), m_Body.toString(), authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
