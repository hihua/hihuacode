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

public class WebRecruit extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String userName = request.getParameter("username");		
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		String cityId = request.getParameter("cityid");
		if (!Numeric.isNumber(cityId)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		String soldierName = request.getParameter("soldiername");
		if (soldierName == null || soldierName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		String count = request.getParameter("count");
		if (!Numeric.isNumber(cityId)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		AutoTask task = AutoTask.getInstance();
		boolean success = task.recruit(userName, Long.parseLong(cityId), soldierName, Long.parseLong(count));
				
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
}
