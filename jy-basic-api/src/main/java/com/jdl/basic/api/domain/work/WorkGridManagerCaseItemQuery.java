package com.jdl.basic.api.domain.work;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 作业区巡检-项目明细表-查询条件实体类
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@Setter
@Getter
public class WorkGridManagerCaseItemQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 场景编码
	 */
	private String caseCode;

	/**
	 * 检查项编码
	 */
	private String caseItemCode;

	/**
	 * 检查项描述内容
	 */
	private String caseItemText;

	/**
	 * 是否可选,0-否,1-是
	 */
	private Integer canSelect;

	/**
	 * 选择标志,0-未选中,1-选中
	 */
	private Integer selectFlag;

	/**
	 * 排序值，1-100
	 */
	private Integer orderNum;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;

}
