package com.jdl.basic.provider.core.service.site;

import com.jdl.basic.api.dto.site.BasicSiteVO;
import com.jdl.basic.api.dto.site.SiteQueryCondition;
import com.jdl.basic.api.service.site.SiteQueryService;
import com.jdl.basic.common.utils.Pager;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.dto.BasicPsStoreInfo;
import com.jdl.basic.provider.dto.BasicSiteChangeMQ;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    public void querySiteByConditionFromBasicSite() {
        try {
            SiteQueryCondition siteQueryCondition = new SiteQueryCondition();
            siteQueryCondition.setSiteNamePym("xzz");
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
            siteQueryCondition.setSiteNamePym("xzz");

            Result<Pager<BasicSiteVO>> result = siteQueryService.querySitePageByConditionFromBasicSite(siteQueryPager);
            
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