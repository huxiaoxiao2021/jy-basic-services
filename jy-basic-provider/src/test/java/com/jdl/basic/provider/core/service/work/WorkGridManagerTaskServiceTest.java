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
import com.jdl.basic.api.domain.work.WorkGridManagerTask;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskQuery;

/**
 * 作业区巡检任务表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridManagerTaskServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerTaskServiceTest.class);

	@Autowired
	@Qualifier("workGridManagerTaskService")
	private WorkGridManagerTaskService workGridManagerTaskService;

    @Test
    public void test(){
    	WorkGridManagerTask insertData = EntityUtil.getInstance(WorkGridManagerTask.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridManagerTaskService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridManagerTaskQuery query = new WorkGridManagerTaskQuery();
        Result<PageDto<WorkGridManagerTask>> queryPageList = workGridManagerTaskService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridManagerTask updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridManagerTask> queryById = workGridManagerTaskService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridManagerTaskService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridManagerTaskService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridManagerTask> queryByIdYn0 = workGridManagerTaskService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
