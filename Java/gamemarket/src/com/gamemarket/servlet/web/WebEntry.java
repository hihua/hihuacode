package com.gamemarket.servlet.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelEntry;
import com.gamemarket.model.ModelTable;
import com.gamemarket.util.DateTime;
import com.gamemarket.util.Numeric;

public class WebEntry extends WebBase {

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String command = getParameter(request, "command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
		
		if (command.equals("0")) {
			final Map<String, Object> admin = getAdmin(content, request);			
			if (admin != null) {
				Object object = admin.get("admin_id");
				final int adminId = Numeric.toInteger(object);
				object = admin.get("admin_parent");
				final int adminParent = Numeric.toInteger(object);
				final ModelTable table = new ModelTable();
				final List<Map<String, Object>> list = table.tableSelect(adminId, adminParent);
				table.destroy();
				
				if (list != null)
					setContent(content, list);											
			}
			
			return;
		}
		
		if (command.equals("1")) {
			final String entryTable = getParameter(request, "entry_table");
			final String entryStartDate = getParameter(request, "entry_start_date");
			final String entryEndDate = getParameter(request, "entry_end_date");
			
			if (Numeric.isInteger(entryTable)) {
				if (Numeric.isInteger(entryStartDate) || Numeric.isInteger(entryEndDate)) {										
					int startDate = 0;
					if (Numeric.isInteger(entryStartDate))
						startDate = Integer.parseInt(entryStartDate);
					
					int endDate = 0;
					if (Numeric.isInteger(entryEndDate))
						endDate = Integer.parseInt(entryEndDate);
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
						final ModelTable table = new ModelTable();
						final Map<String, Object> map = table.tableSelectAdmin(Integer.parseInt(entryTable));
						table.destroy();
						
						if (map != null) {
							if (checkAuthority(admin, map)) {
								final ModelEntry entry = new ModelEntry();
								final List<Map<String, Object>> list = entry.entrySelect(Integer.parseInt(entryTable), startDate, endDate);
								entry.destroy();
								
								if (list != null)
									setContent(content, list);																
							} else
								setCode(content, 3);														
						} else
							setCode(content, 2);
					} 
					
					return;
				}
			}			
		}
		
		setCode(content, 1);
	}
}
