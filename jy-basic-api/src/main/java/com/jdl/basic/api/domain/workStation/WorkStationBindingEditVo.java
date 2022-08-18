package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liwenji
 * @description 异常网格绑定关系编辑
 * @date 2022-08-15 23:23
 */
@Data
public class WorkStationBindingEditVo implements Serializable {
    private List<WorkStationBindingVo> workStationBindingVo;
    private List<Integer> defaultChecks;
}
