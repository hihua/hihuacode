package com.request;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParent extends RequestBase {	
	protected static String[] Cookie = { "Cookie", "" };
	protected static Date Expires = null;
	protected static final long Timeout = 72000000;	
	private final String[] UserAgent = { "User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/526.11 (KHTML, like Gecko)" };
	private final String[] Udid = { "Udid", "8f3c3b1d78132a495a8641cdbf51bf50b931f458" };	
	private final String[] Appid = { "Appid", "tap4fun.islandempire.50gems" };    
	private final String[] Locale = { "Locale", "cn" };
	private final String[] Accept = { "Accept", "*/*" };
	private final String[] AcceptEncoding = { "Accept-Encoding", "gzip, deflate" };
	private final String[] AcceptLanguage = { "Accept-Language", "zh-cn" };
	private final String[] Macid = { "macid", "NDAzMDA0RTJEQ0ZB" };
	private final String Key = "3da541559918a808c2402bba5012f6c60b27661c";
	private final String Sig = "sig";
	private final String Clientv = "clientv";
	private final String Authorization = "Authorization";
	private final String Sessions = "/sessions.json";
	private final String UserName = "hihua";
	private final String Password = "hihua1012";
	private final String Secret = "12345678";	
	private final String Body = "device_version=iPad2,1&username=%s&password=%s&ios_version=5.0.1";
	private final HashMap<String, String> m_Header = new HashMap<String, String>();		
	private String m_Charset = "UTF-8";
	
	private String getMD5(String web, String body) {
		String content = web;
		if (body != null)
			content = body;	
				
        StringBuilder sb = new StringBuilder();
        sb.append(Key);
        sb.append(Udid[1]);
        sb.append(content);
        sb.append(Appid[1]);
        
        String s = sb.toString();

        try {
			byte[] org = s.getBytes(m_Charset);
			if (org == null || org.length == 0)
				return null;
        			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] dest = md5.digest(org);
			if (dest == null || dest.length == 0)
				return null;
			
			sb.setLength(0);
			
			for (byte b : dest) {
				String tmp = Integer.toHexString(b & 0xFF);
				if (tmp.length() == 1)
					tmp = "0" + tmp;		
				
				sb.append(tmp);
			}
			
			s = sb.toString();
			s = s.toUpperCase();
			return s;
		} catch (UnsupportedEncodingException e) {			
			return null;
		} catch (NoSuchAlgorithmException e) {			
			return null;
		}
    }
	
	private String getBase64(String authorization) {
		String s = "";
		
		try {
			s = new sun.misc.BASE64Encoder().encode(authorization.getBytes(m_Charset));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		
		if (s.length() == 0)
			return null;
		else
			return "Basic " + s;
	}
	
	private void setHeader(String authorization, String clientv, String md5) {
		m_Header.clear();			
		m_Header.put(Authorization, authorization);	
		m_Header.put(Clientv, clientv);						
		m_Header.put(AcceptEncoding[0], AcceptEncoding[1]);
		m_Header.put(AcceptLanguage[0], AcceptLanguage[1]);
		m_Header.put(Accept[0], Accept[1]);
		m_Header.put(Udid[0], Udid[1]);		
		m_Header.put(UserAgent[0], UserAgent[1]);		
		m_Header.put(Appid[0], Appid[1]);
		m_Header.put(Locale[0], Locale[1]);
		m_Header.put(Macid[0], Macid[1]);
		m_Header.put(Sig, md5);		
	}
	
	private boolean setCookie(String host, String url, String clientv, String username, String body) {		
		String web = host + url;
		String md5 = getMD5(web, body);
		if (md5 == null)
			return false;
		
		String authorization = null;
		if (username == null)
			authorization = getBase64(UserName + ":" + Secret);
		else
			authorization = getBase64(username + ":" + Secret);
		
		if (authorization == null)
			return false;
		
		if (Expires == null || Cookie[1].length() == 0 || System.currentTimeMillis() - Expires.getTime() > Timeout || !getCookie(host, clientv))
			return false;

		setHeader(authorization, clientv, md5);
		m_Header.put(Cookie[0], Cookie[1]);
		String content = request(host + Sessions, m_Header, body);
		if (content == null)
			return false;
		
		return true;				
	}
	
	private boolean getCookie(String host, String clientv) {
		String authorization = getBase64(UserName + ":" + Password);
		if (authorization == null)
			return false;					
		
		String web = host + Sessions;
		String body = String.format(Body, UserName, Password);
		String md5 = getMD5(web, body);
		if (md5 == null)
			return false;
		
		setHeader(authorization, clientv, md5);		
		String content = request(web, m_Header, body);
		if (content == null)
			return false;
		else {
			Map<String, List<String>> map = getHeader();
			if (map == null)
				return false;
			else {
				if (map.containsKey("Cookie")) {
					List<String> cookies = map.get("Cookie");
					StringBuilder sb = new StringBuilder();
					for (String cookie : cookies) {
						sb.append("; ");
						sb.append(cookie);
					}
					
					if (sb.length() == 0)
						return false;
					else {
						sb.delete(0, 2);
						Cookie[1] = sb.toString();
						Expires = new Date();						
						return true;
					}						
				} else
					return false;
			}
		}
	}
	
	protected String request(String host, String url, String clientv, String username, String body) {
		if (!setCookie(host, url, clientv, username, body))
			return null;
		
		String web = host + url;
		return request(web, m_Header, body);
	}
	
	protected String request(String host, String url, String clientv, String body) {
		return request(host, url, clientv, null, body);
	}
}
