package com.util;

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
	
	public static int rndNumber(int max, int min) {
		Random rand = new Random();				
		return rand.nextInt(max - min + 1) + min;		
	}
	
	public static int rndNumber(Long max, Long min) {
		Random rand = new Random();				
		return rand.nextInt(max.intValue() - min.intValue() + 1) + min.intValue();		
	}
}
