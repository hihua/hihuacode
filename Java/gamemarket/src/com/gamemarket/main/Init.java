package com.gamemarket.main;

import com.gamemarket.util.db.DataBase;

public class Init {

	public static boolean start() {
		if (DataBase.init())			
			return true;
		else
			return false;
	}
}
