package com.apps.game.market.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Numeric {
	public static boolean isNumber(String s) {
		if (s == null || s.trim().length() == 0)
			return false;
		
		for (int i = 0;i < s.length();i++) {
			final char c = s.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		
		return true;
	}
	
	public static float rndNumber(float min, float max) {
		final Random rand = new Random();				
		return rand.nextFloat() * (max - min) + min;
	}
	
	public static int rndNumber(int min, int max) {
		final Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}
	
	public static String md5(String s, String charset) {
		if (s == null || s.length() == 0)
			return null;
				
		try {
			final byte[] org = s.getBytes(charset);
			if (org == null || org.length == 0)
				return null;
			
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			final byte[] dest = md5.digest(org);
			if (dest == null || dest.length == 0)
				return null;
			
			final StringBuilder sb = new StringBuilder();
			
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
