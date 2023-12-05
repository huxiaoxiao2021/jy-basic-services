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
     * 根据字典编码和值获取租户信息
     * @param query 查询条件
     * @return 结果对象，包含租户信息
     */
    @Override
    public JyConfigDictTenant getTenantByDictCodeAndValue(JyConfigDictTenantQuery query){
        if (query == null){
            log.warn("JyConfigDictTenantServiceImpl->getTenantByDictCodeAndValue参数如空，请检查");
            return null;
        }
        if (DictCodeEnum.getDictCodeEnumByCode(query.getDictCode()) == null){
            log.warn("JyConfigDictTenantServiceImpl->getTenantByDictCodeAndValue字典编码不合法，请检查");
            return null;
        }
        if (StringUtils.isBlank(query.getDictItemValue())){
            log.warn("JyConfigDictTenantServiceImpl->getTenantByDictCodeAndValue字典值为空，请检查");
            return null;
        }
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
        JyConfigDictTenantQuery query = new JyConfigDictTenantQuery();
        query.setBelongTenantCode(tenantCode);
        query.setDictCode(dictCode);
        return jyConfigDictTenantDao.getBusinessLineByTenantCode(query);
    }
}
