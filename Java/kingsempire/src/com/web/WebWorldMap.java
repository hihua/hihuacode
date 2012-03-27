package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebWorldMap extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String userName = request.getParameter("username");
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String x = request.getParameter("x");
		if (!Numeric.isNumber(x)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String y = request.getParameter("y");
		if (!Numeric.isNumber(y)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String width = request.getParameter("width");
		if (!Numeric.isNumber(width)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		AutoTask task = AutoTask.getInstance();
		
		String worldMap = task.getWorldMaps(userName, Long.parseLong(x), Long.parseLong(y), Long.parseLong(width));				
		if (worldMap == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}					
						
		try {
			JSONObject json = JSONObject.fromObject(worldMap);
			String s = json.toString();
			out.print(s);					
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
	}
}
