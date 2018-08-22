package com.zwy.work.dao;

import com.zwy.work.entity.Cost;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostDao {
    public List<Cost> findCost() {
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
            new RuntimeException("查询自费列表失败");
        } finally {
            DBUtils.close(conn);
        }
        return null;

    }

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
            new RuntimeException("保存失败");
        } finally {
            DBUtils.close(conn);
        }
        return 0;
    }

    //根据id查找信息
    public Cost findById(int id) {
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
            new RuntimeException("根据id查找信息失败");
        } finally {
            DBUtils.close(conn);
        }
        return null;

    }

    public int updateCost(Cost c) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE COST SET "
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
            new RuntimeException("保存失败");
        } finally {
            DBUtils.close(conn);
        }
        return 0;
    }

    public static void main(String[] args) {
        CostDao dao = new CostDao();
        Cost c = dao.findById(6);
        System.out.println(c);
    }
}
