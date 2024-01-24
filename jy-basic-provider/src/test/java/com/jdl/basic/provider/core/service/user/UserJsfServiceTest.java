package com.jdl.basic.provider.core.service.user;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.user.JyUserDto;
import com.jdl.basic.api.domain.user.JyUserQueryDto;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
public class UserJsfServiceTest {

    @Autowired
    private UserJsfService userJsfService;

    @Test
    public void getUserByUserCode() {
        JyUserQueryDto condition = new JyUserQueryDto();
        condition.setUserCode("130102196909025547");
        Result<JyUserDto> result = userJsfService.getUserByUserCode(condition);
        log.info("result {}", JsonHelper.toJSONString(result));
    }
}
