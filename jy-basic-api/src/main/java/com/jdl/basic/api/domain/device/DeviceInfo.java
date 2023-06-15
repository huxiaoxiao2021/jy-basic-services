package com.jdl.basic.api.domain.device;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
@Data
public class DeviceInfo implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //columns START
    /**
     * 主键  db_column: id
     */
    private Long id;
    /**
     * 设备编码  db_column: device_code
     */
    private String deviceCode;
    /**
     * 设备名称  db_column: device_name
     */
    private String deviceName;
    /**
     * 设备唯一序列号  db_column: device_sn
     */
    private String deviceSn;
    /**
     * 设备类型ID  db_column: device_type_id
     */
    private Integer deviceTypeId;
    /**
     * 设备类型  db_column: device_type
     */
    private String deviceType;
    /**
     * 生产厂商编码  db_column: manufacturer_code
     */
    private String manufacturerCode;
    /**
     * 生产厂商名称  db_column: manufacturer_name
     */
    private String manufacturerName;
    /**
     * 备注描述  db_column: description
     */
    private String description;
    /**
     * 区域ID  db_column: org_id
     */
    private Integer orgId;
    /**
     * 区域名称  db_column: org_name
     */
    private String orgName;
    /**
     * 场地id  db_column: site_code
     */
    private Integer siteCode;
    /**
     * 场地名称  db_column: site_name
     */
    private String siteName;
    /**
     * 设备状态
     */
    private Integer deviceStatus;
    /**
     * 创建人  db_column: create_user
     */
    private String createUser;
    /**
     * 创建人名称  db_column: create_user_name
     */
    private String createUserName;
    /**
     * 修改人  db_column: update_user
     */
    private String updateUser;
    /**
     * 修改人名称  db_column: update_user_name
     */
    private String updateUserName;
    /**
     * 创建时间  db_column: create_time
     */
    private Date createTime;
    /**
     * 修改时间  db_column: update_time
     */
    private Date updateTime;
    /**
     * 有效标志  db_column: yn
     */
    private Integer yn;
    /**
     * 数据库时间  db_column: ts
     */
    private Date ts;

    /**
     * 省区编码
     */
    private String provinceAgencyCode;
    /**
     * 省区名称
     */
    private String provinceAgencyName;
    /**
     * 枢纽编码
     */
    private String areaHubCode;
    /**
     * 枢纽名称
     */
    private String areaHubName;
    //columns END

    public DeviceInfo(){
    }
    public DeviceInfo(Long id){
        this.id = id;
    }
}
