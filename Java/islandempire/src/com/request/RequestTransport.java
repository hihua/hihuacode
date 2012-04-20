package com.request;

public class RequestTransport extends RequestParent {
	private final String URL = "/transport_events.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, String cookie, Long from, Long to, Long wood, Long food, Long marble, Long iron, Long gold) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?from_town_id=");
		m_URL.append(from);
		m_URL.append("&to_town_id=");
		m_URL.append(to);
		
		if (wood != null && wood > 0) {
			m_URL.append("&wood=");
			m_URL.append(wood);
		}
		
		if (food != null && food > 0) {
			m_URL.append("&food=");
			m_URL.append(food);
		}
		
		if (marble != null && marble > 0) {
			m_URL.append("&marble=");
			m_URL.append(marble);
		}
		
		if (iron != null && iron > 0) {
			m_URL.append("&iron=");
			m_URL.append(iron);
		}
		
		if (gold != null && gold > 0) {
			m_URL.append("&gold=");
			m_URL.append(gold);
		}
		
		String response = super.request(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return false;
		else
			return true;
	}
}
