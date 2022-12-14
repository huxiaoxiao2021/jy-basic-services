package com.jdl.basic.api.domain.machine;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liwenji
 * @date 2022-10-26 20:00
 */
@Data
public class WorkStationGridMachine implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * ref：work_station_grid业务主键
     */
    private String refGridKey;

    /**
     * 自动化设备编码
     */
    private String machineCode;

    /**
     * 自动化设备类型编码
     */
    private String machineTypeCode;
    
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
