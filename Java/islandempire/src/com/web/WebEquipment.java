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

public class WebEquipment extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
				
		String equipmentId = request.getParameter("equipment_id");
		if (!Numeric.isNumber(equipmentId)) {
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
				String fromIndex = request.getParameter("from_index");
				if (!Numeric.isNumber(fromIndex)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String toIndex = request.getParameter("to_index");
				if (!Numeric.isNumber(toIndex)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String townId = request.getParameter("town_id");
				if (!Numeric.isNumber(townId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				Hashtable<String, Long> table = new Hashtable<String, Long>();
				
				if (task.putEquipment(Long.parseLong(equipmentId), Long.parseLong(fromIndex), Long.parseLong(toIndex), Long.parseLong(townId)))
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
				String townId = request.getParameter("town_id");
				if (!Numeric.isNumber(townId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				Hashtable<String, Long> table = new Hashtable<String, Long>();
				
				if (task.sellEquipment(Long.parseLong(equipmentId), Long.parseLong(townId)))
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
				String iniPrice = request.getParameter("ini_price");
				if (!Numeric.isNumber(iniPrice)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String totalTimeInhours = request.getParameter("total_time_inhours");
				if (!Numeric.isNumber(totalTimeInhours)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String townId = request.getParameter("town_id");
				if (!Numeric.isNumber(townId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				Hashtable<String, Long> table = new Hashtable<String, Long>();
				
				if (task.actionsEquipment(Long.parseLong(equipmentId), Long.parseLong(iniPrice), Long.parseLong(totalTimeInhours), Long.parseLong(townId)))
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
		}		
	}
}
