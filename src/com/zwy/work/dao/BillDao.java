package com.zwy.work.dao;

import com.zwy.work.entity.Admin;
import com.zwy.work.entity.Bill;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDao {

    private Bill createBill(ResultSet rs) throws SQLException {
        Bill bill=new Bill();
        bill.setBillId(rs.getInt("bill_id"));
        bill.setAccountId(rs.getInt("account_id"));
        bill.setBillFee(rs.getFloat("bill_fee"));
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月");
        String month=format.format(rs.getTimestamp("bill_month"));
        System.out.println(month);
        System.out.println(rs.getTimestamp("bill_month"));
        bill.setBillMonth(month);
        bill.setPayMode(rs.getString("pay_mode"));
        bill.setPayStatus(rs.getString("pay_status"));
        return bill;
    }
    public List<Bill> findBills() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM bill order by bill_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Bill> bills = new ArrayList<Bill>();
            while (rs.next()) {
                Bill bill = createBill(rs);
                bills.add(bill);
            }
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账单列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public Bill findBillById(Integer billId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM bill where bill_id=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1,billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill bill = createBill(rs);
                return bill;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账单失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }

    public List<Bill> searchBills(String idCard,String loginName,String realName,String year,String month){
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "select * from bill natural join account where idcard_no like ? " +
                    "and login_name like ? and real_name like ? and date_format(bill_month,'%Y-%m') like ?" +
                    "order by bill_id;";
            PreparedStatement ps=conn.prepareStatement(sql1);
            ps.setString(1,"%"+idCard+"%");
            ps.setString(2,"%"+loginName+"%");
            ps.setString(3,"%"+realName+"%");
            if(month.equals("全部")){
                month="";
            }
            String billMonth=year+"-%"+month;
            ps.setString(4,billMonth+"%");
            ResultSet rs = ps.executeQuery();
            List<Bill> bills = new ArrayList<Bill>();
            while (rs.next()) {
                Bill bill = createBill(rs);
                bills.add(bill);
            }
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询管理员列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
//    public void addBill(){
//        Connection conn = null;
//        try {
//            conn = DBUtils.getConnection();
//            String sql1 = "insert into bill natural join account where idcard_no like ? " +
//                    "and login_name like ? and real_name like ? and date_format(bill_month,'%Y-%m') like ?" +
//                    "order by bill_id;";
//            PreparedStatement ps=conn.prepareStatement(sql1);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("添加账单失败:" + e.getMessage(), e);
//        } finally {
//            DBUtils.close(conn);
//        }
//    }
}
