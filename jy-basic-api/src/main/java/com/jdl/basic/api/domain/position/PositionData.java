package com.jdl.basic.api.domain.position;

import lombok.Data;

import java.io.Serializable;

/**
 * 上岗码数据信息
 *
 * @author wuyoude
 * @date 2022/2/25 5:25 PM
 */
@Data
public class PositionData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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
     * 作业区编码
     */
    private String areaCode;

    /**
     * 作业区名称
     */
    private String areaName;

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
     * 工序编码
     */
    private String workCode;

    /**
     * 工序名称
     */
    private String workName;
    /**
     * 默认功能码-PDA自动跳转相关的功能页面
     */
    private String defaultMenuCode;

    /**
     * ref：work_station_grid业务主键
     */
    private String refGridKey;

    /**
     * 岗位编码
     */
    private String positionCode;
}
