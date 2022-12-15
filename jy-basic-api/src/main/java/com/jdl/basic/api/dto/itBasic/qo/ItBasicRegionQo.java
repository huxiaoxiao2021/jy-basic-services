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
public class ItBasicRegionQo extends BaseQuery implements Serializable{
    private static final long serialVersionUID = -626703195929136976L;

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
}
