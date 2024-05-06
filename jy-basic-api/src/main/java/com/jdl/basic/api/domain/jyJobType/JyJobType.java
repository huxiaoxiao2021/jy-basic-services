package com.jdl.basic.api.domain.jyJobType;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pengchong28
 * @description 拣运工种数据实体
 * @date 2024/2/1
 */
@Data
public class JyJobType implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 工种类型
     * */
    private String name;
    /**
     * 工种编码
     * */
    private Integer code;
    /**
     * 工种启用状态,0-停用,1-启用
     */
    private Integer status;
    /**
     * 工种归属：自有员工，三方员工
     * */
    private String belong;
    /**
     * 是否支持拍照签到，不支持-0，支持-1
     * */
    private Integer photoSupport;
    /**
     * 工种自动签退时间-默认18小时
     * */
    private Integer autoSignOutHour;
    /**
     * 工种排序字段，默认0
     * */
    private Integer sort;
    /**
     * 创建人ERP
     * */
    private String createUser;
    /**
     * 修改人ERP
     * */
    private String updateUser;
    /**
     * 创建时间
     * */
    private Date createTime;
    /**
     * 修改时间
     * */
    private Date updateTime;
    /**
     * 是否存在
     * */
    private Integer yn;
    /**
     * 数据库时间
     */
    private Date ts;
}
