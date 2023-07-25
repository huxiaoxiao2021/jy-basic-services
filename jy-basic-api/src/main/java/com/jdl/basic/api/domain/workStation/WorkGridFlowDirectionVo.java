package com.jdl.basic.api.domain.workStation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 场地网格流向表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkGridFlowDirectionVo extends WorkGridFlowDirection implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 流向-推荐使用状态 1-推荐
	 */
	private Integer flowSiteUseStatus;
	/**
	 * 流向-推荐使用状态名称
	 */
	private String flowSiteUseStatusName;
	/**
	 * 流向配置-状态
	 */
	private Integer configFlowStatus;
	/**
	 * 流向配置-状态
	 */
	private String configFlowStatusName;
	
}
