package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebBase extends HttpServlet {
	protected final String Charset = "UTF-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setCharacterEncoding(Charset);
		PrintWriter out = response.getWriter();
		onPost(request, response, out);
		out.flush();
		out.close();
	}
	
	protected abstract void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException;
}
