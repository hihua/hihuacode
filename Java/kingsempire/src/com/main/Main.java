package com.main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.auto.AutoTask;

public class Main extends HttpServlet {
	
	public Main() {
		super();
	}

	public void destroy() {
		super.destroy();
	}
	
	public void init() throws ServletException {
		AutoTask task = AutoTask.getInstance();
		task.start();
	}
}
