package com.jdl.basic.api.dto.itBasic.vo;

import com.jdl.basic.api.domain.itBasic.ItBasicStorageIpRegion;
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
public class ItBasicStorageIpRegionVo extends ItBasicStorageIpRegion implements Serializable {

    private static final long serialVersionUID = 2984663770185837915L;

    /**
     * 开始IP  db_column: start_ip
     */
    private Long startIp;
    private String startIpStr;

    /**
     * 结束IP  db_column: end_ip
     */
    private Long endIp;
    private String endIpStr;

    /**
     * 大区
     */
    private Long regionId;

    private String regionName;

    /**
     * 省
     */
    private Long provinceId;

    private String provinceName;

    /**
     * 市
     */
    private Long cityId;

    private String cityName;

    /**
     * 仓库/园区id，对应region中的level为4的id  db_column: storage_id
     */
    private Long storageId;

    /**
     * 园区名称
     */
    private String storageName;

    private Long tsMillSeconds;
}
