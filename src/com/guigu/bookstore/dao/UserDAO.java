package com.guigu.bookstore.dao;

import com.guigu.bookstore.domain.User;

public interface UserDAO {

	/**
	 * �����û�����ȡUser����
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);
}
