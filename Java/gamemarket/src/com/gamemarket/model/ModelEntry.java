package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Level;

public class ModelEntry extends ModelBase {
	
	public boolean entryDelete(final int entryId) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_entry_delete(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, entryId);			
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
	
	public boolean entryDeleteTable(final int entryTable) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_entry_delete_table(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, entryTable);			
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
	
	public boolean entryInsert(final int entryTable, final int entryOpen, final int entryTimes, final int entryDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_entry_insert(?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;	
			pre.setInt(i++, entryTable);
			pre.setInt(i++, entryOpen);
			pre.setInt(i++, entryTimes);
			pre.setInt(i++, entryDate);
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
		
	public List<Map<String, Object>> entrySelect(final int entryTable, final int entryStartDate, final int entryEndDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_entry_select_table(?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return null;
		}
		
		try {
			int i = 1;				
			pre.setInt(i++, entryTable);
			pre.setInt(i++, entryStartDate);
			pre.setInt(i++, entryEndDate);
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
