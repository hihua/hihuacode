package com.gamemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;

import com.gamemarket.util.DateTime;

public abstract class ServletBase extends HttpServlet {
	protected final String Charset = "UTF-8";
	protected final String Json = "application/json";
	protected final String Text = "text/plain";
	protected final String Html = "text/html";	
	protected final String Upgrade = "upgrade";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Map<String, Object> content = new HashMap<String, Object>();
		logRequest(request);
		response.setCharacterEncoding(Charset);
		response.setContentType(Json);
		setCode(content, 0);
		PrintWriter out = response.getWriter();
		try {
			onPost(request, response, content);
		} catch (Exception e) {				
			writeLog(Level.ERROR, e.getMessage(), e);
			setCode(content, 1);
		}
				
		final JSONObject json = JSONObject.fromObject(content);
		final String s = json.toString();
		writeLog(Level.INFO, s);
		out.print(s);
		out.flush();
		out.close();
	}
	
	private void logRequest(final HttpServletRequest request) {
		String method = request.getMethod();
		if (method == null)
			return;
		
		if (method.equals("GET")) {
			writeLog(Level.INFO, "url: " + request.getRequestURL());
			writeLog(Level.INFO, "parameter: " + request.getQueryString());
			return;
		}
		
		if (method.equals("POST")) {
			writeLog(Level.INFO, "url: " + request.getRequestURL());
			StringBuilder sb = new StringBuilder();
			Map<String, String[]> map = request.getParameterMap();
			if (map != null) {				
				Map<String, String[]> tree = new TreeMap<String, String[]>();
				for (Entry<String, String[]> entry : map.entrySet()) {					
					String key = entry.getKey();															
					String[] value = entry.getValue();
					tree.put(key, value);				
				}
				
				for (Entry<String, String[]> entry : tree.entrySet()) {					
					String key = entry.getKey();
					
					try {
						key = new String(key.getBytes("ISO8859-1"), Charset);
					} catch (UnsupportedEncodingException e) {
						writeLog(Level.ERROR, e.getMessage(), e);
					} 
					
					String[] value = entry.getValue();
					if (value != null) {
						for (String s : value) {
							try {
								s = new String(s.getBytes("ISO8859-1"), Charset);
							} catch (UnsupportedEncodingException e) {
								writeLog(Level.ERROR, e.getMessage(), e);
							}
							
							sb.append("&");
							sb.append(key);
							sb.append("=");
							sb.append(s);
						}
					}				
				}
			}
			
			if (sb.length() > 0)
				sb.deleteCharAt(0);
			
			writeLog(Level.INFO, "parameter: " + sb.toString());			
		}
	}
	
	protected boolean checkString(final String s) {
		if (s != null && s.length() > 0)
			return true;
		else
			return false;
	}
	
	protected String getParameter(final HttpServletRequest request, String parameter) {
		parameter = request.getParameter(parameter);
		if (parameter == null)
			return null;
		
		try {
			parameter = new String(parameter.getBytes("ISO8859-1"), Charset);
			return parameter;
		} catch (UnsupportedEncodingException e) {			
			return null;
		}
	}
	
	protected int getNow() {
		return (int) DateTime.getNow();
	}
	
	protected int getDate() {
		return (int) DateTime.getDate();
	}
	
	protected void setCode(final Map<String, Object> content, final int code) {
		content.put("code", code);
	}
	
	protected void setContent(final Map<String, Object> content, final Object object) {
		content.put("content", object);
	}
	
	protected String cutString(final String s, final int max) {
		if (s == null)
			return null;
		
		if (max == 0)
			return s;
		
		if (s.length() > max)
			return s.substring(0, max);
		else
			return s;
	}
	
	protected abstract void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException;	
	protected abstract void writeLog(final Priority priority, final Object message, final Throwable t);
	protected abstract void writeLog(final Priority priority, final Object message);
}
