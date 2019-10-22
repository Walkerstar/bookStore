package com.guigu.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.guigu.bookstore.dao.AccountDAO;
import com.guigu.bookstore.dao.BookDAO;
import com.guigu.bookstore.dao.TradeDAO;
import com.guigu.bookstore.dao.TradeItemDAO;
import com.guigu.bookstore.dao.UserDAO;
import com.guigu.bookstore.dao.impl.AccountDAOImpl;
import com.guigu.bookstore.dao.impl.BookDAOImpl;
import com.guigu.bookstore.dao.impl.TradeDAOImpl;
import com.guigu.bookstore.dao.impl.TradeItemDAOImpl;
import com.guigu.bookstore.dao.impl.UserDAOImpl;
import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.domain.ShoppingCart;
import com.guigu.bookstore.domain.ShoppingCartItem;
import com.guigu.bookstore.domain.Trade;
import com.guigu.bookstore.domain.TradeItem;
import com.guigu.bookstore.web.CriteriaBook;
import com.guigu.bookstore.web.Page;

public class BookService {
	
	private BookDAO bookdao=new BookDAOImpl();
	
	/**
	 * 返回一个页面的内容
	 * @param cb
	 * @return
	 */
	public Page<Book> getPage(CriteriaBook cb){
		return bookdao.getPage(cb);
	}
	
	/**
	 * 根据id ,返回该书的所有信息
	 * @param id
	 * @return
	 */
	public Book getBook(int id) {
		return bookdao.getBook(id);
	}

	/**
	 * 添加商品到购物车
	 * @param id 传入的书本id
	 * @param sc 购物车对象
	 * @return
	 */
	public boolean  addToCart(int id,ShoppingCart sc) {
		Book book = bookdao.getBook(id);
		
		if(book!=null) {
			sc.addBook(book);
			return true;
		}
		return false;
	}

	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.remover(id);
		
	}

	public void clearShoppingCart(ShoppingCart sc) {
	  sc.clear();
		
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
		
	}
	
	private AccountDAO accountdao=new AccountDAOImpl();
	private TradeDAO tradedao = new TradeDAOImpl();
	private UserDAO userdao = new UserDAOImpl();
	private TradeItemDAO tradeItemdao = new TradeItemDAOImpl();

	//业务方法.
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		//1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
		bookdao.batchupdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
//		int i=10/0;
		
		//2. 更新 account 数据表的 balance
		accountdao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
	
		//3. 向 trade 数据表插入一条记录
		Trade trade=new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userdao.getUser(username).getUserId());
		tradedao.insert(trade);
		
		//4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items=new ArrayList<>();
		for(ShoppingCartItem sci:shoppingCart.getItems()) {
			TradeItem tradeItem=new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		
		tradeItemdao.batchSave(items);
		//5. 清空购物车
		shoppingCart.clear();
		
	}
	
}
