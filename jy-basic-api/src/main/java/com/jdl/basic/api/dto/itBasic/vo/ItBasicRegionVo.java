package com.jdl.basic.api.dto.itBasic.vo;

import com.jdl.basic.api.domain.itBasic.ItBasicRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * it区域信息
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-03 23:34:06 周六
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItBasicRegionVo extends ItBasicRegion implements Serializable {

    private static final long serialVersionUID = 2984663770185837915L;

    /**
     * 父级区域名称
     */
    private String parentRegionName;

    private Long tsMillSeconds;
}
