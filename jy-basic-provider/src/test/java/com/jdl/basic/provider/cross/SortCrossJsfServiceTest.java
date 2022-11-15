package com.jdl.basic.provider.cross;

import com.jd.ql.basic.dto.BaseSiteInfoDto;
import com.jdl.basic.api.domain.cross.SortCrossDetail;
import com.jdl.basic.api.domain.cross.SortCrossQuery;
import com.jdl.basic.api.domain.cross.SortCrossUpdateRequest;
import com.jdl.basic.api.service.cross.SortCrossJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.manager.basicSiteQueryWS.IBasicSiteQueryWSManager;
import com.jdl.basic.provider.core.service.cross.SortCrossService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liwenji
 * @description TODO
 * @date 2022-11-09 11:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SortCrossJsfServiceTest {
    
    @Autowired
    private SortCrossJsfService sortCrossJsfService;
    
    @Autowired
    SortCrossService sortCrossService;

    @Autowired
    private IBasicSiteQueryWSManager basicSiteQueryWSManager;
    
    @Test
    public void queryPageTest() {
        SortCrossQuery query = new SortCrossQuery();
        query.setPageSize(10);
        query.setPageNumber(1);
        Result<PageDto<SortCrossDetail>> result = sortCrossJsfService.queryPage(query);
        System.out.println(JsonHelper.toJSONString(result));
    }
    
    @Test
    public void updateEnableByIdsTest() {
        SortCrossUpdateRequest request = new SortCrossUpdateRequest();
        List<Long> ids = new ArrayList<Long>(Arrays.asList(2L,3L,4L,5L,6L,7L));
        request.setEnable(1);
        request.setIds(ids);
        Result<Boolean> result = sortCrossJsfService.updateEnableByIds(request);
    }
    
    @Test
    public void queryNotInitTest() {
        List<SortCrossDetail> details = sortCrossService.queryNotInit(500);
        System.out.println("总数：" + details.size());
        System.out.println(JsonHelper.toJSONString(details));
    }
    
    @Test
    public void getBaseSiteInfoBySiteIdTest() {
        BaseSiteInfoDto baseSiteInfo= basicSiteQueryWSManager.getBaseSiteInfoBySiteId(11282);
        System.out.println(JsonHelper.toJSONString(baseSiteInfo));
    }
    
    @Test
    public void updateByIdTest() {
        SortCrossDetail sortCrossDetail = new SortCrossDetail();
        sortCrossDetail.setId(2L);
        sortCrossDetail.setSiteType(4);
        sortCrossDetail.setSubType(4);
        sortCrossDetail.setThirdType(2);
        sortCrossService.initSiteTypeById(sortCrossDetail);
    }
    
    @Test
    public void initSortCrossTest() {
        System.out.println(sortCrossJsfService.initSortCross());
    }
    
}
