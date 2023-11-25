package com.harmony.common;

import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	public static Connection getConnection() {
		Connection con = null;
		String path = JDBCTemplate.class.getResource("/common/driver.properties").getPath();
		Properties prop = new Properties();
		try {			
			prop.load(new FileReader(path));
			Class.forName(prop.getProperty("driver"));
			con = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("user"), 
					prop.getProperty("pw"));
			con.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return con;
	}
	public static void close(Connection con) {
		try {
			if(con !=null && !con.isClosed()) con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement st) {
		try {
			if(st !=null && !st.isClosed()) st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs !=null && !rs.isClosed()) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void commit(Connection con) {
		try {
			if(con !=null && !con.isClosed()) con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection con) {
		try {
			if(con !=null && !con.isClosed()) con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
