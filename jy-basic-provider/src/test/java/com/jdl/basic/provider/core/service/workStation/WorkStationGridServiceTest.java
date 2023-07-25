package com.jdl.basic.provider.core.service.workStation;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jdl.basic.api.domain.workStation.WorkGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGrid;
import com.jdl.basic.api.domain.workStation.WorkStationGridQuery;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.utils.EntityUtil;

/**
 * 场地网格表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationGridServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkStationGridServiceTest.class);

	@Autowired
	@Qualifier("workStationGridService")
	private WorkStationGridService workStationGridService;
	
	public static void main(String[] args){
        List<Field> fields = ObjectHelper.getAllFieldsList(WorkStationGrid.class);
        StringBuffer sf = new StringBuffer();
        String sName = "workGrid";
        String tName = "updateData";
        for(Field file : fields) {
        	String fileName = file.getName();
        	String mName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1,fileName.length());
        	sf.append("\t"+tName+".set"+mName+"("+sName+".get"+mName+"());\n");
        }
        System.err.println(sf);
	}
	
    @Test
    public void test(){
    	WorkStationGrid insertData = EntityUtil.getInstance(WorkStationGrid.class);
    	insertData.setYn(1);
    	Result<Boolean> insert = workStationGridService.insert(insertData);
        Assert.assertTrue(insert != null);
        
        WorkStationGridQuery query = new WorkStationGridQuery();
        Result<PageDto<WorkStationGrid>> queryPageList = workStationGridService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkStationGrid updateData = queryPageList.getData().getResult().get(0);
        workStationGridService.initWorkGrid(updateData.getId());
        
        workStationGridService.initAllWorkGrid();
        Result<WorkStationGrid> queryById = workStationGridService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        Result<Boolean> updateById = workStationGridService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workStationGridService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkStationGrid> queryByIdYn0 = workStationGridService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
