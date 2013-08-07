package com.gamemarket.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Numeric {
	public static boolean isNumber(String s) {
		if (s == null || s.trim().length() == 0)
			return false;
		
		for (int i = 0;i < s.length();i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		
		return true;
	}
	
	public static boolean isLong(String s) {
		if (s == null || s.length() == 0)
			return false;
		
		try {
			Long v = Long.parseLong(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isInteger(String s) {
		if (s == null || s.length() == 0)
			return false;
		
		try {			
			Integer v = Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Long toLong(Object object) {
		String s = String.valueOf(object);
		return Long.parseLong(s);
	}
	
	public static Integer toInteger(Object object) {
		String s = String.valueOf(object);
		return Integer.parseInt(s);
	}
	
	public static String rndNumber(int length) {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0;i < length;i++)
			sb.append(rand.nextInt(10));
		
		return sb.toString();
	}
	
	public static int rndNumber(int min, int max) {
		Random rand = new Random();				
		return rand.nextInt(max - min + 1) + min;		
	}
	
	public static String md5(String s, String charset) {
		if (s == null || s.length() == 0)
			return null;
				
		try {
			byte[] org = s.getBytes(charset);
			if (org == null || org.length == 0)
				return null;
			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] dest = md5.digest(org);
			if (dest == null || dest.length == 0)
				return null;
			
			StringBuilder sb = new StringBuilder();
			
			for (byte b : dest) {
				String tmp = Integer.toHexString(b & 0xFF);
				if (tmp.length() == 1)
					tmp = "0" + tmp;		
				
				sb.append(tmp);
			}
			
			return sb.toString();
		} catch (UnsupportedEncodingException e) {			
			return null;
		} catch (NoSuchAlgorithmException e) {			
			return null;
		}		
	}
}
