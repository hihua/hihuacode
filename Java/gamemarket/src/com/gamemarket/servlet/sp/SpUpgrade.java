package com.gamemarket.servlet.sp;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelUpgrade;
import com.gamemarket.util.Numeric;

public class SpUpgrade extends SpBase {

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String upgradeTable = getParameter(request, "upgrade_table");
		
		if (Numeric.isInteger(upgradeTable) && checkRequest(request)) {
			final ModelUpgrade upgrade = new ModelUpgrade();
			Map<String, Object> map = upgrade.upgradeSelect(Integer.parseInt(upgradeTable));
			upgrade.destroy();
			
			if (map != null) {
				final Object upgradeVersionCode = map.get("upgrade_version_code");
				final Object upgradeVersionName = map.get("upgrade_version_name");
				final String upgradeFilename = String.valueOf(map.get("upgrade_filename"));				
				final Object upgradeForce = map.get("upgrade_force");
				map.clear();
				
				final String dir = getServletContext().getRealPath(Upgrade);				
				final File file = new File(dir + "\\" + upgradeFilename);
				final long upgradeFileSize = file.length();
				
				final String serverName = request.getServerName();
				final int serverPort = request.getServerPort();
				final String url = "http://" + serverName + (serverPort == 80 ? "" : ":" + serverPort) + "/gamemarket/" + Upgrade + "/" + upgradeFilename;				
				map.put("upgrade_version_code", upgradeVersionCode);
				map.put("upgrade_version_name", upgradeVersionName);
				map.put("upgrade_url", url);	
				map.put("upgrade_filesize", upgradeFileSize);
				map.put("upgrade_force", upgradeForce);	
				setContent(content, map);
				return;
			}	
		} 
		
		setCode(content, 1);
	}
}
