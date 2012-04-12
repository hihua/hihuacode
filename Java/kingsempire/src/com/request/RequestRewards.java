package com.request;

public class RequestRewards extends RequestBase {
	private final String URL = "/login_rewards";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, String authorization, Long gems, Long cityId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("gems=");
		m_Body.append(gems);
		m_Body.append("&city_id=");		
		m_Body.append(cityId);
		
		String response = super.request(m_URL.toString(), m_Body.toString(), authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
