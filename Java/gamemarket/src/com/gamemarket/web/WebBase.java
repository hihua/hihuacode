package com.gamemarket.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gamemarket.util.DateTime;
import com.gamemarket.util.Logs;

public abstract class WebBase extends HttpServlet {
	protected final Logger Log = Logs.getDefault();
	protected final String Charset = "UTF-8";
	protected final String Json = "application/json";
	protected final String Text = "text/plain";
	protected final String Html = "text/html";
	protected final Map<String, Object> Map = new HashMap<String, Object>();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map.clear();
		logRequest(request);
		response.setCharacterEncoding(Charset);
		response.setContentType(Html);
		setCode(0);
		PrintWriter out = response.getWriter();
		try {
			onPost(request, response, out);
		} catch (Exception e) {				
			Log.log(Level.ERROR, e.getMessage(), e);
			throw new ServletException(e);
		}
				
		JSONObject json = JSONObject.fromObject(Map);
		String s = json.toString();
		out.print(s);
		out.flush();
		out.close();
	}
	
	private void logRequest(final HttpServletRequest request) {
		String method = request.getMethod();
		if (method == null)
			return;
		
		if (method.equals("GET")) {
			Log.log(Level.INFO, "url: " + request.getRequestURL());
			Log.log(Level.INFO, "parameter: " + request.getQueryString());
			return;
		}
		
		if (method.equals("POST")) {
			Log.log(Level.INFO, "url: " + request.getRequestURL());
			StringBuilder sb = new StringBuilder();
			Map<String, String[]> map = request.getParameterMap();
			if (map != null) {
				for (Entry<String, String[]> entry : map.entrySet()) {
					String key = entry.getKey();
					
					try {
						key = new String(key.getBytes("ISO8859-1"), Charset);
					} catch (UnsupportedEncodingException e) {
						Log.log(Level.ERROR, e.getMessage(), e);
					} 
					
					String[] value = entry.getValue();
					if (value != null) {
						for (String s : value) {
							try {
								s = new String(s.getBytes("ISO8859-1"), Charset);
							} catch (UnsupportedEncodingException e) {
								Log.log(Level.ERROR, e.getMessage(), e);
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
			
			Log.log(Level.INFO, "parameter: " + sb.toString());			
			return;
		}
	}
	
	protected boolean checkString(final String s) {
		if (s != null && s.length() > 0)
			return true;
		else
			return false;
	}
	
	protected int getNow() {
		return (int) DateTime.getNow();
	}
	
	protected void setCode(final int code) {
		Map.put("code", code);
	}
	
	protected void setResult(final Object object) {
		Map.put("result", object);
	}
	
	protected String getParameter(HttpServletRequest request, String parameter) {
		parameter = request.getParameter(parameter);
		if (parameter == null)
			return null;
		
		try {
			parameter = new String(parameter.getBytes("ISO8859-1"), Charset);
			return parameter;
		} catch (UnsupportedEncodingException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
	}
	
	protected abstract void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException;
}
