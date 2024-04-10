package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author pengchong28
 * @description 场地网格工序更新实体
 * @date 2024/3/20
 */
@Data
public class UpdateRequest<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 操作人站点
     */
    private Integer operateSiteCode;
    /**
     * 操作人编码
     */
    private String operateUserCode;
    /**
     * 操作人名称
     */
    private String operateUserName;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 删除数据列表
     */
    private List<T> dataList;
    /**
     * 操作业务主键
     */
    private String operateBusinessKey;
    /**
     * 删除失败原因
     */
    private String deleteFailMessage;
    /**
     * 原始状态
     */
    private Integer sourceStatus;
    /**
     * 目标状态
     */
    private Integer targetStatus;
}
