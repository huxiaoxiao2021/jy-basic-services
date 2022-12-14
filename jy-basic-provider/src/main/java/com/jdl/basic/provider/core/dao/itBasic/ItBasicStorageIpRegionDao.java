package com.jdl.basic.provider.core.dao.itBasic;

import com.jdl.basic.api.domain.itBasic.ItBasicStorageIpRegion;
import com.jdl.basic.api.dto.itBasic.po.ItBasicIpRegionPo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageIpRegionQo;
import com.jdl.basic.provider.core.dao.base.BaseDao;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
public interface ItBasicStorageIpRegionDao extends BaseDao<ItBasicStorageIpRegion, ItBasicStorageIpRegionQo> {

    /**
     * 根据IP查询匹配的IP区段对应园区信息
     * @param itBasicIpRegionPo 查询参数
     * @return 匹配的记录 || null
     * @author fanggang7
     * @time 2022-12-04 18:28:51 周日
     */
    ItBasicStorageIpRegion selectMatchRegionByIp(ItBasicIpRegionPo itBasicIpRegionPo);
}
