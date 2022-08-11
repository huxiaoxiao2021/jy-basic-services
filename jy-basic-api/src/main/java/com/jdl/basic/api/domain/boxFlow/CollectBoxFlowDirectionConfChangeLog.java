package com.jdl.basic.api.domain.boxFlow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 集包规则配置表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectBoxFlowDirectionConfChangeLog {
    /**
    * 自增主键
    */
    private Long id;

    /**
    * 始发区域id
    */
    private Integer startOrgId;

    /**
    * 始发区域名称
    */
    private String startOrgName;

    /**
    * 始发站点id
    */
    private Integer startSiteId;

    /**
    * 始发站点名称
    */
    private String startSiteName;

    /**
    * 目的区域id
    */
    private Integer endOrgId;

    /**
    * 目的区域名称
    */
    private String endOrgName;

    /**
    * 目的站点id
    */
    private Integer endSiteId;

    /**
    * 目的站点名称
    */
    private String endSiteName;

    /**
    * 原始建包流向id
    */
    private Integer originalBoxReceiveId;

    /**
    * 原始建包流向名称
    */
    private String originalBoxReceiveName;

    /**
    * 原始包牌名称
    */
    private String originalBoxPkgName;

    /**
    * 新的建包流向id
    */
    private Integer newBoxReceiveId;

    /**
    * 新的建包流向名称
    */
    private String newBoxReceiveName;

    /**
    * 新的包牌名称
    */
    private String newBoxPkgName;

    /**
    * 承运类型，1 公路 2 铁路
    */
    private Integer transportType;

    /**
    * 规则，1 进港 2 出港
    */
    private Integer flowType;



    /**
    * 修改erp
    */
    private String updateUserErp;


    /**
    * 更新时间
    */
    private Long updateTime;

    /**
    * 时间戳
    */
    private Long ts;

}