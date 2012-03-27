package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebMessages extends WebBase {
	private final String ContentType = "application/json";
	
	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String userName = request.getParameter("username");
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
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
		
		AutoTask task = AutoTask.getInstance();
		
		switch (com) {
			case 0: {								
				String message = task.getMessages(userName);				
				if (message == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
								
				try {
					JSONObject json = JSONObject.fromObject(message);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}
			break;
			
			case 1: {
				String to = request.getParameter("to");
				if (to == null || to.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
				
				String page = request.getParameter("page");
				if (page == null || page.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
												
				String message = task.getMessages(userName, to, Long.parseLong(page));				
				if (message == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}					
								
				try {
					JSONObject json = JSONObject.fromObject(message);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}
			break;
			
			default: {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);				
			}
			break;
		}	
	}
}
