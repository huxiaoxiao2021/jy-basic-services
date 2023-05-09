package com.jdl.basic.provider.core.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkStationJobTypePO implements Serializable {


    private static final long serialVersionUID = 436260512451450694L;
    //主键id
    private Long id;

    //关联：work_station业务主键
    private String refBusinessKey;


     // 签到工种类型：1-正式工 2-派遣工 3-外包工 4-临时工 5-小时工 6-支援 7-长期联盟工
    private Integer jobCode;

    private String createUser;

    private String updateUser;

    private Date createTime;

    private Date updateTime;

    private Boolean yn;

    private Date ts;
}