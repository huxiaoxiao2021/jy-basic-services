package com.jdl.basic.api.domain.boxLimit;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * box_limit_config
 *
 * @author
 */
@Data
public class BoxLimitConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 建箱包裹数上限
     */
    @Min(value = 0, message = "最小值不能小于0")
    private Integer limitNum;

    /**
     * 操作人ERP
     */
    private String operatorErp;

    /**
     * 操作人所在站点ID
     */
    private Integer operatorSiteId;

    /**
     * 操作人所在站点名称
     */
    private String operatorSiteName;

    /**
     * 操作时间
     */
    private String operatingTime;


    /**
     * 配置类型(1:通用配置 2：场地建箱配置)
     */
    //@NotNull(message = "配置类型不能为空!")
    private Integer configType;

    /**
     * 箱号类型
     * 1-BC（正向普通） 2-TC（退货普通） 3-GC（取件普通） 4-FC（返调度再投普通） 5-BS（正向奢侈品） 6-TS（退货奢侈品） 7-GS（取件奢侈品）
     * 8-FS（返调度再投奢侈品） 9-FC（签单返还） 10-ZC（上门接货） 11-ZC（商家售后） 12-BX（正向虚拟） 13-TW（逆向内配） 14-WJ(文件信封)
     */
    private String boxNumberType;


    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private Integer offset = 0;

}