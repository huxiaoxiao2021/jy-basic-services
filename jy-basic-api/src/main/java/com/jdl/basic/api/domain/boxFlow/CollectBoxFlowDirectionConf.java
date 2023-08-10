package com.jdl.basic.api.domain.boxFlow;

import lombok.Data;

import java.util.Date;

/**
    * 集包规则配置表
    */
@Data
public class CollectBoxFlowDirectionConf {

    public static Integer FLOW_TYPE_IN = 1;//进港
    public static Integer FLOW_TYPE_OUT = 2;//出港

    public static Integer TRANSPORT_TYPE_HIGHWAY = 1;//公路
    public static Integer TRANSPORT_TYPE_AIR = 2;//航空

    public static Integer PARAM_ERR=401;//参数错误
    public static Integer UN_CONFIG=402;//未配置
    public static Integer WRONG_CONF=403;//错误的配置
    public static Integer SUCCESS=200;//错误的配置

    public static Integer COLLECT_CLAIM_MIX = 2;//可混包
    public static Integer COLLECT_CLAIM_FINISH = 1;//成品包
    
    public static Integer COLLECT_CLAIM_SPECIFY_MIX = 3;//指定混包


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

    /**
    * 规则，1 进港 2 出港
    */
    private Integer flowType;

    /**
    * 创建人erp
    */
    private String createUserErp;

    /**
    * 修改erp
    */
    private String updateUserErp;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 时间戳
    */
    private Date ts;

    /**
    * 是否已逻辑删除
    */
    private Boolean yn;

    /**
     * 集包要求 1成品包2可混包
     */
    private Integer collectClaim;

    /**
     * 根据本次推送数据，是否有变化，0无变化/1更新为成品包/2更新为可混包/3更新为指定混包
     */
    private Integer ifChangeSinceLastUpdate;

    /**
     * 版本
     */
    private String version;

    /**
     * 省区编码
     */
    private String startProvinceAgencyCode;
    /**
     * 省区名称
     */
    private String startProvinceAgencyName;
    /**
     * 枢纽编码
     */
    private String startAreaHubCode;
    /**
     * 枢纽名称
     */
    private String startAreaHubName;
    /**
     * 省区编码
     */
    private String endProvinceAgencyCode;
    /**
     * 省区名称
     */
    private String endProvinceAgencyName;
    /**
     * 枢纽编码
     */
    private String endAreaHubCode;
    /**
     * 枢纽名称
     */
    private String endAreaHubName;
}
