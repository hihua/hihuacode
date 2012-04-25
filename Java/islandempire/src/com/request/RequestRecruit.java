package com.request;

import com.soldier.Recruit;

public class RequestRecruit extends RequestParent {
	private final String URL = "/recruit_events/%d.json?soldier_name=%s";
	private final StringBuilder m_URL = new StringBuilder();
	
	public Recruit request(String host, String clientv, String cookie, Long townId, String soldierName) {
		String url = String.format(URL, townId, soldierName);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);

		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Recruit.parse(response);
	}
}
