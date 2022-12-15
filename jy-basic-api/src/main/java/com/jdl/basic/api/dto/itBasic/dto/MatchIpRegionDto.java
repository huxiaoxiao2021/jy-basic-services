package com.jdl.basic.api.dto.itBasic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 匹配的IP园区信息
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-04 10:49:50 周日
 */
@Data
public class MatchIpRegionDto implements Serializable {
    private static final long serialVersionUID = 3918389406809568915L;

    private Long id;

    /**
     * 开始IP  db_column: start_ip
     */
    private Long startIpNumber;
    private String startIp;

    /**
     * 结束IP  db_column: end_ip
     */
    private Long endIpNumber;
    private String endIp;

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

    private String ip;

    private Long ipNumber;
}
