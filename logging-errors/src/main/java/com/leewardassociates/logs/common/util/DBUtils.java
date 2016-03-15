package com.leewardassociates.logs.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leewardassociates.logs.models.ConnectionInfoModel;

public class DBUtils {
	
	private static String DB_URL = "";
	private static String DB_USER = "";
	private static String DB_PASSWORD = "";
	
	private static Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	public static void init(ConnectionInfoModel params) {
		DB_URL = params.getUrl();
		DB_USER = params.getUser();
		DB_PASSWORD = params.getPassword();
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		return conn;
	}
	
	public static void destroyConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			log.error("Exception destroying connection: " + e.getMessage(), e);
		} finally {
			conn = null;
		}
	}
	
	public static void registerDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			log.error("Exception registering driver '" + driver + "': " + e.getMessage(), e);
		}
	}
}
