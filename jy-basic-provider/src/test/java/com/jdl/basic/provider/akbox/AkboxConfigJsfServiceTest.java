package com.jdl.basic.provider.akbox;

import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.api.domain.akbox.AkboxConfigQuery;
import com.jdl.basic.api.service.akbox.AkboxConfigJsfService;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class AkboxConfigJsfServiceTest {

    @Resource
    AkboxConfigJsfService jsfService;

    @Test
    public void save() {
        AkboxConfig akboxConfig = new AkboxConfig();
        akboxConfig.setProvinceAgencyCode("p1001");
        akboxConfig.setProvinceAgencyName("pn1001");
        akboxConfig.setAreaHubCode("a100101");
        akboxConfig.setAreaHubName("an100101");
        akboxConfig.setSiteCode(10010101);
        akboxConfig.setSiteName("sn10010101");
        akboxConfig.setSmallStock(1);
        akboxConfig.setLargeStock(2);
        Date date = new Date();
        akboxConfig.setCreateTime(date);
        akboxConfig.setUpdateTime(date);
        akboxConfig.setTs(date);

        akboxConfig.setCreateUserErp("001erp");
        akboxConfig.setUpdateUserErp("002erp");
        akboxConfig.setCreateUser("001");
        akboxConfig.setUpdateUser("002");
        akboxConfig.setYn(true);
        // jsfService.save(akboxConfig);
        List<AkboxConfig> list = new ArrayList<>();
        list.add(akboxConfig);
        jsfService.batchSave(list);
    };

    @Test
    public void batchSave(){
        List<AkboxConfig> akboxConfigList = new ArrayList<>();
        jsfService.batchSave(akboxConfigList);
    };

    @Test
    public void update() {
        AkboxConfig akboxConfig = new AkboxConfig();
        akboxConfig.setId(1L);
        akboxConfig.setProvinceAgencyCode("p1002");
        akboxConfig.setProvinceAgencyName("pn1002");
        akboxConfig.setAreaHubCode("a100101");
        akboxConfig.setAreaHubName("an100101");
        jsfService.update(akboxConfig);
    };

    @Test
    public void queryPageByCondition() {
        AkboxConfigQuery query = new AkboxConfigQuery();
        query.setPageSize(10);
        query.setPageNo(1);
        Result<Pager<AkboxConfig>> pagerResult = jsfService.queryPageByCondition(query);
        log.info(pagerResult.getData().getData().toString());
    };
}
