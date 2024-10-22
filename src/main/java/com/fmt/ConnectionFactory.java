package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  This is a class that establishes a database connection.
 *  
 *  Name: Peter Morales
 *  Date: 2023-04-14
 *
 */
public class ConnectionFactory {

	public static Connection getConn() {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	
}
