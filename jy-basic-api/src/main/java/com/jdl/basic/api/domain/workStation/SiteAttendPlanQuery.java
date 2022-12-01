package com.jdl.basic.api.domain.workStation;

import com.jdl.basic.api.domain.BasePagerCondition;
import lombok.Data;

import java.util.Date;

@Data
public class SiteAttendPlanQuery extends BasePagerCondition {

    /**
     * 开始时间字符串
     */
    private String startTimeStr;

    /**
     * 结束时间字符串
     */
    private String endTimeStr;

    /**
     * 查询开始时间
     */
    private Date startTime;

    /**
     * 查询结束时间
     */
    private Date endTime;

    /**
     * 区域编码
     */
    private Integer orgCode;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 状态:0-未确认 1-已确认
     */
    private Integer status;

    /**
     * 分页大小
     */
    private Integer pageSize;

}
