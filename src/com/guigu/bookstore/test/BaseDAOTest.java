package com.guigu.bookstore.test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.guigu.bookstore.dao.impl.BookDAOImpl;
import com.guigu.bookstore.db.JDBCUtils;
import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.web.ConnectionContext;


public class BaseDAOTest {
	private BookDAOImpl bookDAOImpl=new BookDAOImpl();
	
	@Test
	public void testInsert() {
		String sql="insert into trade(userid,tradetime) values(?,?)";
		long id = bookDAOImpl.insert(sql,1, new Date(new java.util.Date().getTime()));
		
		System.out.println(id);
	}
	
	@Test
	public void testUpdate() {
		String sql="update mybooks set salesamount= ? where id= ? ";
		bookDAOImpl.update(sql,4,6);
	}
	
	@Test
	public void testQuery() {
		Connection conn=JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(conn);
		String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		
		Book book = bookDAOImpl.query(sql, 4);
		System.out.println(book);
	}
	
	@Test
	public void testQueryForList() {
		Connection conn=JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(conn);
		String sql = "SELECT id, author, title, price, publishingDate, salesAmount, storeNumber, remark " + 
				"FROM mybooks WHERE id < ?";
		
		List<Book> books = bookDAOImpl.queryForList(sql, 3);
		System.out.println(books);
	}
	
	@Test
	public void testGetSingleVal() {
		String sql = "SELECT count(id) FROM mybooks";

		long count = bookDAOImpl.getSingleVal(sql);
		System.out.println(count); 
	}
	
	@Test
	public void testBatch() {
		String sql = "UPDATE mybooks SET salesAmount = ?, storeNumber = ? " +
				"WHERE id = ?";
		
		bookDAOImpl.batch(sql, new Object[]{1, 1, 1}, new Object[]{2, 2, 2}, new Object[]{3, 3, 3}); 
	}
	
	

}
