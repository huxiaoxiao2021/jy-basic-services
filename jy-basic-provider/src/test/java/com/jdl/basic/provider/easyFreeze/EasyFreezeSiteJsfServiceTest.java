package com.jdl.basic.provider.easyFreeze;

import com.alibaba.fastjson.JSON;
import com.jdl.basic.api.domain.LoginUser;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteDto;
import com.jdl.basic.api.domain.easyFreeze.EasyFreezeSiteQueryDto;
import com.jdl.basic.api.service.easyFreeze.EasyFreezeSiteJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenyaguo@jd.com
 * @Date: 2022/11/9 16:10
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class EasyFreezeSiteJsfServiceTest {

    @Autowired
    private EasyFreezeSiteJsfService jsfService;

    @Test
    public void insertTest(){
        EasyFreezeSiteDto dto = new EasyFreezeSiteDto();
        dto.setSiteCode(1086);
        dto.setSiteName("北京通州桥分拣中心");
        //dto.setCityName("北京");
        //dto.setOrgName("华北区");
        dto.setSiteType("转运");
        dto.setRemindStartTime(new Date());
        dto.setRemindEndTime(new Date());
        dto.setCreateTime(new Date());
        dto.setUseState(1);


        LoginUser loginUser= new LoginUser();
        loginUser.setUserErp("chenyaguo");
        Result<Boolean> insert = jsfService.insert(dto, loginUser);
        System.out.println(JSON.toJSONString(insert));
    }
    @Test
    public void selectByPrimaryKeyTest(){
        Result<EasyFreezeSiteDto> result = jsfService.selectByPrimaryKey(1L);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void updateByPrimaryKeySelectiveTest(){
        EasyFreezeSiteDto dto = new EasyFreezeSiteDto();
        dto.setId(3L);
        dto.setSiteCode(1086);
        dto.setSiteName("北京通州桥分拣中心");
        dto.setCityName("北京");
        dto.setOrgName("华北区");
        dto.setSiteType("转运");
        dto.setRemindStartTime(new Date());
        dto.setRemindEndTime(new Date());
        dto.setCreateTime(new Date());
        dto.setUseState(1);


        LoginUser loginUser= new LoginUser();
        loginUser.setUserErp("chenyaguo11");
        Result<Boolean> insert = jsfService.updateByPrimaryKeySelective(dto, loginUser);
        System.out.println(JSON.toJSONString(insert));
    }

    @Test
    public void getEasyFreezeSiteListBypageTest(){

        EasyFreezeSiteQueryDto queryDto = new EasyFreezeSiteQueryDto();
        //queryDto.setSiteName("通州");
        queryDto.setUseState(1);
        Result<PageDto<EasyFreezeSiteDto>> easyFreezeSiteListBypage = jsfService.getEasyFreezeSiteListBypage(queryDto);
        System.out.println(JSON.toJSONString(easyFreezeSiteListBypage));
    }

    @Test
    public void importEasyFreezeSiteListTest(){

        List<EasyFreezeSiteDto> list = new ArrayList<>();

        EasyFreezeSiteDto dto = new EasyFreezeSiteDto();
        dto.setSiteCode(910);
        dto.setRemindStartTime(new Date());
        dto.setRemindEndTime(new Date());
        dto.setUseState(0);

        EasyFreezeSiteDto dto1 = new EasyFreezeSiteDto();
        dto1.setSiteCode(100200);
        dto1.setRemindStartTime(new Date());
        dto1.setRemindEndTime(new Date());
        dto1.setUseState(1);

        EasyFreezeSiteDto dto2 = new EasyFreezeSiteDto();
        //dto2.setSiteCode(1868);
        dto2.setRemindStartTime(new Date());
        dto2.setRemindEndTime(new Date());
        dto2.setUseState(1);

        EasyFreezeSiteDto dto3 = new EasyFreezeSiteDto();
        dto3.setSiteCode(2214);
        dto3.setRemindStartTime(new Date());
        dto3.setRemindEndTime(new Date());
        dto3.setUseState(0);


        list.add(dto);
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);
        LoginUser loginUser= new LoginUser();
        loginUser.setUserErp("chenyaguo11");
        Result<Boolean> result = jsfService.importEasyFreezeSiteList(list, loginUser);
        System.out.println(JSON.toJSONString(result));

    }
}
