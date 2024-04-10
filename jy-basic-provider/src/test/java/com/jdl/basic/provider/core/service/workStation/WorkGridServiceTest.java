package com.jdl.basic.provider.core.service.workStation;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
import com.jdl.basic.api.domain.workStation.WorkGridQuery;
import com.jdl.basic.api.domain.workStation.WorkGridVo;
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
public class WorkGridServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridServiceTest.class);

	@Autowired
	@Qualifier("workGridService")
	private WorkGridService workGridService;
	
	public static void main(String[] args){
        List<Field> fields = ObjectHelper.getAllFieldsList(WorkGrid.class);
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
    	WorkGrid insertData = EntityUtil.getInstance(WorkGrid.class);
    	insertData.setYn(1);
    	insertData.setId(null);
    	Result<Boolean> insert = workGridService.insert(insertData);
        Assert.assertTrue(insert != null && insert.getData());
        
        WorkGridQuery query = new WorkGridQuery();
        Result<PageDto<WorkGridVo>> queryPageList = workGridService.queryPageList(query);
        Assert.assertTrue(queryPageList != null && CollectionUtils.isNotEmpty(queryPageList.getData().getResult()));
        logger.info("queryPageList:{}"+JsonHelper.toJSONString(queryPageList));
        
        WorkGrid updateData = queryPageList.getData().getResult().get(0);
        Result<WorkGrid> queryById = workGridService.queryById(updateData.getId());
        Assert.assertTrue(queryById != null && queryById.getData() != null);
        workGridService.saveData(updateData);
        Result<Boolean> updateById = workGridService.updateById(updateData);
        Assert.assertTrue(updateById != null && updateById.getData());
        
        Result<Boolean> deleteById = workGridService.deleteById(updateData);
        Assert.assertTrue(deleteById != null && deleteById.getData());
        
        Result<WorkGrid> queryByIdYn0 = workGridService.queryById(updateData.getId());
        Assert.assertTrue(queryByIdYn0 != null && queryByIdYn0.getData() == null);
    }

    @Test
    public void batchQueryByWorkGridKeyTest() {
        List<String> workGridKeys = new ArrayList<>();
        workGridKeys.add("CDWG00000019007");
        workGridKeys.add("CDWG00000022001");
        workGridKeys.add("CDWG00000022002");
        List<WorkGrid> workGrids = workGridService.batchQueryByWorkGridKey(workGridKeys);
        logger.info("batchQueryByWorkGridKeyTest response {}", workGrids);
    }

    @Test
    public void queryAreaWorkGridTest() {
        WorkGridQuery query = new WorkGridQuery();
        query.setAreaCode("JBGQ");
        workGridService.queryAreaWorkGrid(query);
    }


    @Test
    public void queryAllGridBySiteCode() {
        WorkGridQuery query = new WorkGridQuery();
        query.setSiteCode(910);
        List<WorkGrid> result = workGridService.queryAllGridBySiteCode(query);
        logger.info("result {}", JsonHelper.toJSONString(result));
    }
    /**
     * 测试查询方法
     */
    @Test
    public void testQuery(){


        WorkGridQuery query = new WorkGridQuery();


        Result<PageDto<WorkGridVo>> queryPageList1 = workGridService.queryPageList(query);
        for (WorkGridVo workGridVo : queryPageList1.getData().getResult()) {
            Integer yn = workGridVo.getYn();
            System.out.println("删除"+ workGridVo.getId()+ ":"+ yn);
        }
        System.out.println("queryPageList222:{}"+JsonHelper.toJSONString(queryPageList1));
    }
}
