package com.jdl.basic.provider.service.transferDp;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteMatchQo;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.service.transferDp.ConfigTransferDpBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * description
 *
 * @author fanggang7
 * @copyright jd.com 京东物流JDL
 * @time 2022-12-22 14:18:37 周四
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class ConfigTransferDpBusinessServiceTest {

    @Resource
    public ConfigTransferDpBusinessService configTransferDpBusinessService;

    @Test
    public void queryMatchConditionRecordTest(){
        ConfigTransferDpSiteMatchQo configTransferDpSiteMatchQo = new ConfigTransferDpSiteMatchQo();
        configTransferDpSiteMatchQo.setHandoverSiteCode(67492);
        configTransferDpSiteMatchQo.setPreSortSiteCode(39);

        final Result<ConfigTransferDpSite> configTransferDpSiteResult = configTransferDpBusinessService.queryMatchConditionRecord(configTransferDpSiteMatchQo);
        System.out.println(JsonHelper.toJSONString(configTransferDpSiteResult));
    }
}
