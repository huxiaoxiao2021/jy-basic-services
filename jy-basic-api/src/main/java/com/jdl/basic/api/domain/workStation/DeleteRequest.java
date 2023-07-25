package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 删除请求
 * @author wuyoude
 *
 * @param <T> 删除实体类型
 */
@Data
public class DeleteRequest<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 操作人站点
	 */
	private Integer operateSiteCode;
	/**
	 * 操作人编码
	 */
	private String operateUserCode;
	/**
	 * 操作人名称
	 */
	private String operateUserName;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 删除数据列表
	 */
	private List<T> dataList;
	/**
	 * 操作业务主键
	 */
	private String operateBusinessKey;
}
