package com.jdl.basic.provider.workMapFunc;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncConfigDetailVO;
import com.jdl.basic.api.domain.workMapFunc.JyWorkMapFuncQuery;
import com.jdl.basic.api.service.workMapFunc.JyWorkMapFuncConfigJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/9/1 16:22
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class JyWorkMapFuncConfigJsfServiceTest {

    @Autowired
    private JyWorkMapFuncConfigJsfService jyWorkMapFuncConfigJsfService;

    @Test
    public void queryPageListTest(){

        JyWorkMapFuncQuery query = new JyWorkMapFuncQuery();
        query.setFuncCode("UNLOAD_CAR_POSITION");
        query.setPageSize(10);
        query.setPageNumber(1);

        Result<PageDto<JyWorkMapFuncConfigDetailVO>> pageDtoResult = jyWorkMapFuncConfigJsfService.queryPageList(query);
        System.out.println(JSON.toJSONString(pageDtoResult));
    }
}

