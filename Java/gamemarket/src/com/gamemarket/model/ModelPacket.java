package com.gamemarket.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Level;

public class ModelPacket extends ModelBase {

	public Map<String, Object> packetSelect() {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_packet_select()}");
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
	
	public boolean packetUpdate(final int versionCode, final String versionName, final int packetStatus, final String packetFilename) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_packet_update(?,?,?,?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setInt(i++, versionCode);
			pre.setString(i++, versionName);
			pre.setInt(i++, packetStatus);
			pre.setString(i++, packetFilename);
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
	
	public boolean packetUpdateFilename(final String packetFilename) {
		PreparedStatement pre;
		
		try {
			pre = mConn.prepareStatement("{call p_packet_update(?)}");
		} catch (SQLException e) {				
			Log.log(Level.ERROR, e.getMessage(), e);			
			return false;
		}
		
		try {
			int i = 1;		
			pre.setString(i++, packetFilename);
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
