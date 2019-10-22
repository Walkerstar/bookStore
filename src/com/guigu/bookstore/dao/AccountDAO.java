package com.guigu.bookstore.dao;

import com.guigu.bookstore.domain.Account;

public interface AccountDAO {

	/**
	 * 根据accountId获取对应的Account对象
	 * @param accountId
	 * @return
	 */
	public abstract Account get(Integer accountId);
	
	/**
	 * 根据传入的accountId,amount 更新指定账户的余额:扣除amonut指定的钱数
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId,float amount);
}
