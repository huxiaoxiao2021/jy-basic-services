package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 作业区信息表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkAreaQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 作业区编码
	 */
	private String areaCode;

	/**
	 * 作业区名称
	 */
	private String areaName;

	/**
	 * 业务条线编码
	 */
	private String businessLineCode;

	/**
	 * 业务条线名称
	 */
	private String businessLineName;

	/**
	 * 作业区类型
	 */
	private Integer areaType;

	/**
	 * 流向类型 1-进 2-出 0-其他
	 */
	private Integer flowDirectionType;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

}
