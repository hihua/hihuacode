package com.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;

public class WebRequestOthers extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
				
		if (!request.getMethod().equals("POST")) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
						
		String userName = request.getParameter("username");
		String url = request.getParameter("url");
		
		if (userName == null || userName.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		if (url == null || url.length() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
		
		InputStream in = request.getInputStream();
		if (in == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			return;
		}
						
		AutoTask task = AutoTask.getInstance();
		boolean success = task.requestOthers(userName, url, in);
		in.close();
		
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
