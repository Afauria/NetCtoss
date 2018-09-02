package com.zwy.work.dao;

import com.zwy.work.entity.Service;
import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    private Service createService(ResultSet rs) throws SQLException {
        Service service=new Service();
        service.setServiceId(rs.getInt("service_id"));
        service.setAccountId(rs.getInt("account_id"));
        service.setUnixHost(rs.getString("unix_host"));
        service.setOsUsername(rs.getString("os_username"));
        service.setLoginPasswd(rs.getString("login_passwd"));
        service.setStatus(rs.getString("status"));
        service.setCreateDate(rs.getTimestamp("create_date"));
        service.setPauseDate(rs.getTimestamp("pause_date"));
        service.setCloseDate(rs.getTimestamp("close_date"));
        service.setCostId(rs.getInt("cost_id"));
        return service;
    }
    public List<Service> findServices() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM service order by service_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Service> services = new ArrayList<>();
            while (rs.next()) {
                Service service = createService(rs);
                services.add(service);
            }
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务账号列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

}
