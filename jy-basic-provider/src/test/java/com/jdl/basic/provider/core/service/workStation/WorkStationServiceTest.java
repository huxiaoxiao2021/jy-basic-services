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

import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkStation;
import com.jdl.basic.api.domain.workStation.WorkStationQuery;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.utils.EntityUtil;

/**
 * 作业区信息表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkStationServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkStationServiceTest.class);

	@Autowired
	@Qualifier("workStationService")
	private WorkStationService workStationService;

	public static void main(String[] args){
        List<Field> fields = ObjectHelper.getAllFieldsList(WorkStation.class);
        StringBuffer sf = new StringBuffer();
        String sName = "workStation";
        String tName = "workArea";
        for(Field file : fields) {
        	String fileName = file.getName();
        	String mName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1,fileName.length());
        	sf.append("\t"+tName+".set"+mName+"("+sName+".get"+mName+"());\n");
        }
        System.err.println(sf);
	}
    @Test
    public void test(){
    	WorkStation insertData = EntityUtil.getInstance(WorkStation.class);
    	insertData.setBusinessLineCode("1");
    	insertData.setYn(1);
    	Result<Boolean> insert = workStationService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkStationQuery query = new WorkStationQuery();
        Result<PageDto<WorkStation>> queryPageList = workStationService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkStation updateData = queryPageList.getData().getResult().get(0);
        Result<WorkStation> queryById = workStationService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        workStationService.initWorkArea(updateData.getId());
        
        workStationService.initAllWorkArea();
        
        Result<Boolean> updateById = workStationService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workStationService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkStation> queryByIdYn0 = workStationService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
