package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengchong28
 * @description 网格工序删除mq消息
 * @date 2024/4/22
 */
@Data
public class WorkStationGridDeleteMqData implements Serializable {
    /**
     * 操作标识
     ADD(1,"新增"),
     MODIFY(2,"修改"),
     DELETE (3,"删除")
     *
     */
    private Integer editType;
    /**
     * 删除实体
     */
    private WorkStationGrid workStationGrid;
    /**
     * 删除实体
     */
    private UpdateRequest<WorkStationGrid> updateRequest;

}
