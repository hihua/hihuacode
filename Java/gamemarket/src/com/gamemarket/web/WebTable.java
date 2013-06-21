package com.gamemarket.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gamemarket.model.ModelTable;
import com.gamemarket.util.Numeric;

public class WebTable extends WebBase {

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		final String command = request.getParameter("command");
		if (command == null) {
			setCode(1);
			return;
		}
		
		if (command.equals("0")) {
			final String tableName = getParameter(request, "table_name");
			final String tableDesc = getParameter(request, "table_desc");
						
			if (checkString(tableName)) {
				final HttpSession session = request.getSession();
				if (session != null) {
					Object object = session.getAttribute("admin");
					if (object != null) {
						final Map<String, Object> map = (Map<String, Object>) object;
						if (map.containsKey("admin_id")) {
							object = map.get("admin_id");
							final int adminId = Numeric.toInteger(object);
							final ModelTable table = new ModelTable();
							if (table.tableExists(tableName, adminId) == null) {
								int now = getNow();
								if (!table.tableInsert(tableName, tableDesc, adminId, now)) {
									setCode(1);
									table.destroy();
									return;
								}
							}
														
							table.destroy();
							return;
						}
					}				
				}
			}			
		}
		
		if (command.equals("1")) {
			final String adminId = getParameter(request, "admin_id");
			final String adminParent = getParameter(request, "admin_parent");
			
			if (Numeric.isNumber(adminId) && Numeric.isInteger(adminId) && Numeric.isNumber(adminParent) && Numeric.isInteger(adminParent)) {
				final ModelTable table = new ModelTable();
				final List<Map<String, Object>> list = table.tableSelect(Integer.parseInt(adminId), Integer.parseInt(adminParent));
				if (list != null)
					setResult(list);
				
				table.destroy();
				return;
			}				
		}
		
		setCode(1);
	}
}
