package com.request;

import com.util.Logs;

public class RequestBase {
	protected final Logs Log = Logs.getInstance();
	
	private final String[] UserAgent = { "User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/526.11 (KHTML, like Gecko)" };
	private final String[] Udid = { "Udid", "8f3c3b1d78132a495a8641cdbf51bf50b931f458" };
	private final String[] Clientv = { "Clientv", "1.3.4" };
	private final String[] Appid = { "Appid", "tap4fun.islandempire.50gems" };    
	private final String[] Locale = { "Locale", "cn" };
	private final String[] Accept = { "Accept", "*/*" };
	private final String[] AcceptEncoding = { "Accept-Encoding", "gzip" };
	private final String[] AcceptLanguage = { "Accept-Language", "zh-cn" };
	private final String[] Macid = { "macid", "NDAzMDA0RTJEQ0ZB" };
	private final String Key = "3da541559918a808c2402bba5012f6c60b27661c";
	private final String Sig = "sig";
	private final String Authorization = "Authorization";

}
