package com.jdl.basic.provider.dao.transferDp;

import com.alibaba.fastjson.JSON;
import com.jd.ql.basic.util.DateUtil;
import com.jdl.basic.api.domain.transferDp.ConfigTransferDpSite;
import com.jdl.basic.api.dto.transferDp.ConfigTransferDpSiteQo;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.dao.transferDp.ConfigTransferDpSiteDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * 交接至德邦配置单测
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class ConfigTransferDpSiteDaoTest {

    @Autowired
    private ConfigTransferDpSiteDao configTransferDpSiteDao;

    @Test
    public void queryListTest(){
        final ConfigTransferDpSiteQo configTransferDpSiteQo = new ConfigTransferDpSiteQo();
        configTransferDpSiteQo.setYn(1);
        configTransferDpSiteQo.setPageNumber(1);
        configTransferDpSiteQo.setPageSize(2);
        final List<ConfigTransferDpSite> dataList = configTransferDpSiteDao.queryList(configTransferDpSiteQo);
        System.out.println(JSON.toJSONString(dataList));
        Assert.assertTrue(true);
    }

    @Test
    public void insertTest(){
        try {
            final ConfigTransferDpSite configTransferDpSite = new ConfigTransferDpSite();
            configTransferDpSite.setHandoverOrgId(6);
            configTransferDpSite.setHandoverOrgName("总公司");
            configTransferDpSite.setHandoverSiteCode(910);
            configTransferDpSite.setHandoverSiteName("北京马驹桥分拣中心");
            configTransferDpSite.setPreSortSiteCode(39);
            configTransferDpSite.setPreSortSiteName("石景山营业部");
            configTransferDpSite.setConfigStatus(1);
            configTransferDpSite.setEffectiveStartTime(DateUtil.parse("2022-12-14 00:00:00", DateUtil.FORMAT_DATE_TIME));
            configTransferDpSite.setEffectiveStopTime(DateUtil.parse("2023-03-14 00:00:00", DateUtil.FORMAT_DATE_TIME));
            configTransferDpSite.setCreateUser("wuyoude");
            configTransferDpSite.setCreateUserName("吴有德");
            configTransferDpSite.setCreateTime(new Date());
            final int insert = configTransferDpSiteDao.insertSelective(configTransferDpSite);
            System.out.println(insert);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("error ", e);
            Assert.fail();
        }
    }

    @Test
    public void updateByPrimaryKeySelectiveTest(){
        try {
            final ConfigTransferDpSite configTransferDpSite = new ConfigTransferDpSite();
            configTransferDpSite.setId(1L);
            configTransferDpSite.setUpdateUser("xumigen");
            configTransferDpSite.setUpdateUserName("徐迷根");
            configTransferDpSite.setUpdateTime(new Date());
            final int update = configTransferDpSiteDao.updateByPrimaryKeySelective(configTransferDpSite);
            System.out.println(update);
            Assert.assertTrue(true);
        }catch (Exception e){
            log.error("error ", e);
            Assert.fail();
        }
    }
}
