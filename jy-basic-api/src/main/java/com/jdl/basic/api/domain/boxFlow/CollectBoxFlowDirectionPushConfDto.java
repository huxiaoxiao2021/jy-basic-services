package com.jdl.basic.api.domain.boxFlow;

import lombok.Data;

/**
 * 集包规则配置表
 */
@Data
public class CollectBoxFlowDirectionPushConfDto {

    public static Integer FLOW_TYPE_IN = 1;//进港
    public static Integer FLOW_TYPE_OUT = 2;//出港

    public static Integer TRANSPORT_TYPE_HIGHWAY = 1;//公路
    public static Integer TRANSPORT_TYPE_AIR = 2;//航空

    public static Integer COLLECT_CLAIM_FINISHED_BOX = 1;//成品包
    public static Integer COLLECT_CLAIM_MIX_BOX = 2;//可混包


    /**
     * 始发区域id
     */
    private Integer startOrgId;


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
     * 目的站点id
     */
    private Integer endSiteId;

    /**
     * 目的站点名称
     */
    private String endSiteName;

    /**
     * 建包流向id
     */
    private Integer boxReceiveId;

    /**
     * 建包流向名称
     */
    private String boxReceiveName;

    /**
     * 包牌名称
     */
    private String boxPkgName;

    /**
     * 承运类型，1 公路 2 航空
     */
    private Integer transportType;

    /**
     * 规则，1 进港 2 出港
     */
    private Integer flowType;

    /**
     * 集包要求
     */
    private Integer collectClaim;

    /**
     * 更新时间
     */
    private String updateDate;

    /**
     * 版本
     */
    private String version;
}
