package com.jdl.basic.provider.core.provider.tenant;

import com.jd.ql.basic.dto.BaseStaffSiteOrgDto;
import com.jdl.basic.api.domain.tenant.JyTenant;
import com.jdl.basic.api.domain.user.JyUser;
import com.jdl.basic.api.service.tenant.JyTenantJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.StringUtils;
import com.jdl.basic.provider.core.manager.BaseMajorManager;
import com.jdl.basic.provider.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class JyTenantJsfServiceImpl implements JyTenantJsfService {

    @Autowired
    private UserService userService;
    @Autowired
    private BaseMajorManager baseMajorManager;

    /**
     * 插入JyTenant数据
     *
     * @param insertData 待插入的JyTenant对象
     * @return 插入操作的结果，返回一个Boolean类型的结果
     */
    @Override
    public Result<Boolean> insert(JyTenant insertData) {
        return null;
    }

    /**
     * 更新JyTenant对象的数据
     *
     * @param updateData 需要更新的JyTenant对象
     * @return 更新是否成功的结果
     */
    @Override
    public Result<Boolean> updateById(JyTenant updateData) {
        return null;
    }

    /**
     * 根据ID删除JyTenant对象
     *
     * @param deleteData 要删除的JyTenant对象
     * @return 删除操作的结果，返回一个Boolean类型的Result对象
     */
    @Override
    public Result<Boolean> deleteById(JyTenant deleteData) {
        return null;
    }

    /**
     * 通过 ERP 编码获取 JyTenant 对象
     *
     * @param erp ERP 编码
     * @return 响应的 Result 对象，包含 JyTenant 对象
     */
    @Override
    public Result<JyTenant> getJyTenantByErp(String erp) {
        if(StringUtils.isEmpty(erp)){
            return Result.fail("未查询到相关的用户信息");
        }
        JyUser condition = new JyUser();
        condition.setUserErp(erp);
        JyUser exitUser = userService.queryUserInfo(condition);
        if(exitUser == null){
            return Result.fail("未查询到相关的用户信息");
        }
        if(exitUser.getSiteCode() != null){
            return this.getJyTenantBySiteCode(exitUser.getSiteCode());
        }else if(StringUtils.isNotEmpty(exitUser.getOrganizationCode())){
            //查询配置表
            //查询到就返回，查询不到返回拣运租户兜底逻辑
            return null;
        }else{
            //返回拣运租户兜底逻辑
            return null;
        }
    }

    /**
     * 根据站点编码获取JyTenant信息
     *
     * @param siteCode 站点编码
     * @return 返回JyTenant响应结果
     */
    @Override
    public Result<JyTenant> getJyTenantBySiteCode(Integer siteCode) {
        if(siteCode == null){
            return Result.fail("未查询到相关的用户信息");
        }
        BaseStaffSiteOrgDto baseSite = baseMajorManager.getBaseSiteBySiteId(siteCode);
        if(baseSite == null){
            return Result.fail("未查询到相关的用户信息");
        }
        //查询配置表
        //查询到就返回，查询不到返回拣运租户兜底逻辑
        return null;
    }
}
