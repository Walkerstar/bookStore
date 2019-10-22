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
	 * ����һ��ҳ�������
	 * @param cb
	 * @return
	 */
	public Page<Book> getPage(CriteriaBook cb){
		return bookdao.getPage(cb);
	}
	
	/**
	 * ����id ,���ظ����������Ϣ
	 * @param id
	 * @return
	 */
	public Book getBook(int id) {
		return bookdao.getBook(id);
	}

	/**
	 * �����Ʒ�����ﳵ
	 * @param id ������鱾id
	 * @param sc ���ﳵ����
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

	//ҵ�񷽷�.
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		//1. ���� mybooks ���ݱ���ؼ�¼�� salesamount �� storenumber
		bookdao.batchupdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
//		int i=10/0;
		
		//2. ���� account ���ݱ�� balance
		accountdao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
	
		//3. �� trade ���ݱ����һ����¼
		Trade trade=new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userdao.getUser(username).getUserId());
		tradedao.insert(trade);
		
		//4. �� tradeitem ���ݱ���� n ����¼
		Collection<TradeItem> items=new ArrayList<>();
		for(ShoppingCartItem sci:shoppingCart.getItems()) {
			TradeItem tradeItem=new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		
		tradeItemdao.batchSave(items);
		//5. ��չ��ﳵ
		shoppingCart.clear();
		
	}
	
}
