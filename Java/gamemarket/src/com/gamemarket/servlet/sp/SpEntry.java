package com.gamemarket.servlet.sp;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelRecord;
import com.gamemarket.util.Numeric;

public class SpEntry extends SpBase {

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String entryTable = getParameter(request, "entry_table");
		final String entryOpen = getParameter(request, "entry_open");
		String recordImei = getParameter(request, "record_imei");
		String recordVersionName = getParameter(request, "record_version_name");
						
		if (Numeric.isInteger(entryTable) && checkString(entryOpen) && (entryOpen.equals("0") || entryOpen.equals("1")) && checkString(recordImei) && checkString(recordVersionName) && checkRequest(request)) {
			recordImei = cutString(recordImei, 32);
			recordVersionName = cutString(recordVersionName, 50);
			final int now = getNow();
			final ModelRecord record = new ModelRecord();			
			record.recordInsert(Integer.parseInt(entryTable), recordImei, now, recordVersionName);			
			record.destroy();			
		} else
			setCode(content, 1);		
	}
}
