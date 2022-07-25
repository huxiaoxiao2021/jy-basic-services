package com.jdl.basic.provider;

import com.jdl.basic.api.BoxlimitConfigApi;
import com.jdl.basic.domain.boxlimit.BoxLimitConfigPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 17:08
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class BoxlimitConfigTest {

    @Autowired
    private BoxlimitConfigApi boxlimitConfigApi;

    @Test
    public void testAdd(){
        BoxLimitConfigPO po = new BoxLimitConfigPO();
        po.setConfigType(2);
        po.setBoxNumberType("BC");
        po.setLimitNum(1000);
        po.setSiteName("测试站点1010");
        boxlimitConfigApi.insertBoxlimitConfig(po);
    }
}
