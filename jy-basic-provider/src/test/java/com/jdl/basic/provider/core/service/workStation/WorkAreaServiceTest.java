package com.jdl.basic.provider.core.service.workStation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.utils.EntityUtil;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.common.utils.ObjectHelper;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.api.domain.workStation.WorkArea;
import com.jdl.basic.api.domain.workStation.WorkAreaQuery;

/**
 * 作业区信息表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkAreaServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkAreaServiceTest.class);

	@Autowired
	@Qualifier("workAreaService")
	private WorkAreaService workAreaService;

	public static void main(String[] args){
        List<Field> fields = ObjectHelper.getAllFieldsList(WorkArea.class);
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
    	WorkArea insertData = EntityUtil.getInstance(WorkArea.class);
    	insertData.setBusinessLineCode("1");
    	insertData.setYn(1);
    	Result<Boolean> insert = workAreaService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkAreaQuery query = new WorkAreaQuery();
        Result<PageDto<WorkArea>> queryPageList = workAreaService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkArea updateData = queryPageList.getData().getResult().get(0);
        Result<WorkArea> queryById = workAreaService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        
        workAreaService.saveData(updateData);
        
        Result<Boolean> updateById = workAreaService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workAreaService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkArea> queryByIdYn0 = workAreaService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

}
