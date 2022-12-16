package com.jdl.basic.api.dto.transferDp;

import com.alibaba.fastjson.JSON;
import com.jd.dms.java.utils.sdk.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigTransferDpSiteMatchQo extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Integer handoverSiteCode;

    private Integer preSortSiteCode;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}