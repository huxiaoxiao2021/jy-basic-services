package com.jdl.basic.ommon.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageDto<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页
	 */
	private int currentPage = 1;
	/**
	 * 每页条数
 	 */
	private int pageSize = 10;
	/**
	 * 总条数
 	 */
	private int totalRow;
	/**
	 * 总页数
 	 */
	private int totalPage;
	/**
	 * 集合-结果集||查询集
	 */
	private List<T> result;

	public PageDto() {
	}

	public PageDto(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	public int getTotalRow() {
		return this.totalRow;
	}

	public void setTotalRow(int totalRow) {
		if (totalRow > 0) {
			this.totalRow = totalRow;
		}
	}

	public int getTotalPage() {
		return (int) Math.ceil((double) getTotalRow() / (double) getPageSize());
	}

	public int getOffset() {
		int page_index = getCurrentPage() - 1;
		page_index = page_index < 0 ? 0 : page_index;
		return page_index * pageSize;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return (getCurrentPage() < getTotalPage());
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}
