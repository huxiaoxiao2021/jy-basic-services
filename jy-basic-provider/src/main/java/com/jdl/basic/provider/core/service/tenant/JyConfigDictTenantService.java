package com.jdl.basic.provider.core.service.tenant;

import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenantQuery;

import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.provider.core.service.tenant
 * @Description:
 * @date Date : 2023年11月29日 14:02
 */
public interface JyConfigDictTenantService {

    /**
     * 根据字典编码和值获取租户信息
     * @param dictCode 查询条件
     * @param dictItemValue 查询条件
     * @return 结果对象，包含租户信息
     */
    JyConfigDictTenant getTenantByDictCodeAndValue(String dictCode,String dictItemValue);
    /**
     * 根据租户代码和业务代码获取业务线列表
     *
     * @param dictCode 业务代码
     * @param tenantCode 租户代码
     * @return 业务线列表
     */
    List<JyConfigDictTenant> getJyConfigDictTenantByTenantCodeAndDictCode(String dictCode, String tenantCode);
}
