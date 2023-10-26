package com.jdl.basic.provider.core.provider.workStation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jdl.basic.api.service.workStation.WorkGridJsfService;
import com.jdl.basic.provider.ApplicationLaunch;

/**
 * 场地网格表--Service测试用例
 * 
 * @author wuyoude
 * @date 2023年04月25日 00:18:56
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class WorkGridJsfServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(WorkGridJsfServiceTest.class);

	@Autowired
	@Qualifier("workGridJsfService")
	private WorkGridJsfService workGridJsfService;
	
    @Test
    public void test(){
    	List<Long> ids = new ArrayList<>();
    	ids.add(1000L);
    	//workGridJsfService.initAllWorkGrid(ids);
    	ids.clear();
    	//workGridJsfService.initAllWorkGrid(ids);
    }
}
