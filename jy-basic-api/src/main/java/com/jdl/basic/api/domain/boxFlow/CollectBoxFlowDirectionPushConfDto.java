package com.jdl.basic.api.domain.boxFlow;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.util.concurrent.*;

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
     * 始发省区编码
     */
    private String startProvinceAgencyCode;
    /**
     * 始发省区名称
     */
    private String startProvinceAgencyName;
    /**
     * 始发枢纽编码
     */
    private String startAreaHubCode;
    /**
     * 始发枢纽名称
     */
    private String startAreaHubName;
    /**
     * 目的省区编码
     */
    private String endProvinceAgencyCode;
    /**
     * 目的省区名称
     */
    private String endProvinceAgencyName;
    /**
     * 目的枢纽编码
     */
    private String endAreaHubCode;
    /**
     * 目的枢纽名称
     */
    private String endAreaHubName;

    /**
     * 是否支持副流向
     */
    private Integer supportDeputyReceiveSite;

    /**
     * 副流向
     */
    private Integer deputyBoxReceiveId;

    /**
     * 副流向站点名字
     */
    private String deputyBoxReceiveName;

    /**
     * 副流向包牌名称
     */
    private String deputyBoxPkgName;

    public static void main(String[] args) {
        CollectBoxFlowDirectionPushConfDto dto = new CollectBoxFlowDirectionPushConfDto();
        dto.setStartOrgId(6);
        dto.setStartSiteId(482);
        dto.setStartSiteName("startName");
        dto.setEndOrgId(6);
        dto.setEndSiteId(1868);
        dto.setEndSiteName("endName");
        dto.setBoxReceiveId(910);
        dto.setBoxReceiveName("目的地名");
        dto.setBoxPkgName("主包牌名");
        dto.setTransportType(1);
        dto.setFlowType(2);
        dto.setCollectClaim(1);
        dto.setUpdateDate("2023-10-10");
        dto.setSupportDeputyReceiveSite(1);
        dto.setDeputyBoxReceiveId(910);
        dto.setDeputyBoxPkgName("包牌名称");
//        System.out.println(JSONObject.toJSONString(dto, SerializerFeature.PrettyFormat));

    }
    
}
