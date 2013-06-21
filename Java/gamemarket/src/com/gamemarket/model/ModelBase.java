package com.gamemarket.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gamemarket.util.Logs;
import com.gamemarket.util.db.DataBase;

public abstract class ModelBase {
	protected final Logger Log = Logs.getDefault();
	protected Connection mConn = null;
	
	public ModelBase() {
		mConn = DataBase.getPool();
	}
	
	public void destroy() {
		DataBase.close(mConn);
	}
	
	protected void closeStmt(PreparedStatement stmt) {
		if (stmt == null)
			return;
		
		try {
			stmt.close();
		} catch (SQLException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}	
	}
	
	protected void closeRs(ResultSet rs) {
		if (rs == null)
			return;
		
		try {
			rs.close();
		} catch (SQLException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
			
	protected Map<String, Object> createField(ResultSet rs) {		
		ResultSetMetaData meta = null;
		
		try {
			meta = rs.getMetaData();
		} catch (Exception e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
						
		try {
			Map<String, Object> map = new HashMap<String, Object>();			
			for (int i = 1;i <= meta.getColumnCount();i++) {
				String name = meta.getColumnName(i);
				name = name.toLowerCase();																	
				map.put(name, rs.getObject(i));					
			}
			
			return map;
		} catch (Exception e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}		
	}
}
