package com.jdl.basic.api.service.tenant;

import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.common.utils.Result;

import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.api.service.user
 * @Description:
 * @date Date : 2023年11月28日 17:00
 */
public interface JyConfigDictTenantJsfService {
    /**
     * 新增
     * @param insertData
     * @return
     */
    Result<Boolean> insert(JyConfigDictTenant insertData);
    /**
     * 根据id更新数据
     * @param updateData
     * @return
     */
    Result<Boolean> updateById(JyConfigDictTenant updateData);
    /**
     * 根据id删除数据
     * @param deleteData
     * @return
     */
    Result<Boolean> deleteById(JyConfigDictTenant deleteData);

    /**
     * 根据erp获取租户信息
     * @param erp
     * @return
     */
    Result<JyConfigDictTenant> getTenantByErp(String erp);
    /**
     * 根据场地id获取租户信息
     * @param siteCode
     * @return
     */
    Result<JyConfigDictTenant> getTenantBySiteCode(Integer siteCode);
    /**
     * 根据租户编码获取业务线列表
     *
     * @param tenantCode 租户编码
     * @return 业务线列表的结果
     */
    Result<List<JyConfigDictTenant>> getBusinessLineByTenantCode(String tenantCode);
    /**
     * 根据租户代码获取站点类型
     *
     * @param tenantCode 租户代码
     * @return 响应的站点类型列表
     */
    Result<List<JyConfigDictTenant>> getSiteTypeByTenantCode(String tenantCode);

    /**
     * 获取调用接口别名
     * @param tenantCode 租户代码
     * @param dictCode 字典代码
     * @return 调用接口别名
     */
    Result<String> getCallInterfaceAlies(String tenantCode,String dictCode);
}
