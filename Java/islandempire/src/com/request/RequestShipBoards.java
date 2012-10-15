package com.request;

import java.util.HashMap;
import java.util.Map.Entry;

public class RequestShipBoards extends RequestParent {
	private final String URL = "/ship_boards/save_board";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	private final StringBuilder m_Troops = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long userId, Long townId, HashMap<String, Long> soldiers) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("user_id=");
		m_Body.append(userId);
		m_Body.append("&town_id=");
		m_Body.append(townId);
						
		m_Troops.setLength(0);
		for (Entry<String, Long> entry : soldiers.entrySet()) {
			m_Troops.append(",");
			m_Troops.append(entry.getKey());
			m_Troops.append(":");
			m_Troops.append(entry.getValue());
		}
		
		if (m_Troops.length() > 0)
			m_Troops.deleteCharAt(0);
		
		m_Body.append("&troops=");
		m_Body.append(m_Troops.toString());
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
}
