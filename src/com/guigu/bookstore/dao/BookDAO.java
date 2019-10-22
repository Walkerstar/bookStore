package com.guigu.bookstore.dao;

import java.util.Collection;
import java.util.List;

import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.domain.ShoppingCartItem;
import com.guigu.bookstore.web.CriteriaBook;
import com.guigu.bookstore.web.Page;

public interface BookDAO {

	/**
	 * 根据id获取指定Book对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	
	/**
	 * 根据传入的CriteriaBook对象返回对应的Page对象
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	
	/**
	 * 根据传入的CriteriaBook 对象返回其对应的记录数
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * 根据传入的CriteriaBook 和 pageSize 返回当前对应的List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb,int pageSize);
	
	/**
	 * 返回指定 ID 的Book 的storeNumber 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	
	/**
	 * 根据传入的ShoppingCartItem的集合
	 * 批量更新books 数据表的storenumber 和 salesamount 字段的值
	 * @param items
	 */
	public abstract void batchupdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
}
