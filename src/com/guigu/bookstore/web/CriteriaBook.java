package com.guigu.bookstore.web;

/**
 * criteria  标准，准则
 * 封装查询条件
 * @author Administrator
 *
 */

public class CriteriaBook {
	/**
	 * 最小值 默认为0
	 */
	private float minPrice=0;
	
	/**
	 * 价格最大值
	 */
	private float maxPrice=Integer.MAX_VALUE;
	
	/**
	 * 页码
	 */
	private int pageNo;
	
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
	}


	

}
