package com.guigu.bookstore.dao.impl;

import com.guigu.bookstore.dao.UserDAO;
import com.guigu.bookstore.domain.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql="select userId,username,accountId from userinfo where username = ?  ";
		return query(sql, username);
	}

}
