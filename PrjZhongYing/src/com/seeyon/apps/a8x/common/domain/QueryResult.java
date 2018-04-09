package com.seeyon.apps.a8x.common.domain;

import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable {

	private static final long serialVersionUID = 195653951950726173L;

	private List<?> datas = null;

	private int pageNo = 0;

	private int pageSize = 0;

	private int totalSize = 0;

	private int size;

	public QueryResult(List<?> datas) {
		this.datas = datas;
	}

	public QueryResult(List<?> datas, int totalSize, int pageNo, int pageSize) {
		this.datas = datas;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.size = size();
	}

	public List<?> getDatas() {
		return datas;
	}

	public int size() {
		if (datas == null) {
			return 0;
		}
		return datas.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean isDisplayByPage() {
		return pageNo > 0 && pageSize > 0;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return 1 + (totalSize - 1) / getPageSize();
	}

	public int getStartIndex() {
		return (getPageNo() - 1) * getPageSize() + 1;
	}

	public int getEndIndex() {
		return getStartIndex() + size - 1;
	}

	public int getTotalSize() {
		return totalSize;
	}
}