package com.ht.bean.base;

import java.io.Serializable;

/**
 * 页码
 * @author swt
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pageNum		 	= 1;	// 当前页码
	private int pageSize		= 10;	// 每页记录数
	private long total			= 0;	// 总记录数

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
