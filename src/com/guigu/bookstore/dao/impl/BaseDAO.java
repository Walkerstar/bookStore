package com.guigu.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.guigu.bookstore.dao.Dao;
import com.guigu.bookstore.db.JDBCUtils;
import com.guigu.bookstore.utils.ReflectionUtils;
import com.guigu.bookstore.web.ConnectionContext;
import com.mysql.jdbc.Statement;

public class BaseDAO<T> implements Dao<T> {

	private QueryRunner runner=new QueryRunner();
	
	private Class<T> clazz;
	
	public BaseDAO() {
		clazz=ReflectionUtils.getSuperGenericType(getClass());
	}
	
	/**
	 * 插入后返回插入记录的id
	 * @param sql
	 * @param args
	 * @return
	 */
	@Override
	public long insert(String sql, Object... args) {
		long id=0;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn=ConnectionContext.getInstance().get();
			ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			if(args!=null) {
				for(int i=0;i<args.length;i++) {
					ps.setObject(i+1, args[i]);
				}
			}
			
			ps.executeUpdate();
			
			//获取生成的主键值
			rs=ps.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, ps);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			runner.update(conn, sql,args);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public T query(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			return runner.query(conn, sql, new BeanHandler<>(clazz),args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			return runner.query(conn, sql, new BeanListHandler<>(clazz) ,args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			return (V) runner.query(conn, sql, new ScalarHandler(),args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection conn=null;
		try {
			conn=ConnectionContext.getInstance().get();
			runner.batch(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
