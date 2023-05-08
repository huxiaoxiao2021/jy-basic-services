package com.jdl.basic.api.domain.workStation;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 场地网格表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月27日 16:16:10
 *
 */
@Setter
@Getter
public class WorkGridImport extends WorkGrid{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 干线流向ID-站点编码字符串
	 */
	private String flowSiteCodeStr1;
	/**
	 * 支线流向ID-站点编码字符串
	 */
	private String flowSiteCodeStr2;
	/**
	 * 传站流向ID-站点编码字符串
	 */
	private String flowSiteCodeStr3;
	/**
	 * 摆渡流向ID-站点编码字符串
	 */
	private String flowSiteCodeStr4;
	
	private Map<Integer,List<WorkGridFlowDirection>> flowDataMap;
}
