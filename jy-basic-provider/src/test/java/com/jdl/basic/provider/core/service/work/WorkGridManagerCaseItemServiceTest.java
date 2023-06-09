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
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItem;
import com.jdl.basic.api.domain.work.WorkGridManagerCaseItemQuery;

/**
 * 作业区巡检-项目明细表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年06月08日 10:54:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridManagerCaseItemServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridManagerCaseItemServiceTest.class);

	@Autowired
	@Qualifier("workGridManagerCaseItemService")
	private WorkGridManagerCaseItemService workGridManagerCaseItemService;

    @Test
    public void test(){
    	WorkGridManagerCaseItem insertData = EntityUtil.getInstance(WorkGridManagerCaseItem.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridManagerCaseItemService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridManagerCaseItemQuery query = new WorkGridManagerCaseItemQuery();
        Result<PageDto<WorkGridManagerCaseItem>> queryPageList = workGridManagerCaseItemService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridManagerCaseItem updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridManagerCaseItem> queryById = workGridManagerCaseItemService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridManagerCaseItemService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridManagerCaseItemService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridManagerCaseItem> queryByIdYn0 = workGridManagerCaseItemService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
