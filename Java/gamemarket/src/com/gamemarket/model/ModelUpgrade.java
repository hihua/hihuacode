package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;

public class ModelUpgrade extends ModelBase {
			
	public boolean upgradeDeleteTable(final int upgradeTable) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_delete_table(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, upgradeTable);			
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
	
	public Map<String, Object> upgradeSelect(final int upgradeTable) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_select(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, upgradeTable);			
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

	public List<Map<String, Object>> upgradeSelectAll(final int adminId, final int adminParent) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_select_all(?,?)}");
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
	
	public List<Map<String, Object>> upgradeSelectTable1(final int adminId, final int adminParent) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_select_table(?,?)}");
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
	
	public boolean upgradeInsert(final int upgradeTable, final int upgradeVersionCode, final String upgradeVersionName, final int upgradeDate, final String upgradeFilename, final int upgradeForce) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_insert(?,?,?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, upgradeTable);			
			pre.setInt(i++, upgradeVersionCode);
			pre.setString(i++, upgradeVersionName);
			pre.setInt(i++, upgradeDate);
			pre.setString(i++, upgradeFilename);			
			pre.setInt(i++, upgradeForce);
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
	
	public boolean upgradeUpdateForce(final int upgradeTable, final int upgradeForce) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_upgrade_update_force(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, upgradeTable);			
			pre.setInt(i++, upgradeForce);
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
}
