package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;

public class ModelAdmin extends ModelBase {

	public List<Map<String, Object>> adminAllSelect() {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_all_select()}");
		} catch (SQLException e) {				
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
	
	public boolean adminDelete(final int adminId) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_delete(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, adminId);			
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
	
	public boolean adminDescUpdate(final int adminId, final String adminDesc) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_desc_update(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, adminId);
			pre.setString(i++, adminDesc);
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
	
	public int adminExists(final String adminUsername) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_exists(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return -1;
		}
		
		try {
			int i = 1;	
			pre.setString(i++, adminUsername);
		} catch (SQLException e) {				
			closeStmt(pre);			
			Log.log(Level.ERROR, e.getMessage(), e);
			return -1;
		}
		
		ResultSet rs;
		
		try {
			rs = pre.executeQuery();
		} catch (SQLException e) {
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return -1;
		}
		
		int total = 0;
		
		try {
			if (rs.next())
				total = rs.getInt(1);
						
			closeRs(rs);
			closeStmt(pre);
			return total;
		} catch (SQLException e) {
			closeStmt(pre);
			Log.log(Level.ERROR, e.getMessage(), e);
			return -1;
		}
	}
	
	public Map<String, Object> adminIdSelect(final int adminId) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_id_select(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;	
			pre.setInt(i++, adminId);
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
	
	public boolean adminInsert(final String adminUsername, final String adminPassword, final int adminParent, final String adminDesc, final int adminDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_insert(?,?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;	
			pre.setString(i++, adminUsername);
			pre.setString(i++, adminPassword);
			pre.setInt(i++, adminParent);
			pre.setString(i++, adminDesc);
			pre.setInt(i++, adminDate);
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
	
	public List<Map<String, Object>> adminParentSelect(final int adminParent) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_parent_select(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;	
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
			
	public boolean adminPasswordUpdate(final int adminId, final String adminPassword) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_password_update(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, adminId);
			pre.setString(i++, adminPassword);
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
	
	public Map<String, Object> adminSelect(final String adminUsername, final String adminPassword) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_admin_select(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;	
			pre.setString(i++, adminUsername);
			pre.setString(i++, adminPassword);
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
}
