package com.request;

import java.util.List;
import java.util.Map;

import com.config.Config;

public class RequestSessions extends RequestParent {
	private final String URL = "/sessions.json";
	private final String Body = "device_version=iPad2,1&username=%s&password=%s&ios_version=5.0.1";
	private final StringBuilder m_URL = new StringBuilder();
	
	public boolean request(String host, String clientv, Config config, String username, String password) {
		String body = String.format(Body, username, password);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		String response = requestUrl(m_URL.toString(), clientv, null, username, password, body);
		if (response == null)
			return false;
		else {
			Map<String, List<String>> map = getHeader();
			if (map == null)
				return false;
			else {
				if (map.containsKey("Set-Cookie")) {
					List<String> cookies = map.get("Set-Cookie");
					StringBuilder sb = new StringBuilder();
					for (String cookie : cookies) {
						if (cookie.indexOf("user_id=") > -1)
							continue;
						
						int p = cookie.indexOf("; ");
						if (p > 0) {
							sb.append("; ");
							sb.append(cookie.substring(0, p));
						}					
					}
					
					if (sb.length() == 0)
						return false;
					else {
						sb = sb.delete(0, 2);
						config.setCookie(sb.toString());
						return true;
					}					
				} else
					return false;
			}			
		}
	}
	
	public boolean request(String host, String clientv, String cookie, Config config) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append("/deals.json?do=public&goods_name=wood&page=0");
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return false;
		else {
			config.setCookie(cookie);
			return true;
		}
	}
}
