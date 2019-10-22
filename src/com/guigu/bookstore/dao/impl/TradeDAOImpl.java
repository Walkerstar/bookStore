package com.guigu.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.guigu.bookstore.dao.TradeDAO;
import com.guigu.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql="insert into trade(userid,tradetime) values(?,?)";
		long tradeid = insert(sql, trade.getUserId(),trade.getTradeTime());
		
		trade.setTradeId((int) tradeid);

	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql="select tradeId,userId,tradeTime from trade where "
				+ "userId=? order by tradeTime desc";
		return new LinkedHashSet(queryForList(sql, userId));
	}

}
