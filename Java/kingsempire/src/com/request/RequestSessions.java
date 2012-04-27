package com.request;

import java.util.List;
import java.util.Map;

import com.config.Config;

public class RequestSessions extends RequestBase {
	private final String URL = "/sessions";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public boolean request(String host, Config config, String password) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("username=");
		m_Body.append(config.getUserName());
		m_Body.append("&");
		m_Body.append("password=");
		m_Body.append(password);
		
		String response = request(m_URL.toString(), m_Body.toString(), null, null);
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
}
