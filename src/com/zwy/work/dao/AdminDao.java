package com.zwy.work.dao;

import com.zwy.work.entity.Admin;
import com.zwy.work.entity.Role;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private Admin createAdmin(ResultSet rs) throws SQLException {
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

    public Admin findUserByCode(String code) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM admin_info WHERE admin_code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = createAdmin(rs);
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询管理员失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public Admin findAdminById(int adminId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM admin_info WHERE admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = createAdmin(rs);
                return admin;
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

    public List<Admin> findAdmins() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM admin_info order by admin_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Admin> admins = new ArrayList<Admin>();
            while (rs.next()) {
                Admin admin = createAdmin(rs);
                admins.add(admin);
            }
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询管理员列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public List<Admin> searchAdmins(String moduleName, String roleName) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "select * from admin_info where admin_id in" +
                    "(SELECT distinct admin_id FROM admin_role natural join role_module " +
                    "natural join module_info natural join role_info " +
                    "where module_name=? and role_name like ?)order by admin_id;";
            String sql2 = "select * from admin_info where admin_id in" +
                    "(SELECT distinct admin_id FROM admin_role natural join role_info " +
                    "where role_name like ?)order by admin_id;";
            String sql3 = "select * from admin_info where admin_id in" +
                    "(SELECT distinct admin_id FROM admin_role natural join role_module " +
                    "natural join module_info where module_name=?)order by admin_id;";
            String sql4 = "select * from admin_info order by admin_id;";
            PreparedStatement ps;
            if(!roleName.equals("")&&!moduleName.equals("全部")){
                ps = conn.prepareStatement(sql1);
                ps.setString(1, moduleName);
                ps.setString(2, "%" + roleName + "%");
            } else if (moduleName.equals("全部")&&!roleName.equals("")) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1,"%" + roleName + "%");
            } else if(!moduleName.equals("全部")&&roleName.equals("")) {
                ps = conn.prepareStatement(sql3);
                ps.setString(1, moduleName);
            }else{
                ps = conn.prepareStatement(sql4);
            }
            ResultSet rs = ps.executeQuery();
            List<Admin> admins = new ArrayList<Admin>();
            while (rs.next()) {
                Admin admin = createAdmin(rs);
                admins.add(admin);
            }
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询管理员列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void updateAdmin(Admin admin) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //更新管理员信息
            String sql = "UPDATE admin_info SET "
                    + "admin_name=?,telephone=?,email=? WHERE admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getAdminName());
            ps.setString(2, admin.getTelephone());
            ps.setString(3, admin.getEmail());
            ps.setInt(4, admin.getAdminId());
            ps.executeUpdate();
            //删除管理员已有角色
            String sql2 = "delete from admin_role where admin_id=?";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, admin.getAdminId());
            ps.executeUpdate();
            //重新插入管理员角色
            String sql3 = "insert into admin_role(admin_id,role_id) values(?,?)";
            ps = conn.prepareStatement(sql3);
            for (Role role : admin.getAdminRoles()) {
                ps.setInt(1, admin.getAdminId());
                ps.setInt(2, role.getRoleId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新管理员信息失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void addAdmin(Admin admin) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //插入管理员信息
            String sql = "insert into admin_info(admin_code,admin_name,password,telephone,email) " +
                    "values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getAdminCode());
            ps.setString(2, admin.getAdminName());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getTelephone());
            ps.setString(5, admin.getEmail());
            ps.executeUpdate();
            //查询新插入的管理员id
            String sql2 = "select admin_id from admin_info where admin_code=?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, admin.getAdminCode());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                admin.setAdminId(rs.getInt("admin_id"));
            }
            //插入管理员角色
            String sql3 = "insert into admin_role values (?,?)";
            ps = conn.prepareStatement(sql3);
            for (Role role : admin.getAdminRoles()) {
                ps.setInt(1, admin.getAdminId());
                ps.setInt(2, role.getRoleId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加管理员失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void deleteAdmin(Integer adminId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //删除admin_info表数据
            String sql = "delete from admin_info where admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, adminId);
            ps.executeUpdate();
            //删除admin_role表数据
            String sql2 = "delete from admin_role where admin_id=?";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, adminId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除管理员失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }


    public void resetPwd(String[] selectAdminIds) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "update admin_info set password='123' where admin_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (String adminId : selectAdminIds) {
                ps.setInt(1, Integer.parseInt(adminId));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("重置管理员密码失败:" + e.getMessage(), e);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("重置管理员密码失败:" + e.getMessage(), e);
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
