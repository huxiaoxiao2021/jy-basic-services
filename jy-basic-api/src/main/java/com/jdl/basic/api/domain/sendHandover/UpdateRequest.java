package com.jdl.basic.api.domain.sendHandover;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liwenji
 * @description 批量更新
 * @date 2022-09-19 19:14
 */
@Data
public class UpdateRequest<T> implements Serializable {

    /**
     * 审批状态
     */
    private Integer approvalStatus;
    /**
     * 更新数据列表
     */
    private List<T> dataList;
}
