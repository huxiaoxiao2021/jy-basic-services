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
}
