package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liwenji
 * @description 异常网格关联树
 * @date 2022-08-12 16:54
 */
@Data
public class WorkStationBindingVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;
    /**
     * 描述
     */
    private String label;
    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 网格编码
     */
    private String gridCode;
    /**
     * 禁选标识
     */
    private Boolean disabled;
    /**
     * 子节点
     */
    private List<WorkStationBindingVo> children;
}
