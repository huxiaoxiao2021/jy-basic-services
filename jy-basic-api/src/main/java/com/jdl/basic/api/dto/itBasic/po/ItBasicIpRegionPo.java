package com.jdl.basic.api.dto.itBasic.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据IP查询匹配区域入参
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-01 18:23:35 周四
 */
@Data
public class ItBasicIpRegionPo implements Serializable {
    private static final long serialVersionUID = -739512427356500234L;

    private String ip;

    private Integer yn;
}
