package com.guigu.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.guigu.bookstore.domain.TradeItem;

public interface TradeItemDAO {

	/**
	 * 批量保存TradeItem对象
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	
	/**
	 * 根据trade获取和其相关联的TradeItem的集合
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);
}
