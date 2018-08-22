package com.zwy.work.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBTools {
	private static String drvier;
	private static String url;
	private static String username;
	private static String password;
	static{
		try {
			Properties p=new Properties();
			p.load(DBTools.class.getClassLoader().getResourceAsStream("db.properties"));
			drvier=p.getProperty("driver");
			url=p.getProperty("url");
			username=p.getProperty("username");
			password=p.getProperty("password");
			//注册驱动，只需要注册一次即可
			Class.forName(drvier);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}

	public static void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
