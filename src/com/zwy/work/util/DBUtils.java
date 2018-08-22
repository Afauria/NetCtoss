package com.zwy.work.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    //声明一个连接池对象
    private static BasicDataSource bds;

    static {
//		读取连接参数
        try {
            Properties p = new Properties();
            p.load(DBUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            String driver = p.getProperty("driver");
            String url = p.getProperty("url");
            String username = p.getProperty("username");
            String password = p.getProperty("password");

            int initSize = Integer.parseInt(p.getProperty("initSize"));
            int maxActive = Integer.parseInt(p.getProperty("maxActive"));
            bds = new BasicDataSource();
            bds.setDriverClassName(driver);
            bds.setUrl(url);
            bds.setUsername(username);
            bds.setPassword(password);
            bds.setInitialSize(initSize);//初始化连接数量
            bds.setMaxActive(maxActive);//最大连接数量
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //该方法并不是真正的关闭的连接,它是把连接归还给连接池
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //返回一个由连接池管理的连接
    public static Connection getConnection() throws SQLException {
        return bds.getConnection();
    }
}
