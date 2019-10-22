package com.guigu.bookstore.test;

import org.junit.Test;

import com.guigu.bookstore.dao.UserDAO;
import com.guigu.bookstore.dao.impl.UserDAOImpl;
import com.guigu.bookstore.domain.User;

public class UserDAOTest {

	private UserDAO userdao=new UserDAOImpl();
	@Test
	public void testGetUser() {
		User user = userdao.getUser("AAA");
		System.out.println(user);
	}

}
