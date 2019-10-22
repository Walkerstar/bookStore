package com.guigu.bookstore.web;

import java.util.List;

public class Page<T> {

	//��ǰ�ڼ�ҳ
	private int pageNo;
	
	//��ǰ��List
	private List<T> list;
	
	//ÿҳ��ʾ3����¼
	private int pagerSize=3;
	
	//���ж�������¼
	private long totalItemNumber;
	
	//����������Ҫ��pageNo���г�ʼ��
	public Page(int pageNo) {
		super();
		this.pageNo=pageNo;
	}
	
	
	//У��ҳ��
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


	//��ȡ��ҳ��
	public int getTotalPageNumber() {
		int totalPageNumber = (int) (totalItemNumber / pagerSize);

		if (totalItemNumber % pagerSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}


	//���ô����ݿ��в�ѯ���ܼ�¼��
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	//��û����һҳ
	public boolean isHasNext() {
		if(getPageNo()<getTotalPageNumber()) {
			return true;
		}
		return false;
	} 
	
	//��û����һҳ
	public boolean isHasPrev() {
		if(getPageNo()>1) {
			return true;
		}
		return false;
	}
	
	//��ȡ��һҳ ҳ��
	public int getPrevPage() {
		if(isHasPrev()) {
			return getPageNo()-1;
		}
		return getPageNo();
	}
	
	
	//��ȡ��һҳ ҳ��
	public int getNextPage() {
		if(isHasNext()) {
			return getPageNo()+1;
		}
		return getPageNo();
	}
	
}
