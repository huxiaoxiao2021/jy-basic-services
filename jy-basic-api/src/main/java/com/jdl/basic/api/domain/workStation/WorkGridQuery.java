package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.jdl.basic.api.domain.BasePagerCondition;

/**
 * 场地网格表-查询条件实体类
 *
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@Setter
@Getter
public class WorkGridQuery extends BasePagerCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务主键：site_code、floor、grid_no、area_code
	 */
	private String businessKey;

	/**
	 * 机构编码
	 */
	private Integer orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 场地编码
	 */
	private Integer siteCode;

	/**
	 * 场地名称
	 */
	private String siteName;

	/**
	 * 楼层
	 */
	private Integer floor;

	/**
	 * 网格号:01~99
	 */
	private String gridNo;

	/**
	 * 网格ID
	 */
	private String gridCode;

	/**
	 * 网格名称
	 */
	private String gridName;

	/**
	 * 作业区编码
	 */
	private String areaCode;

	/**
	 * 作业区名称
	 */
	private String areaName;

	/**
	 * 场地类型
	 */
	private Integer siteType;

   /**
	* 分页-pageSize
	*/
	private Integer pageSize;
	/**
	 * 作业区列表
	 */
	private List<String> areaCodeList;

    /**
     * 流向维护状态,0-未配置,1-已配置
     */
    private Integer configFlowStatus;

	/**
	 * 省区编码
	 */
	private String provinceAgencyCode;
	/**
	 * 枢纽编码
	 */
	private String areaHubCode;
	/**
	 * 列表
	 */
	private List<String> businessKeyList;
	/**
	 * 是否删除状态,0-已删除,1-生效中
	 */
	private Integer yn;
}
