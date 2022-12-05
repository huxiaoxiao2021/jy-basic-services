package com.jdl.basic.api.dto.device.vo;

import com.jdl.basic.api.domain.device.DeviceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备信息
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-04 15:58:12 周日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceInfoVo extends DeviceInfo {
    private static final long serialVersionUID = -4753101136535970489L;
}
