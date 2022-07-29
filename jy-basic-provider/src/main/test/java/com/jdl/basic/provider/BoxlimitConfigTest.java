package com.jdl.basic.provider;


import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.BoxlimitConfigApi;

import com.jdl.basic.ommon.utils.PageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        BoxLimitConfigDto po = new BoxLimitConfigDto();
        po.setConfigType(2);
        po.setBoxNumberType("BC");
        po.setLimitNum(1000);
        po.setSiteName("    ");
        JDResponse response = boxlimitConfigApi.insertBoxlimitConfig(po);
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void testlistData(){
        BoxLimitConfigQueryDto dto = new BoxLimitConfigQueryDto();
        dto.setConfigType(2);
        JDResponse<PageDto<BoxLimitConfigDto>> pageDtoJDResponse = boxlimitConfigApi.listData(dto);
        System.out.println(JSONObject.toJSONString(pageDtoJDResponse));
    }
}
