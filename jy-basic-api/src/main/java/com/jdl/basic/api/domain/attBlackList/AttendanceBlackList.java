package com.jdl.basic.api.domain.attBlackList;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceBlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;//id

    private String name;//姓名

    private String userCode;//身份证号

    private Date takeTime;//生效时间

    private Date loseTime;//失效时间

    private String operatorErp;//操作人

    private String notes;//备注

    private Integer cancelFlag;//是否作废 1是 0否

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private Integer yn;//是否有效

    private Long ts;//时间戳

    private Integer status;

    private String statusStr;

}
