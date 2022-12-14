package com.jdl.basic.api.dto.itBasic.qo;

import com.jd.dms.java.utils.sdk.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class ItBasicStorageIpRegionQo extends BaseQuery implements Serializable{

    private static final long serialVersionUID = -5366597398540713269L;

    /**
     * 主键ID  db_column: id
     */
    private Long id;
    /**
     * 开始IP  db_column: end_ip
     */
    private Long endIp;
    /**
     * 结束IP  db_column: start_ip
     */
    private Long startIp;
    /**
     * 仓库/园区id，对应region中的level为4的id  db_column: storage_id
     */
    private Long storageId;
    /**
     * 是否有效  db_column: yn
     */
    private Integer yn;
}
