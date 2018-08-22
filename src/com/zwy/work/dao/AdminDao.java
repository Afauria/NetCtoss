package com.zwy.work.dao;

import com.zwy.work.entity.Admin;
import com.zwy.work.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
									
	public Admin findByCode(String code){
		Connection conn=null;
		try {
			conn=DBUtils.getConnection();
			String sql="SELECT * FROM admin_info WHERE admin_code=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				Admin a = new Admin();
				a.setAdminId(rs.getInt("admin_id"));
				a.setAdminCode(rs.getString("admin_code"));
				a.setPassword(rs.getString("password"));
				a.setName(rs.getString("name"));
				a.setTelephone(rs.getString("telephone"));
				a.setEmail(rs.getString("email"));
				a.setEnrolldate(rs.getTimestamp("enrolldate"));
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询管理员失败",e);
		}finally{
			DBUtils.close(conn);
		}
		return null;
	}
	public static void main(String[] args) {
		AdminDao dao=new AdminDao();
		Admin admin=dao.findByCode("caocao");
		System.out.println(admin.getAdminCode()+","+admin.getPassword());
	}
}
