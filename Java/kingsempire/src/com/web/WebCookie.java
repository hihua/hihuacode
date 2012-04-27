package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auto.AutoTask;

public class WebCookie extends WebBase {
	private final String ContentType = "text/html";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String userName = request.getParameter("username");
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String passWord = request.getParameter("password");
		if (passWord == null || passWord.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		AutoTask task = AutoTask.getInstance();
		String cookie = task.resetCookie(userName, passWord);
		
		String s = "";
		if (cookie == null)
			s = userName + ": failed"; 
		else
			s = userName + ": " + cookie;
		
		out.print(s);
	}
}
