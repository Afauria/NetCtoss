package com.zwy.work.dao;

import com.zwy.work.entity.Module;
import com.zwy.work.entity.Role;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    private Role createRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));
        return role;
    }
    public Role findRoleById(Integer roleId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM role_info WHERE role_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Role role = createRole(rs);
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询角色名称失败" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public List<Role> findRolesByAdminId(Integer adminId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM role_info NATURAL JOIN admin_role WHERE admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            List<Role> roles = new ArrayList<>();
            while (rs.next()) {
                Role role = createRole(rs);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询用户角色失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public List<Role> findRoles() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM role_info order by role_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Role> roles = new ArrayList<Role>();
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询角色列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void updateRoleInfo(Role role) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //更新角色名称
            String sql = "UPDATE role_info SET "
                    + "role_name=? WHERE role_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, role.getRoleName());
            ps.setInt(2, role.getRoleId());
            ps.executeUpdate();
            //删除角色已有权限
            String sql2 = "delete from role_module where role_id=?";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, role.getRoleId());
            ps.executeUpdate();
            //重新插入角色权限
            String sql3 = "insert into role_module values(?,?)";
            ps = conn.prepareStatement(sql3);
            for (Module module : role.getRoleModules()) {
                ps.setInt(1, role.getRoleId());
                ps.setInt(2, module.getModuleId());
                ps.addBatch();
            }
           /*如果存在则更新，不存在则插入，DUAL并没有什么实际用途，只是为了满足select * form 习惯
           String sql2="insert into role_module(role_id,module_id) " +
                    "select ?,? from DUAL where not exists("+
                    "select role_id,module_id from role_module where role_id=? and module_id=?)";
            ps=conn.prepareStatement(sql2);
            for (Module module:role.getRoleModules()) {
                ps.setInt(1, role.getRoleId());
                ps.setInt(2, module.getModuleId());
                ps.setInt(3, role.getRoleId());
                ps.setInt(4, module.getModuleId());
                ps.addBatch();
            }*/
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新角色信息失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void addRole(Role role) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //插入角色信息
            String sql = "insert into role_info(role_name) values(?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, role.getRoleName());
            ps.executeUpdate();
            //查找新插入的角色id
            String sql2 = "select role_id from role_info where role_name=?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, role.getRoleName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                role.setRoleId(rs.getInt("role_id"));
            }
            //重新插入角色权限
            String sql3 = "insert into role_module values(?,?)";
            ps = conn.prepareStatement(sql3);
            for (Module module : role.getRoleModules()) {
                ps.setInt(1, role.getRoleId());
                ps.setInt(2, module.getModuleId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新角色信息失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void deleteRole(Integer roleId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //删除role_info表数据
            String sql = "delete from role_info where role_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, roleId);
            ps.executeUpdate();
            //删除role_module表数据
            String sql2 = "delete from role_module where role_id=?";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, roleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除角色失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
}
