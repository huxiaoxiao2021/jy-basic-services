package com.jdl.basic.api.domain.businessWhite;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BusinessWhiteList implements Serializable {
    private Long id;//id

    private Integer classId;//分类id

    private String className;//分类名

    private String content;//内容

    private Integer businessType;//类型

    private String createUserErp;//创建人erp

    private String createUser;//创建人名

    private Date createTime;//创建时间

    private String updateUserErp;//更新人erp

    private String updateUser;//更新人

    private Date updateTime;//更新时间

    private Integer yn;//是否有效

    private Long ts;//时间戳
}
