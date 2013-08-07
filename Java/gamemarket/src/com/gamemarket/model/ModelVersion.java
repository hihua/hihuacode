package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;

public class ModelVersion extends ModelBase {

	public boolean versionInsert(final String versionName, final int versionTimes, final int versionDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_version_insert(?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setString(i++, versionName);
			pre.setInt(i++, versionTimes);	
			pre.setInt(i++, versionDate);
		} catch (SQLException e) {				
			closeStmt(pre);			
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		try {
			pre.execute();
			closeStmt(pre);
			return true;
		} catch (SQLException e) {
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
	}
	
	public List<Map<String, Object>> versionSelect(final int versionStartDate, final int versionEndDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_version_select(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, versionStartDate);
			pre.setInt(i++, versionEndDate);			
		} catch (SQLException e) {				
			closeStmt(pre);			
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
		
		ResultSet rs;
		
		try {
			rs = pre.executeQuery();
		} catch (SQLException e) {
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
		
		final List<Map<String, Object>> list = new Vector<Map<String,Object>>();
		
		try {
			while (rs.next()) {
				final Map<String, Object> map = createField(rs);
				list.add(map);
			}
						
			closeRs(rs);
			closeStmt(pre);
			return list;
		} catch (SQLException e) {
			closeRs(rs);
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
	}
}
