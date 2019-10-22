package com.guigu.bookstore.test;

import org.junit.Test;

import com.guigu.bookstore.dao.AccountDAO;
import com.guigu.bookstore.dao.impl.AccountDAOImpl;
import com.guigu.bookstore.domain.Account;

public class AccountDAOTest {

	AccountDAO accountdao=new AccountDAOImpl();
	@Test
	public void testGet() {
		Account account=accountdao.get(2);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		accountdao.updateBalance(1, 50);
	}

}
