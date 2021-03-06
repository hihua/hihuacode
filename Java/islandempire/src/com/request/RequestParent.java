package com.request;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class RequestParent extends RequestBase {	
	private final String[] UserAgent = { "User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/526.11 (KHTML, like Gecko)" };
	private final String[] Udid = { "Udid", "8f3c3b1d78132a495a8641cdbf51bf50b931f458" };	
	private final String[] Appid = { "Appid", "tap4fun.islandempire.50gems" };    
	private final String[] Locale = { "Locale", "cn" };
	private final String[] Accept = { "Accept", "*/*" };
	private final String[] AcceptEncoding = { "Accept-Encoding", "gzip, deflate" };
	private final String[] AcceptLanguage = { "Accept-Language", "zh-cn" };
	private final String[] Macid = { "macid", "NDAzMDA0RTJEQ0ZB" };
	private final String[] Idfa = { "idfa", "DFB94F11-EC7F-49C0-BF84-1B529EA465C0" };
	private final String[] SystemVersion = { "systemVersion", "6.1" };
	private final String[] Openudid = { "openudid", "FA2CFA7B-2E7C-4512-9491-F7C4EAAF6CD6" };
	private final String Key = "3da541559918a808c2402bba5012f6c60b27661c";
	private final String Sig = "sig";
	private final String Clientv = "clientv";
	private final String Authorization = "Authorization";
	private final String Cookie = "Cookie";	
	private final String UserName = "abb_02";
	private final String Password = "12345678";
	private final HashMap<String, String> m_Header = new HashMap<String, String>();
	private final StringBuilder m_Authorization = new StringBuilder();
	private String m_Charset = "UTF-8";
	
	private String getMD5(String webUrl, String body) {
		String content = webUrl;
		if (body != null)
			content = body;	
				
        StringBuilder sb = new StringBuilder();
        sb.append(Key);
        sb.append(Openudid[1]);
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
	
	private void setHeader(String authorization, String clientv, String cookie, String md5) {
		m_Header.clear();			
		m_Header.put(Authorization, authorization);	
		m_Header.put(Clientv, clientv);						
		m_Header.put(AcceptEncoding[0], AcceptEncoding[1]);
		m_Header.put(AcceptLanguage[0], AcceptLanguage[1]);
		m_Header.put(Accept[0], Accept[1]);
		//m_Header.put(Udid[0], Udid[1]);
		m_Header.put(UserAgent[0], UserAgent[1]);		
		m_Header.put(Appid[0], Appid[1]);
		m_Header.put(Locale[0], Locale[1]);
		m_Header.put(Macid[0], Macid[1]);
		m_Header.put(Idfa[0], Idfa[1]);
		m_Header.put(SystemVersion[0], SystemVersion[1]);
		m_Header.put(Openudid[0], Openudid[1]);
		
		if (cookie != null)
			m_Header.put(Cookie, cookie);
		
		m_Header.put(Sig, md5);
	}
		
	protected String requestUrl(String webUrl, String clientv, String cookie, String username, String password, String body) {		
		String md5 = getMD5(webUrl, body);
		if (md5 == null)
			return null;
		
		m_Authorization.setLength(0);		
		if (username == null)
			m_Authorization.append(UserName.replaceAll("_", "%5F"));
		else
			m_Authorization.append(username.replaceAll("_", "%5F"));
						
		m_Authorization.append(":");
		if (password == null)
			m_Authorization.append(Password);
		else
			m_Authorization.append(password);
		
		String authorization = getBase64(m_Authorization.toString());		
		if (authorization == null)
			return null;
				
		setHeader(authorization, clientv, cookie, md5);
		return request(webUrl, m_Header, body);
	}
	
	protected String requestUrl(String webUrl, String clientv, String cookie, String body) {
		return requestUrl(webUrl, clientv, cookie, null, null, body);
	}
	
	protected String requestUrl(String webUrl, String clientv, String cookie, String username, String body) {
		return requestUrl(webUrl, clientv, cookie, username, null, body);
	}	
}
