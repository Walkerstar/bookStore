package com.guigu.bookstore.test;

import org.junit.Test;

import com.guigu.bookstore.dao.BookDAO;
import com.guigu.bookstore.dao.impl.BookDAOImpl;
import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.web.CriteriaBook;
import com.guigu.bookstore.web.Page;

public class BookDAOTest {

	private BookDAO bookDAO=new BookDAOImpl();
	
	@Test
	public void testGetBook() {
		
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb=new CriteriaBook(0,Integer.MAX_VALUE,5);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("pageNo:"+page.getPageNo());
		System.out.println("totalPageNumber:"+page.getTotalPageNumber());
		System.out.println("list:"+page.getList());
		System.out.println("prevPage:"+page.getPrevPage());
		System.out.println("NextPage:"+page.getNextPage());
	}



	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDAO.getStoreNumber(6);
		System.out.println(storeNumber);
	}

}
