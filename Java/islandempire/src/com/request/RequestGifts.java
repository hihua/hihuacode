package com.request;

public class RequestGifts extends RequestParent {
	private final String URL = "/gifts/show_gifts.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long userId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?");
		m_URL.append("user_id=");
		m_URL.append(userId);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return false;
		else
			return true;
	}
}
