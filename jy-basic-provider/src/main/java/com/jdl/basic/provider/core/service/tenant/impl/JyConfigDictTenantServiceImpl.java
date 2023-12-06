package com.jdl.basic.provider.core.service.tenant.impl;

import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenantQuery;
import com.jdl.basic.api.enums.DictCodeEnum;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.dao.tenant.JyConfigDictTenantDao;
import com.jdl.basic.provider.core.service.tenant.JyConfigDictTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.provider.core.service.tenant.impl
 * @Description:
 * @date Date : 2023年12月04日 17:14
 */
@Slf4j
@Service
public class JyConfigDictTenantServiceImpl implements JyConfigDictTenantService {

    @Resource
    private JyConfigDictTenantDao jyConfigDictTenantDao;

    /**
     * 根据字典编码和字典项值获取租户信息
     *
     * @param dictCode 字典编码
     * @param dictItemValue 字典项值
     * @return JyConfigDictTenant 响应的租户信息
     */
    @Override
    public JyConfigDictTenant getTenantByDictCodeAndValue(String dictCode,String dictItemValue){
        if(StringUtils.isBlank(dictCode) || StringUtils.isBlank(dictItemValue)){
            return null;
        }
        if (DictCodeEnum.getDictCodeEnumByCode(dictCode) == null){
            log.warn("JyConfigDictTenantServiceImpl->getTenantByDictCodeAndValue字典编码不合法，请检查");
            return null;
        }
        JyConfigDictTenantQuery query = new JyConfigDictTenantQuery();
        query.setDictCode(dictCode);
        query.setDictItemValue(dictItemValue);
        return jyConfigDictTenantDao.getTenantByDictCodeAndValue(query);
    }

    /**
     * 根据租户代码和业务代码获取业务线列表
     *
     * @param dictCode 业务代码
     * @param tenantCode 租户代码
     * @return 业务线列表
     */
    @Override
    public List<JyConfigDictTenant> getJyConfigDictTenantByTenantCodeAndDictCode(String dictCode, String tenantCode) {
        if(StringUtils.isBlank(dictCode) || StringUtils.isBlank(tenantCode)){
            return new ArrayList<>(0);
        }
        if (DictCodeEnum.getDictCodeEnumByCode(dictCode) == null){
            log.warn("JyConfigDictTenantServiceImpl->getJyConfigDictTenantByTenantCodeAndDictCode字典编码不合法，请检查");
            return new ArrayList<>(0);
        }
        JyConfigDictTenantQuery query = new JyConfigDictTenantQuery();
        query.setBelongTenantCode(tenantCode);
        query.setDictCode(dictCode);
        return jyConfigDictTenantDao.getBusinessLineByTenantCode(query);
    }
}
