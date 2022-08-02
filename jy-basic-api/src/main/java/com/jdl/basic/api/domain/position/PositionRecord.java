package com.jdl.basic.api.domain.position;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上岗码记录
 *
 * @author hujiping
 * @date 2022/2/25 5:25 PM
 */
@Data
public class PositionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 场地ID
     */
    private Integer siteCode;

    /**
     * ref：work_station_grid业务主键
     */
    private String refGridKey;

    /**
     * 岗位编码
     */
    private String positionCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人ERP
     */
    private String createUser;

    /**
     * 修改人ERP
     */
    private String updateUser;

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
