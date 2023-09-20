package com.jdl.basic.provider.core.service.workStation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.utils.EntityUtil;
import com.jdl.basic.common.utils.JsonHelper;


import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUser;
import com.jdl.basic.api.domain.workStation.WorkGridOwnerUserQuery;

/**
 * 场地网格-负责人表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年09月18日 22:43:58
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridOwnerUserServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridOwnerUserServiceTest.class);

	@Autowired
	@Qualifier("workGridOwnerUserService")
	private WorkGridOwnerUserService workGridOwnerUserService;

    @Test
    public void test(){
    	WorkGridOwnerUser insertData = EntityUtil.getInstance(WorkGridOwnerUser.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridOwnerUserService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridOwnerUserQuery query = new WorkGridOwnerUserQuery();
        Result<PageDto<WorkGridOwnerUser>> queryPageList = workGridOwnerUserService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridOwnerUser updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridOwnerUser> queryById = workGridOwnerUserService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridOwnerUserService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<WorkGridOwnerUser> queryByIdYn0 = workGridOwnerUserService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
