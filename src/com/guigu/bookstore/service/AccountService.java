package com.guigu.bookstore.service;

import com.guigu.bookstore.dao.AccountDAO;
import com.guigu.bookstore.dao.impl.AccountDAOImpl;
import com.guigu.bookstore.domain.Account;

public class AccountService {
	private AccountDAO accountdao=new AccountDAOImpl();
	
	public Account getAccount(int accountId) {
		return accountdao.get(accountId);
	}

}
