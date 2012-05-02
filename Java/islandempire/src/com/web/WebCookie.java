package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebCookie extends WebBase {
	private final String ContentType = "text/html";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String command = request.getParameter("command");
		if (!Numeric.isNumber(command)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		int com = 0;
		
		try {
			com = Integer.parseInt(command);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		switch (com) {
			case 0: {
				String username = request.getParameter("username");
				if (username == null || username.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
				String password = request.getParameter("password");
				if (password == null || password.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
				AutoTask task = AutoTask.getInstance();
				String cookie = task.resetCookie(username, password);
				
				String s = "";
				if (cookie == null)
					s = username + ": failed"; 
				else
					s = username + ": " + cookie;
				
				out.print(s);
			}
			break;
			
			case 1: {
				String cookie = request.getParameter("cookie");
				if (cookie == null || cookie.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
				AutoTask task = AutoTask.getInstance();
				if (task.setCookie(cookie))
					out.print("success");
				else
					out.print("failed");
			}
			break;
			
			default: {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);				
			}
			break;
				
		}	
	}	
}
