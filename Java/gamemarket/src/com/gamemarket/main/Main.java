package com.gamemarket.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gamemarket.util.Logs;

public class Main implements ServletContextListener {
	private final Logger Log = Logs.getDefault();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Log.log(Level.INFO, "gamemarket close");
		Log.log(Level.INFO, "gamemarket closed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Log.log(Level.INFO, "gamemarket init");
		
		if (Init.start())
			Log.log(Level.INFO, "gamemarket started");
		else
			Log.log(Level.INFO, "gamemarket init error");
	}
}
