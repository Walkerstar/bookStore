package com.guigu.bookstore.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.guigu.bookstore.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC �Ĺ�����
 * @author Administrator
 *
 */
public class JDBCUtils {

	private static DataSource source=null;
	
	static {
		source=new ComboPooledDataSource("javawebapp");
	}
	
	//��ȡ����
	public static Connection getConnection() {
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("���ݿ����Ӵ���!");
		}
	}
	
	
	//�ر����ݿ�����
	public static void release(Connection conn) {
		try {
			if(conn!=null) {				
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("�ر����ݿ�����conn����");
		}
	}
	
	//�ر����ݿ�����
	public static void release(ResultSet rs,Statement ps) {
		try {
			if(rs!=null) {
				rs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBException("�ر����ݿ�����rs����");
		}
		
		try {
			if(ps!=null) {
				ps.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBException("�ر����ݿ�����ps����");
		}
	}
	
}
