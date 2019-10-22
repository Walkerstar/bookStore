package com.guigu.bookstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.guigu.bookstore.dao.TradeItemDAO;
import com.guigu.bookstore.dao.impl.TradeItemDAOImpl;
import com.guigu.bookstore.domain.TradeItem;



public class TradeItemDAOTest {

	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<>();
		
		items.add(new TradeItem(null, 1, 5, 17));
		items.add(new TradeItem(null, 2, 20, 17));
		items.add(new TradeItem(null, 3, 30, 17));
		items.add(new TradeItem(null, 4, 40, 17));
		items.add(new TradeItem(null, 5, 50, 17));
		
		tradeItemDAO.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(17);
		System.out.println(items);
	}

}
