package com.jdl.basic.api.domain.boxFlow;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class CollectBoxFlowInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 数据版本
     */
    private String version;

    /**
     * 版本状态：0历史版本 1当前版本 2待激活版本
     */
    private Integer status;

    /**
     * 操作类型,1-新增,2-激活,3-回滚
     */
    private Integer operateType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标志,0-删除,1-正常
     */
    private Integer yn;

    /**
     * 数据库时间
     */
    private Date ts;

   
}
