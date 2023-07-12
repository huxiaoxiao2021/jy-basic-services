package com.jdl.basic.provider.core.service.site;

import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.ProvinceAgencyVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.dto.BasicPsStoreInfo;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类的描述
 *
 * @author hujiping
 * @date 2023/5/30 3:16 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class SiteServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(SiteServiceImplTest.class);
    
    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteQueryService siteQueryService;


    @Test
    public void queryProvinceAgencyInfoByCode() {
        try {
            String context = "100000";
            Result<ProvinceAgencyVO> result = siteQueryService.queryProvinceAgencyInfoByCode(context);

            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }
    
    @Test
    public void querySiteByConditionFromBasicSite() {
        try {
            SiteQueryCondition siteQueryCondition = new SiteQueryCondition();
//            siteQueryCondition.setSiteNamePym("xzz");
            siteQueryCondition.setSiteName("石景山");
            Result<List<BasicSiteVO>> result = siteQueryService.querySiteByConditionFromBasicSite(siteQueryCondition, 10);
            
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }

    @Test
    public void querySitePageByConditionFromBasicSite() {
        try {
            Pager<SiteQueryCondition> siteQueryPager = new Pager<>();
            siteQueryPager.setPageNo(1);
            siteQueryPager.setPageSize(10);
            SiteQueryCondition siteQueryCondition = new SiteQueryCondition();
            siteQueryPager.setSearchVo(siteQueryCondition);
//            siteQueryCondition.setSiteNamePym("xzz");
//            siteQueryCondition.setSiteCode(65478);
//            siteQueryCondition.setSiteName("测试仓库");
//            siteQueryCondition.setSubTypes(Lists.newArrayList(6420,6450));
//            siteQueryCondition.setProvinceAgencyCode("100000");
            siteQueryCondition.setSiteTypes(Lists.newArrayList(901,902,903,904,905,906));

            Result<Pager<BasicSiteVO>> result = siteQueryService.querySitePageByConditionFromBasicSite(siteQueryPager);
//            Result<Pager<BasicSiteVO>> result = null;
//            List<Integer> siteList = Lists.newArrayList();
//            
//            Integer pageNo = 1;
//            Integer pageSize = 100;
//            while (true){
//                siteQueryPager.setPageNo(pageNo);
//                siteQueryPager.setPageSize(pageSize);
//                result = siteQueryService.querySitePageByConditionFromBasicSite(siteQueryPager);
//                
//                if(CollectionUtils.isEmpty(result.getData().getData())){
//                    break;
//                }
//                List<Integer> single = result.getData().getData().stream().map(BasicSiteVO::getSiteCode).collect(Collectors.toList());
//                siteList.addAll(single);
//                pageNo ++;
//            }
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }
    
    @Test
    public void syncBasicSite() {
        try {
            // 初始化站点数据
            siteService.syncBasicSite();
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }

    @Test
    void syncAllWmsNew() {
        try {
            // 初始化仓站点数据
            siteService.syncAllWmsNew();
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }

    @Test
    void updateWmsDto() {
        try {
            // 更新仓数据
            BasicPsStoreInfo storeInfo = new BasicPsStoreInfo();
            
            siteService.updateWmsDto(storeInfo);
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }

    @Test
    void updateBasicSite() {
        try {
            // 更新站点数据
            BasicSiteChangeMQ basicSiteChangeMQ = new BasicSiteChangeMQ();
            
            siteService.updateBasicSite(basicSiteChangeMQ);
            Assert.assertTrue(true);
        }catch (Exception e){
            logger.error("服务异常!", e);
            Assert.fail();
        }
    }
}
