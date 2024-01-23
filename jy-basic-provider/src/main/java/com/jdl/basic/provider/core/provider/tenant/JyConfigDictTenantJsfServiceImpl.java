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
import org.apache.commons.collections.CollectionUtils;
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
        try {
            JyUser condition = new JyUser();
            condition.setUserErp(erp);
            JyUser exitUser = userService.queryUserInfo(condition);
            if (exitUser == null) {
                return Result.success(defaultJyConfigDictTenant);
            }
            //判断是否冷链租户
            if (isColdTenant(exitUser)) {
                JyConfigDictTenant coldJyConfigDictTenant = new JyConfigDictTenant();
                coldJyConfigDictTenant.setBelongTenantCode(TenantEnum.TENANT_COLD_MEDICINE.getCode());
                return Result.success(coldJyConfigDictTenant);
            }
        }catch (Exception e){
            log.error("根据erp:{}查询租户信息异常", erp,e);
        }
        //返回拣运租户兜底逻辑
        return Result.success(defaultJyConfigDictTenant);
    }

    /**
     * 判断是否为冷链租户
     * @param exitUser 退出用户对象
     * @return 是否为冷租户，true表示是，false表示否
     */
    private boolean isColdTenant(JyUser exitUser){
        if(exitUser == null){
            return false;
        }
        BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(exitUser.getSiteCode());
        if(baseSite != null) {
            List<JyConfigDictTenant> qlDataList = jyConfigDictTenantService.getJyConfigDictTenantByTenantCodeAndDictCode(DictCodeEnum.BELONG_QL_SITE_TYPE.getCode(), TenantEnum.TENANT_COLD_MEDICINE.getCode());
            if (CollectionUtils.isNotEmpty(qlDataList) && qlDataList.stream().anyMatch(e -> StringUtils.isNotBlank(e.getDictItemValue()) && e.getDictItemValue().equals(String.valueOf(baseSite.getSortSubType())))) {
                return true;
            }
        }
        List<JyConfigDictTenant> rzDataList = jyConfigDictTenantService.getJyConfigDictTenantByTenantCodeAndDictCode(DictCodeEnum.BELONG_RZ_ORG_CODE.getCode(),TenantEnum.TENANT_COLD_MEDICINE.getCode());
        if(CollectionUtils.isNotEmpty(rzDataList) && rzDataList.stream().anyMatch(e->StringUtils.isNotBlank(e.getDictItemValue()) && e.getDictItemValue().equals(String.valueOf(exitUser.getOrganizationCode())))){
            return true;
        }
        return false;
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
            Result.success(defaultJyConfigDictTenant);
        }
        try {
            BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(siteCode);
            if(baseSite == null){
                Result.success(defaultJyConfigDictTenant);
            }
            //查询配置表
            JyConfigDictTenant databaseTenant = jyConfigDictTenantService.getTenantByDictCodeAndValue(DictCodeEnum.BELONG_QL_SITE_TYPE.getCode(), String.valueOf(baseSite.getSortSubType()));
            if (databaseTenant != null) {
                return Result.success(databaseTenant);
            }
        }catch (Exception e){
            log.error("根据场地id:{}查询租户信息异常", siteCode,e);
        }
        return Result.success(defaultJyConfigDictTenant);
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


    /**
     * 获取呼叫接口别名
     * @param tenantCode 租户编码
     * @param dictCode 字典编码
     * @return Result<String> 字符串类型的结果
     */
    @Override
    public Result<String> getCallInterfaceAlies(String tenantCode, String dictCode) {
        log.info("根据租户查询接口回调别名 getCallInterfaceAlies 租户:{},字典项:{}", tenantCode,dictCode);
        if(!TenantEnum.isLegal(tenantCode)){
            return Result.fail("租户不合法");
        }
        if(!DictCodeEnum.isLegalAliesDictCode(dictCode)){
            return Result.fail("字典项不合法");
        }
        List<JyConfigDictTenant> list = jyConfigDictTenantService.getJyConfigDictTenantByTenantCodeAndDictCode(dictCode,tenantCode);
        if(CollectionUtils.isEmpty(list)){
            return Result.fail("回调别名未配置");
        }
        return Result.success(list.get(0).getDictItemValue());
    }
}
