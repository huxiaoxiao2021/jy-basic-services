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
public class ItBasicRegion implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //columns START
    /**
     * 主键ID  db_column: id
     */
    private Long id;
    /**
     * 区域名称  db_column: region_name
     */
    private String regionName;
    /**
     * 父级ID  db_column: parent_id
     */
    private Long parentId;
    /**
     * 区域级别  db_column: region_level
     */
    private Integer regionLevel;
    /**
     * 是否有效  db_column: yn
     */
    private Integer yn;
    /**
     * 数据库时间  db_column: ts
     */
    private Date ts;
    //columns END

    public ItBasicRegion(){
    }
    public ItBasicRegion(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
