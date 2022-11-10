package com.jdl.basic.api.domain.easyFreeze;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 17:23
 * @Description:
 */
@Data
public class EasyFreezeSiteQueryDto implements Serializable {
    private static final long serialVersionUID = 2621864324469348241L;

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

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private Integer offset = 0;
}
