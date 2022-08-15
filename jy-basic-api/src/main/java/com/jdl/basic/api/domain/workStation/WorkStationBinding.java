package com.jdl.basic.api.domain.workStation;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liwenji
 * @description 异常网格绑定关系实体类
 * @date 2022-08-14 7:38
 */
@Data
public class WorkStationBinding implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 场地编码
     */
    private Integer siteCode;

    /**
     * 场地名称
     */
    private String siteName;

    /**
     * 创建人ERP
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人ERP
     */
    private String updateUser;

    /**
     * 更新人姓名
     */
    private String updateUserName;

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
     * 楼层
     */
    private Integer floor;

    /**
     * 网格ID
     */
    private String gridCode;

    /**
     * 异常楼层
     */
    private Integer excpFloor;

    /**
     *异常网格ID
     */
    private String excpGridCode;
}
