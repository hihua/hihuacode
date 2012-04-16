package com.request;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

public class RequestParent extends RequestBase {
	
	protected static String[] Cookie = { "Cookie", "" };
	protected static Date Expires = null;
	protected static long Timeout = 72000000;
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
	private final String Body = "device_version=iPad2,1&username=hihua&hihua1012&ios_version=5.0.1";
	
	private String m_MD5Charset = "UTF-8";
	
	private String GetMD5(String webUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(Key);
        sb.append(Udid[1]);
        sb.append(webUrl);
        sb.append(Appid[1]);
        
        String s = sb.toString();

        try {
			byte[] org = s.getBytes(m_MD5Charset);
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
	
	private HashMap<String, String> setHeader(String authorization, String clientv, String md5) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		if (authorization != null)
			map.put(Authorization, authorization);
		
		if (clientv != null)
			map.put(Clientv, clientv);
						
		map.put(AcceptEncoding[0], AcceptEncoding[1]);
		map.put(AcceptLanguage[0], AcceptLanguage[1]);
		map.put(Accept[0], Accept[1]);
		map.put(Udid[0], Udid[1]);		
		map.put(UserAgent[0], UserAgent[1]);		
		map.put(Appid[0], Appid[1]);
		map.put(Locale[0], Locale[1]);
		map.put(Macid[0], Macid[1]);
		map.put(Sig, md5);
		return map;
	}
	
	private boolean getCookie(String webUrl, String authorization, String clientv) {
		String url = webUrl + Sessions;
		String md5 = GetMD5(url);		
		
		HashMap<String, String> map = setHeader(authorization, clientv, md5);
		if (Expires != null && Cookie[1].length() > 0 && System.currentTimeMillis() - Expires.getTime() < Timeout) {
			map.put(Cookie[0], Cookie[1]);
			return true;
		}
		
		request()
	}
	
	protected String request(String webUrl, String authorization, String clientv, String body) {
		
	}
}
