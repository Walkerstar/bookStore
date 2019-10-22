package com.guigu.bookstore.domain;

/**
 * 封装了购物车中的商品, 包含对商品的引用以及购物车中该商品的数量
 *
 */
public class ShoppingCartItem {
	/**
	 * 书的信息
	 */
	private Book book;
	
	/**
	 * 书的数量
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
	 * 返回该商品在购物车中的钱数
	 * @return
	 */
	public float getItemMoney() {
		return book.getPrice()*quantity;
	}
	
	/**
	 * 商品数量加1
	 */
	public void increment() {
		quantity++;
	}

}
