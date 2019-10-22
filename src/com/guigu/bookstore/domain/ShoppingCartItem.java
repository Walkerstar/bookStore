package com.guigu.bookstore.domain;

/**
 * ��װ�˹��ﳵ�е���Ʒ, ��������Ʒ�������Լ����ﳵ�и���Ʒ������
 *
 */
public class ShoppingCartItem {
	/**
	 * �����Ϣ
	 */
	private Book book;
	
	/**
	 * �������
	 */
	private int quantity;

	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity=1;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * ���ظ���Ʒ�ڹ��ﳵ�е�Ǯ��
	 * @return
	 */
	public float getItemMoney() {
		return book.getPrice()*quantity;
	}
	
	/**
	 * ��Ʒ������1
	 */
	public void increment() {
		quantity++;
	}

}
