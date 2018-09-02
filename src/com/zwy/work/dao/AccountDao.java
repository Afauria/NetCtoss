package com.zwy.work.dao;

import com.zwy.work.entity.Account;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private Account createAccount(ResultSet rs) throws SQLException {
        Account account=new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setRecommenderId(rs.getInt("recommender_id"));
        account.setLoginName(rs.getString("login_name"));
        account.setLoginPasswd(rs.getString("login_passwd"));
        account.setStatus(rs.getString("status"));
        account.setCreateDate(rs.getTimestamp("create_date"));
        account.setPauseDate(rs.getTimestamp("pause_date"));
        account.setCloseDate(rs.getTimestamp("close_date"));
        account.setRealName(rs.getString("real_name"));
        account.setIdCard(rs.getString("idcard_no"));
        account.setBirthdate(rs.getDate("birthdate"));
        account.setGender(rs.getString("gender"));
        account.setOccupation(rs.getString("occupation"));
        account.setTelephone(rs.getString("telephone"));
        account.setEmail(rs.getString("email"));
        return account;
    }
    public List<Account> findAccounts() {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM account order by account_id";
            ResultSet rs = stm.executeQuery(sql);
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = createAccount(rs);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账务账号列表失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public Account findAccountById(int accountId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "SELECT * FROM account where account_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = createAccount(rs);
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
        return null;
    }
}
