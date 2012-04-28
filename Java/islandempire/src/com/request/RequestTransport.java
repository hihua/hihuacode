package com.request;

import java.util.HashMap;
import java.util.Map.Entry;

public class RequestTransport extends RequestParent {
	private final String URL = "/transport_events.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long from, Long to, HashMap<String, Long> resources) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?from_town_id=");
		m_URL.append(from);
		m_URL.append("&to_town_id=");
		m_URL.append(to);
		
		for (Entry<String, Long> entry : resources.entrySet()) {	
			m_URL.append("&");
			m_URL.append(entry.getKey());
			m_URL.append("=");
			m_URL.append(entry.getValue());
		}
				
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return false;
		else
			return true;
	}
}
