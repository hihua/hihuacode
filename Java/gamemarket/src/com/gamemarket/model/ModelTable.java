package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;

public class ModelTable extends ModelBase {

	public Map<String, Object> tableExists(final String tableName, final int tableAdmin) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_table_exists(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;	
			pre.setString(i++, tableName);
			pre.setInt(i++, tableAdmin);
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
		
		Map<String, Object> map = null;
		
		try {
			if (rs.next())
				map = createField(rs);								
						
			closeRs(rs);
			closeStmt(pre);
			return map;
		} catch (SQLException e) {
			closeRs(rs);
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
	}
	
	public boolean tableInsert(final String tableName, final String tableDesc, final int tableAdmin, final int tableDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_table_insert(?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setString(i++, tableName);
			pre.setString(i++, tableDesc);
			pre.setInt(i++, tableAdmin);
			pre.setInt(i++, tableDate);
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
	
	public List<Map<String, Object>> tableSelect(final int adminId, final int adminParent) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_table_select(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, adminId);
			pre.setInt(i++, adminParent);
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
		
		List<Map<String, Object>> list = new Vector<Map<String,Object>>();
		
		try {
			while (rs.next()) {
				Map<String, Object> map = createField(rs);
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
