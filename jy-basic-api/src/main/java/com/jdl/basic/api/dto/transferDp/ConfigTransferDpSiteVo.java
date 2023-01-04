package com.jdl.basic.api.dto.transferDp;

import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
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
public class ConfigTransferDpSiteVo extends ConfigTransferDpSite implements Serializable {

    private static final long serialVersionUID = -5669557348305980269L;

    /**
     * 配置状态
     */
    private String configStatusStr;

    public ConfigTransferDpSiteVo(){
    }

}
