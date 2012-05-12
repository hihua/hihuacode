package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebTransport extends WebBase {
	private final String ContentType = "application/json";
	
	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
		String fromTownId = request.getParameter("from_town_id");
		if (!Numeric.isNumber(fromTownId)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
		
		String toTownId = request.getParameter("to_town_id");
		if (!Numeric.isNumber(toTownId)) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
		
		HashMap<String, Long> resources = new HashMap<String, Long>();
		String wood = request.getParameter("wood");
		if (Numeric.isNumber(wood))
			resources.put("wood", Long.parseLong(wood));
		
		String food = request.getParameter("food");
		if (Numeric.isNumber(food))
			resources.put("food", Long.parseLong(food));
		
		String gold = request.getParameter("gold");
		if (Numeric.isNumber(gold))
			resources.put("gold", Long.parseLong(gold));
		
		String iron = request.getParameter("iron");
		if (Numeric.isNumber(iron))
			resources.put("iron", Long.parseLong(iron));
		
		String marble = request.getParameter("marble");
		if (Numeric.isNumber(marble))
			resources.put("marble", Long.parseLong(marble));
		
		if (resources.size() == 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
			return;
		}
		
		AutoTask task = AutoTask.getInstance();		
		Hashtable<String, Long> table = new Hashtable<String, Long>();				
		
		if (task.requestTransport(Long.parseLong(fromTownId), Long.parseLong(toTownId), resources))
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
