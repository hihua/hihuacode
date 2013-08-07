package com.gamemarket.servlet.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelEntry;
import com.gamemarket.model.ModelRecord;
import com.gamemarket.model.ModelTable;
import com.gamemarket.model.ModelUpgrade;
import com.gamemarket.util.Numeric;

public class WebTable extends WebBase {

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String command = request.getParameter("command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
		
		if (command.equals("0")) {
			final String tableName = getParameter(request, "table_name");
						
			if (checkString(tableName)) {				
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					final Object object = admin.get("admin_id");
					final int adminId = Numeric.toInteger(object);
					final int tableId = Integer.parseInt(Numeric.rndNumber(8));													
					final ModelTable table = new ModelTable();
					int count = 0;
					do {
						count = table.tableExists(tableId);
					} while (count > 0);
					
					if (count == 0) {
						final int now = getNow();
						if (table.tableInsert(tableId, tableName, adminId, now)) {									
							table.destroy();
							return;
						}
					}
					
					setCode(content, 1);														
					table.destroy();					
				}
				
				return;
			} 
		}
		
		if (command.equals("1")) {					
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
		
		if (command.equals("2")) {
			final String tableId = getParameter(request, "table_id");
			
			if (Numeric.isInteger(tableId)) {
				final Map<String, Object> admin = getAdmin(content, request);			
				if (admin != null) {		
					final ModelTable table = new ModelTable();
					Map<String, Object> map = table.tableSelectAdmin(Integer.parseInt(tableId));
					if (map != null) {
						if (checkAuthority(admin, map)) {
							final ModelEntry entry = new ModelEntry();
							final ModelUpgrade upgrade = new ModelUpgrade();
							final ModelRecord record = new ModelRecord();
							map = upgrade.upgradeSelect(Integer.parseInt(tableId));
							if (map != null) {
								final Object object = map.get("upgrade_filename");
								final String upgradeFilename = String.valueOf(object);
								final String dir = getServletContext().getRealPath(Upgrade);
								final File dest = new File(dir + "\\" + upgradeFilename);
								dest.delete();
							}								
								
							entry.entryDeleteTable(Integer.parseInt(tableId));
							upgrade.upgradeDeleteTable(Integer.parseInt(tableId));
							record.recordDeleteTable(Integer.parseInt(tableId));
							table.tableDelete(Integer.parseInt(tableId));
							entry.destroy();
							upgrade.destroy();
						} else					
							setCode(content, 2);										
					} else
						setCode(content, 3);
					
					table.destroy();
				}
				
				return;
			}			
		}
		
		setCode(content, 1);
	}
}
