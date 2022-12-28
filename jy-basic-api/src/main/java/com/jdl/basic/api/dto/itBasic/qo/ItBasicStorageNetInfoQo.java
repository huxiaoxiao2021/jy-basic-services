package com.jdl.basic.api.dto.itBasic.qo;

import com.jd.dms.java.utils.sdk.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Data
public class ItBasicStorageNetInfoQo extends BaseQuery implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //columns START
    /**
     * 主键ID  db_column: id
     */
    private Long id;
    /**
     * ac_ipregion  db_column: ac_ipregion
     */
    private String acIpregion;
    /**
     * ap_ipregion  db_column: ap_ipregion
     */
    private String apIpregion;
    /**
     * core_switch_ip  db_column: core_switch_ip
     */
    private String coreSwitchIp;
    /**
     * 创建时间  db_column: create_time
     */
    private Date createTime;
    /**
     * 创建人  db_column: create_user
     */
    private String createUser;
    /**
     * net_level  db_column: net_level
     */
    private Byte netLevel;
    /**
     * pcserver_ipregion  db_column: pcserver_ipregion
     */
    private String pcserverIpregion;
    /**
     * region_id  db_column: region_id
     */
    private Long regionId;
    /**
     * snmp_community  db_column: snmp_community
     */
    private String snmpCommunity;
    /**
     * switch_ipregion  db_column: switch_ipregion
     */
    private String switchIpregion;
    /**
     * 更新时间  db_column: update_time
     */
    private Date updateTime;
    /**
     * 更新人  db_column: update_user
     */
    private String updateUser;
    /**
     * fire_wall_extranet_ip  db_column: fire_wall_extranet_ip
     */
    private String fireWallExtranetIp;
    /**
     * fire_wall_intranet_ip  db_column: fire_wall_intranet_ip
     */
    private String fireWallIntranetIp;
    /**
     * storage_name  db_column: storage_name
     */
    private String storageName;
    /**
     * audit_ip  db_column: audit_ip
     */
    private String auditIp;
    /**
     * first_extranet_interface  db_column: first_extranet_interface
     */
    private String firstExtranetInterface;
    /**
     * first_extranet_ip  db_column: first_extranet_ip
     */
    private String firstExtranetIp;
    /**
     * first_isp  db_column: first_isp
     */
    private String firstIsp;
    /**
     * second_extranet_interface  db_column: second_extranet_interface
     */
    private String secondExtranetInterface;
    /**
     * `second_extranet_ip`  db_column: second_extranet_ip
     */
    private String secondExtranetIp;
    /**
     * second_isp  db_column: second_isp
     */
    private String secondIsp;
    /**
     * third_extranet_interface  db_column: third_extranet_interface
     */
    private String thirdExtranetInterface;
    /**
     * third_extranet_ip  db_column: third_extranet_ip
     */
    private String thirdExtranetIp;
    /**
     * third_isp  db_column: third_isp
     */
    private String thirdIsp;
    /**
     * first_extranet_bandwidth  db_column: first_extranet_bandwidth
     */
    private String firstExtranetBandwidth;
    /**
     * second_extranet_bandwidth  db_column: second_extranet_bandwidth
     */
    private String secondExtranetBandwidth;
    /**
     * third_extranet_bandwidth  db_column: third_extranet_bandwidth
     */
    private String thirdExtranetBandwidth;
    /**
     * gate_way_ips  db_column: gate_way_ips
     */
    private String gateWayIps;
    /**
     * 内网VIP  db_column: ip_vip
     */
    private String ipVip;
    /**
     * 备注  db_column: remarks
     */
    private String remarks;

    /**
     * 是否有效  db_column: yn
     */
    private Integer yn;


    public ItBasicStorageNetInfoQo(){
    }
    public ItBasicStorageNetInfoQo(Long id){
        this.id = id;
    }

}
