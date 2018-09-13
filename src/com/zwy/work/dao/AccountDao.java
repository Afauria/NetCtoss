package com.zwy.work.dao;

import com.zwy.work.entity.Account;
import com.zwy.work.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private Account createAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
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
            ps.setInt(1, accountId);
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

    public void addAccount(Account account) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "insert into account(real_name,idcard_no,login_name,login_passwd,telephone,email,recommender_id," +
                    "birthdate,gender,occupation,zipcode,mailaddress,qq,status) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,'1')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getRealName());
            ps.setString(2, account.getIdCard());
            ps.setString(3, account.getLoginName());
            ps.setString(4, account.getLoginPasswd());
            ps.setString(5, account.getTelephone());
            ps.setString(6, account.getEmail());
            ps.setInt(7, account.getRecommenderId());
            ps.setDate(8, new Date(account.getBirthdate().getTime()));
            ps.setString(9, account.getGender());
            ps.setString(10, account.getOccupation());
            ps.setString(11, account.getZipcode());
            ps.setString(12, account.getMailAddress());
            ps.setString(13, account.getQQ());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加账务账户失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void deleteAccount(Integer accountId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "update account set close_date=?,status='3' where account_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            Timestamp closeDate = new Timestamp(new java.util.Date().getTime());
            ps.setTimestamp(1, closeDate);
            ps.setInt(2, accountId);
            ps.executeUpdate();
            //删除账务账号下所有的业务账号
            String sql2 = "update service set close_date=?,status='3' where account_id=?";
            ps = conn.prepareStatement(sql2);
            ps.setTimestamp(1, closeDate);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除账务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public List<Account> searchAccounts(String status, String idcard, String realname, String loginname) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "select * from account where status like ? and idcard_no like ? " +
                    "and real_name like ? and login_name like ? order by account_id;";
            PreparedStatement ps = conn.prepareStatement(sql1);
            if (status.equals("0")) {
                status = "";
            }
            ps.setString(1, "%" + status + "%");
            ps.setString(2, "%" + idcard + "%");
            ps.setString(3, "%" + realname + "%");
            ps.setString(4, "%" + loginname + "%");

            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<Account>();
            while (rs.next()) {
                Account account = createAccount(rs);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询账务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public Account findAccountByIdCard(String idcard) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "SELECT * FROM account where idcard_no=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idcard);
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

    public Account findAccountByLoginName(String loginName) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM account where login_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loginName);
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

    public void updateAccount(Account account) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "update account set real_name=?, telephone=?, email=?, occupation=?, gender=?, " +
                    "mailaddress=?, zipcode=?, qq=? where account_id= ?";
            PreparedStatement ps = conn.prepareStatement(sql1);

            ps.setString(1, account.getRealName());
            ps.setString(2, account.getTelephone());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getOccupation());
            ps.setString(5, account.getGender());
            ps.setString(6, account.getMailAddress());
            ps.setString(7, account.getZipcode());
            ps.setString(8, account.getQQ());
            ps.setInt(9, account.getAccountId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新账务账号失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }

    public void setAccountState(Integer accountId, String status) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            String sql1 = "update account set status=?,pause_date=? where account_id=?";
            PreparedStatement ps = null;
            Timestamp pauseDate = null;
            if (status.equals("2")) {
                pauseDate = new Timestamp(new java.util.Date().getTime());
            }
            ps = conn.prepareStatement(sql1);
            ps.setString(1, status);
            ps.setTimestamp(2, pauseDate);
            ps.setInt(3, accountId);
            ps.executeUpdate();
            //同时暂停下属的业务账号，开通跳过
            if (status.equals("2")) {
                String sql2 = "update service set status=?,pause_date=? where account_id=?";
                ps = conn.prepareStatement(sql2);
                ps.setString(1, status);
                ps.setTimestamp(2, pauseDate);
                ps.setInt(3, accountId);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改账务账号状态失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
}
