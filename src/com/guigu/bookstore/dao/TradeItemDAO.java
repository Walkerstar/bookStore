package com.guigu.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.guigu.bookstore.domain.TradeItem;

public interface TradeItemDAO {

	/**
	 * ��������TradeItem����
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	
	/**
	 * ����trade��ȡ�����������TradeItem�ļ���
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);
}
