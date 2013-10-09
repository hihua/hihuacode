package com.request;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hero.Hero;

public class RequestArmy extends RequestParent {
	private final String URL = "/army_events.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long fromTownId, Long toTownId, Long armyOwnerTownId, HashMap<String, Long> soldiers, Hero hero) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("do=attack");
		m_Body.append("&from_town_id=");
		m_Body.append(fromTownId);
		m_Body.append("&to_town_id=");
		m_Body.append(toTownId);
		m_Body.append("&army_owner_town_id=");
		m_Body.append(armyOwnerTownId);
						
		for (Entry<String, Long> entry : soldiers.entrySet()) {			
			m_Body.append("&");
			m_Body.append(entry.getKey());
			m_Body.append("=");
			m_Body.append(entry.getValue());
		}
		
		if (hero != null) {
			m_Body.append("&hero_id=");
			m_Body.append(hero.getId());
		}
				
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}	
}
