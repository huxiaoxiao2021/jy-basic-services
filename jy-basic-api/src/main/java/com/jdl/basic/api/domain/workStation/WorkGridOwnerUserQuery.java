package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 场地网格-负责人表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
@Setter
@Getter
public class WorkGridOwnerUserQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联场地网格key:work_grid
	 */
	private String refWorkGridKey;

	/**
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 班次:1-白班 2-中班 3-晚班
	 */
	private Integer waveCode;

	/**
	 * 负责人erp
	 */
	private String ownerUserErp;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

}
