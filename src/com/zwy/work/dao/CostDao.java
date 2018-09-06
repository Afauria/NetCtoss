package com.zwy.work.dao;

import com.zwy.work.entity.Cost;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostDao {

    private Cost createCostEntity(ResultSet rs) throws SQLException {
        Cost c = new Cost();
        c.setCostId(rs.getInt("cost_id"));
        c.setName(rs.getString("name"));
        c.setBaseDuration(rs.getInt("base_duration"));
        c.setBaseCost(rs.getDouble("base_cost"));
        c.setUnitCost(rs.getDouble("unit_cost"));
        c.setStatus(rs.getString("status"));
        c.setDescr(rs.getString("descr"));
        c.setCreatime(rs.getTimestamp("creatime"));
        c.setStartime(rs.getTimestamp("startime"));
        c.setCostType(rs.getString("cost_type"));
        return c;
    }

    public List<Cost> findCosts() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM cost order by cost_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Cost> costs = new ArrayList<Cost>();
            while (rs.next()) {
                Cost c = createCostEntity(rs);
                costs.add(c);
            }
            return costs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询咨费列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public int save(Cost c) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "INSERT INTO COST VALUES "
                    + "(NULL,?,?,?,?,'1',?,DEFAULT,DEFAULT,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setObject(2, c.getBaseDuration());
            ps.setObject(3, c.getBaseCost());
            ps.setObject(4, c.getUnitCost());
            ps.setString(5, c.getDescr());
            ps.setString(6, c.getCostType());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    //根据id查找信息
    public Cost findCostById(int id) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM cost WHERE cost_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createCostEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查找资费失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;

    }

    public int updateCost(Cost c) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE cost SET "
                    + "name=?,base_duration=?,base_cost=?,unit_cost=?,descr=?,cost_type=? WHERE cost_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setObject(2, c.getBaseDuration());
            ps.setObject(3, c.getBaseCost());
            ps.setObject(4, c.getUnitCost());
            ps.setString(5, c.getDescr());
            ps.setString(6, c.getCostType());
            ps.setInt(7, c.getCostId());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新资费信息失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void deleteCost(Integer costId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            //删除role_info表数据
            String sql = "delete from cost where cost_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, costId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除资费失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public Cost findCostByName(String costName) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM cost WHERE name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, costName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createCostEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据名称查找资费失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }
    public static void main(String[] args) {
        CostDao dao = new CostDao();
        Cost c = dao.findCostById(6);
        System.out.println(c);
    }

}
