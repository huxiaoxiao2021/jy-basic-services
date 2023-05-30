package com.jdl.basic.provider.businessWhite;

import com.jdl.basic.api.domain.businessWhite.BusinessWhiteList;
import com.jdl.basic.api.domain.businessWhite.BusinessWhiteListCondition;
import com.jdl.basic.api.service.businessWhite.BusinessWhiteListJsfService;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class BusinessWhiteListJsfServiceTest {

    @Autowired
    private BusinessWhiteListJsfService businessWhiteListJsfService;


    @Test
    public void queryPageTest() {
        BusinessWhiteListCondition condition =  new BusinessWhiteListCondition();
        condition.setClassId(1);
        condition.setPageSize(10);
        condition.setPageNumber(1);
        Result<List<BusinessWhiteList>> listResult = businessWhiteListJsfService.selectByParam(condition);
        System.out.println(listResult);
    }

    @Test
    public void countTest() {
        BusinessWhiteListCondition condition =  new BusinessWhiteListCondition();
        condition.setClassId(0);
        condition.setPageSize(10);
        condition.setPageNumber(1);
        Result<Long> count = businessWhiteListJsfService.count(condition);
        System.out.println(count);
    }

    @Test
    public void insertTest() {
        BusinessWhiteList businessWhiteList = new BusinessWhiteList();
        businessWhiteList.setBusinessType(2);
        businessWhiteList.setClassId(1);
        businessWhiteList.setClassName("生鲜");
        businessWhiteList.setContent("猕猴");
        businessWhiteList.setCreateTime(new Date());
        businessWhiteList.setCreateUserErp("luqinglin3");
        businessWhiteListJsfService.insert(businessWhiteList);
    }

    @Test
    public void deleteByIdTest() {
        BusinessWhiteList businessWhiteList = new BusinessWhiteList();
        businessWhiteList.setId(100L);
        businessWhiteListJsfService.deleteById(businessWhiteList);
    }

}
