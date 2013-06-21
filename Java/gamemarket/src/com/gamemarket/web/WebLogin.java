package com.gamemarket.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelLogin;
import com.gamemarket.model.ModelRecord;
import com.gamemarket.util.Numeric;

public class WebLogin extends WebBase {

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {				
		String loginTable = request.getParameter("login_table");
		String loginOpen = request.getParameter("login_open");
		String recordImei = request.getParameter("record_imei");
		
		if ((Numeric.isNumber(loginTable) && Numeric.isInteger(loginTable)) && (loginOpen.equals("0") || loginOpen.equals("1")) && checkString(recordImei)) {
			int now = getNow();
			ModelRecord record = new ModelRecord();
			ModelLogin login = new ModelLogin();
			record.recordInsert(Integer.parseInt(loginTable), recordImei, now);			
			login.loginInsert(Integer.parseInt(loginTable), Integer.parseInt(loginOpen), now);
			record.destroy();
			login.destroy();
		} else
			setCode(1);
	}
}
