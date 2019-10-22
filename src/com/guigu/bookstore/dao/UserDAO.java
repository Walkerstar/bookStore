package com.guigu.bookstore.dao;

import com.guigu.bookstore.domain.User;

public interface UserDAO {

	/**
	 * 根据用户名获取User对象
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);
}
