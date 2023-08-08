package com.jdl.basic.provider.core.dao.device;

import com.jdl.basic.api.domain.device.DeviceInfo;
import com.jdl.basic.api.dto.device.qo.DeviceInfoQo;
import com.jdl.basic.provider.core.dao.base.BaseDao;

import java.util.List;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
public interface DeviceInfoDao extends BaseDao<DeviceInfo, DeviceInfoQo> {

    /**
     * 刷数-分页查询
     * @param startId
     * @return
     */
    List<DeviceInfo> brushQueryAllByPage(Integer startId);

    /**
     * 刷数-批量更新
     * @param list
     * @return
     */
    Integer brushUpdateById(List<DeviceInfo> list);
}
