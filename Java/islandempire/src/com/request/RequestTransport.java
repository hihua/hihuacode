package com.request;

import java.util.HashMap;
import java.util.Map.Entry;

import com.towns.TransportTown;

public class RequestTransport extends RequestParent {
	private final String URL = "/transport_events.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public TransportTown request(String host, String clientv, String cookie, Long fromTownId, Long toTownId, HashMap<String, Long> resources) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("from_town_id=");
		m_Body.append(fromTownId);
		m_Body.append("&to_town_id=");
		m_Body.append(toTownId);
		
		for (Entry<String, Long> entry : resources.entrySet()) {	
			m_Body.append("&");
			m_Body.append(entry.getKey());
			m_Body.append("=");
			m_Body.append(entry.getValue());
		}
				
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return null;
		else
			return TransportTown.parse(response);
	}
}
