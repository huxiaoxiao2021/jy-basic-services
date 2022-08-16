package com.jdl.basic.api.domain.workStation;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liwenji
 * @description 异常网格绑定查询实体类
 * @date 2022-08-10 18:37
 */
@Data
public class WorkStationFloorGridQuery extends BasePagerCondition {

    private static final long serialVersionUID = 1L;
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
     * 分页-pageSize
     */
    private Integer pageSize;
    /**
     * 网格ID
     */
    private String excpGridCode;

    /**
     * 楼层
     */
    private Integer excpFloor;

    /**
     * 是否查询异常
     */
    private Integer excpFlag;
}
