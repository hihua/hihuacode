package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Level;

public class ModelRecord extends ModelBase {
	
	public boolean recordDeleteTable(final int recordTable) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_record_delete_table(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, recordTable);			
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
	
	public boolean recordInsert(final int entryTable, final String recordImei, final int recordDate, final String recordVersionName) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_record_insert(?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, entryTable);			
			pre.setString(i++, recordImei);
			pre.setInt(i++, recordDate);
			pre.setString(i++, recordVersionName);
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
