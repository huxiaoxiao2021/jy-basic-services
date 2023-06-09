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
import com.jdl.basic.api.domain.work.WorkGridManagerCase;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseQuery;

/**
 * 作业区巡检场景表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridManagerCaseServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerCaseServiceTest.class);

	@Autowired
	@Qualifier("workGridManagerCaseService")
	private WorkGridManagerCaseService workGridManagerCaseService;

    @Test
    public void test(){
    	WorkGridManagerCase insertData = EntityUtil.getInstance(WorkGridManagerCase.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridManagerCaseService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridManagerCaseQuery query = new WorkGridManagerCaseQuery();
        Result<PageDto<WorkGridManagerCase>> queryPageList = workGridManagerCaseService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridManagerCase updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridManagerCase> queryById = workGridManagerCaseService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridManagerCaseService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridManagerCaseService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridManagerCase> queryByIdYn0 = workGridManagerCaseService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
