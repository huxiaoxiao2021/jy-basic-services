package com.jdl.basic.provider.boxlimit;

import com.jdl.basic.service.boxlimit.BoxlimitService;
import com.jdl.basic.api.BoxlimitConfigApi;
import com.jdl.basic.domain.boxlimit.BoxLimitConfigPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 16:35
 * @Description:
 */
@Component
@Slf4j
public class BoxlimitConfigApiImpl implements BoxlimitConfigApi {

    @Autowired
    private BoxlimitService boxlimitService;

    @Override
    public void insertBoxlimitConfig() {
        BoxLimitConfigPO po = new BoxLimitConfigPO();
        po.setConfigType(1);
        po.setBoxNumberType("BC");
        po.setLimitNum(1000);
        po.setSiteName("测试站点1010");
        boxlimitService.insertBoxlimitConfig(po);
    }
}
