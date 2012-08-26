package com.request;

import java.util.HashMap;
import java.util.Map.Entry;

public class RequestDissolve extends RequestParent {
	private final String URL = "/army/dissolve_army";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, HashMap<String, Long> soldiers, Long townId) {		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("army=");
		
		StringBuilder army = new StringBuilder();
		for (Entry<String, Long> entry : soldiers.entrySet()) {
			army.append(",");
			army.append(entry.getKey());
			army.append(":");
			army.append(entry.getValue());			
		}
		
		if (army.length() > 0) {			
			army.deleteCharAt(0);
			m_Body.append(army.toString());
		}
		
		m_Body.append("&town_id=");
		m_Body.append(townId);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
}
