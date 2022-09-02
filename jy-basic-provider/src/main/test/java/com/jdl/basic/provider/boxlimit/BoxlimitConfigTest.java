package com.jdl.basic.provider.boxlimit;


import com.alibaba.fastjson.JSONObject;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigDto;
import com.jdl.basic.api.domain.boxLimit.BoxLimitConfigQueryDto;
import com.jdl.basic.api.response.JDResponse;
import com.jdl.basic.api.service.boxLimit.BoxlimitConfigJsfService;

import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/7/24 17:08
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class BoxlimitConfigTest {

    @Autowired
    private BoxlimitConfigJsfService boxlimitConfigApi;

    @Test
    public void testAdd() {
        BoxLimitConfigDto po = new BoxLimitConfigDto();
        //po.setConfigType(2);
        po.setBoxNumberType("BC");
        po.setLimitNum(1000);
        po.setSiteName("    ");
        Result response = boxlimitConfigApi.insertBoxlimitConfig(po);
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void testlistData() {
        BoxLimitConfigQueryDto dto = new BoxLimitConfigQueryDto();
        dto.setConfigType(2);
        Result<PageDto<BoxLimitConfigDto>> pageDtoJDResponse = boxlimitConfigApi.listData(dto);
        System.out.println(JSONObject.toJSONString(pageDtoJDResponse));
    }

    @Test
    public void testgetSiteNameById() {
        Result<String> siteNameById = boxlimitConfigApi.getSiteNameById(910);
        System.out.println(siteNameById);
    }

    @Test
    public void saveOrUpdateTest() {

        BoxLimitConfigDto dto = new BoxLimitConfigDto();
        //dto.setId(381L);
        dto.setSiteName("haha123站点");
        dto.setConfigType(2);
        dto.setLimitNum(100);
        dto.setSiteId(910);
        dto.setBoxNumberType("BC");
        LoginUser loginUser = new LoginUser();
        loginUser.setSiteCode(910);
        loginUser.setSiteName("管理站点");
        loginUser.setUserErp("chen");

        Result response = boxlimitConfigApi.saveOrUpdate(dto, loginUser);
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void testDelete() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(379L);
        ids.add(380L);
        LoginUser user = new LoginUser();
        Result delete = boxlimitConfigApi.delete(ids, user);
        System.out.println(JSONObject.toJSONString(delete));
    }

    @Test
    public void toImport() {

        List<BoxLimitConfigDto> dtos = new ArrayList<>();
        BoxLimitConfigDto dto1 = new BoxLimitConfigDto();
        dto1.setSiteName("北京马驹桥分拣中心最多20个字试试行不行");
        dto1.setConfigType(2);
        dto1.setLimitNum(100);
        dto1.setSiteId(910);
        dto1.setBoxNumberType("AA");

        BoxLimitConfigDto dto2 = new BoxLimitConfigDto();
        dto2.setSiteName("北京马驹桥分拣中心最多20个字试试行不行");
        dto2.setConfigType(2);
        dto2.setLimitNum(100);
        dto2.setSiteId(910);
        dto2.setBoxNumberType("BB");

        dtos.add(dto1);
        dtos.add(dto2);

        LoginUser loginUser = new LoginUser();
        loginUser.setSiteCode(910);
        loginUser.setSiteName("管理站点");
        loginUser.setUserErp("chen");
        Result response = boxlimitConfigApi.toImport(dtos, loginUser);
        System.out.println(response);
    }

    @Test
    public void testcountByCondition() {
        BoxLimitConfigQueryDto dto1 = new BoxLimitConfigQueryDto();
        dto1.setSiteId(910);

        Result<Integer> integerJDResponse = boxlimitConfigApi.countByCondition(dto1);
        System.out.println(integerJDResponse);
    }

    @Test
    public void getBoxTypeListTest() {
        Result<List<String>> boxTypeList = boxlimitConfigApi.getBoxTypeList();
        System.out.println(JSONObject.toJSONString(boxTypeList));
    }

    @Test
    public void getLimitNumsTest(){
        Result<Integer> bc = boxlimitConfigApi.getLimitNums(null, "BC");
        System.out.println(JSONObject.toJSONString(bc));
    }
}
