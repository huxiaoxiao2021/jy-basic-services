package com.jdl.basic.api.domain.workStation;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.io.Serializable;

/**
 * 场地网格-设备-实体类
 * 
 * @author wuyoude
 * @date 2023年04月27日 16:16:10
 *
 */
@Setter
@Getter
public class WorkGridDeviceVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 格口
	 */
	private String chuteCode;

	/**
	 * 设备编码
	 */
	private String machineCode;

	/**
	 * 供包台编码
	 */
	private String supplyNo;
}
