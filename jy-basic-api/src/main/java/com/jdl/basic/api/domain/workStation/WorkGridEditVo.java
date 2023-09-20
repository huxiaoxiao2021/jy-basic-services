package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 场地网格表-实体类
 * 
 * @author wuyoude
 * @date 2023年04月27日 16:16:10
 *
 */
@Setter
@Getter
public class WorkGridEditVo extends WorkGrid{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 早班-负责人
	 */
	private String ownerUserErp1;
	/**
	 * 枢纽编码
	 */
	private String ownerUserErp2;
	/**
	 * 枢纽名称
	 */
	private String ownerUserErp3;
}
