package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebItems extends WebBase {
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
				String items = task.getItems(userName);				
				if (items == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
								
				try {
					JSONObject json = JSONObject.fromObject(items);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}
			break;
		
			case 1: {
				String cityId = request.getParameter("cityid");
				if (!Numeric.isNumber(cityId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
				
				String itemType = request.getParameter("itemtype");
				if (!Numeric.isNumber(itemType)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
				
				String number = request.getParameter("number");
				if (!Numeric.isNumber(number)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
				
				boolean success = task.useItems(userName, Long.parseLong(cityId), Long.parseLong(itemType), Long.parseLong(number));
				Hashtable<String, Long> table = new Hashtable<String, Long>();
				
				if (success)
					table.put("ret", 0L);
				else
					table.put("ret", 1L);
				
				try {
					JSONObject json = JSONObject.fromObject(table);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}
			break;
			
			case 2: {								
				String owner = request.getParameter("owner");
				if (owner == null || owner.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String items = task.getItems(userName, owner);				
				if (items == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
								
				try {
					JSONObject json = JSONObject.fromObject(items);
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
