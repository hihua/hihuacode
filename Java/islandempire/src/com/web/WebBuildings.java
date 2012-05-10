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

public class WebBuildings extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String buildingId = request.getParameter("building_id");
		if (!Numeric.isNumber(buildingId)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
		
		AutoTask task = AutoTask.getInstance();
		Hashtable<String, Long> table = new Hashtable<String, Long>();				
				
		if (task.requestBuildings(Long.parseLong(buildingId)))
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
