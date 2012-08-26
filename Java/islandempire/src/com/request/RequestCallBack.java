package com.request;

public class RequestCallBack extends RequestParent {
	private final String URL = "/command_post/army_call_back";
	private final String Body = "event_id=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long eventId) {
		String body = String.format(Body, eventId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, body);
		if (response == null)
			return false;
		else
			return true;
	}
	
	public boolean request(String host, String clientv, String cookie, String username, Long eventId) {
		String body = String.format(Body, eventId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, username, body);
		if (response == null)
			return false;
		else
			return true;
	}
}
