package com.jdl.basic.provider.core.provider.tenant;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenantQuery;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.enums.DictCodeEnum;
import com.jdl.basic.api.enums.TenantEnum;
import com.jdl.basic.api.service.tenant.JyConfigDictTenantJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.tenant.JyConfigDictTenantService;
import com.jdl.basic.provider.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.provider.core.provider.tenant
 * @Description:
 * @date Date : 2023年11月29日 10:36
 */
@Slf4j
@Service
public class JyConfigDictTenantJsfServiceImpl implements JyConfigDictTenantJsfService {

    @Autowired
    private UserService userService;
    @Autowired
    private BaseMajorManager baseMajorManager;
    @Resource
    private JyConfigDictTenantService jyConfigDictTenantService;

    /**
     * 插入JyTenant数据
     *
     * @param insertData 待插入的JyTenant对象
     * @return 插入操作的结果，返回一个Boolean类型的结果
     */
    @Override
    public Result<Boolean> insert(JyConfigDictTenant insertData) {
        return null;
    }

    /**
     * 更新JyTenant对象的数据
     *
     * @param updateData 需要更新的JyTenant对象
     * @return 更新是否成功的结果
     */
    @Override
    public Result<Boolean> updateById(JyConfigDictTenant updateData) {
        return null;
    }

    /**
     * 根据ID删除JyTenant对象
     *
     * @param deleteData 要删除的JyTenant对象
     * @return 删除操作的结果，返回一个Boolean类型的Result对象
     */
    @Override
    public Result<Boolean> deleteById(JyConfigDictTenant deleteData) {
        return null;
    }

    /**
     * 通过 ERP 编码获取 JyTenant 对象
     *
     * @param erp ERP 编码
     * @return 响应的 Result 对象，包含 JyTenant 对象
     */
    @Override
    public Result<JyConfigDictTenant> getTenantByErp(String erp) {
        log.info("根据erp查询租户信息 getTenantByErp 入参-{}", erp);
        JyConfigDictTenant defaultJyConfigDictTenant = new JyConfigDictTenant();
        defaultJyConfigDictTenant.setBelongTenantCode(TenantEnum.TENANT_JY.getCode());
        if(StringUtils.isEmpty(erp)){
            return Result.success(defaultJyConfigDictTenant);
        }
        JyUser condition = new JyUser();
        condition.setUserErp(erp);
        JyUser exitUser = userService.queryUserInfo(condition);
        if(exitUser == null){
            return Result.success(defaultJyConfigDictTenant);
        }
        if(exitUser.getSiteCode() != null){
            return this.getTenantBySiteCode(exitUser.getSiteCode());
        }else if(StringUtils.isNotEmpty(exitUser.getOrganizationCode())){
            //查询配置表
            JyConfigDictTenant dataBaseTenant = this.getTenantByCodeAndValue(DictCodeEnum.BELONG_RZ_ORG_CODE.getCode(),exitUser.getOrganizationCode());
            //查询到就返回，查询不到返回拣运租户兜底逻辑
            return dataBaseTenant != null ? Result.success(dataBaseTenant) : Result.success(defaultJyConfigDictTenant);
        }else{
            //返回拣运租户兜底逻辑
            return Result.success(defaultJyConfigDictTenant);
        }
    }

    /**
     * 根据站点编码获取JyTenant信息
     *
     * @param siteCode 站点编码
     * @return 返回JyTenant响应结果
     */
    @Override
    public Result<JyConfigDictTenant> getTenantBySiteCode(Integer siteCode) {
        log.info("根据场地id查询租户信息 getTenantBySiteCode 入参-{}", siteCode);
        JyConfigDictTenant defaultJyConfigDictTenant = new JyConfigDictTenant();
        defaultJyConfigDictTenant.setBelongTenantCode(TenantEnum.TENANT_JY.getCode());
        if(siteCode == null){
            return Result.success(defaultJyConfigDictTenant);
        }
        BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(siteCode);
        if(baseSite == null){
            return Result.success(defaultJyConfigDictTenant);
        }
        //查询配置表
        JyConfigDictTenant dataBaseTenant = this.getTenantByCodeAndValue(DictCodeEnum.BELONG_QL_SITE_TYPE.getCode(),String.valueOf(baseSite.getSortSubType()));
        //查询到就返回，查询不到返回拣运租户兜底逻辑
        return dataBaseTenant != null ? Result.success(dataBaseTenant) : Result.success(defaultJyConfigDictTenant);
    }

    /**
     * 根据字典编码和字典项值获取租户信息
     *
     * @param dictCode 字典编码
     * @param dictItemValue 字典项值
     * @return 响应的租户信息
     */
    private JyConfigDictTenant getTenantByCodeAndValue(String dictCode,String dictItemValue){
        JyConfigDictTenantQuery query = new JyConfigDictTenantQuery();
        query.setDictCode(dictCode);
        query.setDictItemValue(dictItemValue);
        return jyConfigDictTenantService.getTenantByDictCodeAndValue(query);
    }

    /**
     * 根据租户编码获取业务线列表
     *
     * @param tenantCode 租户编码
     * @return 业务线列表的结果
     */
    @Override
    public Result<List<JyConfigDictTenant>> getBusinessLineByTenantCode(String tenantCode) {
        log.info("根据租户查询业务条线 getBusinessLineByTenantCode 入参-{}", tenantCode);
        if(StringUtils.isBlank(tenantCode)){
            return Result.success(new ArrayList<>());
        }
        return Result.success(jyConfigDictTenantService.getJyConfigDictTenantByTenantCodeAndDictCode(DictCodeEnum.TENANT_BUSINESS_LINE.getCode(),tenantCode));
    }

    /**
     * 根据租户代码获取站点类型
     *
     * @param tenantCode 租户代码
     * @return 响应的站点类型列表
     */
    @Override
    public Result<List<JyConfigDictTenant>> getSiteTypeByTenantCode(String tenantCode) {
        log.info("根据租户查询场地类型 getSiteTypeByTenantCode 入参-{}", tenantCode);
        if(StringUtils.isBlank(tenantCode)){
            return Result.success(new ArrayList<>());
        }
        return Result.success(jyConfigDictTenantService.getJyConfigDictTenantByTenantCodeAndDictCode(DictCodeEnum.TENANT_SITE_TYPE.getCode(),tenantCode));
    }
}
