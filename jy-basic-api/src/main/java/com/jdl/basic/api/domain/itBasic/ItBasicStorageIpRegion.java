package com.jdl.basic.api.domain.itBasic;

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
public class ItBasicStorageIpRegion implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //columns START
    /**
     * 主键ID  db_column: id
     */
    private Long id;
    /**
     * 结束IP  db_column: end_ip
     */
    private Long endIp;
    /**
     * 开始IP  db_column: start_ip
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
    /**
     * 数据库时间  db_column: ts
     */
    private Date ts;
    //columns END

    public ItBasicStorageIpRegion(){
    }
    public ItBasicStorageIpRegion(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
