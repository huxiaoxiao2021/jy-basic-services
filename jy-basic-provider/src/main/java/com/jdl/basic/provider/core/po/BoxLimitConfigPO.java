package com.jdl.basic.provider.core.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/26 16:55
 * @Description:
 */
@Data
public class BoxLimitConfigPO implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 站点名称
     */

    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 建箱包裹数上限
     */
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
    private Date operatingTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 记录是否有效：0-无效，已删除  1-有效
     */
    private Boolean yn;

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

    private static final long serialVersionUID = 1L;
}
