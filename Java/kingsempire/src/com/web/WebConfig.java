package com.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.auto.AutoTask;

public class WebConfig extends WebBase {	
	private final String ContentType = "text/xml";

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		response.setContentType(ContentType);
		String method = request.getMethod();
		
		if (method.equals("GET")) {
			AutoTask task = AutoTask.getInstance();
			String xml = task.getConfig();
			if (xml == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			} else {
				out.print(xml);
				return;
			}
		}
		
		if (method.equals("POST")) {
			InputStream in = request.getInputStream();
			if (in != null) {
				AutoTask task = AutoTask.getInstance();			
				boolean success = task.setConfig(in);
				in.close();
				
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("ret");
				
				if (success)
					root.addText("0");
				else
					root.addText("1");
								 
				String xml = document.asXML();
				out.print(xml);
				return;
			}
		}
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
