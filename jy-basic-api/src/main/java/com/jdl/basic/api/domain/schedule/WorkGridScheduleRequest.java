package com.jdl.basic.api.domain.schedule;

import com.jdl.basic.common.contants.Constants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkGridScheduleRequest implements Serializable {
    private static final long serialVersionUID = 6311945137681734758L;

    private Long id;
    /**
     * 场地id
     */
    private Integer siteCode;
    /**
     * 作业区编码
     */
    private String areaCode;
    /**
     * 网格key
     */
    private String workGridKey;
    /**
     * 班次序号（1、2、3）
     */
    private Integer scheduleNo;
    /**
     * 班次key=workGridKey-scheduleType-scheduleNo
     */
    private String scheduleKey;
    /**
     * 班次名字
     */
    private String scheduleName;
    /**
     * 维护维度来源
     * @see com.jdl.basic.api.enums.ScheduleTypeEnum
     */
    private Integer sourceType;
    /**
     * 班次开始时间
     */
    private String startTime;
    /**
     * 班次结束时间
     */
    private String endTime;
    /**
     * 白中夜班
     * @see com.jdl.basic.common.enums.WaveTypeEnum
     */
    private Integer scheduleType;

    private Date createTime;

    private Date updateTime;

    private String createUserErp;

    private String createUserName;

    private String updateUserErp;

    private String updateUserName;
    /**
     * 旧班次开始时间
     */
    private String oldStartTime = Constants.EMPTY_FILL;
    /**
     * 旧班次结束时间
     */
    private String oldEndTime = Constants.EMPTY_FILL;
}
