package com.jdl.basic.provider.core.service.akbox;

import com.jdl.basic.api.domain.akbox.AkboxConfig;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
class AkboxConfigServiceTest {
    @Resource
    IAkboxConfService akboxConfService;

    @Test
    void save() {
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
        akboxConfig.setCreateUser("002");
        akboxConfig.setYn(true);
        akboxConfService.save(akboxConfig);
    }

    @Test
    void batchSave() {
    }

    @Test
    void update() {
    }

    @Test
    void selectById() {
    }

    @Test
    void queryByCondition() {
    }
}
