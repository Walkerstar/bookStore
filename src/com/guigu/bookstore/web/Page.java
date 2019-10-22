package com.guigu.bookstore.web;

import java.util.List;

public class Page<T> {

	//当前第几页
	private int pageNo;
	
	//当前的List
	private List<T> list;
	
	//每页显示3条记录
	private int pagerSize=3;
	
	//共有多少条记录
	private long totalItemNumber;
	
	//构造器中需要对pageNo进行初始化
	public Page(int pageNo) {
		super();
		this.pageNo=pageNo;
	}
	
	
	//校验页码
	public int getPageNo() {
		if(pageNo<0) {
			pageNo=1;
		}
		if(pageNo>getTotalPageNumber()) {
			pageNo=getTotalPageNumber();
		}
		return pageNo;
	}

	

	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}


	public int getPagerSize() {
		return pagerSize;
	}


	//获取总页数
	public int getTotalPageNumber() {
		int totalPageNumber = (int) (totalItemNumber / pagerSize);

		if (totalItemNumber % pagerSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}


	//设置从数据库中查询的总记录数
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	//有没有下一页
	public boolean isHasNext() {
		if(getPageNo()<getTotalPageNumber()) {
			return true;
		}
		return false;
	} 
	
	//有没有上一页
	public boolean isHasPrev() {
		if(getPageNo()>1) {
			return true;
		}
		return false;
	}
	
	//获取上一页 页码
	public int getPrevPage() {
		if(isHasPrev()) {
			return getPageNo()-1;
		}
		return getPageNo();
	}
	
	
	//获取下一页 页码
	public int getNextPage() {
		if(isHasNext()) {
			return getPageNo()+1;
		}
		return getPageNo();
	}
	
}
