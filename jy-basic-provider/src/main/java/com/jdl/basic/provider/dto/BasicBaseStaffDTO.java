package com.jdl.basic.provider.dto;

import java.util.Date;

import lombok.Data;

/**
 * 基础资料人员增量MQ实体
 *
 * @author: hujiping
 * @date: 2020/3/27 10:09
 */
@Data
public class BasicBaseStaffDTO {


    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 员工编号
     */
    private Integer staffNo;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 员工角色
     */
    private Integer staffRole;

    /**
     * ERP账号
     */
    private String accountNumber;

    /**
     * 京东PIN
     */
    private String jdAccount;

    /**
     * 配送员电话或手机号
     */
    private String phone;

    /**
     * 电话或手机号1
     */
    private String mobilePhone1;

    /**
     * 电话或手机号2
     */
    private String mobilePhone2;

    /**
     * 公司ERP系统员工编码
     */
    private String userCode;

    /**
     * 站点编号
     */
    private Integer siteCode;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 机构Id
     */
    private Integer orgId;
    private String orgName;

    /**
     * 站点类型
     */
    private Integer siteType;
    /**
     * 站点子类型
     */
    private Integer subType;

    /**
     * 站点三级子类型
     */
    private Integer thirdType;

    /**
     * 站点7位编码
     */
    private String dmsCode;

    /**
     * 省Id
     */
    private Integer provinceId;
    /**
     * 市Id
     */
    private Integer cityId;
    /**
     * 县Id
     */
    private Integer countyId;
    /**
     * 乡Id
     */
    private Integer townId;
    /**
     * 详细地址
     */
    private String address;

    /**
     * 省公司编号
     */
    private String provinceCompanyCode;


    /**
     * 片区编号
     */
    private String areaCode;

    /**
     * 分区编号
     */
    private String partitionCode;


    /**
     * 是否在职：1-在职，0-离职
     */
    private Integer isResign;

    /**
     * 员工邮箱
     */
    private String mail;

    /**
     * 员工性别
     */
    private Integer sex;

    /**
     * 员工类别：1-自营、2-三方
     */
    private Integer userType;

    /**
     * 员工头像url
     */
    private String staffPhoto;

    /**
     * 员工原头像url
     */
    private String bigStaffPhoto;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;


    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效：1-有效，0-无效
     */
    private Integer yn;

    /**
     * 员工标位
     */
    private String staffSign;

    /**
     * 站点标位
     */
    private String siteSign;

}
