package com.request;

import com.config.Config;

public class RequestSessions extends RequestParent {
	private final String URL = "/sessions.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, Config config) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		String cookie = super.sessions(m_URL.toString(), clientv);
		if (cookie == null)
			return false;
		else {
			config.setCookie(cookie);
			return true;
		}
	}
	
	public boolean request(String host, String clientv, String cookie) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append("/deals.json?do=public&goods_name=wood&page=0");
		
		String response = super.request(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return false;
		else
			return true;
	}
}
