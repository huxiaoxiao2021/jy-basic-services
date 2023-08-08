package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liwenji
 * @description 异常网格绑定查询结果实体类
 * @date 2022-08-10 18:44
 */
@Data
public class WorkStationFloorGridVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 业务主键：site_code、floor、grid_no、ref_station_key
     */
    private String businessKey;

    /**
     * 机构编码
     */
    private Integer orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 场地名称
     */
    private String siteName;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 网格号:01~99
     */
    private String gridNo;

    /**
     * 网格ID
     */
    private String gridCode;

    /**
     * 网格名称
     */
    private String gridName;

    /**
     * 作业区编码
     */
    private String areaCode;

    /**
     * 作业区名称
     */
    private String areaName;

    /**
     * 是否绑定实操网络，0 未绑定 1 已绑定
     */
    private Integer binding;

    /**
     * 省区编码
     */
    private String provinceAgencyCode;
    /**
     * 省区名称
     */
    private String provinceAgencyName;
    /**
     * 枢纽编码
     */
    private String areaHubCode;
    /**
     * 枢纽名称
     */
    private String areaHubName;
    public String getRefid(){
        return this.getSiteCode()+"-"+this.getFloor()+"-"+this.getGridCode();
    }

}
