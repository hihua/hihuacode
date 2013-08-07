package com.gamemarket.servlet.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelVersion;
import com.gamemarket.util.DateTime;
import com.gamemarket.util.Numeric;

public class WebVersion extends WebBase {

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, Map<String, Object> content) throws ServletException, IOException {
		final String command = getParameter(request, "command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
						
		if (command.equals("0")) {			
			final String versionStartDate = getParameter(request, "version_start_date");
			final String versionEndDate = getParameter(request, "version_end_date");
					
			if (Numeric.isInteger(versionStartDate) || Numeric.isInteger(versionEndDate)) {										
				int startDate = 0;
				if (Numeric.isInteger(versionStartDate))
					startDate = Integer.parseInt(versionStartDate);
				
				int endDate = 0;
				if (Numeric.isInteger(versionEndDate))
					endDate = Integer.parseInt(versionEndDate);
				else {
					final String s = DateTime.getDateTime(new Date(), "yyyy-MM-dd");
					Date date = DateTime.getDateTime(s, "yyyy-MM-dd");
					date = DateTime.getDay(date, 1);
					endDate = (int) DateTime.getTimeStamp(date);
				}
				
				if (startDate > 0 && endDate > 0 && startDate == endDate) {						
					Date date = DateTime.getTimeStamp(startDate);
					date = DateTime.getDay(date, 1);
					endDate = (int) DateTime.getTimeStamp(date);
				}
				
				final Map<String, Object> admin = getAdmin(content, request);			
				if (admin != null) {				
					final ModelVersion version = new ModelVersion();
					final List<Map<String, Object>> list = version.versionSelect(startDate, endDate);
					version.destroy();
					
					if (list != null)
						setContent(content, list);				
				}
				
				return;
			}
		}
		
		setCode(content, 1);
	}	
}
