package com.jdl.basic.api.domain.position;

import lombok.Data;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2022/2/26 8:28 PM
 */
@Data
public class PositionDetailRecord extends PositionRecord {

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

}
