package com.jdl.basic.provider.core.dao.itBasic;


import com.jd.etms.framework.utils.cache.annotation.Cache;
import com.jdl.basic.api.domain.itBasic.ItBasicStorageNetInfo;
import com.jdl.basic.api.dto.itBasic.qo.ItBasicStorageNetInfoQo;
import com.jdl.basic.provider.core.dao.base.BaseDao;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2020<br>
 * Company: jd.com 京东物流JDL<br>
 * 
 * @author fanggang7
 * @time 2022-11-23 17:29:43 周三
 */
public interface ItBasicStorageNetInfoDao extends BaseDao<ItBasicStorageNetInfo, ItBasicStorageNetInfoQo> {

    /**
     * 按主键查询
     * @param primaryKey 主键
     * @return 查得记录对象|null
     * @author fanggang7
     * @time 2022-12-04 21:58:48 周日
     */
    @Cache(key = "ItBasicStorageNetInfo:id:args0", memoryExpiredTime = 2 * 60 * 1000, redisExpiredTime = 2 * 60 * 1000)
    ItBasicStorageNetInfo selectByPrimaryKey(long primaryKey);

}
