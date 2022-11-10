package com.jdl.basic.provider.core.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 10:47
 * @Description: 易冻品场地配置
 */
@Data
public class EasyFreezeSitePO implements Serializable {
    private static final long serialVersionUID = -7454749935260213658L;

    //主键ID
    private Long id;

    //站点编码
    private Integer siteCode;
    //站点名称
    private String siteName;
    //站点类型 分拣 or 转运
    private String siteType;

    //场地所在城市
    private String cityName;

    /**
     * 当前所在区域名称
     */
    private String orgName;
    //易冻提示开始时间
    private Date remindStartTime;
    //易冻提示结束时间
    private Date remindEndTime;
    //
    private Integer useState;
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
