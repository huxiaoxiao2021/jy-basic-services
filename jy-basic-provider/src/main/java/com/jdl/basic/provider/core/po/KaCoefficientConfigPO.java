package com.jdl.basic.provider.core.po;

import com.jdl.basic.api.domain.kaCoefficientConfig.KaCoefficientConfigDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class KaCoefficientConfigPO implements Serializable {

    private Long id;//ID

    private String merchantCode;//商家编码

    private String merchantName;//商家名

    private BigDecimal coefficient;//积分卸车系数（分/每包裹）

    private String status;//状态编码

    private String statusName;//状态名

    private String createUser;//创建人

    private String createUserName;//创建人姓名

    private String updateUser;//更新人erp

    private String updateUserName;//更新人姓名

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private Date ts;//时间戳

    private Integer yn;//是否删除 0：删除，1：正常



    public KaCoefficientConfigPO(KaCoefficientConfigDto param) {
        this.merchantCode = param.getMerchantCode();
        this.merchantName = param.getMerchantName();
        this.coefficient = param.getCoefficient();
        this.status = param.getStatus();
        this.statusName = param.getStatusName();
        this.createUser = param.getCreateUser();
        this.createUserName = param.getCreateUserName();
        this.updateUser = param.getUpdateUser();
        this.updateUserName = param.getUpdateUserName();
        this.createTime = new Date();
        this.updateTime = new Date();
        this.yn = 1;
    }

    public KaCoefficientConfigPO() {
    }
}