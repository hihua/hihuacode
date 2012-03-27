package com.request;

import java.util.Enumeration;
import java.util.Hashtable;

import com.world.City;

public class RequestArmy extends RequestBase {
	private final String URL = "/army_events";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public City request(String host, String authorization, Long fromCityId, Hashtable<String, Long> soldiers, Long x, Long y) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("do=attack_npc");			
		m_Body.append("&from_city_id=");
		m_Body.append(fromCityId);
		
		Enumeration<String> enu = soldiers.keys();
		while (enu.hasMoreElements()) {
			String name = enu.nextElement();
			Long count = soldiers.get(name);			
			m_Body.append("&");
			m_Body.append(name);
			m_Body.append("=");
			m_Body.append(count);
		}
		
		m_Body.append("&npc_x=");
		m_Body.append(x);
		m_Body.append("&npc_y=");
		m_Body.append(y);
						
		String response = super.request(m_URL.toString(), m_Body.toString(), authorization);
		if (response == null)
			return null;
		else
			return City.parse(response);			
	}
	
	public boolean request(String host, String authorization, Long fromCityId, Hashtable<String, Long> soldiers, Long toCityId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("do=attack");		
		m_Body.append("&from_city_id=");
		m_Body.append(fromCityId);
		
		Enumeration<String> enu = soldiers.keys();
		while (enu.hasMoreElements()) {
			String name = enu.nextElement();
			Long count = soldiers.get(name);			
			m_Body.append("&");
			m_Body.append(name);
			m_Body.append("=");
			m_Body.append(count);
		}
		
		m_Body.append("&to_city_id=");
		m_Body.append(toCityId);
		
		String response = super.request(m_URL.toString(), m_Body.toString(), authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
