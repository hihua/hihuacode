package com.gamemarket.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gamemarket.util.Logs;

public class DataBase {
	private static Logger Log = Logs.getDefault();
	private static DataSource DataPool;
    
    public static boolean init() {
    	Context env = null;    
        try {
        	env = (Context) new InitialContext().lookup("java:comp/env");
        	DataPool = (DataSource)env.lookup("gamemarket/connection");        	
        	if (DataPool == null)
        		return false;        	
         } catch(NamingException e) {
        	 Log.log(Level.ERROR, e.getMessage(), e);
        	 return false;
         }
         
         return true;
    }
    
    public static Connection getPool() {
    	if (DataPool == null) {
    		init();
    		if (DataPool == null)
    			return null;
    	}
    	    	
    	try {
			return DataPool.getConnection();
		} catch (SQLException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		}
    }
    
    public static void close(Connection conn) {
    	if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {				
				Log.log(Level.ERROR, e.getMessage(), e);
			}
    	}
    }
}
