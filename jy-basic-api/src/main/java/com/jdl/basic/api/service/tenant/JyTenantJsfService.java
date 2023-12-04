package com.jdl.basic.api.service.tenant;

import com.jdl.basic.api.domain.tenant.JyTenant;
import com.jdl.basic.common.utils.Result;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.service.user
 * @Description:
 * @date Date : 2023年11月28日 17:00
 */
public interface JyTenantJsfService {
    /**
     * 新增
     * @param insertData
     * @return
     */
    Result<Boolean> insert(JyTenant insertData);
    /**
     * 根据id更新数据
     * @param updateData
     * @return
     */
    Result<Boolean> updateById(JyTenant updateData);
    /**
     * 根据id删除数据
     * @param deleteData
     * @return
     */
    Result<Boolean> deleteById(JyTenant deleteData);

    /**
     * 根据erp获取租户信息
     * @param erp
     * @return
     */
    Result<JyTenant> getJyTenantByErp(String erp);
    /**
     * 根据场地id获取租户信息
     * @param siteCode
     * @return
     */
    Result<JyTenant> getJyTenantBySiteCode(Integer siteCode);
}
