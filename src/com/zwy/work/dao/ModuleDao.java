package com.zwy.work.dao;

import com.zwy.work.entity.Module;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDao {
    private Module createModule(ResultSet rs) throws SQLException{
        Module module = new Module();
        module.setModuleId(rs.getInt("module_id"));
        module.setModuleName(rs.getString("module_name"));
        return module;
    }
    public List<Module> findRoleModulesByRoleId(int roleId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM role_module natural join module_info where role_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,roleId);
            ResultSet rs = ps.executeQuery();
            List<Module> modules = new ArrayList<Module>();
            while (rs.next()) {
                Module module = createModule(rs);
                modules.add(module);
            }
            return modules;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询角色权限失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
    public List<Module> findModules(){
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM module_info order by module_id";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            List<Module> modules = new ArrayList<Module>();
            while (rs.next()) {
                Module module = createModule(rs);
                modules.add(module);
            }
            return modules;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询所有权限失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
    public Module findModuleById(Integer moduleId){
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM module_info where module_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,moduleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Module module = createModule(rs);
                return module;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询指定权限失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }
}
