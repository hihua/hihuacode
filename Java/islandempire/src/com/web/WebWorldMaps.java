package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;

public class WebWorldMaps extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		AutoTask task = AutoTask.getInstance();		
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		
		String packet = task.getWorldMaps(x, y);
		if (packet == null)  {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
		
		try {
			JSONObject json = JSONObject.fromObject(packet);
			String s = json.toString();
			out.print(s);					
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
	}	
}
