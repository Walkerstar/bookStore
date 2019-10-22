package com.guigu.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.guigu.bookstore.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC 的工具类
 * @author Administrator
 *
 */
public class JDBCUtils {

	private static DataSource source=null;
	
	static {
		source=new ComboPooledDataSource("javawebapp");
	}
	
	//获取连接
	public static Connection getConnection() {
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
	
	
	//关闭数据库连接
	public static void release(Connection conn) {
		try {
			if(conn!=null) {				
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("关闭数据库连接conn错误");
		}
	}
	
	//关闭数据库连接
	public static void release(ResultSet rs,Statement ps) {
		try {
			if(rs!=null) {
				rs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBException("关闭数据库连接rs错误");
		}
		
		try {
			if(ps!=null) {
				ps.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBException("关闭数据库连接ps错误");
		}
	}
	
}
