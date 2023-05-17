package com.jdl.basic.api.domain.boxFlow;

import lombok.Data;

import java.util.Date;

@Data
public class CollectBoxFlowDirectionConfVo {

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
    private String transportTypeName;

    /**
     * 规则，1 进港 2 出港
     */
    private Integer flowType;
    private String flowTypeName;

    /**
     * 创建人erp
     */
    private String createUserErp;
    private String createUserName;

    /**
     * 修改erp
     */
    private String updateUserErp;
    private String updateUserName;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeStr;

    /**
     * 更新时间
     */
    private Date updateTime;
    private String updateTimeStr;


    /**
     * 是否已配置
     */
    private Boolean configed;

    /**
     * 集包要求 1成品包2可混包
     */
    private Integer collectClaim;
    private String collectClaimStr;

    /**
     * 根据本次推送数据，是否有变化，0无变化/1更新为成品包/2更新为可混包
     */
    private Integer ifChangeSinceLastUpdate;
    private String ifChangeSinceLastUpdateStr;
    
    private String version;

}
