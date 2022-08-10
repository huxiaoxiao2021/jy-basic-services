package com.jdl.basic.api.domain.workMapFunc;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

/**
 * 拣运功能配置查询条件
 *
 * @author hujiping
 * @date 2022/4/6 6:29 PM
 */
@Data
public class JyWorkMapFuncQuery extends BasePagerCondition {

    private static final long serialVersionUID = 1L;

    /**
     * 功能编码
     */
    private String funcCode;
    /**
     * 工序表业务主键
     */
    private String refWorkKey;
    /**
     * 作业区编码
     */
    private String areaCode;
    /**
     * 网格编码
     */
    private String workCode;

    /**
     * 分页-pageSize
     */
    private Integer pageSize;

}
