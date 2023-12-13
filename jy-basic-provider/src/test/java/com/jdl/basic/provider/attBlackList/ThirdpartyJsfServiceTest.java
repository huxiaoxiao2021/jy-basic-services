package com.jdl.basic.provider.attBlackList;

import com.jd.dms.java.utils.sdk.base.Result;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.domain.user.JyThirdpartyUser;
import com.jdl.basic.api.domain.user.JyThirdpartyUserSaveDto;
import com.jdl.basic.api.service.attBlackList.AttendanceBlackListJsfService;
import com.jdl.basic.api.service.user.UserJsfService;
import com.jdl.basic.common.utils.DateUtil;
import com.jdl.basic.common.utils.JsonHelper;
import com.jdl.basic.provider.ApplicationLaunch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationLaunch.class)
@Slf4j
public class ThirdpartyJsfServiceTest {

    @Autowired
    private UserJsfService userJsfService;



    @Test
    public void insertTest() {
        JyThirdpartyUser jyThirdpartyUser =new JyThirdpartyUser();
        jyThirdpartyUser.setUserCode("13112311111111");
        jyThirdpartyUser.setUserName("zhangsan");
        jyThirdpartyUser.setMobile("18311112222");
        jyThirdpartyUser.setPresentDate(new Date());
        jyThirdpartyUser.setTaskBizId("JTU1730177157730959360");
        jyThirdpartyUser.setTaskDetailBizId("JTU1730177157730959366");
        jyThirdpartyUser.setNature("外包工");
        jyThirdpartyUser.setNatureDesc("外包工");
        jyThirdpartyUser.setSiteCode(1);
        jyThirdpartyUser.setDeadlineTime(new Date());
        jyThirdpartyUser.setCompanyName("人瑞");
        jyThirdpartyUser.setCreateUser("zhangsan");
        jyThirdpartyUser.setCreateUserName("lisi");
        jyThirdpartyUser.setUpdateUser("wangwu");
        jyThirdpartyUser.setUpdateUserName("zhangliu");


        JyThirdpartyUser jyThirdpartyUser1 =new JyThirdpartyUser();
        jyThirdpartyUser1.setUserCode("13112322222222");
        jyThirdpartyUser1.setUserName("lissss");
        jyThirdpartyUser1.setMobile("18311112222");
        jyThirdpartyUser1.setPresentDate(new Date());
        jyThirdpartyUser1.setTaskBizId("JTU1730177157730951234");
        jyThirdpartyUser1.setTaskDetailBizId("JTU1730177157730954321");
        jyThirdpartyUser1.setNature("外包工");
        jyThirdpartyUser1.setNatureDesc("外包工");
        jyThirdpartyUser1.setSiteCode(1);
        jyThirdpartyUser1.setDeadlineTime(new Date());
        jyThirdpartyUser1.setCompanyName("人瑞");
        jyThirdpartyUser1.setCreateUser("zhangsan");
        jyThirdpartyUser1.setCreateUserName("lisi");
        jyThirdpartyUser1.setUpdateUser("wangwu");
        jyThirdpartyUser1.setUpdateUserName("zhangliu");

        //userJsfService.addJyThirdpartyUserOne(jyThirdpartyUser);
        JyThirdpartyUserSaveDto dto =new JyThirdpartyUserSaveDto();
        List<JyThirdpartyUser> list=new ArrayList<>();
        dto.setJyThirdpartyUserList(list);
        userJsfService.saveJyThirdpartyUser(dto);
    }

    @Test
    public void query() {JyThirdpartyUser jyThirdpartyUser =new JyThirdpartyUser();
        jyThirdpartyUser.setSiteCode(1);
        Result<List<JyThirdpartyUser>> rs=userJsfService.queryJyThirdpartyUserByCondition(jyThirdpartyUser);
        JyThirdpartyUser jyThirdpartyUser1 =new JyThirdpartyUser();
        jyThirdpartyUser1.setSiteCode(1);
        jyThirdpartyUser1.setYn(false);
        userJsfService.updateJyThirdpartyUserYn(jyThirdpartyUser1);
    }

}
