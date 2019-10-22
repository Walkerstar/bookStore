package com.guigu.bookstore.service;

import java.util.Iterator;
import java.util.Set;

import com.guigu.bookstore.dao.BookDAO;
import com.guigu.bookstore.dao.TradeDAO;
import com.guigu.bookstore.dao.TradeItemDAO;
import com.guigu.bookstore.dao.UserDAO;
import com.guigu.bookstore.dao.impl.BookDAOImpl;
import com.guigu.bookstore.dao.impl.TradeDAOImpl;
import com.guigu.bookstore.dao.impl.TradeItemDAOImpl;
import com.guigu.bookstore.dao.impl.UserDAOImpl;
import com.guigu.bookstore.domain.Trade;
import com.guigu.bookstore.domain.TradeItem;
import com.guigu.bookstore.domain.User;

public class UserService {
	
	private UserDAO userdao=new UserDAOImpl();
	private TradeDAO tradedao=new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	
	public User getUserByUserName(String username) {
		return userdao.getUser(username);
	}

	public User getUserWithTrades(String username) {
		User user=userdao.getUser(username);
		if(user==null) {
			return null;
		}
		
		Integer userId = user.getUserId();
		
		Set<Trade> trades = tradedao.getTradesWithUserId(userId);
		
		if(trades!=null) {
			Iterator<Trade> tradeIt = trades.iterator();
			
			while(tradeIt.hasNext()) {
				Trade trade = tradeIt.next();
				
				Integer tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items!=null) {
					for(TradeItem item:items) {
						item.setBook(bookDAO.getBook(item.getBookId()));
					}
					
					if(items!=null && items.size()!=0) {
						trade.setItems(items);
					}
				}
				
				if(items==null || items.size()==0) {
					tradeIt.remove();
				}
			}
		}
		
		if(trades!=null && trades.size()!=0) {
			user.setTrades(trades);
		}
		
		return user;
	}

}
