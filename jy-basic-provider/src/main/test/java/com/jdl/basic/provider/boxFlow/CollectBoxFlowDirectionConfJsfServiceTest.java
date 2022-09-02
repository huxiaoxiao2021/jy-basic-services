package com.jdl.basic.provider.boxFlow;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.boxFlow.CollectBoxFlowDirectionConf;
import com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionConfJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 15:41
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class CollectBoxFlowDirectionConfJsfServiceTest {

    @Autowired
    private CollectBoxFlowDirectionConfJsfService collectBoxFlowDirectionConfJsfService;


    @Test
    public void selectByIdTest(){
        Result<CollectBoxFlowDirectionConf> result = collectBoxFlowDirectionConfJsfService.selectById(1L);
        System.out.println(JSON.toJSONString(result));
    }


}
