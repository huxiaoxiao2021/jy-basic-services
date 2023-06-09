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
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigArea;
import com.jdl.basic.api.domain.work.WorkGridManagerTaskConfigAreaQuery;

/**
 * 作业区巡检任务配置-作业区表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridManagerTaskConfigAreaServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerTaskConfigAreaServiceTest.class);

	@Autowired
	@Qualifier("workGridManagerTaskConfigAreaService")
	private WorkGridManagerTaskConfigAreaService workGridManagerTaskConfigAreaService;

    @Test
    public void test(){
    	WorkGridManagerTaskConfigArea insertData = EntityUtil.getInstance(WorkGridManagerTaskConfigArea.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridManagerTaskConfigAreaService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridManagerTaskConfigAreaQuery query = new WorkGridManagerTaskConfigAreaQuery();
        Result<PageDto<WorkGridManagerTaskConfigArea>> queryPageList = workGridManagerTaskConfigAreaService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridManagerTaskConfigArea updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridManagerTaskConfigArea> queryById = workGridManagerTaskConfigAreaService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridManagerTaskConfigAreaService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridManagerTaskConfigAreaService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridManagerTaskConfigArea> queryByIdYn0 = workGridManagerTaskConfigAreaService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
