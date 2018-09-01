package com.zwy.work.dao;

import com.zwy.work.entity.Bill;
import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    public List<Bill> findBills() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM bill order by bill_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Bill> bills = new ArrayList<Bill>();
            while (rs.next()) {
                Bill c = createBill(rs);
                bills.add(c);
            }
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账单列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    private Bill createBill(ResultSet rs) {
        return null;
    }
}
