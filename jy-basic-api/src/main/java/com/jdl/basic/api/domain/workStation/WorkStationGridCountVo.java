package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;

/**
 * 网格统计信息
 * 
 * @author wuyoude
 * @date 2021年12月30日 14:30:43
 *
 */
@Data
public class WorkStationGridCountVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 作业区数量
	 */
	private Integer areaNum;
	/**
	 * 工序数量
	 */
	private Integer workNum;
	/**
	 * 网格号数量
	 */
	private Integer gridNoNum;
	

	
}
