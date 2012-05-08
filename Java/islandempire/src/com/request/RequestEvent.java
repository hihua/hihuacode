package com.request;

import java.util.List;

import com.queue.BattleQueue;

public class RequestEvent extends RequestParent {
	private final String URL = "/command_post/get_events_by_player?user_id=%d";
	private final StringBuilder m_URL = new StringBuilder();
	
	public List<BattleQueue> request(String host, String clientv, String cookie, Long userId) {
		String url = String.format(URL, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return BattleQueue.parse(response);
	}
	
	public List<BattleQueue> request(String host, String clientv, String cookie, String username, Long userId) {
		String url = String.format(URL, userId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, username, null);
		if (response == null)
			return null;
		else
			return BattleQueue.parse(response);
	}
}
