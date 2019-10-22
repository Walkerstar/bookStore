package com.guigu.bookstore.dao;

import java.util.Set;

import com.guigu.bookstore.domain.Trade;

public interface TradeDAO {
	
	/**
	 * 向数据表中插入Trade对象
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	
	/**
	 * 根据userId获取和其关联的Trade的集合
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);

}
