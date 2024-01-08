package com.jdl.basic.provider.tenant;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.tenant.JyConfigDictTenant;
import com.jdl.basic.api.service.tenant.JyConfigDictTenantJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author : caozhixing3
 * @version V1.0
 * @Project: jy-basic-services
 * @Package com.jdl.basic.provider.tenant
 * @Description:
 * @date Date : 2023年12月11日 14:39
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class JyConfigDictTenantJsfServiceTest {

    @Resource
    private JyConfigDictTenantJsfService jyConfigDictTenantJsfService;

    @Test
    public void getTenantByErpTest() {
        //根据erp查询租户
        for (String erp : Arrays.asList("wuyoude", "wangjikui", "wangjialin5","caozhixing3","shenpengpeng")){
            Result<JyConfigDictTenant> result = jyConfigDictTenantJsfService.getTenantByErp(erp);
            log.info("getTenantByErp结果：{}", JSON.toJSONString(result));
        }
        for (Integer siteCode : Arrays.asList(910,14518,64228)){
            Result<JyConfigDictTenant> result = jyConfigDictTenantJsfService.getTenantBySiteCode(siteCode);
            log.info("getTenantBySiteCode结果：{}", JSON.toJSONString(result));
        }
        for (String tenantCode : Arrays.asList("JY","CC")){
            Result<List<JyConfigDictTenant>> result = jyConfigDictTenantJsfService.getBusinessLineByTenantCode(tenantCode);
            log.info("getBusinessLineByTenantCode结果：{}", JSON.toJSONString(result));
        }
        for (String tenantCode : Arrays.asList("JY","CC")){
            Result<List<JyConfigDictTenant>> result = jyConfigDictTenantJsfService.getSiteTypeByTenantCode(tenantCode);
            log.info("getSiteTypeByTenantCode结果：{}", JSON.toJSONString(result));
        }
    }
}
