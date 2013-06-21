package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Level;

public class ModelRecord extends ModelBase {
	
	public boolean recordInsert(final int recordTable, final String recordImei, final int recordDate) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_login_new_insert(?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, recordTable);			
			pre.setString(i++, recordImei);
			pre.setInt(i++, recordDate);
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
