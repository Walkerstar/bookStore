package com.guigu.bookstore.web;

/**
 * criteria  ��׼��׼��
 * ��װ��ѯ����
 * @author Administrator
 *
 */

public class CriteriaBook {
	/**
	 * ��Сֵ Ĭ��Ϊ0
	 */
	private float minPrice=0;
	
	/**
	 * �۸����ֵ
	 */
	private float maxPrice=Integer.MAX_VALUE;
	
	/**
	 * ҳ��
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
