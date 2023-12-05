package com.jdl.basic.provider.core.dao.tenant;

import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenantQuery;

import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.provider.core.dao.tenant
 * @Description:
 * @date Date : 2023年12月04日 17:18
 */
public interface JyConfigDictTenantDao {
    /**
     * 根据字典编码和值获取租户信息
     * @param query 查询条件
     * @return 结果对象，包含租户信息
     */
    JyConfigDictTenant getTenantByDictCodeAndValue(JyConfigDictTenantQuery query);
    /**
     * 根据租户代码和业务代码获取业务线列表
     *
     * @param query
     * @return 业务线列表
     */
    List<JyConfigDictTenant> getBusinessLineByTenantCode(JyConfigDictTenantQuery query);
}
