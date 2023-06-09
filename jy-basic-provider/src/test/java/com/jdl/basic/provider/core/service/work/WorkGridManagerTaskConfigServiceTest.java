package com.jdl.basic.provider.core.service.work;

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
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfig;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigQuery;

/**
 * 作业区巡检任务配置表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridManagerTaskConfigServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerTaskConfigServiceTest.class);

	@Autowired
	@Qualifier("workGridManagerTaskConfigService")
	private WorkGridManagerTaskConfigService workGridManagerTaskConfigService;

    @Test
    public void test(){
    	WorkGridManagerTaskConfig insertData = EntityUtil.getInstance(WorkGridManagerTaskConfig.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridManagerTaskConfigService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridManagerTaskConfigQuery query = new WorkGridManagerTaskConfigQuery();
        Result<PageDto<WorkGridManagerTaskConfig>> queryPageList = workGridManagerTaskConfigService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridManagerTaskConfig updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridManagerTaskConfig> queryById = workGridManagerTaskConfigService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridManagerTaskConfigService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridManagerTaskConfigService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridManagerTaskConfig> queryByIdYn0 = workGridManagerTaskConfigService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
