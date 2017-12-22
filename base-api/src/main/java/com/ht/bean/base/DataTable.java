package com.ht.bean.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 数据集合
 * @author swt
 */
public class DataTable<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Page page = new Page();
	private List<T> dataList = new ArrayList<T>();

	public DataTable() {
	}

	public DataTable(Page page, List<T> dataList) {
		this.page = page;
		this.dataList = dataList;
	}

	public DataTable(int pageNum, int pageSize, long total, List<T> dataList) {
		this.page.setPageNum(pageNum);
		this.page.setPageSize(pageSize);
		this.page.setTotal(total);
		this.dataList = dataList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
