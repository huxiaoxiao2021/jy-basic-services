package com.jdl.basic.api.domain.position;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.util.List;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2022/2/26 8:42 PM
 */
@Data
public class PositionQuery extends BasePagerCondition {

    private static final long serialVersionUID = 1L;

    /**
     * 机构编码
     */
    private Integer orgCode;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 作业区编码
     */
    private String areaCode;

    /**
     * 网格号:01~99
     */
    private String gridNo;

    /**
     * 网格ID
     */
    private String gridCode;

    /**
     * 工序编码
     */
    private String workCode;

    /**
     * 分页-pageSize
     */
    private Integer pageSize;

    /**
     * 功能编码
     */
    private String funcCode;

    /**
     * 工序主键列表
     */
    private List<String> refStationKeyList;

}
