package com.zwy.work.dao;

import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao {
    public String findRoleNameById(Integer roleId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM role_info WHERE roleId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String roleName = rs.getString("name");
                return roleName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询角色名称失败" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public String findRoleByAdminId(Integer adminId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT role_name FROM role_info NATURAL JOIN admin_role WHERE admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            StringBuilder roles = new StringBuilder();
            while (rs.next()) {
                String roleName = rs.getString("role_name");
                if (!rs.isFirst()) {
                    roles.append(",");
                }
                roles.append(roleName);
            }
            return roles.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询用户角色失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
}
