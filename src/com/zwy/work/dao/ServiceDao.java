package com.zwy.work.dao;

import com.zwy.work.entity.Service;
import com.zwy.work.util.DBUtils;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    private Service createService(ResultSet rs) throws SQLException {
        Service service = new Service();
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

    public Service findServiceById(int serviceId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "SELECT * FROM service where service_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service service = createService(rs);
                return service;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public void deleteService(Integer serviceId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "update service set close_date=?,status='3' where service_id=?";
            Timestamp closeDate = new Timestamp(new java.util.Date().getTime());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, closeDate);
            ps.setInt(2, serviceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public List<Service> searchServices(String status, String idcard, String username, String host) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "select * from service where status like ? " +
                    "and os_username like ? and unix_host like ? order by service_id;";
            String sql2 = "select * from service where status like ? " +
                    "and os_username like ? and unix_host like ? " +
                    "and service_id in (select service_id from service, account where account.account_id=service.account_id and idcard_no like ?)" +
                    "order by service_id;";
            PreparedStatement ps;
            if (status.equals("0"))
                status = "";
            if (idcard.equals("")) {
                ps = conn.prepareStatement(sql1);
                ps.setString(1, "%" + status + "%");
                ps.setString(2, "%" + username + "%");
                ps.setString(3, "%" + host + "%");
            } else {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, "%" + status + "%");
                ps.setString(2, "%" + username + "%");
                ps.setString(3, "%" + host + "%");
                ps.setString(4, "%" + idcard + "%");
            }
            ResultSet rs = ps.executeQuery();
            List<Service> services = new ArrayList<Service>();
            while (rs.next()) {
                Service service = createService(rs);
                services.add(service);
            }
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void addService(Service service) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "insert into service(account_id,cost_id,unix_host,os_username,login_passwd,status) " +
                    "values(?,?,?,?,?,'1')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, service.getAccountId());
            ps.setInt(2, service.getCostId());
            ps.setString(3, service.getUnixHost());
            ps.setString(4, service.getOsUsername());
            ps.setString(5, service.getLoginPasswd());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加业务账户失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public Service findServiceByOsUserame(String osUsername) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM service where os_username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, osUsername);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service service = createService(rs);
                return service;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public void updateService(Service service) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "update service set cost_id=? where service_id= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, service.getCost().getCostId());
            ps.setInt(2, service.getServiceId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void setServiceState(Integer serviceId, String status) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "update service set status=?,pause_date=? where service_id=?";
            PreparedStatement ps = null;
            Timestamp pauseDate = null;
            if (status.equals("2")) {
                pauseDate = new Timestamp(new java.util.Date().getTime());
            }
            ps = conn.prepareStatement(sql1);
            ps.setString(1, status);
            ps.setTimestamp(2, pauseDate);
            ps.setInt(3, serviceId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改业务账号状态失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public List<Service> findServicesByAccountId(Integer accountId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "SELECT * FROM service where account_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            List<Service> services = new ArrayList<>();
            while (rs.next()) {
                Service service = createService(rs);
                services.add(service);
            }
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void createEvent(String eventName) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "create event cost_per " +
                    "on schedule every 10 second starts now() " +
                    "do " +
                    "begin " +
                    "   start transaction; " +
                    "       insert into bill(account_id,bill_fee,bill_month,pay_mode,pay_status) values(?,?,?,?,?); " +
                    "   commit; " +
                    "end";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setFloat(2, 4.55f);
            ps.setString(3, "111");
            ps.setString(4, "2222");
            ps.setString(5, "3333");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("计算账单失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
    @Test
    public void main(){
        createEvent("cost_per");
    }
}
