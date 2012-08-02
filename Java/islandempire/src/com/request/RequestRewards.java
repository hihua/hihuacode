package com.request;

public class RequestRewards extends RequestParent {
	private final String URL = "/login_rewards/get_login_reward";	
	private final String Body = "user_id=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, Long userId) {
		String body = String.format(Body, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
						
		return requestUrl(m_URL.toString(), clientv, cookie, body);
	}
}
