package com.jdl.basic.api.domain.boxLimit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 集箱包裹限制requestDto
 * box_limit_config
 * @author 
 */
@Data
public class BoxLimitConfigQueryDto implements Serializable {



    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 配置类型(1:通用配置 2：场地建箱配置)
     */
    private Integer configType;

    /**
     * 箱号类型
     *  1-BC（正向普通） 2-TC（退货普通） 3-GC（取件普通） 4-FC（返调度再投普通） 5-BS（正向奢侈品） 6-TS（退货奢侈品） 7-GS（取件奢侈品）
     *  8-FS（返调度再投奢侈品） 9-FC（签单返还） 10-ZC（上门接货） 11-ZC（商家售后） 12-BX（正向虚拟） 13-TW（逆向内配） 14-WJ(文件信封)
     *
     */
    private String boxNumberType;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private Integer offset = 0;

    private static final long serialVersionUID = 2L;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset() {
        this.offset = (this.pageNum - 1) * this.pageSize;
    }

}