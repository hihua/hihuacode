package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.auto.AutoTask;
import com.util.Numeric;

public class WebCities extends WebBase {
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
				try {										
					JSONArray array = new JSONArray();
					Hashtable<Long, String> cities = task.getCities(userName);
					if (cities != null) {
						Enumeration<Long> enu = cities.keys();
						while (enu.hasMoreElements()) {
							Long cityId = enu.nextElement();
							String cityName = cities.get(cityId);
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("cityid", cityId);
							jsonObject.put("cityname", cityName);
							array.add(jsonObject);
						}
					}					
					
					JSONObject json = new JSONObject();
					json.element("cities", array);
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
												
				String city = task.getCityInfo(userName, Long.parseLong(cityId));
				if (city == null)  {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				try {
					JSONObject json = JSONObject.fromObject(city);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}			
			break;
				
			case 2: {
				String cityId = request.getParameter("cityid");
				if (!Numeric.isNumber(cityId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
												
				String updateTime = task.getUpdateTime(userName, Long.parseLong(cityId));
				if (updateTime == null)  {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("updatetime", updateTime);
				
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
			
			case 3: {
				String owner = request.getParameter("owner");
				if (owner == null || owner.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String cityId = request.getParameter("cityid");
				if (!Numeric.isNumber(cityId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String city = task.getCity(userName, owner, Long.parseLong(cityId));
				if (city == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				try {
					JSONObject json = JSONObject.fromObject(city);
					String s = json.toString();
					out.print(s);					
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
			}
			break;
			
			case 4: {								
				String cityId = request.getParameter("cityid");
				if (!Numeric.isNumber(cityId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String type = request.getParameter("type");
				if (type == null || type.length() == 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String number = request.getParameter("number");
				if (!Numeric.isNumber(number)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				boolean success = task.configureWorkers(userName, Long.parseLong(cityId), type, Long.parseLong(number));
				
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
