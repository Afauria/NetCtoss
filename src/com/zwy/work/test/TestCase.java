package com.zwy.work.test;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * JDBC
 * Java Database Connectivity
 * 希望用相同的方式访问不同的数据库,以实现与数据库无关的Java操作界面
 * JDBC定义了一套标准的接口,即访问数据库的通用API,
 * 不同的数据库厂商根据各自数据库的
 * 特点去实现这些接口
 * @author Administrator
 */
public class TestCase {
	@Test
	public void test1(){
		Connection conn = null;
		try {
			//1.注册驱动
			//告诉DriverManager用哪个jar包
			Class.forName("com.mysql.jdbc.Driver");
			//2.创建连接:DriverManager回自动调用驱动类中的方法去创建一个连接.
			conn= (Connection) DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "a410013112");
			//3.创建Statement
			Statement stm=conn.createStatement();
			//4.执行插入语句	,Java代码中SQL语句最后不能有分号
			int rows=stm.executeUpdate("INSERT INTO t_users "
					+ "(username,password) VALUES ('小丽','123')");
			System.out.println(conn);
			System.out.println(rows);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test2(){
		Connection conn = null;
		try {
			//1.注册驱动
			//告诉DriverManager用哪个jar包
			Class.forName("com.mysql.jdbc.Driver");
			//2.创建连接:DriverManager回自动调用驱动类中的方法去创建一个连接.
			conn= (Connection) DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=UTF-8", "root", "");
			//3.创建Statement
			Statement stm=conn.createStatement();
			//4.执行插入语句	,Java代码中SQL语句最后不能有分号
			ResultSet rs = stm.executeQuery("SELECT * FROM t_users");
			while (rs.next()) {
				String username=rs.getString("username");
				System.out.println(username);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//TestCase.class.getClassLoader() 获取类的路径 bin
	@Test
	public void test3(){
		try {
			Properties p=new Properties();
			p.load(TestCase.class.getClassLoader().
					getResourceAsStream("db.properties"));
			String driver=p.getProperty("driver");
			System.out.println(driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//测试DBTools
	@Test
	public  void test4(){
		Connection conn=null;
		try {
			conn= (Connection) DBTools.getConnection();
			System.out.println(conn);
			Statement stm=conn.createStatement();
			String sql="insert into t_users(username,password) "
					+ "values ('王五','123456')";
			int rows=stm.executeUpdate(sql);
			System.out.println(rows);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				DBTools.close(conn);
			}
		}
	}


}
