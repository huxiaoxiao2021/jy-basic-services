package com.jdl.basic.provider.boxFlow;

import com.jdl.basic.provider.ApplicationLaunch;
import com.jdl.basic.provider.core.provider.boxFlow.CollectBoxFlowDirectionVerifyJsfServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class CollectBoxFlowDirectionVerifyJsfServiceImplTest {

    @Autowired
    private CollectBoxFlowDirectionVerifyJsfServiceImpl collectBoxFlowDirectionVerifyJsfService;
    @Test
    public void handleTest(){
        try {
            collectBoxFlowDirectionVerifyJsfService.handle(null, null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        
    }
}
