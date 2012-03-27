package com.request;

public class RequestSkills extends RequestBase {	
	private final String URL = "/skills/";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String authorization, Long skillId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append(skillId);
		
		String response = super.request(m_URL.toString(), "_method=put", authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
