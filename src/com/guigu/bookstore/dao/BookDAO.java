package com.guigu.bookstore.dao;

import java.util.Collection;
import java.util.List;

import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.domain.ShoppingCartItem;
import com.guigu.bookstore.web.CriteriaBook;
import com.guigu.bookstore.web.Page;

public interface BookDAO {

	/**
	 * ����id��ȡָ��Book����
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	
	/**
	 * ���ݴ����CriteriaBook���󷵻ض�Ӧ��Page����
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	
	/**
	 * ���ݴ����CriteriaBook ���󷵻����Ӧ�ļ�¼��
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * ���ݴ����CriteriaBook �� pageSize ���ص�ǰ��Ӧ��List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb,int pageSize);
	
	/**
	 * ����ָ�� ID ��Book ��storeNumber �ֶε�ֵ
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	
	/**
	 * ���ݴ����ShoppingCartItem�ļ���
	 * ��������books ���ݱ��storenumber �� salesamount �ֶε�ֵ
	 * @param items
	 */
	public abstract void batchupdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
}
