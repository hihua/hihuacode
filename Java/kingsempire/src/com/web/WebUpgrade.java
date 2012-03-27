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

public class WebUpgrade extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String userName = request.getParameter("username");
		String command = request.getParameter("command");
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
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
				String buildingId = request.getParameter("buildingid");
				if (!Numeric.isNumber(buildingId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
								
				boolean success = task.upgradeBuildings(userName, Long.parseLong(buildingId));
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
			
			case 1: {
				String skillId = request.getParameter("skillid");
				if (!Numeric.isNumber(skillId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
					return;
				}
								
				boolean success = task.upgradeSkills(userName, Long.parseLong(skillId));
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
			
			default: {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);				
			}
			break;
		}
	}
}
