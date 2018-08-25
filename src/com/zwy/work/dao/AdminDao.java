package com.zwy.work.dao;

import com.zwy.work.entity.Admin;
import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    public Admin findUserByCode(String code) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM admin_info WHERE admin_code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("admin_id"));
                a.setAdminCode(rs.getString("admin_code"));
                a.setPassword(rs.getString("password"));
                a.setAdminName(rs.getString("admin_name"));
                a.setTelephone(rs.getString("telephone"));
                a.setEmail(rs.getString("email"));
                a.setEnrolldate(rs.getTimestamp("enrolldate"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询管理员失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public int updateUserInfo(Admin user) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE admin_info SET "
                    + "admin_name=?,telephone=?,email=? WHERE admin_code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getAdminName());
            ps.setString(2, user.getTelephone());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAdminCode());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新个人信息失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public int modifyPwd(Admin user) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE admin_info SET "
                    + "password=? WHERE admin_code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getAdminCode());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改密码失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public static void main(String[] args) {
        AdminDao dao = new AdminDao();
        Admin admin = dao.findUserByCode("caocao");
        System.out.println(admin.getAdminCode() + "," + admin.getPassword());
    }
}
