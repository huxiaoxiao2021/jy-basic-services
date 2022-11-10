package com.jdl.basic.api.domain.easyFreeze;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 14:55
 * @Description: 易冻品站点配置
 */
@Data
public class EasyFreezeSiteDto implements Serializable {
    private static final long serialVersionUID = 7378807674779976843L;

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
}
