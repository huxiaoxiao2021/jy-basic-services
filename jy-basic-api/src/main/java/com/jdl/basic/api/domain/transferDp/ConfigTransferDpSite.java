package com.jdl.basic.api.domain.transferDp;

import com.alibaba.fastjson.JSON;
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
public class ConfigTransferDpSite implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //columns START
    /**
     * 主键  db_column: id
     */
    private Long id;
    /**
     * 交接区域ID  db_column: handover_org_id
     */
    private Integer handoverOrgId;
    /**
     * 交接区域名称  db_column: handover_org_name
     */
    private String handoverOrgName;
    /**
     * 交接场地ID  db_column: handover_site_code
     */
    private Integer handoverSiteCode;
    /**
     * 交接场地名称  db_column: handover_site_name
     */
    private String handoverSiteName;
    /**
     * 预分拣站点ID  db_column: pre_sort_site_code
     */
    private Integer preSortSiteCode;
    /**
     * 预分拣站点名称  db_column: pre_sort_site_name
     */
    private String preSortSiteName;
    /**
     * 配置状态  db_column: config_status
     */
    private Integer configStatus;
    /**
     * 配置生效开始时间  db_column: effective_start_time
     */
    private Date effectiveStartTime;
    /**
     * 配置生效结束时间  db_column: effective_stop_time
     */
    private Date effectiveStopTime;
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
    //columns END

    public ConfigTransferDpSite(){
    }
    public ConfigTransferDpSite(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
