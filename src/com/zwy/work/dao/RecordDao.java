package com.zwy.work.dao;

import com.zwy.work.entity.Account;
import com.zwy.work.entity.Record;
import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private Record createRecord(ResultSet rs) throws SQLException {
        Record record=new Record();
        record.setRecordId(rs.getInt("record_id"));
        record.setServiceId(rs.getInt("service_id"));
        record.setLoginTime(rs.getTimestamp("login_time"));
        record.setLogoutTime(rs.getTimestamp("logout_time"));
        record.setDuration(rs.getInt("duration"));
        record.setFee(rs.getFloat("fee"));
        return record;
    }

    public List<Record> findRecordsByServiceId(Integer serviceId) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "SELECT * FROM record_info where service_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, serviceId);
            ResultSet rs = ps.executeQuery();
            List<Record> records = new ArrayList<>();
            while (rs.next()) {
                Record record = createRecord(rs);
                records.add(record);
            }
            return records;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询业务记录失败:" + e.getMessage(), e);
        } finally {
            DBUtils.close(conn);
        }
    }
}
