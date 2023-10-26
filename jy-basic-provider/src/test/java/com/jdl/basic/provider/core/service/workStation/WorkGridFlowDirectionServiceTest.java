package com.jdl.basic.provider.core.service.workStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jdl.basic.api.domain.workStation.WorkGridFlowDirection;
import com.jdl.basic.api.domain.workStation.WorkGridFlowDirectionQuery;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.utils.EntityUtil;

/**
 * 场地网格流向表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridFlowDirectionServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridFlowDirectionServiceTest.class);

	@Autowired
	@Qualifier("workGridFlowDirectionService")
	private WorkGridFlowDirectionService workGridFlowDirectionService;

    @Test
    public void test(){
    	WorkGridFlowDirection insertData = EntityUtil.getInstance(WorkGridFlowDirection.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workGridFlowDirectionService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridFlowDirectionQuery query = new WorkGridFlowDirectionQuery();
        Result<PageDto<WorkGridFlowDirection>> queryPageList = workGridFlowDirectionService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGridFlowDirection updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGridFlowDirection> queryById = workGridFlowDirectionService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workGridFlowDirectionService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridFlowDirectionService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGridFlowDirection> queryByIdYn0 = workGridFlowDirectionService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
        
        WorkGridFlowDirection deleteData = new WorkGridFlowDirection();
        deleteData.setRefWorkGridKey(updateData.getRefWorkGridKey());
        deleteData.setUpdateTime(new Date());
        deleteData.setUpdateUser("updateUser");
        workGridFlowDirectionService.deleteByRefGridKey(deleteData);
    }
    @Test
    public void queryFlowDirectionByConditionTest(){
        List<String> refWorkGridKeyList = Arrays.asList("CDWG00000061007", "CDWG00000061008");
        
        List<Integer> lineTypeList = Arrays.asList(1,2,3,4);
        Integer flowDirectionType = 2;
        Integer flowSiteCode = 38;
        String refWorkGridKey = workGridFlowDirectionService.queryFlowDirectionByCondition(refWorkGridKeyList, lineTypeList, flowDirectionType,
                flowSiteCode);
        logger.info("refWorkGridKey={}",refWorkGridKey);
    }

}
