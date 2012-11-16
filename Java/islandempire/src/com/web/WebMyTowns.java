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

public class WebMyTowns extends WebBase {
	private final String ContentType = "application/json";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		
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
				String townId = request.getParameter("town_id");
				if (!Numeric.isNumber(townId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
										
				String packet = task.getMyTown(Long.parseLong(townId));
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
			break;
			
			case 1: {
				String packet = task.getMyMessages();
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
			break;
			
			case 2: {
				String d = request.getParameter("do");
				if (d == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String packet = task.getMyRanks(d);
				if (packet == null) {
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
			break;
			
			case 3: {
				String townId = request.getParameter("town_id");
				if (!Numeric.isNumber(townId)) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);					
					return;
				}
				
				String packet = task.getMyEquipment(Long.parseLong(townId));
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
			break;
			
			case 4: {
				String to = request.getParameter("to");
				String from = request.getParameter("from");
				String subject = request.getParameter("subject");
				String body = request.getParameter("body");
				
				if (to == null || from == null || subject == null || body == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
				to = new String(to.getBytes("ISO8859-1"), Charset);
				from = new String(from.getBytes("ISO8859-1"), Charset);
				subject = new String(subject.getBytes("ISO8859-1"), Charset);
				body = new String(body.getBytes("ISO8859-1"), Charset);
				
				Hashtable<String, Long> table = new Hashtable<String, Long>();
				if (task.postMyMessages(to, from, subject, body))
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
			
			case 5: {
				String mailId = request.getParameter("mail_id");				
				if (mailId == null) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
				String packet = task.requestBattle(mailId);
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
			break;
			
			default: {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);				
			}
			break;
		}
	}
}
