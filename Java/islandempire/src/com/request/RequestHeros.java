package com.request;

public class RequestHeros extends RequestParent {
	private final String URL = "/heros/%d";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public void request(String host, String clientv, String cookie, Long status, Long id) {
		String url = String.format(URL, id);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		m_Body.setLength(0);
		m_Body.append("status=");
		m_Body.append(status);
		m_Body.append("&id=");
		m_Body.append(id);
		m_Body.append("&do=change_status");
		m_Body.append("&_method=put");
		
		requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
	}
}
